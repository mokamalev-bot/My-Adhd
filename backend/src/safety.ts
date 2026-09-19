export enum SafetyCategory { SAFE = 'safe', DIAGNOSIS = 'diagnosis', MEDICATION = 'medication', CRISIS = 'crisis', DANGEROUS = 'dangerous' }

export type SafetyDecision = { category: SafetyCategory; response: string };

const crisis = ['suicide','kill myself','self harm','hurt myself','انتحار','أقتل نفسي','إيذاء نفسي'];
const medication = ['medication','medicine','dose','prescription','دواء','جرعة','وصفة'];
const diagnosis = ['do i have adhd','diagnose me','هل لدي adhd','هل أنا مصاب','شخّصني'];
const dangerous = ['make a weapon','harm someone','اصنع سلاح','إيذاء شخص'];

export function classifySafety(input: string): SafetyDecision {
  const text = input.trim().toLocaleLowerCase();
  if (crisis.some(term => text.includes(term))) return { category: SafetyCategory.CRISIS, response: 'If you may be in immediate danger, contact local emergency services now and reach a trusted person nearby. This app cannot provide emergency care.' };
  if (medication.some(term => text.includes(term))) return { category: SafetyCategory.MEDICATION, response: 'Medication decisions should be made with a qualified healthcare professional. I can help prepare questions for your clinician.' };
  if (diagnosis.some(term => text.includes(term))) return { category: SafetyCategory.DIAGNOSIS, response: 'I can explain screening information, but I cannot diagnose ADHD. A qualified healthcare professional can evaluate your symptoms and history.' };
  if (dangerous.some(term => text.includes(term))) return { category: SafetyCategory.DANGEROUS, response: 'I cannot help with dangerous instructions. I can help find a safer next step.' };
  return { category: SafetyCategory.SAFE, response: '' };
}
