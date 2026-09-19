# Phase 7 — AI safety foundation

This phase adds a provider-independent AI contract and a conservative local safety gate.

## Safety behavior

- Crisis-like language receives an immediate safety-oriented response.
- Medication questions are redirected to a qualified healthcare professional.
- Diagnosis requests are not answered as diagnoses.
- Normal requests receive a small-next-step productivity response.

This local classifier is not sufficient for production. Before any remote model is enabled:

1. Route requests through an authenticated backend.
2. Run server-side input classification and output validation.
3. Keep provider API keys off the Android client.
4. Add country-specific emergency resources maintained by authorized administrators.
5. Add human review and red-team tests for multilingual crisis and child-safety cases.

The assistant is not a doctor, psychotherapist, emergency service, or replacement for professional care.
