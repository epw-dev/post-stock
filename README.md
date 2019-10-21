# slack-post-stock

Slackワークスペース内の特定のチャンネルへの投稿をDBに保存します。

## 開発環境

- Java8
- Docker
    - Postgresql

## 開発準備

### DBの準備

```
cd ${プロジェクトルート}

# 初回のみ
docker-compose up -d

# 2回目以降
docker-compose start
```

#### DBへ接続
```
docker-compose exec db psql -U dev
```
psql の対話モードへ入ります

### Java
GoogleJavaStyle を適応
(特にビルド時になにかするわけではなくideaに任せる)

#### 環境変数

|キー|概要|例|
|-|-|-|
|DATABASE_URL|DBの接続文字列|e.g.) jdbc:postgresql://localhost:5432/dev|
|DATABASE_USER|DB接続ユーザ名||
|DATABASE_PASSWORD|DB接続ユーザのパスワード||
|SLACK_TOKEN|アプリがSlackAPIを利用する際のトークン||
|STOCK_TARGET_CHANNLE_ID|監視するチャンネルID||


#### ローカル起動
以下のいずれかを行ってアプリを起動する
- 起動パラメータに`-Dspring.profiles.active=local`を付与
- 環境変数`SPRING_PROFILES_ACTIVE`へ`local`を設定する

↑の状態で`SlackPostStockApplication.java` を実行、もしくは`./gradlew bootRun`を実行する