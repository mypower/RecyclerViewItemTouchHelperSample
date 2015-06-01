## はじめに

[ItemTouchHelper - Android Developers](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.html)

Android support libraryのRecyclerViewにItemTouchHelperが追加された. support library v22.2.0を導入することで使用できる. 

```gradle
compile 'com.android.support:recyclerview-v7:22.2.0'
```

ItemTouchHelperを使用すればアイテム項目のスワイプやドラッグ＆ドロップ操作を簡単に導入できる. 

```java
    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
    recyclerView.setHasFixedSize(false);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new RecyclerViewAdapter();
    recyclerView.setAdapter(adapter);
    ItemTouchHelper itemDecor = new ItemTouchHelper(
        new SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            final int fromPos = viewHolder.getAdapterPosition();
            final int toPos = target.getAdapterPosition();
            adapter.notifyItemMoved(fromPos, toPos);
            return true;
          }

          @Override
          public void onSwiped(ViewHolder viewHolder, int direction) {
            final int fromPos = viewHolder.getAdapterPosition();
            datasource.remove(fromPos);
            adapter.notifyItemRemoved(fromPos);
          }
        });
    itemDecor.attachToRecyclerView(recyclerView);
```

関連するクラスとメソッドを下記する. 


## ItemDecoration

> android.support.v7.widget.RecyclerView.ItemDecoration

### Class Overview

> An ItemDecoration allows the application to add a special drawing and layout offset to specific 
> item views from the adapter's data set. This can be useful for drawing dividers between items, 
> highlights, visual grouping boundaries and more.

ItemDecorationはRecyclerViewのAdapterが持つデータセットに対する特殊な描画やレイアウトといった装飾効果を与えるものである. リストアイテムの区切り線やハイライト, グルーピング表示といったことによく使用される. 

> All ItemDecorations are drawn in the order they were added, before the item views (in [onDraw()](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.ItemDecoration.html#onDraw(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.State)) and 
> after the items (in [onDrawOver(Canvas, RecyclerView, RecyclerView.State)](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.ItemDecoration.html#onDrawOver(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.State))).

アイテム項目の装飾は項目を描画する前に([onDraw(Canvas, RecyclerView, RecyclerView.State)](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.ItemDecoration.html#onDraw(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.State)))が呼ばれ, 項目が追加された後に([onDrawOver(Canvas, RecyclerView, RecyclerView.State)](http://developer.android.com/reference/android/support/v7/widget/RecyclerView.ItemDecoration.html#onDrawOver(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.State)))が呼ばれる. 


### Method

#### public void onDraw (Canvas c, RecyclerView parent, RecyclerView.State state)

> Draw any appropriate decorations into the Canvas supplied to the RecyclerView. Any content drawn by this method will be drawn before the item views are drawn, and will thus appear underneath the views.

RecyclerViewのCanvasに装飾を行う. このメソッドはアイテム項目が描画される前に呼ばれる. そのため, アイテムビュよりも背面へ描画されることになる. 


#### public void onDrawOver (Canvas c, RecyclerView parent, RecyclerView.State state)

> Draw any appropriate decorations into the Canvas supplied to the RecyclerView. Any content drawn by this method will be drawn after the item views are drawn and will thus appear over the views.

RecyclerViewのCanvasに装飾を行う. このメソッドはアイテム項目が描画された後に呼ばれる. そのため, アイテムビュよりも前面へ描画されることになる. 

---

## ItemTouchHelper

> android.support.v7.widget.helper.ItemTouchHelper
>   extends RecyclerView.ItemDecoration
>   implements RecyclerView.OnChildAttachStateChangeListener

### Class Overview

> This is a utility class to add swipe to dismiss and drag & drop support to RecyclerView.
> 
> It works with a RecyclerView and a Callback class, which configures what type of interactions 
> are enabled and also receives events when user performs these actions.

ItemTouchHelperはRecyclerView Itemのswipe to dismissとdrag & dropをサポートするユーティリティである. 

> Depending on which functionality you support, you should override 
> [onMove(RecyclerView, ViewHolder, ViewHolder)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onMove(android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.ViewHolder, android.support.v7.widget.RecyclerView.ViewHolder)) and / or [onSwiped(ViewHolder, int)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onSwiped(android.support.v7.widget.RecyclerView.ViewHolder, int)). 
> 
> This class is designed to work with any LayoutManager but for certain situations, 
> it can be optimized for your custom LayoutManager by extending methods in the 
> [ItemTouchHelper.Callback](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html) class or implementing [ItemTouchHelper.ViewDropHandler](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.ViewDropHandler.html) 
>  interface in your LayoutManager.

どちらの機能をサポートするかで,  [onMove(RecyclerView, ViewHolder, ViewHolder)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onMove(android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.ViewHolder, android.support.v7.widget.RecyclerView.ViewHolder)), [onSwiped(ViewHolder, int)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onSwiped(android.support.v7.widget.RecyclerView.ViewHolder, int))のどちらか, あるいは両方をoverrideする. 

このクラスはいくつかのLayoutManagerと協調し, 特定の状況下でうまく動作するようにデザインされている. カスタムLayoutManagerを作成する場合は[ItemTouchHelper.Callback](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html)を拡張して最適化するか[ItemTouchHelper.ViewDropHandler](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.ViewDropHandler.html)インタフェースをカスタムLayoutManagerに実装するかする. 

> By default, ItemTouchHelper moves the items' translateX/Y properties to reposition them. 
> On platforms older than Honeycomb, ItemTouchHelper uses canvas translations and View's 
> visibility property to move items in response to touch events. 
> You can customize these behaviors by overriding [onChildDraw(Canvas, RecyclerView, ViewHolder, float, float, int, boolean)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onChildDraw(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.ViewHolder, float, float, int, boolean)) or [onChildDrawOver(Canvas, RecyclerView, ViewHolder, float, float, int, boolean)](http://developer.android.com/reference/android/support/v7/widget/helper/ItemTouchHelper.Callback.html#onChildDrawOver(android.graphics.Canvas, android.support.v7.widget.RecyclerView, android.support.v7.widget.RecyclerView.ViewHolder, float, float, int, boolean)).

> Most of the time, you only need to override `onChildDraw` but due to limitations of platform prior 
> to Honeycomb, you may need to implement `onChildDrawOver` as well.

標準ではItemTouchHelperはtranslateX/Yプロパティでアイテム項目を移動させる. Honeycombより古いプラットフォームではcanvasのtranslationとViewのvisibilityプロパティでアイテム項目を移動させる. 
これらの振る舞いはonChildDraw(Canvas, RecyclerView, ViewHolder, float, float, int, boolean)かonChildDrawOver(Canvas, RecyclerView, ViewHolder, float, float, int, boolean)をオーバライドすることでカスタマイズできる. 

多くの場合, `onChildDraw`のオーバライドのみが必要であるが, Honeycombより古いプラットフォームでは`onChildDrawOver`の実装を必要とする場合がある. 

---


## ItemTouchHelper.Callback

> android.support.v7.widget.helper.ItemTouchHelper.Callback

### Class Overview

> This class is the contract between ItemTouchHelper and your application. It lets you control which touch 
> behaviors are enabled per each ViewHolder and also receive callbacks when user performs these actions.

このクラスはあなたのアプリケーションとItemTouchHelperの間をとりもつクラスである. タップが有効なViewHolder毎の振る舞いを制御し, ユーザがそれらのアクションを起こした時のコールバックを受け取る. 

> To control which actions user can take on each view, you should override getMovementFlags(RecyclerView, ViewHolder) and return appropriate set of direction flags. (LEFT, RIGHT, START, END, UP, DOWN).
>  You can use makeMovementFlags(int, int) to easily construct it. Alternatively, you can use ItemTouchHelper.SimpleCallback.

ユーザがリスト項目に対してとれるアクションをコントロールするにはgetMovementFlags(RecyclerView, ViewHolder)メソッドをオーバーライドし, コントロール可能な方向を示すフラグ(LEFT, RIGHT, START, END, UP, DOWN)を返却する. 
方向を示すフラグを簡単に作成するためにmakeMovementFlags(int, int)メソッドも用意されている. 
あるいはItemTouchHelper.SimpleCallbackを使うこともできる. 

> If user drags an item, ItemTouchHelper will call onMove(recyclerView, dragged, target). 
> Upon receiving this callback, you should move the item from the old position (dragged.getAdapterPosition()) 
> to new position (target.getAdapterPosition()) in your adapter and also call notifyItemMoved(int, int). 

もし, ユーザがリスト項目をドラッグしたならば, ItemTouchHelperはonMove(recyclerView, dragged, target)を呼ぶ. 
このコールバックう受け取った時, 古いポジション (dragged.getAdapterPosition())にあるリスト項目は新しいポジション(target.getAdapterPosition())に移動させ, それが終わるとnotifyItemMoved(int, int)を呼び出す必要がある. 

> When a dragging View overlaps multiple other views, Callback chooses the closest View with which dragged View might have changed positions.
>  Although this approach works for many use cases, if you have a custom LayoutManager, you can override 
>  chooseDropTarget(ViewHolder, java.util.List, int, int) to select a custom drop target.

ドラッグ中のViewが他の複数のViewと重なった場合, コールバックにはより近くにあるViewのポジションがドロップ先のポジションとして渡される. このアプローチは多くのユースケースで有効に働くが, カスタムLayoutManagerを使用しており, これをカスタマイズしたい場合はchooseDropTarget(ViewHolder, java.util.List, int, int) でドロップ対象を変更することができる. 

> When a View is swiped, ItemTouchHelper animates it until it goes out of bounds, then calls 
> onSwiped(ViewHolder, int). At this point, you should update your adapter (e.g. remove the item) and 
> call related Adapter#notify event.

Viewがスワイプされた時, ItemTouchHelperは画面外に追い出すアニメーション効果をもたらす. この時onSwiped(ViewHolder, int)のコールバックが呼ばれる. この時点でRecyclerViewのAdapterを更新(例えば削除)し, Adapterのnotifyイベントを呼び出す必要がある. 

---

## ItemTouchHelper.SimpleCallback

> android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback

### Class Overview

> A simple wrapper to the default Callback which you can construct with drag and swipe directions and 
> this class will handle the flag callbacks. You should still override onMove or onSwiped depending on your use case.

ItemTouchHelper.Callbackのシンプルなラッパー. 指定されたフラグ(LEFT, RIGHT, START, END, UP, DOWN)に対するスワイプとドラッグのコールバックをハンドリングする. あなたのユースケースにあわせてonMoveとonSwipedをオーバライドする. 

```java
ItemTouchHelper mIth = new ItemTouchHelper(
     new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
         ItemTouchHelper.LEFT) {
         public abstract boolean onMove(RecyclerView recyclerView,
             ViewHolder viewHolder, ViewHolder target) {
             final int fromPos = viewHolder.getAdapterPosition();
             final int toPos = viewHolder.getAdapterPosition();
             // move item in `fromPos` to `toPos` in adapter.
             return true;// true if moved, false otherwise
         }
         public void onSwiped(ViewHolder viewHolder, int direction) {
             // remove from adapter
         }
 });
```

