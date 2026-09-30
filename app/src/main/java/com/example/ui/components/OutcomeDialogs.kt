package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Suspect
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBorder
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

@Composable
fun CaseSolvedDialog(
    culprit: Suspect,
    score: Int,
    elapsedSeconds: Int,
    stars: Int,
    onNextCase: () -> Unit,
    onOtherCases: () -> Unit
) {
    val minutes = elapsedSeconds / 60
    val seconds = elapsedSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    Dialog(onDismissRequest = {}) {
        ParchmentCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            hasPin = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Title
                Text(
                    text = "✨ KASUS TERPECAHKAN! ✨",
                    color = StampRed,
                    fontWeight = FontWeight.Black,
                    fontSize = 19.sp,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )

                // Culprit Name
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pelaku adalah",
                        color = InkMuted,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "${culprit.name}!",
                        color = InkDark,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )
                }

                // Culprit Avatar & Detective Stamp
                Box(
                    modifier = Modifier.size(110.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(culprit.avatarColorHex))
                            .border(2.dp, AntiqueGold, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = culprit.avatarInitials,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 32.sp
                        )
                    }

                    // Stamp Overlay
                    WaxSealStamp(
                        text = "DETEKTIF HEBAT",
                        stampColor = StampRed,
                        rotation = -18f,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(bottom = 2.dp)
                    )
                }

                // Confession / Motive
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, ParchmentBorder)
                ) {
                    Text(
                        text = "\"${culprit.confession}\"",
                        color = InkDark,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                // Stars Rating
                StarRatingBar(stars = stars, modifier = Modifier.padding(vertical = 2.dp))

                // Stats Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Skor Akhir", color = InkMuted, fontSize = 11.sp)
                        Text(text = "$score", color = InkDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Waktu", color = InkMuted, fontSize = 11.sp)
                        Text(text = timeFormatted, color = InkDark, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Pangkat", color = InkMuted, fontSize = 11.sp)
                        Text(text = "+250 XP", color = EmeraldGreen, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onOtherCases,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("solved_other_cases_btn"),
                        border = BorderStroke(1.5.dp, CardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "KASUS LAIN",
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = onNextCase,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("solved_continue_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AntiqueGold
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "LANJUTKAN",
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CaseFailedDialog(
    reason: String,
    onRetry: () -> Unit,
    onBackToSelection: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        ParchmentCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            hasPin = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                WaxSealStamp(
                    text = "INVESTIGASI GAGAL",
                    stampColor = CrimsonRed,
                    rotation = 0f
                )

                Text(
                    text = "Penyelidikan Terhenti!",
                    color = InkDark,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CrimsonRed.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CrimsonRed.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = reason,
                        color = InkDark,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Text(
                    text = "Gunakan petunjuk kosakata dengan cermat untuk menyingkirkan tersangka yang tidak bersalah.",
                    color = InkMuted,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onBackToSelection,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("failed_menu_btn"),
                        border = BorderStroke(1.5.dp, CardBorder),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "PILIH KASUS",
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }

                    Button(
                        onClick = onRetry,
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp)
                            .testTag("failed_retry_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CrimsonRed
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Default.Refresh, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            Text(
                                text = "ULANGI",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
