package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.EnglishTranslationProvider
import com.example.model.ChallengeQuestion
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
import com.example.viewmodel.AnswerResult

@Composable
fun ChallengeModal(
    challenge: ChallengeQuestion,
    answerResult: AnswerResult?,
    onDismiss: () -> Unit,
    onSubmitAnswer: (Int) -> Unit
) {
    var selectedOptionIndex by remember { mutableIntStateOf(-1) }
    val letters = listOf("A", "B", "C", "D")
    val questionEn = remember(challenge.id) { EnglishTranslationProvider.getQuestionTranslation(challenge.id) }

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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Top Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Category & Difficulty Badges
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(challenge.type.badgeColorHex),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = challenge.type.label,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            color = Color(challenge.difficulty.colorHex),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = challenge.difficulty.badge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }

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

                // Prompt & English Subtitle
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = challenge.prompt,
                        color = InkBrown,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    if (questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                        Surface(
                            color = AntiqueGold.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp)
                        ) {
                            Text(
                                text = "SUBTITLE: Arti Kata \"${questionEn.targetWordMeaningEn}\"",
                                color = InkDark,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                // Target Word & Meaning
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, ParchmentBorder)
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = challenge.targetWord,
                            color = InkDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                        if (questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                            Text(
                                text = "Arti Kata: \"${questionEn.targetWordMeaningEn}\"",
                                color = AntiqueGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            text = challenge.contextReason,
                            color = InkMuted,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 14.sp
                        )
                    }
                }

                // Options (Grid of 2x2 or 4 rows) with English Subtitles
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    challenge.options.chunked(2).forEachIndexed { rowIndex, pair ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            pair.forEachIndexed { colIndex, optionText ->
                                val index = rowIndex * 2 + colIndex
                                val isSelected = selectedOptionIndex == index
                                val isAnswered = answerResult != null
                                val isCorrectOption = isAnswered && challenge.correctIndex == index
                                val isWrongSelection = isAnswered && answerResult?.selectedIndex == index && !answerResult.isCorrect
                                val optEn = questionEn?.optionsEn?.getOrNull(index)

                                val optionBorderColor = when {
                                    isCorrectOption -> EmeraldGreen
                                    isWrongSelection -> CrimsonRed
                                    isSelected -> AntiqueGold
                                    else -> ParchmentBorder
                                }

                                val optionBgColor = when {
                                    isCorrectOption -> EmeraldGreen.copy(alpha = 0.2f)
                                    isWrongSelection -> CrimsonRed.copy(alpha = 0.2f)
                                    isSelected -> AntiqueGold.copy(alpha = 0.25f)
                                    else -> Color.White.copy(alpha = 0.7f)
                                }

                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .testTag("option_btn_$index")
                                        .clickable(enabled = answerResult == null) {
                                            selectedOptionIndex = index
                                        },
                                    color = optionBgColor,
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.5.dp, optionBorderColor)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(
                                            text = "${letters.getOrElse(index) { "" }}.",
                                            color = InkBrown,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = optionText,
                                                color = InkDark,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 12.sp
                                            )
                                            if (optEn != null) {
                                                Text(
                                                    text = "↳ Arti: $optEn",
                                                    color = InkBrown.copy(alpha = 0.85f),
                                                    fontSize = 9.sp,
                                                    fontStyle = FontStyle.Italic
                                                )
                                            }
                                        }

                                        if (isCorrectOption) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Benar",
                                                tint = EmeraldGreen,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Feedback section if answered
                AnimatedVisibility(visible = answerResult != null) {
                    if (answerResult != null) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = if (answerResult.isCorrect) EmeraldGreen.copy(alpha = 0.15f) else CrimsonRed.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, if (answerResult.isCorrect) EmeraldGreen else CrimsonRed)
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (answerResult.isCorrect) Icons.Default.CheckCircle else Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = if (answerResult.isCorrect) EmeraldGreen else CrimsonRed,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (answerResult.isCorrect) "JAWABAN TEPAT! PETUNJUK TERBUKA" else "JAWABAN KURANG TEPAT (-1 NYAWA)",
                                        color = if (answerResult.isCorrect) EmeraldGreen else CrimsonRed,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }

                                Text(
                                    text = answerResult.explanation,
                                    color = InkDark,
                                    fontSize = 11.sp,
                                    lineHeight = 15.sp
                                )

                                if (questionEn != null && questionEn.targetWordMeaningEn.isNotBlank()) {
                                    Text(
                                        text = "Arti Kata: \"${questionEn.targetWordMeaningEn}\"",
                                        color = InkBrown,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontStyle = FontStyle.Italic
                                    )
                                }

                                if (answerResult.isCorrect && answerResult.unlockedClueTitle != null) {
                                    Text(
                                        text = "🔍 Petunjuk Baru: \"${answerResult.unlockedClueTitle}\"",
                                        color = AntiqueGold,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }

                // Action Button
                if (answerResult == null) {
                    Button(
                        onClick = {
                            if (selectedOptionIndex >= 0) {
                                onSubmitAnswer(selectedOptionIndex)
                            }
                        },
                        enabled = selectedOptionIndex >= 0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("submit_answer_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AntiqueGold
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "KIRIM JAWABAN",
                            color = InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("continue_after_answer_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (answerResult.isCorrect) EmeraldGreen else AntiqueGold
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "KEMBALI KE PAPAN KASUS",
                            color = if (answerResult.isCorrect) Color.White else InkDark,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
