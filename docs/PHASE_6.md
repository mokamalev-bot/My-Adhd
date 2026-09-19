# Phase 6

- Added a local daily check-in for energy, focus, and stress using Room.
- Added database migration from version 2 to version 3.
- Added a lightweight Home check-in UI with values 1–3 and a save action.
- Added editable estimated task duration with input validation from 1 to 480 minutes.
- Kept check-ins as self-reflection data only; the app makes no medical conclusions from them.

## Build note

Run `./gradlew assembleDebug` in Android Studio or a configured Android environment. Review the generated manifest, notification permission flow, and release signing configuration before production distribution.
