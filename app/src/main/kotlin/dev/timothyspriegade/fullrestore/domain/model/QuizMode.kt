package dev.timothyspriegade.fullrestore.domain.model

enum class QuizMode(val label: String) {
    SINGLE_TYPE("1 type vs 1 type"),
    DUAL_TYPE("1 type vs 2 types"),
    RANDOM_DEFENDERS("Random 1 or 2 types")
}
