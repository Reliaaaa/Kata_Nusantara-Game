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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.theme.ParchmentLight
import com.example.ui.theme.WoodBoard
import com.example.ui.theme.WoodDark
import com.example.viewmodel.GameViewModel
import com.example.viewmodel.GroundingUiState

@Composable
fun GroundingScreen(
    groundingState: GroundingUiState,
    viewModel: GameViewModel,
    onBack: () -> Unit
) {
    BackHandler { onBack() }

    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var inputQuery by remember { mutableStateOf("") }

    val searchPresets = listOf(
        "Kaidah baku kata 'Izin' vs 'Ijin' menurut KBBI resmi",
        "Etimologi kata 'Pusaka' dalam warisan budaya Melayu",
        "Sejarah naskah wasiat Sultan di Keraton Yogyakarta",
        "Tradisi penulisan lontar suci di Besakih Bali",
        "Kedudukan naskah Tambo Alam Minangkabau"
    )

    val mapPresets = listOf(
        "Keraton Ngayogyakarta Hadiningrat, Yogyakarta",
        "Pura Agung Besakih, Karangasem, Bali",
        "Balai Adat Indragiri Hulu, Riau",
        "Istano Basa Pagaruyung, Tanah Datar, Sumatra Barat"
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
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("grounding_back_btn")
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
                        text = "INTELIJEN GROUNDING",
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

            // Tabs: Google Search Grounding vs Google Maps Grounding
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = WoodBoard,
                contentColor = AntiqueGold
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = {
                        selectedTabIndex = 0
                        inputQuery = ""
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(16.dp))
                            Text(text = "Google Search (KBBI)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = {
                        selectedTabIndex = 1
                        inputQuery = ""
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(16.dp))
                            Text(text = "Google Maps (Lokasi TKP)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Query Input
            OutlinedTextField(
                value = inputQuery,
                onValueChange = { inputQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("grounding_query_input"),
                placeholder = {
                    Text(
                        text = if (selectedTabIndex == 0) "Ketik kata atau topik sejarah untuk verifikasi Search..." else "Ketik nama tempat/situs kebudayaan untuk verifikasi Maps...",
                        color = ParchmentLight.copy(alpha = 0.5f),
                        fontSize = 12.sp
                    )
                },
                singleLine = true,
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

            Spacer(modifier = Modifier.height(8.dp))

            // Preset Query Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val presets = if (selectedTabIndex == 0) searchPresets else mapPresets
                items(presets) { preset ->
                    FilterChip(
                        selected = inputQuery == preset,
                        onClick = { inputQuery = preset },
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

            Spacer(modifier = Modifier.height(10.dp))

            // Action Button
            Button(
                onClick = {
                    if (selectedTabIndex == 0) {
                        viewModel.runSearchGrounding(inputQuery)
                    } else {
                        viewModel.runMapsGrounding(inputQuery)
                    }
                },
                enabled = inputQuery.isNotBlank() && !groundingState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("grounding_execute_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = AntiqueGold),
                shape = RoundedCornerShape(8.dp)
            ) {
                if (groundingState.isLoading) {
                    CircularProgressIndicator(color = InkDark, modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(text = "Menyelidiki melalui Gemini 3.5 Flash...", color = InkDark, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                            imageVector = if (selectedTabIndex == 0) Icons.Default.Language else Icons.Default.Place,
                            contentDescription = null,
                            tint = InkDark,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (selectedTabIndex == 0) "TELUSURI DENGAN GOOGLE SEARCH" else "PANTAU DENGAN GOOGLE MAPS",
                            color = InkDark,
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Error Message
            if (groundingState.errorMessage != null) {
                Surface(
                    color = CrimsonRed.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, CrimsonRed),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                ) {
                    Text(
                        text = groundingState.errorMessage,
                        color = Color(0xFFFF8A80),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            // Results Display
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (selectedTabIndex == 0 && groundingState.searchResult != null) {
                    val result = groundingState.searchResult

                    item {
                        ParchmentCard(
                            modifier = Modifier.fillMaxWidth(),
                            hasPin = true
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "HASIL GROUNDING SEARCH (KBBI & SEJARAH)",
                                        color = InkDark,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp,
                                        letterSpacing = 1.sp
                                    )
                                    WaxSealStamp(text = "TERVERIFIKASI", stampColor = EmeraldGreen, rotation = 0f)
                                }

                                Text(
                                    text = result.text,
                                    color = InkDark,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )

                                if (result.searchQueries.isNotEmpty()) {
                                    Text(
                                        text = "Kueri Pencarian Terhubung: " + result.searchQueries.joinToString(", "),
                                        color = InkMuted,
                                        fontSize = 11.sp
                                    )
                                }

                                if (result.sources.isNotEmpty()) {
                                    Text(
                                        text = "Sumber Rujukan Terverifikasi:",
                                        color = InkBrown,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                    result.sources.forEach { src ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Link, contentDescription = null, tint = AntiqueGold, modifier = Modifier.size(14.dp))
                                            Text(
                                                text = "${src.title}: ${src.uri}",
                                                color = Color(0xFF1565C0),
                                                fontSize = 11.sp,
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (selectedTabIndex == 1 && groundingState.mapsResult != null) {
                    val result = groundingState.mapsResult

                    item {
                        ParchmentCard(
                            modifier = Modifier.fillMaxWidth(),
                            hasPin = true
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "REKOGNISI MAPS GROUNDING (TKP)",
                                        color = InkDark,
                                        fontWeight = FontWeight.Black,
                                        fontSize = 12.sp,
                                        letterSpacing = 1.sp
                                    )
                                    WaxSealStamp(text = "PETA RESMI", stampColor = AntiqueGold, rotation = 0f)
                                }

                                Text(
                                    text = result.text,
                                    color = InkDark,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )

                                if (result.mapsPlaces.isNotEmpty()) {
                                    Text(
                                        text = "Titik Landmark Terdeteksi:",
                                        color = InkBrown,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    )
                                    result.mapsPlaces.forEach { place ->
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(imageVector = Icons.Default.Place, contentDescription = null, tint = CrimsonRed, modifier = Modifier.size(14.dp))
                                            Text(text = place, color = InkDark, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (!groundingState.isLoading) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = WoodBoard),
                            border = BorderStroke(1.dp, CardBorder)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = if (selectedTabIndex == 0) Icons.Default.Language else Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = AntiqueGold,
                                    modifier = Modifier.size(32.dp)
                                )
                                Text(
                                    text = if (selectedTabIndex == 0)
                                        "Gunakan Search Grounding untuk mengecek kaidah kata baku dan sejarah naskah secara aktual."
                                    else
                                        "Gunakan Maps Grounding untuk memantau titik koordinat dan keaslian geografi TKP di Nusantara.",
                                    color = ParchmentLight.copy(alpha = 0.8f),
                                    fontSize = 12.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
