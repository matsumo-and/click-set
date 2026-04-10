---
name: verify
description: click-set のコード品質検証スキル。実装が完了したとき、ユーザーが「チェック」「確認」「検証」「verify」「lint」「テスト」「ビルド」などと言ったとき、またはコードの変更が一段落したと判断できるときは必ずこのスキルを使うこと。
---

# click-set コード品質検証

## 実行順序

以下を **この順番で** 実行する。

### 1. Spotless（ktlint フォーマット自動修正）
```bash
./gradlew spotlessApply
```

### 2. Detekt（静的解析）
```bash
./gradlew detekt
```
エラーがあればコードを修正してから次へ。

### 3. ビルド
```bash
./gradlew assembleDebug
```
エラーがあればコードを修正してから次へ。

### 4. テスト
```bash
./gradlew test
```
失敗したテストがあればコードを修正する。

## 結果の報告

全ステップ完了後にまとめて報告する：

```
## 検証結果

| ステップ              | 結果               |
|--------------------|-------------------|
| Spotless (ktlint)  | ✓ or 修正N件      |
| Detekt             | ✓ or エラー内容   |
| ビルド              | ✓ or エラー内容   |
| テスト              | ✓ or N件失敗      |
```
