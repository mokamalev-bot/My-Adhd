import { SafetyCategory, SafetyDecision } from './safety.js';

export interface AiProvider { complete(input: string): Promise<string>; }

export class StubProvider implements AiProvider {
  async complete(_input: string): Promise<string> {
    return "Let's make this smaller. What is the smallest useful next step?";
  }
}

export class AiGateway {
  constructor(private readonly provider: AiProvider = new StubProvider()) {}

  async reply(input: string): Promise<{ text: string; category: SafetyCategory }> {
    const decision = classifySafety(input);
    if (decision.category !== SafetyCategory.SAFE) return { text: decision.response, category: decision.category };
    const output = await this.provider.complete(input);
    return { text: validateOutput(output), category: SafetyCategory.SAFE };
  }
}

function validateOutput(output: string): string {
  const forbidden = /(you have adhd|take this medication|prescribe|stop your medication|أنت مصاب|تناول هذا الدواء|أوقف دواءك)/i;
  return forbidden.test(output) ? "I can help with practical organization, but a qualified healthcare professional must handle medical decisions." : output.slice(0, 2000);
}

export { classifySafety } from './safety.js';
