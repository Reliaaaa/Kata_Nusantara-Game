package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AmberWarning
import com.example.ui.theme.AntiqueGold
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CorkBoard
import com.example.ui.theme.CrimsonRed
import com.example.ui.theme.GoldBright
import com.example.ui.theme.InkBrown
import com.example.ui.theme.InkDark
import com.example.ui.theme.InkMuted
import com.example.ui.theme.ParchmentBase
import com.example.ui.theme.ParchmentBorder
import com.example.ui.theme.ParchmentDark
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.StampRed
import com.example.ui.theme.StringRed
import com.example.ui.theme.WoodBoard

@Composable
fun PushPin(
    modifier: Modifier = Modifier,
    color: Color = CrimsonRed
) {
    Box(
        modifier = modifier
            .size(16.dp)
            .shadow(4.dp, CircleShape)
            .background(
                Brush.radialGradient(
                    colors = listOf(Color.White, color, Color.Black.copy(alpha = 0.6f))
                ),
                CircleShape
            )
            .border(1.dp, Color.Black.copy(alpha = 0.4f), CircleShape)
    )
}

@Composable
fun ParchmentCard(
    modifier: Modifier = Modifier,
    elevation: Dp = 6.dp,
    hasPin: Boolean = false,
    pinOffset: Dp = (-6).dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = ParchmentBase
            ),
            border = BorderStroke(1.5.dp, ParchmentBorder)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                ParchmentLight,
                                ParchmentBase,
                                ParchmentDark.copy(alpha = 0.7f)
                            )
                        )
                    )
            ) {
                content()
            }
        }

        if (hasPin) {
            PushPin(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = pinOffset)
            )
        }
    }
}

@Composable
fun WaxSealStamp(
    text: String,
    modifier: Modifier = Modifier,
    rotation: Float = -12f,
    stampColor: Color = StampRed
) {
    Surface(
        modifier = modifier
            .rotate(rotation)
            .border(2.dp, stampColor, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = stampColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = text.uppercase(),
            color = stampColor,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
            letterSpacing = 1.5.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun DetectiveButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isPrimary: Boolean = true,
    testTag: String = "detective_button"
) {
    val bgBrush = if (isPrimary) {
        Brush.verticalGradient(
            colors = listOf(GoldBright, AntiqueGold)
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(CorkBoard, WoodBoard)
        )
    }

    val textColor = if (isPrimary) InkDark else ParchmentLight
    val borderColor = if (isPrimary) Color(0xFF8C6B10) else CardBorder

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .shadow(4.dp, RoundedCornerShape(8.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.5.dp, borderColor)
    ) {
        Box(
            modifier = Modifier
                .background(bgBrush, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun HeartCounter(
    remaining: Int,
    max: Int = 3,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Nyawa",
            tint = CrimsonRed,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "$remaining/$max",
            color = ParchmentLight,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StarRatingBar(
    stars: Int,
    maxStars: Int = 3,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isFilled = i <= stars
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = if (isFilled) "Bintang Terisi" else "Bintang Kosong",
                tint = if (isFilled) AntiqueGold else Color.Gray.copy(alpha = 0.4f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun RedThreadOverlay(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val threadColor = StringRed.copy(alpha = 0.85f)
        val strokeWidth = 2.5f

        // Draw connecting red yarn threads
        // Point 1: Clues pin to Evidence pin
        drawLine(
            color = threadColor,
            start = Offset(w * 0.22f, h * 0.25f),
            end = Offset(w * 0.52f, h * 0.32f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Point 2: Evidence pin to Suspects pin
        drawLine(
            color = threadColor,
            start = Offset(w * 0.52f, h * 0.32f),
            end = Offset(w * 0.35f, h * 0.70f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Point 3: Suspects to Conclusion pin
        drawLine(
            color = threadColor,
            start = Offset(w * 0.35f, h * 0.70f),
            end = Offset(w * 0.78f, h * 0.75f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Point 4: Evidence to Location pin
        drawLine(
            color = threadColor,
            start = Offset(w * 0.52f, h * 0.32f),
            end = Offset(w * 0.80f, h * 0.28f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}
