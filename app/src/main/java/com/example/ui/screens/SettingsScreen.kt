package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AudioEffects
import com.example.model.GameDifficulty
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldBright
import com.example.ui.theme.InkDark
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameViewModel

@Composable
fun SettingsScreen(
    viewModel: GameViewModel,
    onBack: () -> Unit,
    onResetProgress: () -> Unit
) {
    BackHandler { onBack() }

    val uiState by viewModel.uiState.collectAsState()
    var soundEnabled by remember { mutableStateOf(AudioEffects.isSoundEnabled) }
    var hapticEnabled by remember { mutableStateOf(true) }
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
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
                        AudioEffects.playClick()
                        onBack()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("settings_back_button")
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
                        text = "PENGATURAN DETEKTIF",
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.size(40.dp))
            }

            // 1. Difficulty Level Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.dp, AntiqueGold)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = GoldBright)
                        Text(
                            text = "TINGKAT KESULITAN PERMAINAN",
                            color = GoldBright,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 1.sp
                        )
                    }

                    Text(
                        text = "Pilih tingkat tantangan investigasi. Di mode Sangat Sulit, kegugupan saksi di persidangan berada pada posisi setara (50%) meskipun seluruh petunjuk telah terkumpul!",
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )

                    GameDifficulty.entries.forEach { diff ->
                        val isSelected = uiState.difficulty == diff

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    AudioEffects.playClick()
                                    viewModel.setDifficulty(diff)
                                },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Color(diff.colorHex).copy(alpha = 0.25f) else CorkBoard,
                            border = BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) Color(diff.colorHex) else CardBorder
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = diff.title,
                                        color = if (isSelected) Color(diff.colorHex) else ParchmentLight,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )

                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = Color(diff.colorHex)
                                    ) {
                                        Text(
                                            text = "${diff.badge} (+${((diff.xpMultiplier - 1.0f) * 100).toInt()}% XP)",
                                            color = InkDark,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }

                                Text(
                                    text = diff.description,
                                    color = ParchmentLight.copy(alpha = 0.85f),
                                    fontSize = 10.5.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 2. Background Music & Sound Controls Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "MUSIK BACKSOUND & SUARA",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )

                    // BGM Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.MusicNote, contentDescription = null, tint = AntiqueGold)
                            Column {
                                Text(text = "Musik Backsound Gamelan", color = ParchmentLight, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "Alunan gamelan misteri detektif Nusantara", color = ParchmentLight.copy(alpha = 0.6f), fontSize = 11.sp)
                            }
                        }

                        Switch(
                            checked = uiState.isBgmEnabled,
                            onCheckedChange = {
                                AudioEffects.playClick()
                                viewModel.toggleBgm(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AntiqueGold,
                                checkedTrackColor = CorkBoard,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = WoodDark
                            )
                        )
                    }

                    if (uiState.isBgmEnabled) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Volume Musik Backsound:", color = ParchmentLight.copy(alpha = 0.8f), fontSize = 11.sp)
                                Text(text = "${(uiState.bgmVolume * 100).toInt()}%", color = GoldBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = uiState.bgmVolume,
                                onValueChange = { viewModel.setBgmVolume(it) },
                                valueRange = 0f..1f,
                                colors = SliderDefaults.colors(
                                    thumbColor = GoldBright,
                                    activeTrackColor = AntiqueGold,
                                    inactiveTrackColor = CorkBoard
                                )
                            )
                        }
                    }

                    // Sound Effects Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = AntiqueGold)
                            Column {
                                Text(text = "Efek Suara Teka-Teki", color = ParchmentLight, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "Suara kertas, ketuk palu, & stempel", color = ParchmentLight.copy(alpha = 0.6f), fontSize = 11.sp)
                            }
                        }

                        Switch(
                            checked = soundEnabled,
                            onCheckedChange = {
                                soundEnabled = it
                                AudioEffects.isSoundEnabled = it
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = AntiqueGold,
                                checkedTrackColor = CorkBoard,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = WoodDark
                            )
                        )
                    }

                    // Haptics Switch
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Vibration, contentDescription = null, tint = AntiqueGold)
                            Column {
                                Text(text = "Umpan Balik Getaran", color = ParchmentLight, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = "Getar saat memilih jawaban & interogasi saksi", color = ParchmentLight.copy(alpha = 0.6f), fontSize = 11.sp)
                            }
                        }

                        Switch(
                            checked = hapticEnabled,
                            onCheckedChange = { hapticEnabled = it },
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

            Spacer(modifier = Modifier.height(14.dp))

            // 3. About Project Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "TENTANG KATA NUSANTARA",
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Game Edukasi Detektif Bahasa Indonesia yang mengintegrasikan pembelajaran sinonim, antonim, dan kata baku ke dalam pemecahan kasus misteri kebudayaan Nusantara.",
                        color = ParchmentLight.copy(alpha = 0.9f),
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Text(
                        text = "Referensi Materi: Tata Bahasa Indonesia & Ragam Budaya Nusantara.",
                        color = ParchmentLight.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Danger Zone: Reset Data
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.dp, CrimsonRed.copy(alpha = 0.5f))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "RESET PROGRES DETEKTIF",
                        color = CrimsonRed,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Hapus seluruh catatan kasus yang telah dipecahkan, bintang, skor, dan kembalikan tingkat ke Detektif Magang.",
                        color = ParchmentLight.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )

                    Button(
                        onClick = { showResetDialog = true },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reset_progress_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "RESET SEMUA PROGRES PERMAINAN", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Reset Confirmation Dialog
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                title = { Text(text = "Konfirmasi Reset Progres", color = ParchmentLight, fontWeight = FontWeight.Bold) },
                text = {
                    Text(
                        text = "Apakah kamu yakin ingin menghapus seluruh data kasus, skor, dan peringkat detektif? Tindakan ini tidak dapat dibatalkan.",
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 13.sp
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showResetDialog = false
                            onResetProgress()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = CrimsonRed)
                    ) {
                        Text(text = "Ya, Reset Semua", color = Color.White)
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showResetDialog = false }) {
                        Text(text = "Batal", color = ParchmentLight)
                    }
                },
                containerColor = WoodBoard
            )
        }
    }
}
