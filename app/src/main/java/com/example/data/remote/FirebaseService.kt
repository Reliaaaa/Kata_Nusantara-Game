package com.example.data.remote

import android.content.Context
import android.util.Log
import com.example.data.local.entity.CaseProgressEntity
import com.example.data.local.entity.UserProgressEntity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class UserCloudProfile(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val photoUrl: String?,
    val isAnonymous: Boolean = false
)

class FirebaseService(private val context: Context) {

    private val isFirebaseAvailable: Boolean by lazy {
        try {
            FirebaseApp.initializeApp(context)
            true
        } catch (e: Exception) {
            Log.w("FirebaseService", "FirebaseApp init warning: ${e.message}")
            // Check if default app exists
            try {
                FirebaseApp.getInstance() != null
            } catch (ex: Exception) {
                false
            }
        }
    }

    private val auth: FirebaseAuth?
        get() = try {
            if (isFirebaseAvailable) FirebaseAuth.getInstance() else null
        } catch (e: Exception) {
            null
        }

    private val firestore: FirebaseFirestore?
        get() = try {
            if (isFirebaseAvailable) FirebaseFirestore.getInstance() else null
        } catch (e: Exception) {
            null
        }

    fun getCurrentUser(): UserCloudProfile? {
        val user = auth?.currentUser ?: return null
        return UserCloudProfile(
            uid = user.uid,
            displayName = user.displayName ?: "Detektif Nusantara",
            email = user.email ?: "detektif@nusantara.id",
            photoUrl = user.photoUrl?.toString(),
            isAnonymous = user.isAnonymous
        )
    }

    suspend fun signInAnonymously(): Result<UserCloudProfile> = withContext(Dispatchers.IO) {
        try {
            val authInstance = auth ?: return@withContext Result.failure(Exception("Firebase Auth belum diinisialisasi (cek google-services.json)"))
            val result = authInstance.signInAnonymously().await()
            val user = result.user ?: return@withContext Result.failure(Exception("Pengguna tidak ditemukan"))
            Result.success(
                UserCloudProfile(
                    uid = user.uid,
                    displayName = "Detektif Tamu",
                    email = null,
                    photoUrl = null,
                    isAnonymous = true
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signOut() {
        try {
            auth?.signOut()
        } catch (e: Exception) {
            Log.e("FirebaseService", "Sign out error", e)
        }
    }

    // Save Detective Progress to Cloud Firestore
    suspend fun saveProgressToCloud(
        userProgress: UserProgressEntity,
        caseProgressList: List<CaseProgressEntity>
    ): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val user = auth?.currentUser
            val uid = user?.uid ?: "local_detective"
            val db = firestore ?: return@withContext Result.failure(Exception("Cloud Firestore belum aktif."))

            val data = hashMapOf(
                "xp" to userProgress.xp,
                "totalScore" to userProgress.totalScore,
                "currentRankLevel" to userProgress.currentRankLevel,
                "casesSolvedCount" to userProgress.casesSolvedCount,
                "hintsAvailable" to userProgress.hintsAvailable,
                "lastUpdated" to System.currentTimeMillis(),
                "cases" to caseProgressList.map {
                    mapOf(
                        "caseId" to it.caseId,
                        "isCompleted" to it.isCompleted,
                        "starsEarned" to it.starsEarned,
                        "bestScore" to it.bestScore
                    )
                }
            )

            db.collection("detectives").document(uid).set(data, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Load Detective Progress from Cloud Firestore
    suspend fun loadProgressFromCloud(): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            val user = auth?.currentUser
            val uid = user?.uid ?: "local_detective"
            val db = firestore ?: return@withContext Result.failure(Exception("Cloud Firestore belum aktif."))

            val snapshot = db.collection("detectives").document(uid).get().await()
            if (snapshot.exists()) {
                Result.success(snapshot.data ?: emptyMap())
            } else {
                Result.failure(Exception("Data detektif belum ditemukan di Cloud Firestore."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
