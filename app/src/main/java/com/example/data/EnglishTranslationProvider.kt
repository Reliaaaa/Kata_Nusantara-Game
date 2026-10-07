package com.example.data

import com.example.model.ChallengeType

data class CaseEnglishTranslation(
    val titleEn: String,
    val subtitleEn: String,
    val locationEn: String,
    val physicalEvidenceTitleEn: String,
    val physicalEvidenceDescEn: String,
    val storyIntroEn: String,
    val culturalNotesEn: List<String>
)

data class QuestionEnglishTranslation(
    val promptEn: String,
    val contextReasonEn: String,
    val targetWordMeaningEn: String,
    val optionsEn: List<String>,
    val explanationEn: String,
    val grammarRuleEn: String
)

data class SuspectEnglishTranslation(
    val roleTitleEn: String,
    val bioEn: String,
    val alibiEn: String,
    val confessionEn: String
)

object EnglishTranslationProvider {

    fun getCaseTranslation(caseId: String): CaseEnglishTranslation? = caseTranslations[caseId]

    fun getQuestionTranslation(questionId: String): QuestionEnglishTranslation? = questionTranslations[questionId]

    fun getSuspectTranslation(suspectId: String): SuspectEnglishTranslation? = suspectTranslations[suspectId]

    private val caseTranslations = mapOf(
        "case_01" to CaseEnglishTranslation(
            titleEn = "Mystery of the Lost Sacred Heirloom",
            subtitleEn = "Centuries-Old Sacred Keris Disappears from Cultural Museum",
            locationEn = "Museum of Culture, Yogyakarta, Java",
            physicalEvidenceTitleEn = "Open Glass Display Cabinet",
            physicalEvidenceDescEn = "A secured glass exhibition cabinet housing the sacred heirloom keris was found wide open without any lock damage.",
            storyIntroEn = "At the Yogyakarta Cultural Museum, a sacred keris dagger dating back hundreds of years was reported missing early this morning. The museum curator has requested you — the Indonesian Language Detective — to investigate this case. There are suspects who were near the crime scene. Solve the Indonesian linguistic ciphers (synonyms, antonyms, and standard spelling) to unlock clues, expose false alibis, and catch the culprit!",
            culturalNotesEn = listOf(
                "Keris: Traditional Indonesian asymmetrical dagger imbued with spiritual significance and recognized by UNESCO as an intangible cultural heritage.",
                "Kosa Kata: Key Indonesian vocabulary and cultural heritage.",
                "Museum Kebudayaan: Cultural repository preserving royal Javanese manuscripts and relics."
            )
        ),
        "case_02" to CaseEnglishTranslation(
            titleEn = "The Lost Will of Yogyakarta Royal Palace",
            subtitleEn = "Linguistic Conspiracy Behind the Walls of Bale Prabeyo",
            locationEn = "Yogyakarta Royal Palace (Keraton Ngayogyakarta)",
            physicalEvidenceTitleEn = "Altered Royal Seal Letter",
            physicalEvidenceDescEn = "A golden silk envelope found unsealed, containing an antique royal testament with words deliberately manipulated.",
            storyIntroEn = "During an imperial evening ceremony at the Yogyakarta Palace, the Sultan's confidential will kept in Bale Prabeyo vanished. Only high-ranking court retainers (abdi dalem) knowing palace secret corridors had access. The perpetrator attempted to alter the wording using formal Javanese loanwords. Use your linguistic sharpness to expose the imposter!",
            culturalNotesEn = listOf(
                "Abdi Dalem: Dedicated royal court retainers serving the Sultan.",
                "Bale Prabeyo: Sacred repository pavilion within the Keraton palace grounds.",
                "Kata Serapan: Indonesian words borrowed and adapted from Javanese, Sanskrit, Arabic, and Dutch."
            )
        ),
        "case_03" to CaseEnglishTranslation(
            titleEn = "Word Trail on the Island of the Gods",
            subtitleEn = "Theft of the Sacred Palm-Leaf Scripture on Mount Agung's Slopes",
            locationEn = "Pura Agung Besakih, Karangasem, Bali",
            physicalEvidenceTitleEn = "Crudely Carved Substitute Palm-Leaf (Lontar)",
            physicalEvidenceDescEn = "A counterfeit palm-leaf manuscript with hasty pangutik knife etchings was left behind on the main shrine.",
            storyIntroEn = "Ahead of the sacred Bhatara Turun Kabeh ritual, an ancient palm-leaf manuscript (lontar) containing sacred purification hymns vanished from the main sanctuary. Footprints in incense ash reveal someone climbed the wooden bell tower (bale kulkul) during a midnight fog. Decipher Indonesian antonyms and formal lexicon to protect this sacred Balinese heritage!",
            culturalNotesEn = listOf(
                "Lontar: Traditional manuscripts written on dried palmyra leaves using a special stylus (pangutik).",
                "Pura Besakih: The 'Mother Temple' of Bali, situated on the slopes of Mount Agung.",
                "Kidung: Sacred traditional lyrical chants sung during temple rituals."
            )
        ),
        "case_04" to CaseEnglishTranslation(
            titleEn = "Secrets of the Minangkabau Manuscript",
            subtitleEn = "Torn Pages of Ancestral Tambo Behind the Carvings of Rumah Gadang",
            locationEn = "Istano Basa Pagaruyung, Tanah Datar, West Sumatra",
            physicalEvidenceTitleEn = "Buffalo-Horn Lead Seal",
            physicalEvidenceDescEn = "An antique lead seal depicting buffalo horns detached from the traditional Minang Tambo scroll.",
            storyIntroEn = "The Tambo Alam Minangkabau document recording maternal lineage and customary land distribution was stolen from the attic chamber of Rumah Gadang. Master Indonesian vocabulary, standard terminology, and figurative phrases to resolve this tribal heritage dispute!",
            culturalNotesEn = listOf(
                "Rumah Gadang: Traditional Minangkabau communal house with distinctive curved horn-like roofs.",
                "Tambo: Traditional chronicles narrating the origins, customary laws, and genealogy of Minangkabau clans.",
                "Ninik Mamak: Revered maternal uncles and lineage elders holding customary authority."
            )
        ),
        "case_05" to CaseEnglishTranslation(
            titleEn = "Mystery of the Sriwijaya Golden Inscription",
            subtitleEn = "Gold Inscription Sheet with Pallava Script Vanishes Along the Musi River",
            locationEn = "Sriwijaya Archaeological Park, Palembang, South Sumatra",
            physicalEvidenceTitleEn = "Imitation Brass Plate",
            physicalEvidenceDescEn = "A cheap polished brass sheet placed in the display case of the 7th-century golden royal inscription.",
            storyIntroEn = "A 7th-century gold royal inscription from the maritime empire of Sriwijaya was reported missing just hours before an international exhibition opening in Palembang. Trace the docks of the Musi River, solve nautical terms, synonyms, and standard Indonesian vocabulary to recover this priceless treasure!",
            culturalNotesEn = listOf(
                "Sriwijaya: Ancient thalassocratic Indonesian empire that dominated maritime Southeast Asia from the 7th to 12th centuries.",
                "Aksara Pallawa: Ancient Brahmic script used in early Southeast Asian inscriptions, including Old Malay.",
                "Sungai Musi: Legendary river flowing through Palembang, historically bustling with international spice trade."
            )
        )
    )

    private val questionTranslations = mapOf(
        // CASE 01
        "q_01_1" to QuestionEnglishTranslation(
            promptEn = "An eyewitness saw the suspect 'berlari' (running) away from the museum.\n\nChoose the word closest in meaning (synonym) to 'berlari':",
            contextReasonEn = "Identify the synonymous verb to determine the suspect's speed of movement.",
            targetWordMeaningEn = "'Berlari' = To run (moving rapidly on foot).",
            optionsEn = listOf("To walk", "To hurry / rush", "To crawl", "To swim"),
            explanationEn = "'Bergegas' is a synonym of 'berlari' — both denote moving with swiftness and urgency.",
            grammarRuleEn = "Sinonim (Synonym): Indonesian words sharing closely related core meanings."
        ),
        "q_01_2" to QuestionEnglishTranslation(
            promptEn = "In the alibi statement, the suspect claimed to be in a place that was 'ramai' (crowded).\n\nChoose the opposite word (antonym) of 'ramai':",
            contextReasonEn = "Expose the suspect's false claim about the atmosphere of the location.",
            targetWordMeaningEn = "'Ramai' = Crowded, bustling, lively.",
            optionsEn = listOf("Festive", "Noisy", "Quiet / Deserted", "Rowdy"),
            explanationEn = "'Sepi' (quiet/deserted) is the antonym of 'ramai' (crowded/bustling).",
            grammarRuleEn = "Antonim (Antonym): Word pairs representing contrasting semantic poles."
        ),
        "q_01_3" to QuestionEnglishTranslation(
            promptEn = "On the permit slip found near the crime scene, check the spelling according to correct Indonesian:\n\nWhich spelling is CORRECT?",
            contextReasonEn = "Verify the authenticity of the official stamp left behind by the perpetrator.",
            targetWordMeaningEn = "Permission / permit authorization.",
            optionsEn = listOf("Permission", "Permission", "Permission", "Permission"),
            explanationEn = "The standard Indonesian spelling is 'Izin' with a 'z' (derived from Arabic 'idhn'). 'Ijin' is non-standard colloquially.",
            grammarRuleEn = "Kata Baku (Standard Word): Official Indonesian orthography."
        ),
        "q_01_4" to QuestionEnglishTranslation(
            promptEn = "The incident report states the royal keris had 'lenyap' (vanished) from the showcase.\n\nChoose the word closest in meaning (synonym) to 'lenyap':",
            contextReasonEn = "Ascertain the condition of the display storage.",
            targetWordMeaningEn = "'Lenyap' = Vanished, disappeared, lost.",
            optionsEn = listOf("To appear", "To be lost / gone", "To shine", "To increase"),
            explanationEn = "'Hilang' is a synonym of 'lenyap' — both indicate no longer being visible or present.",
            grammarRuleEn = "Sinonim: 'Lenyap' and 'Hilang' describe sudden disappearance."
        ),
        "q_01_5" to QuestionEnglishTranslation(
            promptEn = "A guard noted the suspect appeared 'tenang' (calm) when walking past the gate.\n\nChoose the opposite word (antonym) of 'tenang':",
            contextReasonEn = "Analyze the perpetrator's emotional state and body language.",
            targetWordMeaningEn = "'Tenang' = Calm, serene, peaceful.",
            optionsEn = listOf("Peaceful", "Nervous / Anxious", "Relaxed", "Patient"),
            explanationEn = "'Gugup' (nervous/flustered) is the antonym of 'tenang' (calm/collected).",
            grammarRuleEn = "Antonim: 'Tenang' (composed) vs 'Gugup' (jittery/panicked)."
        ),
        "q_01_6" to QuestionEnglishTranslation(
            promptEn = "On a receipt near the crime scene, check the spelling of the medicine shop:\n\nWhich spelling is correct?",
            contextReasonEn = "Verify the receipt from the nearby shop.",
            targetWordMeaningEn = "Pharmacy / drugstore.",
            optionsEn = listOf("Pharmacy", "Pharmacy", "Pharmacy", "Pharmacy"),
            explanationEn = "The standard Indonesian word is 'Apotek' with an 'e', loaned from Dutch 'apotheek'.",
            grammarRuleEn = "Kata Baku: Loanwords from Dutch '-theek' become '-tek' in Indonesian (e.g., apotek, diskotek, hipotek)."
        ),
        "q_01_7" to QuestionEnglishTranslation(
            promptEn = "On the museum ticket log, there is a word often misspelled by visitors.\n\nWhich spelling is correct?",
            contextReasonEn = "Examine the visitor ticketing queue log.",
            targetWordMeaningEn = "To queue / line up.",
            optionsEn = listOf("To line up / queue", "To line up / queue", "To line up / queue", "To line up / queue"),
            explanationEn = "The standard form is 'Antre' (the verb is 'mengantre', not 'mengantri').",
            grammarRuleEn = "Kata Baku: 'Antre' uses 'e', loaned from Dutch 'aantreden/entrée'."
        ),
        "q_01_8" to QuestionEnglishTranslation(
            promptEn = "A witness described the stolen keris as being very 'kuno' (ancient).\n\nWhat is a synonym of the word 'kuno'?",
            contextReasonEn = "Understand the historical value of the stolen artifact.",
            targetWordMeaningEn = "'Kuno' = Ancient, antique, primeval.",
            optionsEn = listOf("Modern", "Antique / Ancient", "Sophisticated", "New"),
            explanationEn = "'Purba' and 'Antik' are synonyms of 'kuno' designating relics from ancient past epochs.",
            grammarRuleEn = "Sinonim: Describing historical depth in Indonesian literature."
        ),
        "q_01_9" to QuestionEnglishTranslation(
            promptEn = "In the night watchman's duty log, verify the spelling of the guard rotation schedule:\n\nWhich spelling is correct?",
            contextReasonEn = "Check the night patrol timetable log.",
            targetWordMeaningEn = "Schedule / timetable.",
            optionsEn = listOf("Schedule / timetable", "Schedule / timetable", "Schedule / timetable", "Schedule / timetable"),
            explanationEn = "The correct form is 'Jadwal' with a 'w' (borrowed from Arabic 'jadwal').",
            grammarRuleEn = "Kata Baku: Arabic loanwords with 'waw' typically retain 'w' in standard Indonesian (jadwal)."
        ),
        "q_01_10" to QuestionEnglishTranslation(
            promptEn = "The rear corridor traversed by the suspect was described as 'gelap' (dark).\n\nChoose the opposite word (antonym) of 'gelap':",
            contextReasonEn = "Determine illumination conditions in the hallway during the crime.",
            targetWordMeaningEn = "'Gelap' = Dark, obscure, dim.",
            optionsEn = listOf("Gloomy", "Dismal", "Bright / Light", "Blurred"),
            explanationEn = "'Terang' (bright/illuminated) is the direct antonym of 'gelap' (dark).",
            grammarRuleEn = "Antonim: Opposites of light intensity in descriptive Indonesian."
        ),
        "q_01_11" to QuestionEnglishTranslation(
            promptEn = "On the museum notice board, there is a message written for visitors.\n\nWhich spelling is correct according to standard Indonesian?",
            contextReasonEn = "Examine the reminder note written by the museum head.",
            targetWordMeaningEn = "'Nasihat' = Advice / counsel / moral guidance.",
            optionsEn = listOf("Nasehat", "Nasihat", "Nasihat-an", "Nasehad"),
            explanationEn = "The standard spelling according to KBBI is 'Nasihat' with an 'i', not 'Nasehat'.",
            grammarRuleEn = "Kata Baku: Standard Indonesian orthography for advice."
        ),
        "q_01_12" to QuestionEnglishTranslation(
            promptEn = "The detective praised the night patrol officer who is always 'waspada' (vigilant) on duty.\n\nChoose the synonym of 'waspada':",
            contextReasonEn = "Understand the alertness of the night watchman when the incident occurred.",
            targetWordMeaningEn = "'Waspada' = Vigilant, watchful, alert, cautious.",
            optionsEn = listOf("Careless", "Alert / Watchful", "Ignore", "Sleep"),
            explanationEn = "'Siaga' or 'Hati-hati' is a synonym of 'waspada' — both mean being constantly prepared for danger.",
            grammarRuleEn = "Sinonim: Words denoting vigilance and readiness."
        ),
        "q_01_13" to QuestionEnglishTranslation(
            promptEn = "Outside the exhibition room, the main museum corridor felt very 'ramai' (crowded).\n\nChoose the opposite word (antonym) of 'ramai':",
            contextReasonEn = "Examine the contradiction in the atmosphere of the suspect's hiding place.",
            targetWordMeaningEn = "'Ramai' = Crowded, busy, bustling.",
            optionsEn = listOf("Festive", "Quiet / Deserted", "Noisy", "Crowded"),
            explanationEn = "'Sepi' or 'Lengang' (quiet/deserted) is the antonym of 'ramai'.",
            grammarRuleEn = "Antonim: Bustling vs quiet/empty location states."
        ),
        "q_01_14" to QuestionEnglishTranslation(
            promptEn = "Antique registration documents were arranged neatly inside the filing cabinet.\n\nWhich spelling is correct?",
            contextReasonEn = "Verify the authenticity of the file arrangement of sacred heirlooms.",
            targetWordMeaningEn = "'Rapi' = Neat, orderly, tidy.",
            optionsEn = listOf("Rapih", "Rapi", "Rapie", "Rapihh"),
            explanationEn = "The standard spelling according to KBBI is 'Rapi' without an 'h' at the end.",
            grammarRuleEn = "Kata Baku: Correct Indonesian ending without unneeded 'h'."
        ),
        "q_01_15" to QuestionEnglishTranslation(
            promptEn = "The museum curator's report notes the keris as an 'indah' (beautiful) work of art.\n\nChoose the synonym of 'indah':",
            contextReasonEn = "Uncover the aesthetic value of the royal palace heirloom.",
            targetWordMeaningEn = "'Indah' = Beautiful, fine, lovely.",
            optionsEn = listOf("Bad / Ugly", "Fine / Beautiful", "Coarse", "Worn out"),
            explanationEn = "'Elok' or 'Cantik' is the synonym of 'indah' describing exquisite physical beauty.",
            grammarRuleEn = "Sinonim: Describing artistic beauty and aesthetic value."
        ),

        // CASE 02
        "q_02_1" to QuestionEnglishTranslation(
            promptEn = "Determine the synonym of the word 'muslihat':",
            contextReasonEn = "Understand the cunning tactic used to alter royal documents.",
            targetWordMeaningEn = "'Muslihat' = Deception, stratagem, trickery.",
            optionsEn = listOf("Ruse / Cunning trick", "Wisdom", "Honesty", "Physical strength"),
            explanationEn = "'Muslihat' signifies a cunning ploy or deceptive maneuver.",
            grammarRuleEn = "Sinonim: High-register Indonesian word for strategic deception."
        ),
        "q_02_2" to QuestionEnglishTranslation(
            promptEn = "Choose the correct antonym (opposite word) of 'autentik' (authentic):",
            contextReasonEn = "Expose the fraudulent nature of the smuggled manuscript.",
            targetWordMeaningEn = "'Autentik' = Authentic, genuine, legally certified.",
            optionsEn = listOf("Fake / Counterfeit", "Pure", "Official", "Ancient"),
            explanationEn = "The opposite of 'Autentik' (genuine, valid) is 'Palsu' (counterfeit) or 'Tiruan' (imitation).",
            grammarRuleEn = "Antonim: Legal and forensic document terms in Indonesian."
        ),
        "q_02_3" to QuestionEnglishTranslation(
            promptEn = "Which spelling of this risk warning word is correct?",
            contextReasonEn = "Word written on the warning label on the royal testament cover.",
            targetWordMeaningEn = "Risk / hazard.",
            optionsEn = listOf("Risk / hazard", "Risk / hazard", "Risk / hazard", "Risk / hazard"),
            explanationEn = "Standard Indonesian uses 'Risiko' with the letter 'i', not 'Resiko'.",
            grammarRuleEn = "Kata Baku: Modern Indonesian spelling standardizes Dutch 'risico' into 'risiko'."
        ),
        "q_02_4" to QuestionEnglishTranslation(
            promptEn = "Choose the standard Indonesian form for the polite invitation 'please':",
            contextReasonEn = "Introductory phrase in the royal banquet letter.",
            targetWordMeaningEn = "Please / be invited to.",
            optionsEn = listOf("Please / welcome", "Please / welcome", "Please / welcome", "Please / welcome"),
            explanationEn = "The standard form is 'Silakan' without an 'h', derived from the root word 'sila' (to welcome/invite politely).",
            grammarRuleEn = "Kata Baku: Adding unneeded 'h' is a very common colloquial error; always use 'silakan'."
        ),
        "q_02_5" to QuestionEnglishTranslation(
            promptEn = "Which spelling of 'analysis' is correct in Indonesian?",
            contextReasonEn = "Forensic report on the royal parchment ink composition.",
            targetWordMeaningEn = "Analysis / analytical study.",
            optionsEn = listOf("Analysis", "Analysis", "Analysis", "Analysis"),
            explanationEn = "The standard form in Indonesian is 'Analisis' with '-is' (adapted from Dutch 'analyse' / English 'analysis').",
            grammarRuleEn = "Kata Baku: Scientific nouns ending in '-sis' maintain '-is' in standard Indonesian (analisis, sintesis, hipotesis)."
        ),
        "q_02_6" to QuestionEnglishTranslation(
            promptEn = "Determine the synonym of the royal virtue term 'luhur':",
            contextReasonEn = "Honorific term describing the noble ancestral legacy.",
            targetWordMeaningEn = "'Luhur' = Noble, sublime, exalted.",
            optionsEn = listOf("Noble / Commendable", "Arrogant", "Simple", "Stubborn"),
            explanationEn = "'Luhur' means sublime, noble, and honorable in moral character and heritage.",
            grammarRuleEn = "Sinonim: Elevated literary Indonesian describing moral and royal excellence."
        ),
        "q_02_7" to QuestionEnglishTranslation(
            promptEn = "Choose the antonym (opposite word) of 'purna' (complete/finished):",
            contextReasonEn = "Status of palace retainer duties on the night of the incident.",
            targetWordMeaningEn = "'Purna' = Complete, fulfilled, consummate.",
            optionsEn = listOf("Incomplete / Initial", "Perfect", "Ended", "Intact"),
            explanationEn = "'Purna' means finished or perfected. Its opposite is 'Belum selesai' (incomplete).",
            grammarRuleEn = "Antonim: Sanskrit loanwords frequently used in formal Indonesian civil titles."
        ),
        "q_02_8" to QuestionEnglishTranslation(
            promptEn = "Which spelling of 'shirt/socks' is correct in Indonesian?",
            contextReasonEn = "Witness clothing when departing the royal pavilion.",
            targetWordMeaningEn = "T-shirt / undershirt / socks (kaus kaki).",
            optionsEn = listOf("Shirt / undershirt", "Shirt / undershirt", "Shirt / undershirt", "Shirt / undershirt"),
            explanationEn = "Standard Indonesian spells it 'Kaus' with 'u' (e.g. kaus oblong, kaus kaki), though 'kaos' is colloquially rampant.",
            grammarRuleEn = "Kata Baku: Loanwords with diphthongs standardizing into 'au'."
        ),
        "q_02_9" to QuestionEnglishTranslation(
            promptEn = "Architectural records note the foundational structure of the pavilion.\n\nWhich spelling is correct?",
            contextReasonEn = "Secret tunnel blueprint underneath the royal palace foundation.",
            targetWordMeaningEn = "Foundation / structural footing.",
            optionsEn = listOf("Foundation", "Foundation", "Foundation", "Foundation"),
            explanationEn = "The standard form is 'Fondasi' with an 'F' (borrowed from Dutch 'fundatie').",
            grammarRuleEn = "Kata Baku: Standard Indonesian adopts 'f' for Dutch 'f/ph' roots (fondasi, formal, fakta)."
        ),
        "q_02_10" to QuestionEnglishTranslation(
            promptEn = "A royal poem refers to ancestral virtue as being 'abadi' (eternal).\n\nDetermine the antonym (opposite) of 'abadi':",
            contextReasonEn = "Uncover philosophical nuances in the royal testament.",
            targetWordMeaningEn = "'Abadi' = Eternal, perpetual, everlasting.",
            optionsEn = listOf("Transient / Ephemeral", "Eternal", "Enduring", "Fixed"),
            explanationEn = "The opposite of 'Abadi' (everlasting) is 'Fana' (transient/mortal/ephemeral).",
            grammarRuleEn = "Antonim: Metaphysical and poetic Indonesian terminology."
        ),
        "q_02_11" to QuestionEnglishTranslation(
            promptEn = "An eyewitness described the appearance of the royal retainer as very 'rapi' (neat).\n\nWhich word is a synonym of 'rapi'?",
            contextReasonEn = "Uncover the neatness of attire among witnesses near Bale Prabeyo.",
            targetWordMeaningEn = "'Rapi' = Neat, tidy, well-organized.",
            optionsEn = listOf("Orderly / Clean", "Messy", "Dirty", "Disorganized"),
            explanationEn = "'Teratur' or 'Bersih' is a synonym of 'rapi'.",
            grammarRuleEn = "Sinonim: Orderliness and cleanliness terms."
        ),
        "q_02_12" to QuestionEnglishTranslation(
            promptEn = "The suspect presented a palace education graduation certificate.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Verify the nobility diploma document.",
            targetWordMeaningEn = "'Ijazah' = Diploma / graduation certificate.",
            optionsEn = listOf("Ijazah", "Ijasah", "Idjazah", "Ijasat"),
            explanationEn = "The standard spelling according to KBBI is 'Ijazah' with the letter 'z'.",
            grammarRuleEn = "Kata Baku: Arabic loanwords preserving 'z' sound."
        ),
        "q_02_13" to QuestionEnglishTranslation(
            promptEn = "The stone wall of Bale Prabeyo was built with a very 'kokoh' (sturdy) construction.\n\nChoose the antonym (opposite word) of 'kokoh':",
            contextReasonEn = "Analyze vulnerabilities in the secret palace tunnel wall.",
            targetWordMeaningEn = "'Kokoh' = Sturdy, solid, strong.",
            optionsEn = listOf("Fragile / Weak", "Strong", "Steadfast", "Tough"),
            explanationEn = "'Rapuh' (fragile) is the antonym of 'kokoh' (sturdy/solid).",
            grammarRuleEn = "Antonim: Structural durability terms in Indonesian."
        ),
        "q_02_14" to QuestionEnglishTranslation(
            promptEn = "The Sultan is known by all his people as a very 'dermawan' (generous) figure.\n\nChoose the synonym of 'dermawan':",
            contextReasonEn = "Understand the Sultan's generosity to the community around the keraton.",
            targetWordMeaningEn = "'Dermawan' = Generous, charitable, benevolent.",
            optionsEn = listOf("Generous / Big-hearted", "Stingy / Miserly", "Arrogant", "Selfish"),
            explanationEn = "'Murah Hati' is a synonym of 'dermawan' (generous/charitable).",
            grammarRuleEn = "Sinonim: Virtuous character traits in Indonesian."
        ),
        "q_02_15" to QuestionEnglishTranslation(
            promptEn = "On the antique manuscript cover there is a paper manufacturer trade stamp.\n\nWhich spelling is correct?",
            contextReasonEn = "Check the trade mark stamp on the historical paper.",
            targetWordMeaningEn = "'Merek' = Brand / trade mark.",
            optionsEn = listOf("Merek", "Merk", "Merck", "Merek-an"),
            explanationEn = "The standard Indonesian spelling according to KBBI is 'Merek' with an 'e'.",
            grammarRuleEn = "Kata Baku: Dutch loanword 'merk' becomes 'merek' in Indonesian."
        ),

        // CASE 03
        "q_03_1" to QuestionEnglishTranslation(
            promptEn = "Choose the correct spelling of 'practice / execution':",
            contextReasonEn = "Check research permit documentation for manuscript transcription.",
            targetWordMeaningEn = "Practice / practical application.",
            optionsEn = listOf("Practice / execution", "Practice / execution", "Practice / execution", "Practice / execution"),
            explanationEn = "Standard Indonesian uses 'Praktik' with 'i' (diserap dari Dutch 'praktijk').",
            grammarRuleEn = "Kata Baku: Nouns derived from Dutch '-ijk' become '-ik' in Indonesian (praktik, klinik, taktik)."
        ),
        "q_03_2" to QuestionEnglishTranslation(
            promptEn = "Determine the antonym (opposite word) of 'kekal' (immortal/eternal):",
            contextReasonEn = "Sacred hymn verses regarding the fleeting nature of the mortal world.",
            targetWordMeaningEn = "'Kekal' = Eternal, imperishable.",
            optionsEn = listOf("Mortal / Temporary", "Eternal", "Strong", "Holy"),
            explanationEn = "The opposite of 'Kekal' (perpetual) is 'Fana' (transient/perishable).",
            grammarRuleEn = "Antonim: Spiritual vocabulary in traditional Indonesian literature."
        ),
        "q_03_3" to QuestionEnglishTranslation(
            promptEn = "Choose the synonym of the ancestral heritage term 'pusaka':",
            contextReasonEn = "Decipher the meaning of the sacred relic passed down through generations.",
            targetWordMeaningEn = "'Pusaka' = Sacred ancestral heirloom.",
            optionsEn = listOf("Precious ancestral heirloom", "Expensive jewelry", "Commodity goods", "Weapon of war"),
            explanationEn = "'Pusaka' refers to sacred heirlooms handed down through generations regarded as culturally priceless.",
            grammarRuleEn = "Sinonim: Traditional Indonesian cultural concept of heritage."
        ),
        "q_03_4" to QuestionEnglishTranslation(
            promptEn = "Choose the correct spelling of 'intellectual / scholar':",
            contextReasonEn = "Title applied to scholars analyzing ancient Balinese palm-leaf manuscripts.",
            targetWordMeaningEn = "Scholar / intellectual / sagacious.",
            optionsEn = listOf("Scholar / intellectual", "Scholar / intellectual", "Scholar / intellectual", "Scholar / intellectual"),
            explanationEn = "Standard Indonesian spells it 'Cendekia' with 'e' (an intellectual person is called 'cendekiawan').",
            grammarRuleEn = "Kata Baku: Preserving original Sanskrit vowel structure in Indonesian."
        ),
        "q_03_5" to QuestionEnglishTranslation(
            promptEn = "Determine the synonym of 'sakral' (sacred/holy):",
            contextReasonEn = "Nature of the purification chants recited during the grand ceremony.",
            targetWordMeaningEn = "'Sakral' = Sacred, consecrated, holy.",
            optionsEn = listOf("Holy / Sacred", "Ancient", "Mysterious", "Grand"),
            explanationEn = "'Sakral' is synonymous with 'Suci' or 'Kudus' regarding religious sanctity.",
            grammarRuleEn = "Sinonim: Religious loanword in modern Indonesian."
        ),
        "q_03_6" to QuestionEnglishTranslation(
            promptEn = "Choose the opposite word (antonym) of 'statis' (static/motionless):",
            contextReasonEn = "Shifts in night patrol guard positions around the temple.",
            targetWordMeaningEn = "'Statis' = Static, stationary, at rest.",
            optionsEn = listOf("Dynamic / Moving", "Still", "Calm", "Rigid"),
            explanationEn = "The antonym of 'Statis' (immobile) is 'Dinamis' (active/in motion).",
            grammarRuleEn = "Antonim: Scientific and analytical antonym pairs in Indonesian."
        ),
        "q_03_7" to QuestionEnglishTranslation(
            promptEn = "Which spelling of 'hierarchy' conforms to standard Indonesian rules?",
            contextReasonEn = "Terraced structural tiers of the towering Besakih temple.",
            targetWordMeaningEn = "Hierarchy / tiered order.",
            optionsEn = listOf("Hierarchy", "Hierarchy", "Hierarchy", "Hierarchy"),
            explanationEn = "The correct spelling is 'Hierarki' retaining 'e' (from English 'hierarchy').",
            grammarRuleEn = "Kata Baku: Diphthongs adapted into standard Indonesian consonant clusters."
        ),
        "q_03_8" to QuestionEnglishTranslation(
            promptEn = "Choose the standard spelling of 'object/evidence':",
            contextReasonEn = "Forensic cataloging of the palm-leaf manuscript evidence.",
            targetWordMeaningEn = "Object / item of focus.",
            optionsEn = listOf("Object / entity", "Object / entity", "Object / entity", "Object / entity"),
            explanationEn = "Standard Indonesian uses 'Objek' with 'j', not 'Obyek' with 'y'.",
            grammarRuleEn = "Kata Baku: Modern Indonesian replaced Dutch 'y' with 'j' in loanwords (objek, subjek, proyek)."
        ),
        "q_03_9" to QuestionEnglishTranslation(
            promptEn = "The manuscript dates back to an ancient historic era.\n\nWhich spelling is correct?",
            contextReasonEn = "Verify chronological dating notes on the palm-leaf scripture.",
            targetWordMeaningEn = "Era / age / epoch.",
            optionsEn = listOf("Era / period / age", "Era / period / age", "Era / period / age", "Era / period / age"),
            explanationEn = "The correct form is 'Zaman' with 'z' (loaned from Arabic 'zaman').",
            grammarRuleEn = "Kata Baku: Arabic loanwords with 'zayn' standardize with 'z' (zaman, zakat, izin)."
        ),
        "q_03_10" to QuestionEnglishTranslation(
            promptEn = "The temple courtyard on the night of the theft was described as 'sunyi'.\n\nDetermine the synonym of 'sunyi':",
            contextReasonEn = "Testimony of the night sentry stationed near the wooden bell tower.",
            targetWordMeaningEn = "'Sunyi' = Silent, quiet, desolate.",
            optionsEn = listOf("Silent / Tranquil", "Noisy", "Uproarious", "Deafening"),
            explanationEn = "'Senyap' and 'Hening' are synonymous with 'sunyi' describing absence of noise.",
            grammarRuleEn = "Sinonim: Atmospheric descriptive words in Indonesian storytelling."
        ),
        "q_03_11" to QuestionEnglishTranslation(
            promptEn = "The temple's wooden bell tower is situated on a 'tinggi' (high) mountain slope.\n\nChoose the opposite word (antonym) of 'tinggi':",
            contextReasonEn = "Analyze the stairway direction taken by the fleeing suspect.",
            targetWordMeaningEn = "'Tinggi' = High, tall, elevated.",
            optionsEn = listOf("Low", "Long", "Wide", "Deep"),
            explanationEn = "'Rendah' (low) is the antonym of 'tinggi' (high).",
            grammarRuleEn = "Antonim: Spatial elevation opposites in Indonesian."
        ),
        "q_03_12" to QuestionEnglishTranslation(
            promptEn = "The temple schedule lists the rehearsal timetable for the Pendet dance.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Check the temple dancers' practice schedule book.",
            targetWordMeaningEn = "'Geladi' = Rehearsal / practice run.",
            optionsEn = listOf("Geladi", "Gladi", "Gheladi", "Geladie"),
            explanationEn = "The standard form according to KBBI is 'Geladi' with an 'e'.",
            grammarRuleEn = "Kata Baku: Standard Indonesian spelling for practice/rehearsal."
        ),
        "q_03_13" to QuestionEnglishTranslation(
            promptEn = "The elder priest at the temple is known by villagers as very 'pintar' (smart).\n\nChoose the synonym of 'pintar':",
            contextReasonEn = "Recognize the wisdom of temple elders keeping ancient scriptures.",
            targetWordMeaningEn = "'Pintar' = Smart, intelligent, clever.",
            optionsEn = listOf("Smart / Intelligent", "Foolish", "Forgetful", "Indifferent"),
            explanationEn = "'Cerdas' or 'Pandai' is the synonym of 'pintar'.",
            grammarRuleEn = "Sinonim: Intelligence and mental capacity terms."
        ),
        "q_03_14" to QuestionEnglishTranslation(
            promptEn = "An eyewitness felt very 'cemas' (anxious) when seeing smoke on the temple slope.\n\nChoose the antonym (opposite word) of 'cemas':",
            contextReasonEn = "Uncover the emotions of villagers when the alarm sounded.",
            targetWordMeaningEn = "'Cemas' = Anxious, worried, apprehensive.",
            optionsEn = listOf("Calm / Peaceful", "Fearful", "Restless", "Worried"),
            explanationEn = "'Tenang' or 'Damai' (calm/peaceful) is the antonym of 'cemas'.",
            grammarRuleEn = "Antonim: Emotional states in Indonesian."
        ),
        "q_03_15" to QuestionEnglishTranslation(
            promptEn = "During silent prayer, devotees are asked to ease their 'napas' (breath) slowly.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Examine the purification prayer text at Besakih Temple.",
            targetWordMeaningEn = "'Napas' = Breath / breathing.",
            optionsEn = listOf("Napas", "Nafas", "Naphas", "Naphss"),
            explanationEn = "The standard spelling according to KBBI is 'Napas' with a 'p', not 'Nafas'.",
            grammarRuleEn = "Kata Baku: Arabic loanword standardized with 'p' in Indonesian."
        ),

        // CASE 04
        "q_04_1" to QuestionEnglishTranslation(
            promptEn = "Which spelling of 'receipt' is correct?",
            contextReasonEn = "Examine financial transaction slip found at the crime scene.",
            targetWordMeaningEn = "Receipt / bill of payment.",
            optionsEn = listOf("Receipt / payment bill", "Receipt / payment bill", "Receipt / payment bill", "Receipt / payment bill"),
            explanationEn = "The correct spelling is 'Kuitansi' with 'ui', not 'kwitansi'.",
            grammarRuleEn = "Kata Baku: Diphthongs from Dutch 'kw' standardize as 'ku' (kuitansi, kuintal, kualitas)."
        ),
        "q_04_2" to QuestionEnglishTranslation(
            promptEn = "Determine the antonym (opposite word) of 'tersirat' (implicit/implied):",
            contextReasonEn = "Decipher concealed meanings in the tribal genealogical scroll.",
            targetWordMeaningEn = "'Tersirat' = Implied, implicit, subtly hidden between the lines.",
            optionsEn = listOf("Explicit / Plainly stated", "Hidden", "Vague", "Secret"),
            explanationEn = "The antonym of 'Tersirat' (implicit) is 'Tersurat' (explicitly written/written out clearly).",
            grammarRuleEn = "Antonim: Famous Indonesian rhetorical pair: 'tersurat' (written) vs 'tersirat' (implied)."
        ),
        "q_04_3" to QuestionEnglishTranslation(
            promptEn = "The suspect presented an official graduation/education certificate.\n\nWhich spelling is correct?",
            contextReasonEn = "Verify tribal educational credential documents.",
            targetWordMeaningEn = "Diploma / certificate / degree.",
            optionsEn = listOf("Diploma / certificate", "Diploma / certificate", "Diploma / certificate", "Diploma / certificate"),
            explanationEn = "The correct form is 'Ijazah' with 'z' (derived from Arabic 'ijazah').",
            grammarRuleEn = "Kata Baku: Arabic loanwords preserving 'z' (ijazah, jenazah, mukjizat)."
        ),
        "q_04_4" to QuestionEnglishTranslation(
            promptEn = "In the village tribal council, crucial decisions are made by 'mufakat'.\n\nDetermine the synonym of 'mufakat':",
            contextReasonEn = "Understand Minangkabau communal consensus decision-making.",
            targetWordMeaningEn = "'Mufakat' = Consensus, unanimous agreement.",
            optionsEn = listOf("Agreed / In accord", "Denial", "Dispute", "Coercion"),
            explanationEn = "'Setuju' or 'Sepakat' is synonymous with 'Mufakat', signifying collective consensus.",
            grammarRuleEn = "Sinonim: Famous Indonesian socio-political tenet: 'Musyawarah untuk Mufakat'."
        ),
        "q_04_5" to QuestionEnglishTranslation(
            promptEn = "Clan relationships in the village were described as very 'akrab' (close/intimate).\n\nChoose the antonym of 'akrab':",
            contextReasonEn = "Analyze family dynamics regarding land inheritance.",
            targetWordMeaningEn = "'Akrab' = Close, intimate, friendly.",
            optionsEn = listOf("Distant / Estranged", "Close", "Affectionate", "Thick/Solid"),
            explanationEn = "The antonym of 'Akrab' (close) is 'Renggang' (estranged) or 'Asing' (alienated).",
            grammarRuleEn = "Antonim: Describing interpersonal and communal ties."
        ),
        "q_04_6" to QuestionEnglishTranslation(
            promptEn = "Handwoven songket silk cloth is prized for its high 'quality'.\n\nWhich spelling is correct?",
            contextReasonEn = "Inspect suspect's songket boutique order ledger.",
            targetWordMeaningEn = "Quality / standard of excellence.",
            optionsEn = listOf("Quality / grade", "Quality / grade", "Quality / grade", "Quality / grade"),
            explanationEn = "Standard Indonesian uses 'Kualitas' with 'ku', not 'Kwalitas'.",
            grammarRuleEn = "Kata Baku: English/Dutch 'qu/kw' standardizes into 'ku' in Indonesian (kualitas, kuantitas, kualifikasi)."
        ),
        "q_04_7" to QuestionEnglishTranslation(
            promptEn = "The tribal elder reading the tambo is revered as very 'arif'.\n\nDetermine the synonym of 'arif':",
            contextReasonEn = "Identify the character of the customary custodian of the tambo.",
            targetWordMeaningEn = "'Arif' = Wise, sagacious, discerning.",
            optionsEn = listOf("Wise / Sagacious", "Cunning", "Rude", "Indifferent"),
            explanationEn = "'Bijaksana' is a synonym of 'Arif' describing wise, fair, and prudent judgment.",
            grammarRuleEn = "Sinonim: High ethical vocabulary in Indonesian culture."
        ),
        "q_04_8" to QuestionEnglishTranslation(
            promptEn = "The customary clan oath-taking ceremony was held with deep solemnity.\n\nWhich spelling is correct?",
            contextReasonEn = "Atmosphere report of the council session at Istano Pagaruyung.",
            targetWordMeaningEn = "Solemn / devout / reverent.",
            optionsEn = listOf("Solemn / reverent", "Solemn / reverent", "Solemn / reverent", "Solemn / reverent"),
            explanationEn = "The correct spelling is 'Khidmat' retaining the initial digraph 'Kh'.",
            grammarRuleEn = "Kata Baku: Arabic loanwords with 'kha' retain 'kh' (khidmat, khutbah, khusus)."
        ),
        "q_04_9" to QuestionEnglishTranslation(
            promptEn = "Minangkabau traditions prioritize 'tradisional' communal consultation.\n\nDetermine the antonym of 'tradisional':",
            contextReasonEn = "Uncover suspect's motive to replace artisanal weaving with modern factories.",
            targetWordMeaningEn = "'Tradisional' = Traditional, customary, conventional.",
            optionsEn = listOf("Modern / Contemporary", "Ancient", "Classic", "Old"),
            explanationEn = "The antonym of 'Tradisional' is 'Modern' or 'Kontemporer'.",
            grammarRuleEn = "Antonim: Tradition vs modernity semantic spectrum."
        ),
        "q_04_10" to QuestionEnglishTranslation(
            promptEn = "Tambo poetic verses contain moral advice for the younger generation.\n\nWhich spelling of 'advice' is correct?",
            contextReasonEn = "Examination of the parchment excerpt recovered from the attic.",
            targetWordMeaningEn = "Advice / moral counsel.",
            optionsEn = listOf("Advice / counsel", "Advice / counsel", "Advice / counsel", "Advice / counsel"),
            explanationEn = "The correct spelling is 'Nasihat' with 'i', not 'Nasehat'.",
            grammarRuleEn = "Kata Baku: Very frequently tested Indonesian word; standard is 'nasihat' with 'i'."
        ),
        "q_04_11" to QuestionEnglishTranslation(
            promptEn = "Customary elders advised village residents to maintain 'akrab' (close) family bonds.\n\nChoose the synonym of 'akrab':",
            contextReasonEn = "Understand the intimacy of familial relationships among villagers.",
            targetWordMeaningEn = "'Akrab' = Close, intimate, friendly.",
            optionsEn = listOf("Tight / Close", "Distant / Estranged", "Far", "Alienated"),
            explanationEn = "'Erat' or 'Dekat' is a synonym of 'akrab'.",
            grammarRuleEn = "Sinonim: Words describing closeness of social ties."
        ),
        "q_04_12" to QuestionEnglishTranslation(
            promptEn = "Songket fabric artisans guarantee their woven cloth is of high quality.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Check the woven songket fabric inventory report owned by the suspect.",
            targetWordMeaningEn = "'Kualitas' = Quality / grade.",
            optionsEn = listOf("Kualitas", "Kwalitas", "Qualitas", "Kwalitet"),
            explanationEn = "The standard form according to KBBI is 'Kualitas' with 'Ku'.",
            grammarRuleEn = "Kata Baku: Loanwords with 'qu/kw' standardized as 'ku'."
        ),
        "q_04_13" to QuestionEnglishTranslation(
            promptEn = "The area around the custom hall felt very 'sepi' (quiet) toward midnight.\n\nChoose the opposite word (antonym) of 'sepi':",
            contextReasonEn = "Examine the truthfulness of the suspect's midnight alibi.",
            targetWordMeaningEn = "'Sepi' = Quiet, deserted, silent.",
            optionsEn = listOf("Crowded / Bustling", "Deserted", "Silent", "Quiet"),
            explanationEn = "'Ramai' or 'Riuh' (bustling/crowded) is the antonym of 'sepi'.",
            grammarRuleEn = "Antonim: Quiet vs bustling atmosphere."
        ),
        "q_04_14" to QuestionEnglishTranslation(
            promptEn = "Scripture researchers noted that bamboo manuscript sheets were made to be 'awet' (durable).\n\nChoose the synonym of 'awet':",
            contextReasonEn = "Understand the physical durability of ancient tambo scroll sheets.",
            targetWordMeaningEn = "'Awet' = Durable, long-lasting.",
            optionsEn = listOf("Long-lasting / Durable", "Perishable", "Fragile", "Faded"),
            explanationEn = "'Tahan Lama' is the synonym of 'awet'.",
            grammarRuleEn = "Sinonim: Durability and longevity terms."
        ),
        "q_04_15" to QuestionEnglishTranslation(
            promptEn = "To resolve customary land disputes, an official village working committee was formed.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Verify the names of the custom council committee board.",
            targetWordMeaningEn = "'Panitia' = Committee / organizing board.",
            optionsEn = listOf("Panitia", "Panitya", "Panitiah", "Phanitia"),
            explanationEn = "The standard spelling according to KBBI is 'Panitia' with 'ia'.",
            grammarRuleEn = "Kata Baku: Standard Indonesian spelling for organizing committees."
        ),

        // CASE 05
        "q_05_1" to QuestionEnglishTranslation(
            promptEn = "Forensics examined fingerprints on the copper sheet with meticulous scrutiny.\n\nWhich spelling is correct?",
            contextReasonEn = "Laboratory forensic scrutiny report on physical evidence.",
            targetWordMeaningEn = "Meticulous / thorough / attentive.",
            optionsEn = listOf("Thorough / meticulous", "Thorough / meticulous", "Thorough / meticulous", "Thorough / meticulous"),
            explanationEn = "The correct spelling is 'Saksama' with 'a', not 'Seksama'.",
            grammarRuleEn = "Kata Baku: Sanskrit loanword preserving 'a' vowel ('saksama')."
        ),
        "q_05_2" to QuestionEnglishTranslation(
            promptEn = "Sriwijaya was world-renowned as a great kingdom with a 'bahari' orientation.\n\nDetermine the synonym of 'bahari':",
            contextReasonEn = "Understand the maritime geographic character of Sriwijaya.",
            targetWordMeaningEn = "'Bahari' = Maritime / oceanic / nautical.",
            optionsEn = listOf("Maritime / Nautical", "Mountainous", "Desert", "Landlocked"),
            explanationEn = "'Kelautan' or 'Maritim' is the synonym of 'Bahari' referring to oceanic and seafaring life.",
            grammarRuleEn = "Sinonim: Classical Indonesian word for maritime culture."
        ),
        "q_05_3" to QuestionEnglishTranslation(
            promptEn = "Sriwijaya naval war galleys sailed with great 'megah' (grandeur/splendor).\n\nChoose the antonym of 'megah':",
            contextReasonEn = "Uncover suspect's disguise pretending to be an ordinary small boat.",
            targetWordMeaningEn = "'Megah' = Grand, magnificent, imposing.",
            optionsEn = listOf("Simple / Modest", "Luxurious", "Sturdy", "Exalted"),
            explanationEn = "The antonym of 'Megah' (grand/magnificent) is 'Sederhana' (simple) or 'Bersahaja' (modest).",
            grammarRuleEn = "Antonim: Opposites describing visual stature and aesthetic."
        ),
        "q_05_4" to QuestionEnglishTranslation(
            promptEn = "Port security patrols must operate with high efficacy.\n\nWhich spelling of 'effective' is correct?",
            contextReasonEn = "River dock security protocol assessment.",
            targetWordMeaningEn = "Effective / producing the intended result.",
            optionsEn = listOf("Effective", "Effective", "Effective", "Effective"),
            explanationEn = "Standard Indonesian uses 'Efektif' with final 'f', not 'efektip'.",
            grammarRuleEn = "Kata Baku: Modern loanword adjectival ending '-tif' (efektif, kreatif, inovatif)."
        ),
        "q_05_5" to QuestionEnglishTranslation(
            promptEn = "The king commanded thousands of warriors in a naval 'armada'.\n\nDetermine the synonym of 'armada':",
            contextReasonEn = "Interpret terms for naval squadrons safeguarding royal inscriptions.",
            targetWordMeaningEn = "'Armada' = Fleet of warships or commercial vessels.",
            optionsEn = listOf("Naval fleet / Squadrons", "Infantry", "Artisan guild", "Pilgrims"),
            explanationEn = "'Armada' refers specifically to a fleet of military or transport vessels.",
            grammarRuleEn = "Sinonim: Maritime vocabulary in Indonesian history."
        ),
        "q_05_6" to QuestionEnglishTranslation(
            promptEn = "Coastal patrol radar was temporarily 'pasif' when the smuggler's boat passed.\n\nChoose the antonym of 'pasif':",
            contextReasonEn = "Prove the timing of sabotage on the harbor radar transmitter.",
            targetWordMeaningEn = "'Pasif' = Passive / inactive / idle.",
            optionsEn = listOf("Active / Operational", "Extinguished", "Silent", "Dead"),
            explanationEn = "The opposite of 'Pasif' (inactive) is 'Aktif' (active/in operation).",
            grammarRuleEn = "Antonim: Contrasting functional states in standard Indonesian."
        ),
        "q_05_7" to QuestionEnglishTranslation(
            promptEn = "The gold inscription is a priceless treasure of national heritage.\n\nWhich spelling of 'treasure / repository' is correct?",
            contextReasonEn = "Check official national heritage registry inventory.",
            targetWordMeaningEn = "Treasure trove / repository / cultural wealth.",
            optionsEn = listOf("Treasury / cultural wealth", "Treasury / cultural wealth", "Treasury / cultural wealth", "Treasury / cultural wealth"),
            explanationEn = "The correct spelling is 'Khazanah' with 'z' (derived from Arabic 'khizanah').",
            grammarRuleEn = "Kata Baku: Arabic loanwords preserving 'z' sound."
        ),
        "q_05_8" to QuestionEnglishTranslation(
            promptEn = "Interpretation of the Pallava script reveals a message that is deeply 'bernas'.\n\nDetermine the synonym of 'bernas':",
            contextReasonEn = "Understand the profound moral depth of the Sriwijaya oath.",
            targetWordMeaningEn = "'Bernas' = Full of meaning, substantial, fertile, weighty.",
            optionsEn = listOf("Pithy / Full of deep meaning", "Empty", "Vacuous", "Shallow"),
            explanationEn = "'Bernas' means packed with substance, truth, and profound wisdom.",
            grammarRuleEn = "Sinonim: High-register literary Indonesian describing profound statements."
        ),
        "q_05_9" to QuestionEnglishTranslation(
            promptEn = "Investigation proved the suspect's crime was driven by gain that is not 'hakiki'.\n\nWhat is a synonym of 'hakiki'?",
            contextReasonEn = "Analyze suspect's psychological greed motive.",
            targetWordMeaningEn = "'Hakiki' = True, authentic, essential, intrinsic.",
            optionsEn = listOf("True / Pure / Essential", "Imaginary", "Temporary", "Fake"),
            explanationEn = "'Hakiki' is synonymous with 'Sejati' (true, genuine, essential).",
            grammarRuleEn = "Sinonim: Philosophical vocabulary in Indonesian."
        ),
        "q_05_10" to QuestionEnglishTranslation(
            promptEn = "The perpetrator tried to disguise the gold sheet as a tourist souvenir.\n\nWhich spelling is correct?",
            contextReasonEn = "Inspect cargo declaration label on the speedboat hold.",
            targetWordMeaningEn = "Souvenir / memento / keepsake.",
            optionsEn = listOf("Souvenir / keepsake", "Souvenir / keepsake", "Souvenir / keepsake", "Souvenir / keepsake"),
            explanationEn = "The correct spelling is 'Cenderamata' with 'e', not 'Cinderamata'.",
            grammarRuleEn = "Kata Baku: Compound Indonesian noun for commemorative gifts."
        ),
        "q_05_11" to QuestionEnglishTranslation(
            promptEn = "The Musi harbor dock felt very 'tenang' (calm) at night.\n\nChoose the opposite word (antonym) of 'tenang':",
            contextReasonEn = "Uncover strange atmosphere changes at the dock when the ship passed.",
            targetWordMeaningEn = "'Tenang' = Calm, tranquil, peaceful.",
            optionsEn = listOf("Noisy / Agitated", "Peaceful", "Quiet", "Silent"),
            explanationEn = "'Bising' or 'Gelisah' is the antonym of 'tenang'.",
            grammarRuleEn = "Antonim: Calm vs noisy/troubled states."
        ),
        "q_05_12" to QuestionEnglishTranslation(
            promptEn = "Inside the ship hold logistics order note, a grocery shopping list was found.\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Check the suspect's ship logistics purchase receipt.",
            targetWordMeaningEn = "'Cabai' = Chili pepper.",
            optionsEn = listOf("Cabai", "Cabe", "Tjabai", "Chabe"),
            explanationEn = "The standard spelling according to KBBI is 'Cabai' with 'ai', not 'Cabe'.",
            grammarRuleEn = "Kata Baku: Standard Indonesian ending 'ai' vs colloquial 'e'."
        ),
        "q_05_13" to QuestionEnglishTranslation(
            promptEn = "The speedboat captain is known for having 'hebat' (great) navigation skills.\n\nChoose the synonym of 'hebat':",
            contextReasonEn = "Understand the skill level of the smuggler ship's captain.",
            targetWordMeaningEn = "'Hebat' = Great, highly skilled, expert.",
            optionsEn = listOf("Expert / Masterful", "Weak", "Novice", "Ordinary"),
            explanationEn = "'Ulung' or 'Mahir' is a synonym of 'hebat' describing expert mastery.",
            grammarRuleEn = "Sinonim: Words for high expertise and skill."
        ),
        "q_05_14" to QuestionEnglishTranslation(
            promptEn = "At night, the waters of the Musi river tributary appeared very 'gelap' (dark).\n\nChoose the antonym (opposite word) of 'gelap':",
            contextReasonEn = "Analyze lighting conditions when the boat dropped anchor.",
            targetWordMeaningEn = "'Gelap' = Dark, unlit.",
            optionsEn = listOf("Bright / Light", "Dark", "Gloomy", "Blurry"),
            explanationEn = "'Terang' (bright) is the antonym of 'gelap'.",
            grammarRuleEn = "Antonim: Light vs darkness."
        ),
        "q_05_15" to QuestionEnglishTranslation(
            promptEn = "The wooden box storing the inscription must be kept in a room that is not 'lembap' (humid).\n\nWhich spelling is correct according to KBBI?",
            contextReasonEn = "Verify humidity conditions in the cultural relic storage facility.",
            targetWordMeaningEn = "'Lembap' = Humid, damp, moist.",
            optionsEn = listOf("Lembap", "Lembab", "Lembap-an", "Lembabb"),
            explanationEn = "The standard spelling according to KBBI is 'Lembap' with a 'p' at the end, not 'Lembab'.",
            grammarRuleEn = "Kata Baku: Standard Indonesian ending consonant 'p'."
        )
    )

    private val suspectTranslations = mapOf(
        "s_01_0" to SuspectEnglishTranslation(
            roleTitleEn = "Batik Vendor",
            bioEn = "45-year-old male, frequently seen at the traditional market.",
            alibiEn = "\"I was at the market selling batik cloth in wholesale lots since morning.\"",
            confessionEn = ""
        ),
        "s_01_1" to SuspectEnglishTranslation(
            roleTitleEn = "Indonesian Language Teacher",
            bioEn = "38-year-old female, known for meticulous grammatical precision.",
            alibiEn = "\"I was grading my students' Indonesian essay assignments in the museum staff room.\"",
            confessionEn = ""
        ),
        "s_01_2" to SuspectEnglishTranslation(
            roleTitleEn = "Shadow Puppet Artist",
            bioEn = "52-year-old master of traditional wayang performance arts.",
            alibiEn = "\"I was arranging wayang leather puppets on stage preparing for tonight's gamelan show.\"",
            confessionEn = ""
        ),
        "s_01_3" to SuspectEnglishTranslation(
            roleTitleEn = "Traditional Jamu Herbalist",
            bioEn = "29-year-old female, carrying traditional herbal medicine baskets.",
            alibiEn = "\"I only delivered herbal drinks to the museum kitchen then immediately headed home.\"",
            confessionEn = "\"Yes, I took the sacred keris! My family herbal medicine business was on the brink of bankruptcy, and a shady foreign collector promised a fortune. I hid the keris inside my bamboo jamu basket!\""
        ),
        "s_01_4" to SuspectEnglishTranslation(
            roleTitleEn = "Fisherman",
            bioEn = "41-year-old seafarer with deep knowledge of coastal tides.",
            alibiEn = "\"I was mending fishing nets by the riverside waiting for the tides to turn.\"",
            confessionEn = ""
        ),
        "s_02_4" to SuspectEnglishTranslation(
            roleTitleEn = "Royal Aristocrat Relative",
            bioEn = "Ambitious nobleman seeking control of palace ancestral lands.",
            alibiEn = "\"I don't understand high Javanese court grammar and know nothing of secret passages!\"",
            confessionEn = "\"You detectives are too sharp! That royal will gave land rights to the common people. I altered the words so the property would revert to me. I exploited the secret corridor because I decoded the ancient map!\""
        ),
        "s_03_3" to SuspectEnglishTranslation(
            roleTitleEn = "Black-market Antiquities Dealer",
            bioEn = "Relic dealer frequently conducting illegal overseas transactions.",
            alibiEn = "\"My car broke down on the slope and I never set foot inside the temple courtyard!\"",
            confessionEn = "\"Curse it! I hired a counterfeit carver to etch a fake palm leaf. A wealthy buyer from Europe offered billions for the real sacred scripture. You detectives are extraordinarily clever!\""
        ),
        "s_04_2" to SuspectEnglishTranslation(
            roleTitleEn = "Songket Textile Merchant",
            bioEn = "Greedy merchant intending to seize tribal land for a textile factory.",
            alibiEn = "\"I know nothing of tribal lore and was staying in Bukittinggi that night!\"",
            confessionEn = "\"It is true, I took the tambo scroll! By removing the genealogical page, I could claim the valley lands for my songket enterprise. But your linguistic sleuthing uncovered everything!\""
        ),
        "s_05_2" to SuspectEnglishTranslation(
            roleTitleEn = "Speedboat Captain",
            bioEn = "Veteran mariner who knows every hidden creek of the Musi River.",
            alibiEn = "\"My speedboat was anchored with an overheated engine carrying only vegetables!\"",
            confessionEn = "\"Caught red-handed! I concealed the gold inscription inside the watertight bilge tank. A smuggler in Singapore offered me a new yacht if I navigated it out of the Musi estuary!\""
        )
    )
}
