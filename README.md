# MF_demo

GitHubユーザー一覧を表示するAndroidアプリケーションです。

## TODO：
* １、機能により、Moduleで分ける
   Ex.ログイン、ポイント、収入金、家計簿、口座、資産
* ２、ガイドライン：Model, package,Screen, strings命名などドキュメント作成
* ３、personal access token : TokenやAuthキーなどはローカル保存しないこと
* ４、多言語化ローカライズ：設定画面で言語を選択できるように（現在は携帯言語により、英語、日本語だけ）
* ５、UI：色統一、Theme
* ６、テスト：テストケース、テストCode
* 7、プロジェクト開発、new branch -> push -> merge, mainには直接pushしない

## APP
[app] moduleだけ、MVVM, Jatpack Compose +  Paging 3+ Retrofit + okHttpを使用して構築されています。

## API
* UserList
  GET https://api.github.com/users
* UserList Pagination
  GET https://api.github.com/users?since={count}&per_page={pageSize}
* User Detail
  GET https://api.github.com/users/{username}
* User Repo
  GET https://api.github.com/users/{username}/repos

## 📱 機能

- **GitHubユーザー一覧表示**: GitHub APIからユーザー情報を取得して表示
- **ページネーション**: 効率的なデータ読み込みとスクロール
- **検索機能**: ユーザー名によるリアルタイム検索
- **ユーザー詳細表示**: ユーザーの詳細情報とリポジトリ一覧
- **プルツーリフレッシュ**: データの更新機能
- **エラーハンドリング**: ネットワークエラー時の再試行機能
- **重複クリック防止**: デバウンス機能によるUX向上

## 🏗️ プロジェクト構造

```
MF_demo/
├── app/                                    # メインアプリケーションモジュール
│   ├── src/main/java/com/example/mf_demo/
│   │   ├── base/                           # アプリケーション基盤
│   │   │   ├── AppApplication.kt          # アプリケーションクラス
│   │   │   └── AppCrashHandler.kt         # クラッシュハンドラー
│   │   │
│   │   ├── model/                          # データ層
│   │   │   ├── api/                        # API関連
│   │   │   │   ├── base/                   # API基盤
│   │   │   │   │   ├── IApiRepository.kt   # APIリポジトリインターフェース
│   │   │   │   │   ├── ApiModule.kt        # API依存性注入
│   │   │   │   │   └── ApiResult.kt        # API結果ラッパー
│   │   │   │   └── retrofit/               # Retrofit実装
│   │   │   │       ├── RetrofitApiService.kt # APIサービス定義
│   │   │   │       ├── RetrofitRepository.kt # APIリポジトリ実装
│   │   │   │       └── ApiResponse.kt      # APIレスポンス処理
│   │   │   ├── data/                       # データエンティティ
│   │   │   │   ├── entity/                 # データモデル
│   │   │   │   │   ├── User.kt             # ユーザーエンティティ
│   │   │   │   │   ├── UserDetail.kt       # ユーザー詳細エンティティ
│   │   │   │   │   └── UserDetailRepo.kt   # リポジトリエンティティ
│   │   │   │   └── source/                 # データソース
│   │   │   │       └── UserPagingSource.kt # ページングデータソース
│   │   │   └── network/                    # ネットワーク設定
│   │   │       └── retrofit/
│   │   │           └── RetrofitSetting.kt  # Retrofit設定
│   │   │
│   │   ├── ui/                             # UI層
│   │   │   ├── activity/                   # アクティビティ
│   │   │   │   └── CrashActivity.kt        # クラッシュ画面
│   │   │   ├── components/                 # 再利用可能なUIコンポーネント
│   │   │   │   ├── bar/                    # バーコンポーネント
│   │   │   │   │   ├── SearchBar.kt        # 検索バー
│   │   │   │   │   ├── TopBar.kt           # トップバー
│   │   │   │   │   └── NaviBar.kt          # ナビゲーションバー
│   │   │   │   ├── dialog/                 # ダイアログ
│   │   │   │   │   └── DialogUI.kt         # ダイアログUI
│   │   │   │   ├── CenterIndicator.kt      # 中央ローディングインジケーター
│   │   │   │   ├── RetryLoadDataBox.kt     # データ読み込み再試行ボックス
│   │   │   │   └── RetryLoadMoreDataBox.kt # 追加データ読み込み再試行ボックス
│   │   │   ├── screen/                     # 画面
│   │   │   │   ├── base/                   # 基本画面
│   │   │   │   │   └── CrashScreen.kt      # クラッシュ画面
│   │   │   │   ├── navi/                   # ナビゲーション
│   │   │   │   │   ├── NaviHelper.kt       # ナビゲーションヘルパー
│   │   │   │   │   ├── ScreenNavi.kt       # 画面ナビゲーション
│   │   │   │   │   └── ScreenNaviData.kt   # ナビゲーションデータ
│   │   │   │   └── user/                   # ユーザー関連画面
│   │   │   │       ├── UserListScreen.kt   # ユーザー一覧画面
│   │   │   │       ├── UserDetailScreen.kt # ユーザー詳細画面
│   │   │   │       ├── UserListItem.kt     # ユーザーリストアイテム
│   │   │   │       └── UserRepoItem.kt     # リポジトリアイテム
│   │   │   └── theme/                      # テーマ
│   │   │       ├── Color.kt                # カラー定義
│   │   │       ├── Theme.kt                # テーマ定義
│   │   │       └── Type.kt                 # タイポグラフィ定義
│   │   │
│   │   ├── util/                           # ユーティリティ
│   │   │   ├── constant/                   # 定数
│   │   │   │   ├── CConstant.kt            # 共通定数
│   │   │   │   ├── CPath.kt                # パス定数
│   │   │   │   └── CType.kt                # 型定数
│   │   │   ├── extension/                  # 拡張関数
│   │   │   │   └── ModifierClick.kt        # クリック修飾子拡張
│   │   │   └── helper/                     # ヘルパー
│   │   │       ├── DialogHelper.kt         # ダイアログヘルパー
│   │   │       ├── OpenHelper.kt           # オープンヘルパー
│   │   │       └── log/                    # ログ関連
│   │   │           ├── DLog.kt             # デバッグログ
│   │   │           └── LogHelper.kt        # ログヘルパー
│   │   │
│   │   ├── viewModel/                      # ViewModel層
│   │   │   ├── base/                       # 基本ViewModel
│   │   │   │   ├── BaseViewModel.kt        # 基本ViewModel
│   │   │   │   ├── BasePagingViewModel.kt  # ページング基本ViewModel
│   │   │   │   └── BaseRequestData.kt      # 基本リクエストデータ
│   │   │   └── user/                       # ユーザー関連ViewModel
│   │   │       ├── UserListViewModel.kt    # ユーザー一覧ViewModel
│   │   │       └── UserDetailViewModel.kt  # ユーザー詳細ViewModel
│   │   │
│   │   └── MainActivity.kt                 # メインアクティビティ
│   │
│   └── build.gradle.kts                    # アプリモジュール設定
│
├── login/                                  # ログインモジュール
├── point/                                  # ポイントモジュール
├── gradle/                                 # Gradle設定
├── build.gradle.kts                        # プロジェクト設定
├── settings.gradle.kts                     # プロジェクト設定
└── gradle.properties                       # Gradleプロパティ
```

## 🛠️ 技術スタック

### フレームワーク・ライブラリ
- **Jetpack Compose**: モダンなUI構築
- **Paging 3**: 効率的なデータページネーション
- **Retrofit**: HTTP API通信
- **OkHttp**: HTTPクライアント
- **Coil**: 画像読み込み
- **Navigation Compose**: 画面遷移
- **ViewModel**: UI状態管理
- **Coroutines**: 非同期処理

### アーキテクチャ
- **MVVM**: Model-View-ViewModelパターン
- **Repository Pattern**: データアクセス抽象化
- **Clean Architecture**: レイヤー分離

## 🚀 セットアップ

### 前提条件
- Android Studio Hedgehog | 2023.1.1 以降
- JDK 11
- Android SDK 35
- Kotlin 2.2.0

### インストール手順

1. リポジトリをクローン
```bash
git clone https://github.com/your-username/MF_demo.git
cd MF_demo
```

2. プロジェクトを開く
```bash
# Android Studioでプロジェクトを開く
# または
./gradlew build
```

3. GitHub APIトークンの設定
```kotlin
// app/src/main/java/com/example/mf_demo/util/constant/CPath.kt
const val API_AUTH = "YOUR_GITHUB_TOKEN"
```

4. アプリを実行
```bash
./gradlew installDebug
```

## 📋 主要機能詳細

### ユーザー一覧画面
- GitHub APIからユーザー情報を取得
- ページネーションによる効率的なデータ読み込み
- プルツーリフレッシュ機能
- リアルタイム検索機能
- エラー時の再試行機能

### ユーザー詳細画面
- ユーザーの詳細情報表示
- ユーザーのリポジトリ一覧
- リポジトリの統計情報（スター数、言語等）

### 検索機能
- ユーザー名によるリアルタイム検索
- デバウンス機能（500ms）
- 大文字小文字を区別しない検索

### エラーハンドリング
- ネットワークエラー時の再試行ボタン
- 初期読み込みエラーとページネーションエラーの区別
- ユーザーフレンドリーなエラーメッセージ

## 🔧 設定

### ページネーション設定
```kotlin
// app/src/main/java/com/example/mf_demo/util/constant/CConstant.kt
const val PAGING_SIZE = 30        // 1ページあたりのアイテム数
const val PAGING_DISTANCE = 5     // プリフェッチ距離
```

### ネットワーク設定
```kotlin
// app/src/main/java/com/example/mf_demo/util/constant/CPath.kt
const val BASE_API_URL = "https://api.github.com/"
```

### デバウンス設定
```kotlin
const val TIME_USER_SEARCH_DELAY = 500L  // 検索デバウンス時間
const val TIME_CLICK_ENABLE = 500L       // クリックデバウンス時間
```

## 🧪 テスト

### ユニットテスト
```bash
./gradlew test
```

### インストルメンテーションテスト
```bash
./gradlew connectedAndroidTest
```

## 📱 スクリーンショット

一覧            |  詳細
:-------------------------:|:-------------------------:
![image](https://github.com/jasminelily/MF/blob/main/Screenshot_list.png)  |  ![image](https://github.com/jasminelily/MF/blob/main/Screenshot_detail.png)




