package com.example.model

enum class GameDifficulty(
    val title: String,
    val subtitle: String,
    val badge: String,
    val colorHex: Long,
    val initialSuspectNervousnessMultiplier: Float,
    val clueNervousnessBoost: Int,
    val wrongAccusePenalty: Int,
    val xpMultiplier: Float,
    val description: String
) {
    SANTAI(
        title = "Pemula / Santai",
        subtitle = "Mode Belajar Santai (6 Soal)",
        badge = "🟢 PEMULA",
        colorHex = 0xFF10B981,
        initialSuspectNervousnessMultiplier = 1.0f,
        clueNervousnessBoost = 30,
        wrongAccusePenalty = 10,
        xpMultiplier = 1.0f,
        description = "Menyajikan 6 soal kebahasaan dasar per kasus. Petunjuk otomatis melemahkan pembelaan tersangka."
    ),
    SEDANG(
        title = "Detektif Handal",
        subtitle = "Mode Standar Detektif (10 Soal)",
        badge = "🟡 SEDANG",
        colorHex = 0xFFF59E0B,
        initialSuspectNervousnessMultiplier = 0.8f,
        clueNervousnessBoost = 20,
        wrongAccusePenalty = 20,
        xpMultiplier = 1.25f,
        description = "Tingkat kesulitan standar dengan 10 soal kebahasaan per kasus. Membutuhkan kecermatan pencocokan bukti dan alibi."
    ),
    SANGAT_SULIT(
        title = "Sangat Sulit / Kasus Berat",
        subtitle = "Mode Kritis & Tantangan Panjang (15 Soal)",
        badge = "🔴 SANGAT SULIT",
        colorHex = 0xFFEF4444,
        initialSuspectNervousnessMultiplier = 0.5f,
        clueNervousnessBoost = 5,
        wrongAccusePenalty = 35,
        xpMultiplier = 1.75f,
        description = "Tantangan penuh dengan 15 soal kebahasaan sehari-hari per kasus. Tingkat kegugupan tersangka di sidang tetap SETARA (50%) sehingga membutuhkan analisis bukti kritis!"
    )
}

enum class ChallengeType(val label: String, val badgeColorHex: Long) {
    SINONIM("SINONIM", 0xFF0F766E),
    ANTONIM("ANTONIM", 0xFFB45309),
    KATA_BAKU("KATA BAKU", 0xFF1D4ED8)
}

data class ChallengeQuestion(
    val id: String,
    val type: ChallengeType,
    val prompt: String,
    val contextReason: String,
    val targetWord: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val rewardClueId: String,
    val points: Int = 100,
    val eliminatedTrait: String? = null,
    val clueHint: String = "",
    val difficulty: GameDifficulty = GameDifficulty.SEDANG
)

data class Clue(
    val id: String,
    val title: String,
    val summary: String,
    val revealedDetail: String,
    val category: String, // "Waktu", "Fisik", "Bahasa", "Alibi"
    val isRevealed: Boolean = false
)

data class Suspect(
    val id: String,
    val name: String,
    val roleTitle: String,
    val bio: String,
    val initialAlibi: String,
    val fullAlibi: String,
    val avatarInitials: String,
    val avatarColorHex: Long,
    val contradictionClueId: String?,
    val isCulprit: Boolean,
    val confession: String,
    val isEliminated: Boolean = false,
    val isInvestigated: Boolean = false,
    val emojiIcon: String = "👤",
    val traits: List<String> = emptyList()
)

data class CaseData(
    val id: String,
    val numberCode: String, // e.g. "Kasus 01"
    val title: String,
    val subtitle: String,
    val region: String,
    val locationName: String,
    val drawableRes: Int,
    val physicalEvidenceTitle: String,
    val physicalEvidenceDesc: String,
    val storyIntro: String,
    val suspects: List<Suspect>,
    val clues: List<Clue>,
    val challenges: List<ChallengeQuestion>,
    val unlockXpRequired: Int = 0,
    val isUnlocked: Boolean = false,
    val isCompleted: Boolean = false,
    val starsEarned: Int = 0,
    val bestScore: Int = 0
)

data class DictionaryEntry(
    val id: String,
    val term: String,
    val category: ChallengeType,
    val definition: String,
    val nonStandardForm: String? = null,
    val pairOrOpposite: String? = null,
    val exampleInCase: String,
    val caseTag: String
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean = false,
    val xpBonus: Int = 150
)

data class DetectiveRank(
    val level: Int,
    val title: String,
    val minXp: Int,
    val badgeName: String,
    val description: String
)

data class DetectiveCharacter(
    val id: String,
    val name: String,
    val title: String,
    val quote: String,
    val bio: String,
    val specialtyPerk: String,
    val perkDescription: String,
    val avatarDrawableRes: Int,
    val themeColorHex: Long
)

enum class StorylineStage(val stageNumber: Int, val title: String) {
    CHARACTER_SELECTION(1, "Pilih Karakter"),
    CHAPTER_PROLOGUE(2, "Prolog Cerita"),
    SOLVE_QUESTIONS(3, "Kerjakan Soal"),
    EVIDENCE_AND_INTERROGATION(4, "Analisis Bukti"),
    FINAL_CONFRONTATION(5, "Tuduh Pelaku"),
    CHAPTER_EPILOGUE(6, "Epilog Bab")
}
