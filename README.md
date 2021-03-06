# SelfMadeAroma
SelfMadeAromaアプリは自分が作ったアロマをスマホ内に保存できるアプリです。<br>
その他の機能としてアロマを作るうえで必要な精油の情報(柑橘系などの系統や揮発度など)をまとめたリストを閲覧することができます。

## なぜ作ったのか
自作のアロマを作る際、精油の情報をすぐに調べれたりできるのが便利だと思ったからです。<br>
逆に精油を調べていて思いついたものをすぐに保存できるというのが便利だと思ったからです。

## スクリーンショット
![selfMadeAromaScreenshots](https://user-images.githubusercontent.com/98923767/155232110-bc2bf331-c4be-4cd3-a1d7-85167836ca38.png)

## 動画
<https://user-images.githubusercontent.com/98923767/155825886-43137ecc-4923-4652-adc4-eea0481c3a2d.mp4>

## 使ったライブラリ
- jetpack
  - WorkManage
  - Recyclerview
  - Fragment
  - ViewModel
  - Navigation
  - Room
  - Data Binding
  - Animations & Transitions
- Third party and miscellaneous libraries
  - Croutine
  - Hilt
  - Gson
  - Glide
 
## こだわったポイント
- 外観
  - アニメーションをつけてちょっとカッコよくしたこと。
- 中身の処理
  - 自作アロマを編集する際、選択した精油の情報を一個一個削除したり名前を変えたりするのではなく一括で全部編集できるようにしたこと。
  - 業務で使われていると言われているライブラリをたくさん使っとこと。

## 改善点
- ボトムナビゲーションを使って画面を遷移するとき遷移する前に開いていた情報を保存して次に遷移したときにそれを表示する。
それをすることによって情報を毎回調べながら自作アロマをつくることができる。
- jetpackのnavigationがどのようにスタックされたり削除されたりしているのがまだ完璧に理解できていないので長い間つかっているとスタックが溜まって重くなるかもしれない。
- デザインがカッコよくない。もっとモダンなデザインにしたい。
- 現在、アロマの情報はユーザー側の端末でdbとして保存してるのですがそれをapi経由で情報をとってユーザー側の端末に保存しないかたちにしたい。
- テストをしていないのでバグがたくさんあると思う。
- 入力がでるときボトムナビゲーションが上にあがる。

## 新機能で追加しようと思っているもの(実装は未定)
- ログイン機能を付け、自分が作ったアロマをみんなに公開できるようにしたい。(Twitterみたいな感じで)

## google play storeでインストール
<https://play.google.com/store/apps/details?id=com.ordinaryhuman.selfmadearoma>
