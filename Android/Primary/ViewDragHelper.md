### ViewDragHelper

ViewDragHelper是一个在support-v4包中的解决View拖动手势的辅助类

#### 使用

ViewDragHelper类的设计决定了其适用于被包含在一个自定义ViewGroup之中，而不是对任意一个布局上的视图容器使用ViewDragHelper

```java

/// 这是在一个自定义FrameLayout中创建的
/// ViewDragHelper的实例是通过静态工厂方法创建的
mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {

    /// 获得能拖动的子视图
    @Override
    public boolean tryCaptureView(View view, int i) {
        return true;
    }

    /// 计算横向拖动的位置
    /// View child 被移动的控件
    /// int left 移动到的位置
    /// int dx 移动的距离
    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {
        return left;
    }

    /// 计算横向拖动的位置
    /// View child 被移动的控件
    /// int top 移动到的位置
    /// int dy 移动的距离
    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {
        return top;
    }
});

```

#### 边界检测

调用setEdgeTrackingEnabled方法，并且在Callback中重写 onEdgeTouched, onEdgeDragStarted 等方法

```java
mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_ALL);
```

#### 方法介绍
| 方法 | 介绍 |
| ------------- |:-------------:|
| int clampViewPositionHorizontal (View child, int left, int dx)	| 此方法返回一个值，告诉Helper，这个view能滑动的最大（或者负向最大）的横向坐标 |
| int clampViewPositionVertical (View child, int top, int dy)	|  此方法返回一个值，告诉Helper，这个view能滑动的最大（或者负向最大）的纵向坐标 |
| int getOrderedChildIndex (int index)	| 返回这个索引所指向的子视图的Z轴坐标 |
| int getViewHorizontalDragRange (View child)	| 返回指定View在横向上能滑动的最大距离 |
| int getViewVerticalDragRange (View child)	| 返回指定View在纵向上能滑动的最大距离 |
| void onEdgeDragStarted (int edgeFlags, int pointerId)	| 当边缘开始拖动的时候，会调用这个回调 |
| boolean onEdgeLock (int edgeFlags)	| 返回指定的边是否被锁定 |
| void onEdgeTouched (int edgeFlags, int pointerId)	| 当边缘被触摸时，系统会回调这个函数 (测试是在边缘上被点下时触发) |
| void onViewCaptured (View capturedChild, int activePointerId)	| 当有一个子视图被指定为可拖动时，系统会回调这个函数 |
| void onViewDragStateChanged (int state)	| 拖动状态改变时，会回调这个函数 |
| void onViewPositionChanged (View changedView, int left, int top, int dx, int dy)	| 当子视图位置变化时，会回调这个函数 |
| void onViewReleased (View releasedChild, float xvel, float yvel)	| 当手指从子视图松开时，会调用这个函数，同时返回在x轴和y轴上当前的速度 |
| boolean tryCaptureView (View child, int pointerId)	| 系统会依次列出这个父容器的子视图，你需要指定当前传入的这个视图是否可拖动，如果可拖动则返回true 否则为false |
