package com.example.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.GameDataProvider
import com.example.ui.components.ParchmentCard
import com.example.ui.components.WaxSealStamp
import com.example.ui.theme.AmberWarning
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
import com.example.ui.theme.ParchmentBase
import com.example.ui.theme.ParchmentBorder
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.StampRed
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameUiState
import com.example.viewmodel.Screen

@Composable
fun HomeScreen(
    uiState: GameUiState,
    onNavigate: (Screen) -> Unit,
    onStartCase: (String) -> Unit,
    onStartStoryline: (Boolean) -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val nextCase = uiState.cases.firstOrNull { it.isUnlocked && !it.isCompleted }
        ?: uiState.cases.firstOrNull { it.isUnlocked }
        ?: uiState.cases.firstOrNull()

    val selectedChar = GameDataProvider.characters.find { it.id == uiState.userProgress.selectedCharacterId }
        ?: GameDataProvider.characters.first()
    val currentChapter = uiState.userProgress.currentStoryChapter + 1

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Header with App Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_map),
                    contentDescription = "Peta Nusantara Detektif",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark vignette gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.6f),
                                    WoodDark
                                )
                            )
                        )
                )

                // Title Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    WaxSealStamp(
                        text = "UNGKAP KATA, PECAHKAN MISTERI",
                        rotation = 0f,
                        stampColor = AntiqueGold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "KATA NUSANTARA",
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 30.sp,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Jelajahi Nusantara, pecahkan kasus, dan buktikan dirimu sebagai detektif bahasa terbaik!",
                        color = ParchmentLight,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            com.example.data.local.AudioEffects.playClick()
                            onStartStoryline(false)
                        },
                        modifier = Modifier
                            .height(40.dp)
                            .shadow(6.dp, RoundedCornerShape(20.dp))
                            .testTag("hero_start_storyline_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.5.dp, GoldBright)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = InkDark,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "MAIN KISAH DETEKTIF SEKARANG",
                                color = InkDark,
                                fontWeight = FontWeight.Black,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // Detective Status Card (Progresi Level & Pangkat)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-16).dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(1.5.dp, AntiqueGold.copy(alpha = 0.6f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.MilitaryTech,
                                contentDescription = "Pangkat",
                                tint = AntiqueGold,
                                modifier = Modifier.size(24.dp)
                            )
                            Column {
                                Text(
                                    text = uiState.currentRank.title,
                                    color = ParchmentLight,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = "Tingkat ${uiState.currentRank.level} • ${uiState.currentRank.badgeName}",
                                    color = AntiqueGold,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Score & Stars
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "${uiState.userProgress.xp} XP",
                                    color = GoldBright,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "${uiState.userProgress.casesSolvedCount} Kasus Dipecahkan",
                                    color = ParchmentLight.copy(alpha = 0.8f),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    // Progress Bar towards next rank
                    val nextRank = com.example.data.GameDataProvider.ranks.getOrNull(uiState.currentRank.level)
                    val targetXp = nextRank?.minXp ?: 3000
                    val currentXp = uiState.userProgress.xp
                    val progressFraction = (currentXp.toFloat() / targetXp.toFloat()).coerceIn(0f, 1f)

                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Menuju ${nextRank?.title ?: "Puncak Mahir"}",
                                color = ParchmentLight.copy(alpha = 0.7f),
                                fontSize = 10.sp
                            )
                            Text(
                                text = "$currentXp / $targetXp XP",
                                color = AntiqueGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        LinearProgressIndicator(
                            progress = { progressFraction },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = AntiqueGold,
                            trackColor = CorkBoard
                        )
                    }
                }
            }

            // Primary Navigation Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // FEATURED LINEAR STORYLINE CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(8.dp, RoundedCornerShape(12.dp))
                        .clickable { onStartStoryline(false) }
                        .testTag("home_storyline_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodBoard),
                    border = BorderStroke(2.dp, GoldBright)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            WaxSealStamp(
                                text = "MODE CERITA LINEAR",
                                rotation = 0f,
                                stampColor = AntiqueGold
                            )
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EmeraldGreen.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, EmeraldGreen.copy(alpha = 0.6f))
                            ) {
                                Text(
                                    text = "Bab $currentChapter Aktif",
                                    color = EmeraldGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, AntiqueGold, CircleShape)
                            ) {
                                Image(
                                    painter = painterResource(id = selectedChar.avatarDrawableRes),
                                    contentDescription = selectedChar.name,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "KISAH DETEKTIF NUSANTARA",
                                    color = GoldBright,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 15.sp,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Detektif: ${selectedChar.name} • ${selectedChar.specialtyPerk}",
                                    color = AntiqueGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Pilih Karakter -> Kerjakan Soal -> Analisis Bukti -> Tuduh Pelaku!",
                                    color = ParchmentLight.copy(alpha = 0.8f),
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onStartStoryline(false) },
                                modifier = Modifier
                                    .weight(1.3f)
                                    .height(44.dp)
                                    .testTag("home_resume_storyline_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        tint = InkDark,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = "LANJUT KISAH (BAB $currentChapter)",
                                        color = InkDark,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            OutlinedButton(
                                onClick = { onStartStoryline(true) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("home_new_storyline_button"),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, CardBorder)
                            ) {
                                Text(
                                    text = "GANTI TOKOH",
                                    color = ParchmentLight,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                // MULAI INVESTIGASI (PAPAN BEBAS)
                Button(
                    onClick = {
                        if (nextCase != null) {
                            onStartCase(nextCase.id)
                        } else {
                            onNavigate(Screen.CASE_SELECTION)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("home_start_button")
                        .shadow(4.dp, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WoodBoard
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = AntiqueGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "PAPAN INVESTIGASI BEBAS",
                            color = ParchmentLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }

                // PILIH KASUS
                Button(
                    onClick = { onNavigate(Screen.CASE_SELECTION) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("home_select_case_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = WoodBoard),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Folder, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(18.dp))
                        Text(text = "PILIH KASUS", color = ParchmentLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                // KAMUS DETEKTIF
                Button(
                    onClick = { onNavigate(Screen.DICTIONARY) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("home_dictionary_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = WoodBoard),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CardBorder)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Book, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(18.dp))
                        Text(text = "KAMUS DETEKTIF (KOSAKATA)", color = ParchmentLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }

                // Sub action row: PRESTASI & PENGATURAN
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigate(Screen.ACHIEVEMENTS) },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("home_achievements_button"),
                        border = BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(16.dp))
                            Text(text = "PRESTASI", color = ParchmentLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    OutlinedButton(
                        onClick = { onNavigate(Screen.SETTINGS) },
                        modifier = Modifier
                            .weight(1f)
                            .height(42.dp)
                            .testTag("home_settings_button"),
                        border = BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(16.dp))
                            Text(text = "PENGATURAN", color = ParchmentLight, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // CARA BERMAIN SECTION (Directly matching the tutorial infographic in reference image)
            ParchmentCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                hasPin = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "CARA BERMAIN",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        letterSpacing = 1.sp
                    )

                    // Step 1
                    TutorialStepRow(
                        stepNumber = "1",
                        title = "Terima Kasus",
                        description = "Pilih kasus misteri di berbagai daerah Nusantara yang ingin kamu selidiki."
                    )

                    // Step 2
                    TutorialStepRow(
                        stepNumber = "2",
                        title = "Jawab Tantangan",
                        description = "Selesaikan teka-teki sinonim, antonim, dan kata baku sesuai kaidah KBBI."
                    )

                    // Step 3
                    TutorialStepRow(
                        stepNumber = "3",
                        title = "Kumpulkan Petunjuk",
                        description = "Setiap jawaban benar akan membuka petunjuk bukti penting di Papan Investigasi."
                    )

                    // Step 4
                    TutorialStepRow(
                        stepNumber = "4",
                        title = "Bongkar Alibi",
                        description = "Coret tersangka yang alibinya terbukti bertolak belakang dengan bukti fisik."
                    )

                    // Step 5
                    TutorialStepRow(
                        stepNumber = "5",
                        title = "Tuduh Pelaku",
                        description = "Analisis kesimpulan akhir dan tunjuk pelaku sebenarnya untuk menyelesaikan kasus!"
                    )
                }
            }
        }
    }
}

@Composable
private fun TutorialStepRow(
    stepNumber: String,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(AntiqueGold),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                color = InkDark,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                color = InkDark,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Text(
                text = description,
                color = InkMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}
