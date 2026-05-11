流動量調整♦作成中♦  
  
**♦概要♦**  
月間数量と日々の製造量を入力することで、今後の流動予定を算出する。  
  
**♦制作背景♦**  
React+TypeScript+Javaでシステム開発を勉強するために、新規作成する。今回は前職にてVBAで作成したシステムの内、ノウハウに関わらない部分のみ再現を目指す。  
  
**♦環境♦**  
調整中  
  
**♦プロジェクト構成♦**  
```text
├─ frontend/
│　　　├─ src/　　　　　　　　　　　　　　　　　　　　　　　　　:フロントエンド制御
│　　　├─ index.html　　　　　　　　　　　　　　　　　　　　　　:フロントエンド開始
│　　　└─ Dockerfile　　　　　　　　　　　　　　　　　　　　　　:フロントエンド用Docker設計ファイル
├─ backend/
│　　　├─ src/
│　　　│　　├─ main/
│　　　│　　│　　├─ java/com/example/　　　　　　　　　　　　　:バックエンド制御
│　　　│　　│　　└─ resources/
│　　　│　　│　　　　　└─ application.properties　　　　　　　:アプリケーション設定
│　　　│　　└─ test/　　　　　　　　　　　　　　　　　　　　　　:テストコード (今後作成予定)
│　　　├─ pom.xml　　　　　　　　　　　　　　　　　　　　　　　 :Mavenの構成
│　　　└─ Dockerfile　　　　　　　　　　　　　　　　　　　　　　:バックエンド用Docker設計ファイル
│
├─ docker-compose.yml　　　　　　　　　　　　　　　　　　　　　:Dockerコンテナ設定ファイル
├─ env.example　　　　　　　　　　　　　　　　　　　　　　　　　:Docker用データベース情報ファイル (必要事項を記載して.envで保存してね)
├─ bat/　　　　　　　　　　　　　　　　　　　　　　　　　　　　 :Docker用バッチファイル
│
└─ other/　　　　　　　　　　　　　　　　　　　　　　　　　　　 :設計メモなど (なくてもゲーム影響なし)
```
