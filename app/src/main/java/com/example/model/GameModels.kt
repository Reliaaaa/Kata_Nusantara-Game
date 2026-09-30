package com.example.model

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
    val clueHint: String = ""
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
