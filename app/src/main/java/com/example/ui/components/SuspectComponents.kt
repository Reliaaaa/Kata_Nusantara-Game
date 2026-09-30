package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Clue
import com.example.model.Suspect
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.InkBrown
import com.example.ui.theme.InkDark
import com.example.ui.theme.InkMuted
import com.example.ui.theme.ParchmentBase
import com.example.ui.theme.ParchmentBorder
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.StampRed

@Composable
fun SuspectItemRow(
    suspect: Suspect,
    isEliminated: Boolean,
    onInspect: () -> Unit,
    onToggleEliminate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val alpha = if (isEliminated) 0.5f else 1.0f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
            .testTag("suspect_card_${suspect.id}")
            .clickable { onInspect() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = BorderStroke(
            1.dp,
            if (isEliminated) CrimsonRed.copy(alpha = 0.5f) else CardBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Avatar Circle
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(suspect.avatarColorHex))
                    .border(1.5.dp, AntiqueGold, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = suspect.avatarInitials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            // Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = suspect.name,
                    color = ParchmentLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textDecoration = if (isEliminated) TextDecoration.LineThrough else TextDecoration.None,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = suspect.roleTitle,
                    color = AntiqueGold,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Quick Actions
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Eliminate Toggle Button
                OutlinedButton(
                    onClick = onToggleEliminate,
                    modifier = Modifier
                        .height(32.dp)
                        .testTag("eliminate_btn_${suspect.id}"),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(
                        1.dp,
                        if (isEliminated) EmeraldGreen else CrimsonRed
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isEliminated) EmeraldGreen.copy(alpha = 0.15f) else CrimsonRed.copy(alpha = 0.15f)
                    ),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp)
                ) {
                    Text(
                        text = if (isEliminated) "Kembalikan" else "Coret",
                        color = if (isEliminated) EmeraldGreen else CrimsonRed,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                IconButton(
                    onClick = onInspect,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Lihat Alibi",
                        tint = AntiqueGold,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SuspectAlibiDialog(
    suspect: Suspect,
    clues: List<Clue>,
    revealedClueIds: Set<String>,
    isEliminated: Boolean,
    onDismiss: () -> Unit,
    onToggleEliminate: () -> Unit,
    onAccuse: () -> Unit
) {
    // Check if any revealed clue contradicts this suspect
    val hasContradiction = suspect.contradictionClueId != null &&
            revealedClueIds.contains(suspect.contradictionClueId)

    val contradictionClue = if (hasContradiction) {
        clues.find { it.id == suspect.contradictionClueId }
    } else null

    Dialog(onDismissRequest = onDismiss) {
        ParchmentCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            hasPin = true
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WaxSealStamp(
                        text = "BERKAS TERSANGKA",
                        rotation = -4f,
                        stampColor = StampRed
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = InkBrown
                        )
                    }
                }

                // Suspect Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(Color(suspect.avatarColorHex))
                            .border(2.dp, AntiqueGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = suspect.avatarInitials,
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                    }

                    Column {
                        Text(
                            text = suspect.name,
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = suspect.roleTitle,
                            color = CrimsonRed,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = suspect.bio,
                            color = InkMuted,
                            fontSize = 11.sp,
                            lineHeight = 14.sp
                        )
                    }
                }

                // Alibi Box
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, ParchmentBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "ALIBI RESMI:",
                            color = InkBrown,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "\"${suspect.fullAlibi}\"",
                            color = InkDark,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }

                // Contradiction Notice
                if (hasContradiction && contradictionClue != null) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = CrimsonRed.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, CrimsonRed.copy(alpha = 0.4f))
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Kontradiksi",
                                tint = CrimsonRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = "ALIBI TERBANTAH OLEH BUKTI!",
                                    color = CrimsonRed,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "Berdasarkan petunjuk '${contradictionClue.title}', alibi ini tidak cocok dengan kronologi kejadian. Tersangka dapat dicoret.",
                                    color = InkBrown,
                                    fontSize = 11.sp,
                                    lineHeight = 14.sp
                                )
                            }
                        }
                    }
                }

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onToggleEliminate()
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("dialog_eliminate_btn"),
                        border = BorderStroke(
                            1.dp,
                            if (isEliminated) EmeraldGreen else CrimsonRed
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = if (isEliminated) "Batalkan Coret" else "Coret Tersangka",
                            color = if (isEliminated) EmeraldGreen else CrimsonRed,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            onAccuse()
                            onDismiss()
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("dialog_accuse_btn"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StampRed
                        ),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Tuduh Pelaku",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
