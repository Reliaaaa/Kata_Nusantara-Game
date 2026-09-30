package com.example.ui.screens

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.ParchmentCard
import com.example.ui.components.WaxSealStamp
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldBright
import com.example.ui.theme.InkBrown
import com.example.ui.theme.InkDark
import com.example.ui.theme.InkMuted
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.VideoUiState

@Composable
fun VeoVideoScreen(
    videoState: VideoUiState,
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var customPrompt by remember { mutableStateOf(videoState.prompt) }
    var selectedAspectRatio by remember { mutableStateOf(videoState.aspectRatio) }

    // Zero-permission modern Android Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    viewModel.setVideoBitmap(bitmap)
                }
            } catch (e: Exception) {
                // handle error
            }
        }
    }

    val videoPresets = listOf(
        "Kamera melayang pelan menyorot lorong Bale Prabeyo yang temaram saat surat wasiat diambil",
        "Detik-detik perahu kecil perlahan mundur menjauhi dermaga sungai Indragiri",
        "Angin senja menerpa gerbang Candi Bentar Besakih berpadu kabut mistis pegunungan",
        "Lentera minyak bergetar menampakkan bayangan sosok misterius di loteng Rumah Gadang"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("video_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = AntiqueGold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Surface(
                    color = CorkBoard,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.5.dp, AntiqueGold)
                ) {
                    Text(
                        text = "REKONSTRUKSI VEO 3.1",
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        letterSpacing = 1.5.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(40.dp))
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Banner / Description
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = WoodBoard),
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Movie,
                                    contentDescription = null,
                                    tint = AntiqueGold
                                )
                                Text(
                                    text = "Animasi Foto Menjadi Video (Veo 3.1 Fast)",
                                    color = GoldBright,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                            Text(
                                text = "Unggah foto bukti/TKP atau pilih foto arsip, tentukan aspek rasio (16:9 atau 9:16), dan hasilkan video animasi rekonstruksi TKP dengan veo-3.1-fast-generate-preview.",
                                color = ParchmentLight.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                // Photo Selection
                item {
                    Text(
                        text = "FOTO SUMBER REKONSTRUKSI:",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Current Image Preview Box
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CorkBoard)
                                .border(1.5.dp, AntiqueGold, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (videoState.selectedBitmap != null) {
                                Image(
                                    bitmap = videoState.selectedBitmap.asImageBitmap(),
                                    contentDescription = "Foto Terpilih",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.img_keraton_case),
                                    contentDescription = "Default TKP",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                        // Upload button & Preset options
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .testTag("upload_photo_veo_btn"),
                                colors = ButtonDefaults.buttonColors(containerColor = WoodBoard),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, AntiqueGold)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.AddPhotoAlternate, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(16.dp))
                                    Text(text = "UNGGAH FOTO DARI HP", color = ParchmentLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            OutlinedButton(
                                onClick = {
                                    val bmp = BitmapFactory.decodeResource(context.resources, R.drawable.img_keraton_case)
                                    viewModel.setVideoBitmap(bmp)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp),
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, CardBorder)
                            ) {
                                Text(text = "Pakai Foto Keraton", color = AntiqueGold, fontSize = 11.sp)
                            }
                        }
                    }
                }

                // Aspect Ratio Selector (16:9 vs 9:16)
                item {
                    Text(
                        text = "ASPEK RASIO VIDEO:",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedAspectRatio = "16:9" },
                            color = if (selectedAspectRatio == "16:9") AntiqueGold else WoodBoard,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (selectedAspectRatio == "16:9") GoldBright else CardBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "16:9 (Landscape)",
                                    color = if (selectedAspectRatio == "16:9") InkDark else ParchmentLight,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Format Layar Lebar",
                                    color = if (selectedAspectRatio == "16:9") InkBrown else InkMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedAspectRatio = "9:16" },
                            color = if (selectedAspectRatio == "9:16") AntiqueGold else WoodBoard,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (selectedAspectRatio == "9:16") GoldBright else CardBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "9:16 (Portrait)",
                                    color = if (selectedAspectRatio == "9:16") InkDark else ParchmentLight,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Text(
                                    text = "Format Vertikal Mobile",
                                    color = if (selectedAspectRatio == "9:16") InkBrown else InkMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }

                // Presets
                item {
                    Text(
                        text = "SKENARIO ANIMASI REKONSTRUKSI:",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(videoPresets) { preset ->
                            FilterChip(
                                selected = customPrompt == preset,
                                onClick = { customPrompt = preset },
                                label = { Text(text = preset, fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AntiqueGold,
                                    selectedLabelColor = InkDark,
                                    containerColor = WoodBoard,
                                    labelColor = ParchmentLight
                                )
                            )
                        }
                    }
                }

                // Prompt Input
                item {
                    OutlinedTextField(
                        value = customPrompt,
                        onValueChange = { customPrompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("veo_prompt_input"),
                        label = { Text("Prompt Aksi Animasi Rekonstruksi", color = AntiqueGold, fontSize = 11.sp) },
                        minLines = 2,
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AntiqueGold,
                            unfocusedBorderColor = CardBorder,
                            focusedTextColor = ParchmentLight,
                            unfocusedTextColor = ParchmentLight,
                            focusedContainerColor = WoodBoard,
                            unfocusedContainerColor = WoodBoard
                        )
                    )
                }

                // Generate Button
                item {
                    Button(
                        onClick = {
                            viewModel.generateVideo(customPrompt, selectedAspectRatio)
                        },
                        enabled = customPrompt.isNotBlank() && !videoState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("generate_veo_video_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (videoState.isLoading) {
                            CircularProgressIndicator(color = InkDark, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = "Mengolah Video Veo 3.1 Fast...", color = InkDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.VideoCameraBack, contentDescription = null, tint = InkDark, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "HASILKAN VIDEO REKONSTRUKSI VEO",
                                    color = InkDark,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                // Result Card
                if (videoState.result != null) {
                    val result = videoState.result
                    item {
                        ParchmentCard(
                            modifier = Modifier.fillMaxWidth(),
                            hasPin = true
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                WaxSealStamp(text = "REKONSTRUKSI AKTIF", stampColor = EmeraldGreen, rotation = 0f)

                                Text(
                                    text = result.statusText,
                                    color = InkDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )

                                if (result.operationName != null) {
                                    Text(
                                        text = "ID Operasi: ${result.operationName}",
                                        color = InkMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }
                }

                if (videoState.errorMessage != null) {
                    item {
                        Surface(
                            color = CrimsonRed.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, CrimsonRed),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = videoState.errorMessage,
                                color = Color(0xFFFF8A80),
                                fontSize = 11.sp,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
