import assert from 'node:assert/strict';
import test from 'node:test';
import { classifySafety, SafetyCategory } from './safety.js';

test('crisis takes priority', () => assert.equal(classifySafety('I want to hurt myself').category, SafetyCategory.CRISIS));
test('medication is redirected', () => assert.equal(classifySafety('What medicine should I take?').category, SafetyCategory.MEDICATION));
test('diagnosis is not confirmed', () => assert.equal(classifySafety('Do I have ADHD?').category, SafetyCategory.DIAGNOSIS));
test('ordinary planning is safe', () => assert.equal(classifySafety('Help me plan my report').category, SafetyCategory.SAFE));
