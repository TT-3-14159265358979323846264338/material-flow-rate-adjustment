流動量調整♦作成中♦  
  
**♦概要♦**  
月間数量と日々の製造量を入力することで、今後の流動予定を算出する。  
  
**♦制作背景♦**  
React+TypeScript+Javaでシステム開発を勉強するために、新規作成する。今回は前職にてVBAで作成したシステムの内、ノウハウに関わらない部分のみ再現を目指す。  
  
**♦環境♦**  
**言語:**　　　　　　　Java 25, JavaScript ES6+, TypeScript  
**フレームワーク:**　　Spring boot 4.0.6, Spring Security  
**ライブラリ:**　　　　React 19.2.5, React Router 7  
**データベース:**　　　MySQL 9.6  
**フロントエンド:**　　Tailwind CSS 4  
**認証:**　　　　　　　JWT (jjwt 0.12)  
**通信:**　　　　　　　Axios 1.x  
**テスト:**　　　　　　JUnit 5, Vitest 4  
**ビルド:**　　　　　　Maven, Vite 8, Node 24  
**起動:**　　　　　　　Docker  
  
**♦プロジェクト構成♦**  
```text
├─ frontend/
│　　　├─ src/　　　　　　　　　　　　　　　　　　　　　　　　　:フロントエンド制御
│　　　├─ index.html　　　　　　　　　　　　　　　　　　　　　　:フロントエンド開始
│　　　├─ env.example　　　　　　　　　　　　　　　　　　　　　 :システム設定ファイル (必要事項を記載して.envで保存すること)
│　　　└─ Dockerfile　　　　　　　　　　　　　　　　　　　　　　:フロントエンド用Docker設計ファイル
├─ backend/
│　　　├─ src/
│　　　│　　├─ main/
│　　　│　　│　　├─ java/com/example/　　　　　　　　　　　　　:バックエンド制御
│　　　│　　│　　└─ resources/
│　　　│　　│　　　　　└─ application.properties　　　　　　　:アプリケーション設定 (初回起動時のみspring.jpa.hibernate.ddl-auto=validateにする)
│　　　│　　└─ test/　　　　　　　　　　　　　　　　　　　　　　:テストコード (今後作成予定)
│　　　├─ pom.xml　　　　　　　　　　　　　　　　　　　　　　　 :Mavenの構成
│　　　└─ Dockerfile　　　　　　　　　　　　　　　　　　　　　　:バックエンド用Docker設計ファイル
│
├─ docker-compose.yml　　　　　　　　　　　　　　　　　　　　　:Dockerコンテナ設定ファイル
├─ env.example　　　　　　　　　　　　　　　　　　　　　　　　　:システム設定ファイル (必要事項を記載して.envで保存すること)
├─ bat/　　　　　　　　　　　　　　　　　　　　　　　　　　　　 :Docker用バッチファイル
│
└─ other/　　　　　　　　　　　　　　　　　　　　　　　　　　　 :設計メモなど (なくても影響なし)
```
