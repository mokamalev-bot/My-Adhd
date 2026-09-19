package com.example.myadhd.ai

import org.junit.Assert.assertEquals
import org.junit.Test

class SafetyClassifierTest {
    @Test fun medicationIsRedirected() = assertEquals(SafetyCategory.MEDICATION, SafetyClassifier.classify("What medication should I take?").category)
    @Test fun diagnosisIsNotConfirmed() = assertEquals(SafetyCategory.DIAGNOSIS, SafetyClassifier.classify("Do I have ADHD?").category)
    @Test fun crisisIsPrioritized() = assertEquals(SafetyCategory.CRISIS, SafetyClassifier.classify("I want to hurt myself").category)
    @Test fun ordinaryPlanningIsSafe() = assertEquals(SafetyCategory.SAFE, SafetyClassifier.classify("Help me start my report").category)
}
