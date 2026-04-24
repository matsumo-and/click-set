---
name: commit
description: Commit & push skill for the click-set project. Use when committing, pushing, or creating a PR, or when the user says "commit", "push", or "PR".
---

## Secret Check

Review `git diff HEAD` and ensure none of the following are included:
- API keys, tokens, passwords, or secrets
- Private keys or credentials
- `.env` files or credential files
- `local.properties` (may contain Android signing info)

**If found, abort immediately and ask the user for confirmation before committing.**

## Commit Message Rules

- Concise and clear (subject line under 72 characters)
- Use imperative mood (e.g., "Add feature", "Fix bug")
- Follow the existing style in the repository

## Commit Command

Always include `--signoff`:

```bash
git commit --signoff -m "message"
```

## Push & PR (when requested)

```bash
git push origin <branch>
gh pr create --title "title" --body "description"
```