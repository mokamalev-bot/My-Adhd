package com.example.myadhd.ai

/** Provider-independent contract. Implementations must live behind a secure backend. */
interface AiGateway {
    suspend fun reply(message: String, context: Map<String, String> = emptyMap()): AiReply
}

data class AiReply(val text: String, val category: SafetyCategory)

class SafeLocalAiGateway : AiGateway {
    override suspend fun reply(message: String, context: Map<String, String>): AiReply {
        val decision = SafetyClassifier.classify(message)
        val text = when (decision.category) {
            SafetyCategory.CRISIS -> "If you may be in immediate danger, contact local emergency services now and reach a trusted person nearby. This app cannot provide emergency care."
            SafetyCategory.MEDICATION -> "Medication decisions should be made with a qualified healthcare professional. I cannot recommend or prescribe medication, but I can help prepare questions for your clinician."
            SafetyCategory.DIAGNOSIS -> "I can help with organization and screening information, but I cannot diagnose ADHD. A qualified healthcare professional can evaluate your symptoms and history."
            SafetyCategory.DANGEROUS_INSTRUCTION -> "I cannot help with dangerous instructions. I can help find a safer next step."
            SafetyCategory.SAFE -> "Let's make this smaller. What is the smallest useful next step?"
        }
        return AiReply(text, decision.category)
    }
}
