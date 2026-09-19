# Phase 8 — Secure backend and AI gateway

This phase adds a TypeScript/Express backend scaffold. The Android client must call this backend rather than an AI provider directly.

## Run locally

```bash
cd backend
cp .env.example .env
npm install
npm run build
npm test
npm run dev
```

Endpoints:

- `GET /health`
- `POST /v1/ai/reply` with `{ "message": "..." }`

The current provider is a safe stub. Configure a real provider only by implementing a server-side `AiProvider`; never place `AI_API_KEY` in the APK or commit `.env`.

## Safety limitations

The classifier is a defense-in-depth filter, not clinical safety certification. Production requires authentication, rate limiting, persistent audit controls, provider output validation, multilingual red-team testing, human-reviewed emergency resources, and privacy/consent controls before handling sensitive conversations.
