package com.example.myadhd.ai

/**
 * Conservative, on-device safety gate for the assistant.
 * This is a first filter only; production AI requests must also be checked by a
 * trusted backend before reaching any model provider.
 */
enum class SafetyCategory { SAFE, DIAGNOSIS, MEDICATION, CRISIS, DANGEROUS_INSTRUCTION }

data class SafetyDecision(val category: SafetyCategory, val responseKey: String? = null)

object SafetyClassifier {
    private val crisisTerms = setOf("suicide", "kill myself", "self harm", "hurt myself", "انتحار", "أقتل نفسي", "إيذاء نفسي")
    private val medicationTerms = setOf("medication", "medicine", "dose", "prescription", "دواء", "جرعة", "وصفة")
    private val diagnosisTerms = setOf("do i have adhd", "diagnose me", "هل لدي adhd", "هل أنا مصاب", "شخّصني")

    fun classify(message: String): SafetyDecision {
        val normalized = message.trim().lowercase()
        return when {
            crisisTerms.any(normalized::contains) -> SafetyDecision(SafetyCategory.CRISIS, "ai_crisis_response")
            medicationTerms.any(normalized::contains) -> SafetyDecision(SafetyCategory.MEDICATION, "ai_medication_response")
            diagnosisTerms.any(normalized::contains) -> SafetyDecision(SafetyCategory.DIAGNOSIS, "ai_diagnosis_response")
            else -> SafetyDecision(SafetyCategory.SAFE)
        }
    }
}
