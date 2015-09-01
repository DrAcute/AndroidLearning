Android提供了几种动画类型：View Animation 、Drawable Animation 、Property Animation 。View Animation相当简单，不过只能支持简单的缩放、平移、旋转、透明度基本的动画，且有一定的局限性。比如：你希望View有一个颜色的切换动画；你希望可以使用3D旋转动画；你希望当动画停止时，View的位置就是当前的位置；这些View Animation都无法做到。这就是Property Animation产生的原因，本篇详细介绍Property Animation的用法。

*总的来说，属性动画就是，动画的执行类来设置动画操作的对象的属性、持续时间，开始和结束的属性值，时间差值等，然后系统会根据设置的参数动态的变化对象的属性。*

####ObjectAnimator实现动画

简单的ObjectAnimation实现

```java
ObjectAnimator //
    // 提供了ofInt、ofFloat、ofObject，这几个方法都是设置动画作用的元素、作用的属性、动画开始、结束、以及中间的任意个属性值。
    .ofFloat(view, "rotationX", 0.0F, 360.0F)
    .setDuration(500) //
    .start();  
```
+ 当对于属性值，只设置一个的时候，会认为当然对象该属性的值为开始（getPropName反射获取），然后设置的值为终点。如果设置两个，则一个为开始、一个为结束~~~    动画更新的过程中，会不断调用setPropName更新元素的属性，所有使用ObjectAnimator更新某个属性，必须得有getter（设置一个属性值的时候）和setter方法~

+ 如果你操作对象的该属性方法里面，比如上例的setRotationX如果内部没有调用view的重绘，则你需要自己按照下面方式手动调用。

```java
anim.addUpdateListener(new AnimatorUpdateListener()
    {
    @Override
    public void onAnimationUpdate(ValueAnimator animation)
    {
        view.postInvalidate();
        view.invalidate();
    }
});  
```

+ 用PropertyValuesHolder实现多动画叠加

```java
// 利用ofFloat工厂方法构造PropertyValuesHolder类型对象，控制y属性
PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("y",ball.getY(), getHeight() - BALL_SIZE);
// 利用ofFloat工厂方法构造另一个PropertyValuesHolder类型对象，控制alpha属性
PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0f);
// 利用ofPropertyValuesHolder方法来构造ObjectAnimator对象
// 把多个属性变化结合到一个动画中去
ObjectAnimator yAlphaBouncer = ObjectAnimator
    .ofPropertyValuesHolder(ball, pvhY, pvhAlpha)
    .setDuration(DURATION / 2);
yAlphaBouncer.setInterpolator(new AccelerateInterpolator());
yAlphaBouncer.setRepeatCount(1);
yAlphaBouncer.setRepeatMode(ValueAnimator.REVERSE);
```
