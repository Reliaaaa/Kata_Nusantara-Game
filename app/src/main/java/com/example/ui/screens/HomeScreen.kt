package com.example.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
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
import com.example.data.EnglishTranslationProvider
import com.example.data.GameDataProvider
import com.example.data.local.AudioEffects
import com.example.ui.components.WaxSealStamp
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldBright
import com.example.ui.theme.InkBrown
import com.example.ui.theme.InkDark
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameUiState
import com.example.viewmodel.Screen

@Composable
fun HomeScreen(
    uiState: GameUiState,
    onNavigate: (Screen) -> Unit,
    onStartCase: (String) -> Unit = {},
    onStartStoryline: (Boolean) -> Unit = {},
    onToggleEnglish: () -> Unit = {}
) {
    val scrollState = rememberScrollState()
    val isEn = uiState.storyline.isEnglishEnabled

    val activeCase = uiState.cases.getOrNull(uiState.userProgress.currentStoryChapter)
        ?: uiState.cases.firstOrNull()
        ?: GameDataProvider.getInitialCases().first()

    val caseEn = EnglishTranslationProvider.getCaseTranslation(activeCase.id)

    val selectedChar = GameDataProvider.characters.find { it.id == uiState.userProgress.selectedCharacterId }
        ?: GameDataProvider.characters.first()
    val currentChapter = uiState.userProgress.currentStoryChapter + 1

    // Pulsing animation for main play button
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TOP BAR: Rank status & Language toggle
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = WoodBoard,
                shadowElevation = 4.dp,
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Detective Rank Badge & Difficulty
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = "Pangkat",
                            tint = AntiqueGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = uiState.currentRank.title,
                                    color = GoldBright,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                                Surface(
                                    shape = RoundedCornerShape(3.dp),
                                    color = Color(uiState.difficulty.colorHex)
                                ) {
                                    Text(
                                        text = uiState.difficulty.badge,
                                        color = InkDark,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Black,
                                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Tingkat ${uiState.currentRank.level} • ${uiState.userProgress.xp} XP",
                                color = ParchmentLight.copy(alpha = 0.8f),
                                fontSize = 10.sp
                            )
                        }
                    }

                    // Language Switcher (ID / EN)
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (isEn) EmeraldGreen.copy(alpha = 0.25f) else CorkBoard,
                        border = BorderStroke(1.dp, if (isEn) EmeraldGreen else AntiqueGold.copy(alpha = 0.5f)),
                        modifier = Modifier
                            .clickable {
                                AudioEffects.playClick()
                                onToggleEnglish()
                            }
                            .testTag("home_language_toggle")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Translate,
                                contentDescription = "Bahasa",
                                tint = if (isEn) EmeraldGreen else AntiqueGold,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = if (isEn) "🇬🇧 English" else "🇮🇩 Indonesia",
                                color = if (isEn) EmeraldGreen else ParchmentLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // GAME TITLE HERO BANNER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = painterResource(id = activeCase.drawableRes),
                    contentDescription = "Peta Nusantara Detektif",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dramatic Dark Vignette Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Black.copy(alpha = 0.65f),
                                    WoodDark
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    WaxSealStamp(
                        text = if (isEn) "INDONESIAN DETECTIVE MYSTERY" else "GAME DETEKTIF BAHASA NUSANTARA",
                        rotation = 0f,
                        stampColor = AntiqueGold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "KATA NUSANTARA",
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 32.sp,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = if (isEn)
                            "Learn Indonesian vocabulary & grammar by cracking cultural mystery cases!"
                        else
                            "Pecahkan teka-teki sandi kata, ungkap kontradiksi alibi, dan selesaikan misteri budaya!",
                        color = ParchmentLight,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 15.sp,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }
            }

            // MAIN INTERACTIVE GAME CTA (SINGLE FOCUSED LINEAR FLOW)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-20).dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // CURRENT ACTIVE CHAPTER CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(16.dp))
                        .border(2.dp, GoldBright, RoundedCornerShape(16.dp))
                        .testTag("home_active_chapter_card"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = WoodBoard)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Chapter Tag & Active Detective
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = EmeraldGreen.copy(alpha = 0.2f),
                                border = BorderStroke(1.dp, EmeraldGreen.copy(alpha = 0.7f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = EmeraldGreen,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = if (isEn) "Chapter $currentChapter Active" else "Bab $currentChapter Sedang Berjalan",
                                        color = EmeraldGreen,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // Detective mini profile
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .border(1.5.dp, AntiqueGold, CircleShape)
                                ) {
                                    Image(
                                        painter = painterResource(id = selectedChar.avatarDrawableRes),
                                        contentDescription = selectedChar.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Column {
                                    Text(
                                        text = selectedChar.name,
                                        color = ParchmentLight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = selectedChar.specialtyPerk,
                                        color = AntiqueGold,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = CardBorder.copy(alpha = 0.5f))

                        // Case Title & Location
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = if (isEn) caseEn?.locationEn ?: activeCase.locationName else activeCase.locationName,
                                color = AntiqueGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (isEn) caseEn?.titleEn ?: activeCase.title else activeCase.title,
                                color = GoldBright,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = if (isEn) caseEn?.subtitleEn ?: activeCase.subtitle else activeCase.subtitle,
                                color = ParchmentLight.copy(alpha = 0.85f),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }

                        // Linear 5-Step Journey Diagram
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            color = CorkBoard.copy(alpha = 0.6f),
                            border = BorderStroke(1.dp, CardBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = if (isEn) "ALUR KISAH LINIER / LINEAR PATH:" else "ALUR PERMAINAN LINIER:",
                                    color = AntiqueGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isEn)
                                        "1. Character ➔ 2. Crime Scene ➔ 3. Word Ciphers ➔ 4. Interrogation ➔ 5. Checkmate!"
                                    else
                                        "1. Pilih Tokoh ➔ 2. Olah TKP ➔ 3. Soal Sandi ➔ 4. Interogasi ➔ 5. Skakmat!",
                                    color = ParchmentLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // PRIMARY ARCADE START BUTTON (PULSING)
                        Button(
                            onClick = {
                                AudioEffects.playClick()
                                onStartStoryline(false)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp)
                                .scale(pulseScale)
                                .testTag("home_main_game_button")
                                .shadow(8.dp, RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(2.dp, GoldBright)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = if (isEn)
                                        "▶ START / CONTINUE CHAPTER $currentChapter"
                                    else
                                        "▶ MULAI / LANJUTKAN BAB $currentChapter",
                                    color = InkDark,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Change Character option
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = if (isEn) "🔄 Change Detective Character" else "🔄 Ganti Karakter Detektif",
                                color = AntiqueGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {
                                        AudioEffects.playClick()
                                        onStartStoryline(true)
                                    }
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                                    .testTag("home_change_character_button")
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // CLEAN BOTTOM TOOLBAR (JUST 3 ESSENTIAL GAME ICONS)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = WoodBoard,
                    border = BorderStroke(1.dp, CardBorder),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 1. Kamus Detektif (Dictionary)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    AudioEffects.playClick()
                                    onNavigate(Screen.DICTIONARY)
                                }
                                .padding(8.dp)
                                .testTag("home_dictionary_tab")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Book,
                                contentDescription = "Kamus",
                                tint = AntiqueGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEn) "Dictionary" else "Kamus Sandi",
                                color = ParchmentLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // 2. Prestasi (Achievements)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    AudioEffects.playClick()
                                    onNavigate(Screen.ACHIEVEMENTS)
                                }
                                .padding(8.dp)
                                .testTag("home_achievements_tab")
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = "Prestasi",
                                tint = AntiqueGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEn) "Badges" else "Prestasi",
                                color = ParchmentLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // 3. Pengaturan (Settings)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clickable {
                                    AudioEffects.playClick()
                                    onNavigate(Screen.SETTINGS)
                                }
                                .padding(8.dp)
                                .testTag("home_settings_tab")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Pengaturan",
                                tint = AntiqueGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (isEn) "Settings" else "Pengaturan",
                                color = ParchmentLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
