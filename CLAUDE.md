# click-set

Android / Wear OS / iOS / watchOS 向けのバイブレーションメトロノームアプリ。プリセットとセットリスト機能を備える。

## 技術スタック

- **Kotlin Multiplatform** + **Compose Multiplatform** — Android / iOS で UI を共有
- `apps/android/` — Compose Multiplatform の共通 UI・各プラットフォーム固有コード
- `core/` — ビジネスロジック・ドメインモデルの共通実装
- `apps/ios/` — iOS / watchOS アプリのエントリポイント（SwiftUI）

## 構成

- `apps/android/src/commonMain/` — 共通 UI コンポーネント・画面
- `apps/android/src/androidMain/` — Android 固有の実装（Wear OS 含む）
- `apps/android/src/iosMain/` — iOS 固有の実装
- `core/src/commonMain/` — ドメインモデル・ユースケース・リポジトリ
- `apps/ios/iosApp/` — iOS / watchOS SwiftUI エントリポイント

## ビルド

```shell
# Android デバッグビルド
./gradlew :apps:android:assembleDebug

# コード品質チェック（lint + 静的解析）
./gradlew spotlessCheck detekt
```

## コード品質

- **Spotless + ktlint** — Kotlin / Gradle ファイルのフォーマット
- **Detekt** — 静的解析（`config/detekt/detekt.yml`）
- フォーマット自動修正: `./gradlew spotlessApply`

## 制限

- 調査はサブエージェントに任せること
- 実装後は必ずチェックを行うこと（`verify` スキルを使う）
