import 'dotenv/config';
import express from 'express';
import cors from 'cors';
import helmet from 'helmet';
import { z } from 'zod';
import { AiGateway } from './ai-gateway.js';

const app = express();
const port = Number(process.env.PORT ?? 8080);
const gateway = new AiGateway();
app.use(helmet());
app.use(cors({ origin: process.env.ALLOWED_ORIGINS ? process.env.ALLOWED_ORIGINS.split(',') : false }));
app.use(express.json({ limit: '32kb' }));

app.get('/health', (_req, res) => res.json({ ok: true }));
const chatSchema = z.object({ message: z.string().trim().min(1).max(4000) });
app.post('/v1/ai/reply', async (req, res) => {
  const parsed = chatSchema.safeParse(req.body);
  if (!parsed.success) return res.status(400).json({ error: 'Invalid request' });
  try { return res.json(await gateway.reply(parsed.data.message)); }
  catch { return res.status(502).json({ error: 'AI service unavailable' }); }
});

app.listen(port, () => console.log(`My ADHD backend listening on ${port}`));
