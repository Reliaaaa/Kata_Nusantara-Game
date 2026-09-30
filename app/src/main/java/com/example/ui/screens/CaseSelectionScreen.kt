package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CaseData
import com.example.ui.components.ParchmentCard
import com.example.ui.components.StarRatingBar
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
import com.example.ui.theme.ParchmentBase
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.StampRed
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark

@Composable
fun CaseSelectionScreen(
    cases: List<CaseData>,
    userXp: Int,
    onBack: () -> Unit,
    onSelectCase: (String) -> Unit
) {
    BackHandler { onBack() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(WoodDark)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Bar
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
                        .testTag("case_select_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali ke Menu",
                        tint = AntiqueGold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Golden banner badge "PILIH KASUS"
                Surface(
                    color = CorkBoard,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.5.dp, AntiqueGold)
                ) {
                    Text(
                        text = "PILIH KASUS",
                        color = GoldBright,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Spacer to balance back button
                Box(modifier = Modifier.size(40.dp))
            }

            // Case List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(cases) { caseItem ->
                    CaseDossierCard(
                        caseItem = caseItem,
                        userXp = userXp,
                        onSelect = {
                            if (caseItem.isUnlocked) {
                                onSelectCase(caseItem.id)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CaseDossierCard(
    caseItem: CaseData,
    userXp: Int,
    onSelect: () -> Unit
) {
    val isLocked = !caseItem.isUnlocked
    val cardAlpha = if (isLocked) 0.6f else 1.0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(cardAlpha)
            .testTag("case_item_${caseItem.id}")
            .clickable(enabled = !isLocked) { onSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = WoodBoard
        ),
        border = BorderStroke(
            1.5.dp,
            if (caseItem.isCompleted) EmeraldGreen else if (isLocked) CardBorder else AntiqueGold
        )
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Case Cover Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                Image(
                    painter = painterResource(id = caseItem.drawableRes),
                    contentDescription = caseItem.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Dark gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )

                // Number badge
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    color = CorkBoard.copy(alpha = 0.9f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, AntiqueGold)
                ) {
                    Text(
                        text = caseItem.numberCode,
                        color = AntiqueGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Region Tag
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    color = Color.Black.copy(alpha = 0.7f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = caseItem.region,
                        color = ParchmentLight,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Completion Stars
                if (caseItem.isCompleted) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(8.dp),
                        color = Color.Black.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            StarRatingBar(stars = caseItem.starsEarned, modifier = Modifier.size(14.dp))
                            Text(
                                text = "Skor: ${caseItem.bestScore}",
                                color = AntiqueGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                }
            }

            // Case Info Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = caseItem.title,
                    color = ParchmentLight,
                    fontWeight = FontWeight.Black,
                    fontSize = 16.sp
                )

                Text(
                    text = caseItem.locationName,
                    color = AntiqueGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = caseItem.storyIntro,
                    color = ParchmentLight.copy(alpha = 0.75f),
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Action / Status Button
                if (isLocked) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(CorkBoard, RoundedCornerShape(6.dp))
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Terkunci",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "TERKUNCI (Butuh ${caseItem.unlockXpRequired} XP / Selesaikan Kasus Sebelumnya)",
                            color = Color.Gray,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Button(
                        onClick = onSelect,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(42.dp)
                            .testTag("start_case_btn_${caseItem.id}"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (caseItem.isCompleted) EmeraldGreen else AntiqueGold
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (caseItem.isCompleted) Icons.Default.PlayArrow else Icons.Default.PlayArrow,
                                contentDescription = null,
                                tint = if (caseItem.isCompleted) Color.White else InkDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (caseItem.isCompleted) "MAIN LAGI (SELESAI)" else "MULAI KASUS",
                                color = if (caseItem.isCompleted) Color.White else InkDark,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
