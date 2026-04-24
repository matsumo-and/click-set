---
name: verify
description: Code quality verification skill for click-set. Use when implementation is complete, when the user says "check", "verify", "lint", "test", or "build", or when a batch of code changes is done.
---

# click-set Code Quality Verification

Builds and tests are handled by CI (GitHub Actions). Locally, run only Spotless and Detekt.

## Execution Order

Run the following **in this order**:

### 1. Spotless (ktlint auto-fix)
```bash
./gradlew spotlessApply
```

### 2. Detekt (static analysis)
```bash
./gradlew detekt
```
Fix any errors before proceeding.

## Report

After all steps complete, summarize results:

```
## Verification Result

| Step                | Result              |
|---------------------|---------------------|
| Spotless (ktlint)   | ✓ or N fixes        |
| Detekt              | ✓ or error details  |
| Build & Tests       | Delegated to CI     |
```