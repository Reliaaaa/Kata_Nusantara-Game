package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.viewmodel.MusicUiState

@Composable
fun MusicStudioScreen(
    musicState: MusicUiState,
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    BackHandler {
        viewModel.stopAudio()
        onBack()
    }

    var customPrompt by remember { mutableStateOf(musicState.prompt) }
    var useFullTrack by remember { mutableStateOf(musicState.useFullTrack) }

    val musicPresets = listOf(
        "Gamelan Jawa mistis berpadu rebab malam hari untuk investigasi Keraton",
        "Rentak gambus Melayu dan irama pantun misterius di tepian sungai Indragiri",
        "Kidung sakral Dewata Bali dengan seruling bambu dan genta pura",
        "Dendang saluang kelam berlatar ukiran kayu Rumah Gadang Minangkabau",
        "Suasana tegang detektif menyingkap dokumen rahasia bertabur ketukan kendang"
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
                    onClick = {
                        viewModel.stopAudio()
                        onBack()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("music_back_btn")
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
                        text = "STUDIO MUSIK NUSANTARA",
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
                // Info Card
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
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = null,
                                    tint = AntiqueGold
                                )
                                Text(
                                    text = "Generator Musik Investigasi Lyria",
                                    color = GoldBright,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                            Text(
                                text = "Hadirkan musik latar tradisional Nusantara bernuansa detektif menggunakan model Lyria 3 (lyria-3-clip-preview / lyria-3-pro-preview).",
                                color = ParchmentLight.copy(alpha = 0.8f),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                // Configuration: Clip vs Full Track
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = WoodBoard),
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = if (useFullTrack) "Full Track (lyria-3-pro-preview)" else "Short Clip 30s (lyria-3-clip-preview)",
                                    color = ParchmentLight,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = if (useFullTrack) "Trek instrumen durasi penuh" else "Klip musik pendek berdurasi hingga 30 detik",
                                    color = AntiqueGold,
                                    fontSize = 10.sp
                                )
                            }

                            Switch(
                                checked = useFullTrack,
                                onCheckedChange = { useFullTrack = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = AntiqueGold,
                                    checkedTrackColor = CorkBoard,
                                    uncheckedThumbColor = Color.Gray,
                                    uncheckedTrackColor = WoodDark
                                )
                            )
                        }
                    }
                }

                // Preset Chips
                item {
                    Text(
                        text = "TEMA MUSIK DAERAH:",
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
                        items(musicPresets) { preset ->
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
                            .testTag("music_prompt_input"),
                        label = { Text("Deskripsi Instrumen Musik", color = AntiqueGold, fontSize = 11.sp) },
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
                            viewModel.generateMusic(customPrompt, useFullTrack)
                        },
                        enabled = customPrompt.isNotBlank() && !musicState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("generate_music_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (musicState.isLoading) {
                            CircularProgressIndicator(color = InkDark, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(text = "Meramu Suara Nusantara (Lyria 3)...", color = InkDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(imageVector = Icons.Default.GraphicEq, contentDescription = null, tint = InkDark, modifier = Modifier.size(18.dp))
                                Text(
                                    text = "HASILKAN MUSIK TRADISIONAL",
                                    color = InkDark,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                // Player Card
                if (musicState.currentResult != null) {
                    val result = musicState.currentResult
                    item {
                        ParchmentCard(
                            modifier = Modifier.fillMaxWidth(),
                            hasPin = true
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                WaxSealStamp(text = "MUSIK TERSEDIA", stampColor = EmeraldGreen, rotation = 0f)

                                Text(
                                    text = result.description,
                                    color = InkDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )

                                // Audio Playback Controls
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (result.audioFilePath != null) {
                                        Box(
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(CircleShape)
                                                .background(AntiqueGold)
                                                .clickable {
                                                    if (musicState.isPlaying) {
                                                        viewModel.stopAudio()
                                                    } else {
                                                        viewModel.playAudio(result.audioFilePath)
                                                    }
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = if (musicState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                                contentDescription = if (musicState.isPlaying) "Jeda" else "Putar",
                                                tint = InkDark,
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }
                                }

                                Text(
                                    text = if (musicState.isPlaying) "Sedang Memutar Audio Investigasi..." else "Ketuk tombol untuk mendengarkan lagu.",
                                    color = InkMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                if (musicState.errorMessage != null) {
                    item {
                        Surface(
                            color = CrimsonRed.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, CrimsonRed),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = musicState.errorMessage,
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
