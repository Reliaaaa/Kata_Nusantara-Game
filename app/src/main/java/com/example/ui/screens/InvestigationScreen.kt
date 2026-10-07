package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.CaseData
import com.example.model.ChallengeQuestion
import com.example.model.Clue
import com.example.model.Suspect
import com.example.ui.components.CaseFailedDialog
import com.example.ui.components.CaseSolvedDialog
import com.example.ui.components.ChallengeModal
import com.example.ui.components.HeartCounter
import com.example.ui.components.ParchmentCard
import com.example.ui.components.PushPin
import com.example.ui.components.RedThreadOverlay
import com.example.ui.components.SuspectAlibiDialog
import com.example.ui.components.SuspectItemRow
import com.example.ui.components.WaxSealStamp
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CorkBoardLight
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
import com.example.ui.theme.StringRed
import com.example.ui.theme.TealAccent
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.CaseOutcomeType
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.InvestigationState
import com.example.viewmodel.Screen

@Composable
fun InvestigationScreen(
    investigation: InvestigationState,
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    val activeCase = investigation.activeCase

    if (activeCase == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(WoodDark),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Memuat data berkas kasus...",
                    color = GoldBright,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold)
                ) {
                    Text("Kembali ke Menu", color = InkDark, fontWeight = FontWeight.Bold)
                }
            }
        }
        return
    }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("PAPAN INVESTIGASI", "DAFTAR TERSANGKA (${activeCase.suspects.size})", "SOAL KEBASAHAAN")

    val minutes = investigation.elapsedSeconds / 60
    val seconds = investigation.elapsedSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TOP STATUS BAR (Matching Reference Image)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = WoodBoard,
                shadowElevation = 4.dp,
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("investigation_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali",
                            tint = AntiqueGold
                        )
                    }

                    // Petunjuk Count
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = "Petunjuk",
                            tint = AmberWarning,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(text = "Petunjuk", color = InkMuted, fontSize = 9.sp)
                            Text(
                                text = "${investigation.revealedClueIds.size}/${activeCase.clues.size}",
                                color = ParchmentLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Score
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Skor",
                            tint = AntiqueGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(text = "Skor", color = InkMuted, fontSize = 9.sp)
                            Text(
                                text = "${investigation.currentScore}",
                                color = GoldBright,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Hearts (Health)
                    HeartCounter(
                        remaining = investigation.heartsRemaining,
                        max = investigation.maxHearts
                    )

                    // Timer
                    Text(
                        text = timeFormatted,
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Clue Hint Button
                    IconButton(
                        onClick = { viewModel.useHint() },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("use_hint_button"),
                        enabled = investigation.hintsCount > 0
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Help,
                            contentDescription = "Bantuan (${investigation.hintsCount})",
                            tint = if (investigation.hintsCount > 0) AntiqueGold else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Tab Navigation Row
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = WoodBoard,
                contentColor = AntiqueGold,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = AntiqueGold
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                fontSize = 11.sp,
                                fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                color = if (selectedTabIndex == index) GoldBright else ParchmentLight.copy(alpha = 0.6f)
                            )
                        }
                    )
                }
            }

            // Tab Content
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                when (selectedTabIndex) {
                    0 -> InvestigationBoardTab(
                        activeCase = activeCase,
                        investigation = investigation,
                        onOpenChallenge = { viewModel.openChallenge(it) },
                        onInspectSuspect = { viewModel.selectSuspect(it) },
                        onOpenAccusation = { viewModel.openAccusationDialog(true) }
                    )
                    1 -> SuspectsTab(
                        activeCase = activeCase,
                        investigation = investigation,
                        onInspect = { viewModel.selectSuspect(it) },
                        onToggleEliminate = { viewModel.toggleEliminateSuspect(it) },
                        onAccuse = { viewModel.accuseSuspect(it) }
                    )
                    2 -> ChallengesTab(
                        activeCase = activeCase,
                        investigation = investigation,
                        onOpenChallenge = { viewModel.openChallenge(it) }
                    )
                }
            }
        }

        // Active Challenge Modal
        if (investigation.isChallengeOpen && investigation.activeChallenge != null) {
            ChallengeModal(
                challenge = investigation.activeChallenge,
                answerResult = investigation.lastAnswerResult,
                onDismiss = { viewModel.closeChallengeDialog() },
                onSubmitAnswer = { viewModel.submitChallengeAnswer(it) }
            )
        }

        // Selected Suspect Alibi Inspector
        if (investigation.selectedSuspect != null) {
            SuspectAlibiDialog(
                suspect = investigation.selectedSuspect,
                clues = activeCase.clues,
                revealedClueIds = investigation.revealedClueIds,
                isEliminated = investigation.eliminatedSuspectIds.contains(investigation.selectedSuspect.id),
                onDismiss = { viewModel.selectSuspect(null) },
                onToggleEliminate = { viewModel.toggleEliminateSuspect(investigation.selectedSuspect.id) },
                onAccuse = { viewModel.accuseSuspect(investigation.selectedSuspect) }
            )
        }

        // Accusation Confirmation Modal
        if (investigation.isAccusationOpen) {
            AccusationSelectionDialog(
                suspects = activeCase.suspects,
                eliminatedIds = investigation.eliminatedSuspectIds,
                onDismiss = { viewModel.openAccusationDialog(false) },
                onConfirmAccuse = { suspect ->
                    viewModel.accuseSuspect(suspect)
                }
            )
        }

        // Victory Dialog
        if (investigation.outcome == CaseOutcomeType.VICTORY && investigation.accusedSuspect != null) {
            val earnedStars = when {
                investigation.heartsRemaining == 3 -> 3
                investigation.heartsRemaining >= 2 -> 2
                else -> 1
            }
            CaseSolvedDialog(
                culprit = investigation.accusedSuspect,
                score = investigation.currentScore,
                elapsedSeconds = investigation.elapsedSeconds,
                stars = earnedStars,
                onNextCase = {
                    viewModel.navigateTo(Screen.CASE_SELECTION)
                },
                onOtherCases = {
                    viewModel.navigateTo(Screen.CASE_SELECTION)
                }
            )
        }

        // Defeat Dialog
        if (investigation.outcome == CaseOutcomeType.DEFEAT) {
            CaseFailedDialog(
                reason = "Nyawa detektifmu telah habis atau bukti belum cukup untuk menahan tersangka yang tepat.",
                onRetry = { viewModel.restartCurrentCase() },
                onBackToSelection = { viewModel.navigateTo(Screen.CASE_SELECTION) }
            )
        }
    }
}

@Composable
private fun InvestigationBoardTab(
    activeCase: CaseData,
    investigation: InvestigationState,
    onOpenChallenge: (ChallengeQuestion) -> Unit,
    onInspectSuspect: (Suspect) -> Unit,
    onOpenAccusation: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CorkBoard)
    ) {
        // Red Yarn Thread Drawing across the board
        RedThreadOverlay(
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Case Title Banner Pin
            ParchmentCard(
                modifier = Modifier.fillMaxWidth(),
                hasPin = true
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${activeCase.numberCode}: ${activeCase.title}",
                            color = InkDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "📍 ${activeCase.locationName}",
                            color = InkBrown,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    WaxSealStamp(
                        text = "KASUS AKTIF",
                        stampColor = StampRed,
                        rotation = 4f
                    )
                }
            }

            // Split Row: Crime Scene & Physical Evidence Pins
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Crime Scene Photo Card
                ParchmentCard(
                    modifier = Modifier.weight(1f),
                    hasPin = true
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "TEMPAT KEJADIAN",
                            color = InkBrown,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                        ) {
                            Image(
                                painter = painterResource(id = activeCase.drawableRes),
                                contentDescription = "Tempat Kejadian",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = activeCase.locationName,
                            color = InkDark,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                // Physical Evidence Card
                ParchmentCard(
                    modifier = Modifier.weight(1f),
                    hasPin = true
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "BUKTI FISIK",
                            color = InkBrown,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp),
                            color = Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, ParchmentBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(6.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = activeCase.physicalEvidenceTitle,
                                    color = InkDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = activeCase.physicalEvidenceDesc,
                                    color = InkMuted,
                                    fontSize = 9.sp,
                                    lineHeight = 12.sp,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Dokumen Terverifikasi",
                            color = CrimsonRed,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // PETUNJUK (Clues Collected Pin)
            ParchmentCard(
                modifier = Modifier.fillMaxWidth(),
                hasPin = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PETUNJUK INVESTIGASI",
                            color = InkDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            letterSpacing = 1.sp
                        )

                        Text(
                            text = "${investigation.revealedClueIds.size}/${activeCase.clues.size} Terbuka",
                            color = AntiqueGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }

                    if (investigation.revealedClueIds.isEmpty()) {
                        Text(
                            text = "Belum ada petunjuk terbuka. Jawab tantangan kata di bawah untuk membuka petunjuk bukti!",
                            color = InkMuted,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    } else {
                        activeCase.clues.filter { investigation.revealedClueIds.contains(it.id) }.forEach { clue ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = "📌", fontSize = 12.sp)
                                Column {
                                    Text(
                                        text = clue.title,
                                        color = InkDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = clue.revealedDetail,
                                        color = InkBrown,
                                        fontSize = 11.sp,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }

                    // Next Challenge Trigger Button if unrevealed clues remain
                    val nextChallenge = activeCase.challenges.firstOrNull {
                        !investigation.revealedClueIds.contains(it.rewardClueId)
                    }

                    if (nextChallenge != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(
                            onClick = { onOpenChallenge(nextChallenge) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(42.dp)
                                .testTag("board_solve_challenge_button"),
                            colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = InkDark,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "JAWAB TANTANGAN: ${nextChallenge.type.label}",
                                    color = InkDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }

            // TERSANGKA PIN STRIP
            ParchmentCard(
                modifier = Modifier.fillMaxWidth(),
                hasPin = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "TERSANGKA TERHUBUNG",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 13.sp,
                        letterSpacing = 1.sp
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(activeCase.suspects) { suspect ->
                            val isEliminated = investigation.eliminatedSuspectIds.contains(suspect.id)

                            Column(
                                modifier = Modifier
                                    .width(76.dp)
                                    .clickable { onInspectSuspect(suspect) },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(Color(suspect.avatarColorHex))
                                        .border(
                                            2.dp,
                                            if (isEliminated) CrimsonRed else AntiqueGold,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = suspect.avatarInitials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = suspect.name,
                                    color = if (isEliminated) Color.Gray else InkDark,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    textDecoration = if (isEliminated) TextDecoration.LineThrough else TextDecoration.None,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )

                                if (isEliminated) {
                                    Text(
                                        text = "DICORET",
                                        color = CrimsonRed,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // KESIMPULAN / TUDUH PELAKU CARD (Matching bottom right of reference image)
            ParchmentCard(
                modifier = Modifier.fillMaxWidth(),
                hasPin = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "KESIMPULAN PENYELIDIKAN",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )

                    val remainingSuspects = activeCase.suspects.filter { !investigation.eliminatedSuspectIds.contains(it.id) }

                    Text(
                        text = "Siapa yang paling mungkin pelaku? (${remainingSuspects.size} tersangka tersisa)",
                        color = InkBrown,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = onOpenAccusation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("board_accuse_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = StampRed),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "TUDUH PELAKU SEKARANG",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SuspectsTab(
    activeCase: CaseData,
    investigation: InvestigationState,
    onInspect: (Suspect) -> Unit,
    onToggleEliminate: (String) -> Unit,
    onAccuse: (Suspect) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CorkBoard,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "DAFTAR TERSANGKA & ALIBI",
                        color = GoldBright,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Gunakan petunjuk bukti kebahasaan untuk mencoret alibi dusta dari tersangka yang tidak bersalah.",
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        items(activeCase.suspects) { suspect ->
            SuspectItemRow(
                suspect = suspect,
                isEliminated = investigation.eliminatedSuspectIds.contains(suspect.id),
                onInspect = { onInspect(suspect) },
                onToggleEliminate = { onToggleEliminate(suspect.id) }
            )
        }
    }
}

@Composable
private fun ChallengesTab(
    activeCase: CaseData,
    investigation: InvestigationState,
    onOpenChallenge: (ChallengeQuestion) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = CorkBoard,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, CardBorder)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "STASIUN INVESTIGASI BAHASA",
                        color = GoldBright,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "Jawab soal sinonim, antonim, dan kata baku untuk memverifikasi dokumen serta mematahkan alibi.",
                        color = ParchmentLight.copy(alpha = 0.8f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        items(activeCase.challenges) { challenge ->
            val isCompleted = investigation.revealedClueIds.contains(challenge.rewardClueId)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("challenge_item_${challenge.id}")
                    .clickable { onOpenChallenge(challenge) },
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = WoodBoard),
                border = BorderStroke(
                    1.dp,
                    if (isCompleted) EmeraldGreen else AntiqueGold
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(challenge.type.badgeColorHex),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = challenge.type.label,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            color = Color(challenge.difficulty.colorHex),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = challenge.difficulty.badge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = challenge.targetWord,
                            color = ParchmentLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = challenge.prompt,
                            color = ParchmentLight.copy(alpha = 0.7f),
                            fontSize = 11.sp
                        )
                    }

                    if (isCompleted) {
                        Surface(
                            color = EmeraldGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, EmeraldGreen)
                        ) {
                            Text(
                                text = "TERBUKA",
                                color = EmeraldGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        Button(
                            onClick = { onOpenChallenge(challenge) },
                            modifier = Modifier.height(34.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(text = "JAWAB", color = InkDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AccusationSelectionDialog(
    suspects: List<Suspect>,
    eliminatedIds: Set<String>,
    onDismiss: () -> Unit,
    onConfirmAccuse: (Suspect) -> Unit
) {
    var chosenSuspect by remember { mutableStateOf<Suspect?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        ParchmentCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            hasPin = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WaxSealStamp(
                    text = "TUDUHAN AKHIR",
                    stampColor = StampRed,
                    rotation = 0f
                )

                Text(
                    text = "Tunjuk Pelaku Utama",
                    color = InkDark,
                    fontWeight = FontWeight.Black,
                    fontSize = 17.sp
                )

                Text(
                    text = "Pilihlah salah satu tersangka yang menurutmu adalah dalang kejahatan berdasarkan seluruh petunjuk yang ada.",
                    color = InkMuted,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )

                // List of Suspects
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    suspects.forEach { suspect ->
                        val isEliminated = eliminatedIds.contains(suspect.id)
                        val isSelected = chosenSuspect?.id == suspect.id

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { chosenSuspect = suspect },
                            color = when {
                                isSelected -> AntiqueGold.copy(alpha = 0.3f)
                                isEliminated -> Color.Gray.copy(alpha = 0.15f)
                                else -> Color.White.copy(alpha = 0.6f)
                            },
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(
                                1.5.dp,
                                if (isSelected) AntiqueGold else ParchmentBorder
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(Color(suspect.avatarColorHex)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = suspect.avatarInitials,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = suspect.name,
                                        color = InkDark,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        textDecoration = if (isEliminated) TextDecoration.LineThrough else TextDecoration.None
                                    )
                                    Text(
                                        text = suspect.roleTitle,
                                        color = InkBrown,
                                        fontSize = 10.sp
                                    )
                                }

                                if (isEliminated) {
                                    Text(
                                        text = "Telah Dicoret",
                                        color = CrimsonRed,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, CardBorder)
                    ) {
                        Text(text = "Batal", color = InkDark, fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            val target = chosenSuspect
                            if (target != null) {
                                onConfirmAccuse(target)
                            }
                        },
                        enabled = chosenSuspect != null,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("confirm_accuse_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = StampRed),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "TUDUH SEKARANG",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
