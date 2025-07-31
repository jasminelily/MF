# TODO：
* １、機能により、Moduleで分ける
   Ex.ログイン、ポイント、収入金、家計簿、口座、資産
* ２、ガイドライン：Model, package,Screen, strings命名などドキュメント作成
* ３、personal access token : TokenやAuthキーなどはローカル保存しないこと
* ４、多言語化ローカライズ：設定画面で言語を選択できるように（現在は携帯言語により、英語、日本語だけ）
* ５、UI：色統一、Theme
* ６、テスト：テストケース、テストCode
* 7、プロジェクト開発、new branch -> push -> merge, mainには直接pushしない

# APP
[app] moduleだけ、MVVM, Jatpack Compose + Retrofit+okHttp+Hilt

# API
* UserList
  GET https://api.github.com/users
* User Detail
  GET https://api.github.com/users/{username}
* User Repo
  GET https://api.github.com/users/{username}/repos

# 現在Package
```sh
-base (アプリ全体 application）
-module
    -api
      -base
          -ApiModule (API call window)
	  -ApiResult (API return value)
	  -IApiRepository (API call interface)
      -retrofit (retrofit to get data)
    -data (response data)
    -network (API settings)
-ui
    -activity
    -components (カスタマイズUI）
    -screen　(画面)
    -theme
-util 
    -constant
    -helper
-viewModel
     -base
     -user
	-UserDetailViewModel
	-UserListViewModel
```
