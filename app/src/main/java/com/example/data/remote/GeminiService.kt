package com.example.data.remote

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.util.Base64
import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

data class GroundingSource(
    val title: String,
    val uri: String
)

data class GroundingResult(
    val text: String,
    val sources: List<GroundingSource> = emptyList(),
    val searchQueries: List<String> = emptyList(),
    val mapsPlaces: List<String> = emptyList()
)

data class MusicGenerationResult(
    val audioFilePath: String?,
    val description: String,
    val mimeType: String = "audio/mp3"
)

data class VideoGenerationResult(
    val operationName: String?,
    val statusText: String,
    val videoUri: String? = null
)

class GeminiService(private val context: Context) {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val apiKey: String
        get() = BuildConfig.GEMINI_API_KEY

    // Helper: Convert Bitmap to Base64
    fun bitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return Base64.encodeToString(stream.toByteArray(), Base64.NO_WRAP)
    }

    // 1. Google Search Grounding with gemini-3.5-flash
    suspend fun searchGrounding(query: String): Result<GroundingResult> = withContext(Dispatchers.IO) {
        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val bodyJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "Analisis secara mendalam dan berikan data akurat serta etimologi terpercaya: $query"))
                        })
                    })
                })
                put("tools", JSONArray().apply {
                    put(JSONObject().apply {
                        put("googleSearch", JSONObject())
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(bodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Search Grounding API error (${response.code}): $responseBody"))
            }

            val root = JSONObject(responseBody)
            val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
            val text = candidate?.optJSONObject("content")
                ?.optJSONArray("parts")?.optJSONObject(0)
                ?.optString("text") ?: "Tidak ada respons teks."

            val sources = mutableListOf<GroundingSource>()
            val searchQueries = mutableListOf<String>()

            val groundingMetadata = candidate?.optJSONObject("groundingMetadata")
            if (groundingMetadata != null) {
                val queries = groundingMetadata.optJSONArray("webSearchQueries")
                if (queries != null) {
                    for (i in 0 until queries.length()) {
                        searchQueries.add(queries.optString(i))
                    }
                }

                val chunks = groundingMetadata.optJSONArray("groundingChunks")
                if (chunks != null) {
                    for (i in 0 until chunks.length()) {
                        val web = chunks.optJSONObject(i)?.optJSONObject("web")
                        if (web != null) {
                            sources.add(
                                GroundingSource(
                                    title = web.optString("title", "Sumber Referensi Web"),
                                    uri = web.optString("uri", "")
                                )
                            )
                        }
                    }
                }
            }

            Result.success(
                GroundingResult(
                    text = text,
                    sources = sources,
                    searchQueries = searchQueries
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 1b. Translate Indonesian text to English with language learning insights for international users
    suspend fun translateIndonesianToEnglish(text: String, contextHint: String = ""): Result<String> = withContext(Dispatchers.IO) {
        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"
            val prompt = """
                You are an expert bilingual Indonesian-English translator and language tutor for an Indonesian detective mystery game called Kata Nusantara.
                Translate the following Indonesian text into clear, natural English for international players.
                Provide brief vocabulary notes (word meanings and grammar notes) so English-speaking learners can understand and learn Indonesian from this game context.
                
                Context: $contextHint
                Text to translate:
                $text
                
                Format your output nicely with:
                🇬🇧 English Translation:
                [Translation here]
                
                📖 Key Indonesian Vocabulary & Notes:
                - [Word] = [Meaning / Usage]
            """.trimIndent()

            val bodyJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(bodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Translation API error (${response.code}): $responseBody"))
            }

            val root = JSONObject(responseBody)
            val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
            val translatedText = candidate?.optJSONObject("content")
                ?.optJSONArray("parts")?.optJSONObject(0)
                ?.optString("text") ?: "Translation completed."

            Result.success(translatedText)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 2. Google Maps Grounding with gemini-3.5-flash
    suspend fun mapsGrounding(locationQuery: String): Result<GroundingResult> = withContext(Dispatchers.IO) {
        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val bodyJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", "Berikan profil geospasial mendalam, lokasi persis, titik penting, dan konteks sejarah budaya untuk lokasi kejahatan kasus: $locationQuery"))
                        })
                    })
                })
                put("tools", JSONArray().apply {
                    put(JSONObject().apply {
                        put("googleMaps", JSONObject())
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(bodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Maps Grounding API error (${response.code}): $responseBody"))
            }

            val root = JSONObject(responseBody)
            val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
            val text = candidate?.optJSONObject("content")
                ?.optJSONArray("parts")?.optJSONObject(0)
                ?.optString("text") ?: "Tidak ada respons lokasi."

            val places = mutableListOf<String>()
            val groundingMetadata = candidate?.optJSONObject("groundingMetadata")
            if (groundingMetadata != null) {
                val chunks = groundingMetadata.optJSONArray("groundingChunks")
                if (chunks != null) {
                    for (i in 0 until chunks.length()) {
                        val mapObj = chunks.optJSONObject(i)?.optJSONObject("maps")
                        if (mapObj != null) {
                            val placeTitle = mapObj.optString("title")
                            if (placeTitle.isNotEmpty()) places.add(placeTitle)
                        }
                    }
                }
            }

            Result.success(
                GroundingResult(
                    text = text,
                    mapsPlaces = places
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 3. Music Generation with lyria-3-clip-preview (up to 30s) or lyria-3-pro-preview
    suspend fun generateMusic(
        prompt: String,
        useFullTrack: Boolean = false
    ): Result<MusicGenerationResult> = withContext(Dispatchers.IO) {
        try {
            val modelName = if (useFullTrack) "lyria-3-pro-preview" else "lyria-3-clip-preview"
            val url = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"

            val bodyJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("responseModalities", JSONArray().apply {
                        put("AUDIO")
                    })
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(bodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Music API ($modelName) error (${response.code}): $responseBody"))
            }

            val root = JSONObject(responseBody)
            val candidate = root.optJSONArray("candidates")?.optJSONObject(0)
            val parts = candidate?.optJSONObject("content")?.optJSONArray("parts")

            var savedPath: String? = null
            var mimeType = "audio/mp3"

            if (parts != null) {
                for (i in 0 until parts.length()) {
                    val part = parts.optJSONObject(i)
                    val inlineData = part?.optJSONObject("inlineData")
                    if (inlineData != null) {
                        mimeType = inlineData.optString("mimeType", "audio/mp3")
                        val base64Data = inlineData.optString("data", "")
                        if (base64Data.isNotEmpty()) {
                            val audioBytes = Base64.decode(base64Data, Base64.DEFAULT)
                            val fileExt = if (mimeType.contains("wav")) "wav" else "mp3"
                            val audioFile = File(context.cacheDir, "nusantara_music_${System.currentTimeMillis()}.$fileExt")
                            FileOutputStream(audioFile).use { it.write(audioBytes) }
                            savedPath = audioFile.absolutePath
                            break
                        }
                    }
                }
            }

            Result.success(
                MusicGenerationResult(
                    audioFilePath = savedPath,
                    description = "Musik investigasi Nusantara berhasil dibuat (${if (useFullTrack) "Full Track" else "Clip 30s"})",
                    mimeType = mimeType
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 4. Video Generation with veo-3.1-fast-generate-preview (aspect ratio 16:9 or 9:16)
    suspend fun generateVideo(
        prompt: String,
        bitmapImage: Bitmap?,
        aspectRatio: String = "16:9" // "16:9" or "9:16"
    ): Result<VideoGenerationResult> = withContext(Dispatchers.IO) {
        try {
            val url = "https://generativelanguage.googleapis.com/v1beta/models/veo-3.1-fast-generate-preview:generateVideos?key=$apiKey"

            val bodyJson = JSONObject().apply {
                put("prompt", prompt)
                if (bitmapImage != null) {
                    put("image", JSONObject().apply {
                        put("bytesBase64Encoded", bitmapToBase64(bitmapImage))
                        put("mimeType", "image/jpeg")
                    })
                }
                put("config", JSONObject().apply {
                    put("numberOfVideos", 1)
                    put("aspectRatio", if (aspectRatio == "9:16") "9:16" else "16:9")
                    put("resolution", "720p")
                })
            }

            val request = Request.Builder()
                .url(url)
                .post(bodyJson.toString().toRequestBody("application/json".toMediaType()))
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                return@withContext Result.failure(Exception("Veo Video API error (${response.code}): $responseBody"))
            }

            val root = JSONObject(responseBody)
            val operationName = root.optString("name", "")

            Result.success(
                VideoGenerationResult(
                    operationName = operationName,
                    statusText = if (operationName.isNotEmpty()) "Permintaan rekonstruksi video Veo 3.1 berhasil dikirim ($operationName)" else "Video sedang diproses...",
                    videoUri = null
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
