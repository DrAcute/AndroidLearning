- 每一次手势交互都会依照以下顺序进行

 1. 接触屏幕的一刹那，触发一个MotionEvent事件

 2. 该事件被OnTouchListener监听，在其onTouch()方法里获得该MotionEvent对象

 3. 通过GestureDetector(手势识别器)转发MotionEvent对象至OnGestureListener

 4. OnGestureListener获得该对象，根据该对象封装的信息，做出合适的反馈

- 当你实例化一个GestureDetectorCompat对象时，需要一个实现了GestureDetector.OnGestureListener接口的的对象作为参数。当某个特定的触摸事件发生时，GestureDetector.OnGestureListener就会通知用户。为了让你的GestureDetector对象能到接收到触摸事件，你需要重写View或Activity的onTouchEvent()函数，并且把所有捕获到的事件传递给detector对象。

 on型的函数返回值是true意味着你已经处理完这个触摸事件了。如果返回false，则会把事件沿view栈传递，直到触摸事件被成功地处理了。

- GestureDetector.SimpleOnGestureListener类实现了所有的on型函数，并且都返回false。因此,你可以仅仅重写你所需要的函数。

- 无论你是否使用GestureDetector.OnGestureListener类，最好都实现onDown()函数并且返回true。这是因为所有的手势都是由onDown()消息开始的。

```java
public class MainActivity extends Activity {

    private GestureDetectorCompat mDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString());
            return true;
        }
    }
}
```

- 手势类别
 按下（onDown）： 刚刚手指接触到触摸屏的那一刹那，就是触的那一下。
 抛掷（onFling）： 手指在触摸屏上迅速移动，并松开的动作。
 长按（onLongPress）： 手指按在持续一段时间，并且没有松开。
 滚动（onScroll）： 手指在触摸屏上滑动。
 按住（onShowPress）： 手指按在触摸屏上，它的时间范围在按下起效，在长按之前。
 抬起（onSingleTapUp）：手指离开触摸屏的那一刹那。

 任何手势动作都会先执行一次按下（onDown）动作。

 长按（onLongPress）动作前一定会执行一次按住（onShowPress）动作。
 按住（onShowPress）动作和按下（onDown）动作之后都会执行一次抬起（onSingleTapUp）动作。
 长按（onLongPress）、滚动（onScroll）和抛掷（onFling）动作之后都不会执行抬起（onSingleTapUp）动作。
