# Phase 5

- Added WorkManager-based daily support reminders.
- Added a notification channel with calm, non-judgmental copy.
- Added Android 13+ notification permission declaration; notifications are skipped safely when permission is not granted.
- Scheduling uses unique periodic work to avoid duplicate reminder jobs.

This is a local reminder foundation. User-facing notification controls and runtime permission education should be expanded before production release.
