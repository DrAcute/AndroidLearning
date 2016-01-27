### 关于RecyclerView高度(宽度)的分析

从RecyclerView的onMeasure开始分析

```java
@Override
protected void onMeasure(int widthSpec, int heightSpec) {

......

    if (mLayout == null) {
        defaultOnMeasure(widthSpec, heightSpec);
    } else {
        mLayout.onMeasure(mRecycler, mState, widthSpec, heightSpec);
    }

......

}
```

mLayout 指代的是已设置的LayoutManager

当 mLayout==null 时，我们来看 defaultOnMeasure(int widthSpec, int heightSpec) 中

```java
final int heightMode = MeasureSpec.getMode(heightSpec);
final int heightSize = MeasureSpec.getSize(heightSpec);
switch (heightMode) {
    case MeasureSpec.EXACTLY:
    case MeasureSpec.AT_MOST:
        height = heightSize;
        break;
    case MeasureSpec.UNSPECIFIED:
    default:
        height = ViewCompat.getMinimumHeight(this);
        break;
}
```

这和通常View的onMeasure差别不大，但要注意：

- 当RecyclerView为wrap_content时height的值和match_parent一样，即设置wrap_content时也会占满父级Layout。
- 当RecyclerView被嵌套在RecyclerView，ListView，ScrollView里、并且没有指定确切宽(高)时，mode会被指定为MeasureSpec.UNSPECIFIED，此时RecyclerView的高（宽）会被设置为ViewCompat.getMinimumHeight(this)，而这个值一般来说是0。所以RecyclerView用于嵌套时要特殊处理。

我们再来看下mLayout!=null 时 mLayout.onMeasure(mRecycler, mState, widthSpec, heightSpec);

LayoutManager中
```java
public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
    mRecyclerView.defaultOnMeasure(widthSpec, heightSpec);
}
```
我们可以看到如果不重写LayoutManager中的onMeasure的话，onMeasure也是指向defaultOnMeasure(int widthSpec, int heightSpec)的。

而常用的LinearLayoutManager和GridLayoutManager中并未重写onMeasure方法。

### 解决嵌套宽高的问题

采用指定宽高并重写触摸事件方法
// TODO
