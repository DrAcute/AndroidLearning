对于Android为什么要使用多线程，因为从Android4.0之后，谷歌规定了网络操作不允许放在主线程中执行，由此就有了多线程的机制，有个JAVA学习经验的朋友一定知道多线程指的是什么，简单来讲就是，在JAVA程序中，main()函数开启的即为这个程序的主线程，而我们为了完成一些耗时操作又不想影响到主线程的执行，这是我们往往通过Thread对象创建一个子线程来执行。简单的说，一个程序只有一个主线程，可以有多个主线程。在Android世界中也是这样，Android属于单线程模型，耗时操作必须放在非主线程中执行，故而谷歌为了方便我们使用线程，为我们提供一个AsyncTask多线程操作对象。

对于Android使用线程还有一点需要特别注意，哪就是Android不允许在子线程中更新UI，相信很多初学者一定遇到过这个问题，这个怎么解决呢？在Activity中，我们可以通过

```java
new Thread(new Runnable() {
    @Override
    public void run() {
        Log.v("abc", "子线程执行");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.v("abc", "返回主线程执行");
            }
        });
    }
}).start();
```

来实现我们的效果，不过有一点需要注意的是，在Fragement中，runOnUiThread()不能使用，所以大家在使用时稍微注意一下即可。当然了Android中我们还可以通过Handler+Messager来完成多线程操作，对于这里个的使用，在之前的博客中已经为大家介绍，就不在赘述，下面我们开始介绍本篇的重点：AsyncTask的使用。

#### AsyncTask<Parans, Progress, Result>是一个抽象类，我们需要首先实现这个抽象类，然后才能使用，对于它的三个参数：

- Parans:启动任务时输入的参数类型；
- progress：后台任务执行中返回值的类型；
- Result：后台执行任务完成后返回的结果类型。

#### 构建AsyncTask子类的回调方法：

- 1、doInBackground:必须重写，异步执行后台线程将要完成的任务；
- 2、onPreExecute:执行后台耗时操作前被调用，通过用户完成一些初始化操作；
- 3、onPostExecute:当doInBackground()完成之后，系统会自动调用，并将doInBackground()执行后的返回值，传递给onPostExecute()方法，简单来说就是，doInBackground()完成耗时操作，结果交个onPostExecute()方法更新UI；
- 4、onProgressUpdate：在doInBackground()方法中，调用publishProgress()方法，更新任务的执行进度后，就会触发该方法。

#### AsyncTask中是个抽象方法的执行顺序：

1、创建一个AsyncTask子类对象：

```java
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.v("abc", "onPreExecute");
    }
    @Override
    protected Void doInBackground(Void... arg0) {
        Log.v("abc", "doInBackground");
        publishProgress(arg0);
        return null;
    }
    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        Log.v("abc", "onPostExecute");
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.v("abc", "onProgressUpdate");
    }
}
```

2、在MainActivity中调用给子线程，进行执行：

```java
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MyAsyncTask().execute();//启动执行
}

#### 加载网络图片：

1、首先创建一个承载Activity布局文件的xml：

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_margin="10dp"
    tools:context=".MainActivity" >
    <ImageView
        android:id="@+id/img"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
    <ProgressBar
        android:id="@+id/progressbar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"
        android:layout_centerInParent="true" />
</RelativeLayout>
```

布局文件很简单，就是一个ImageView+ProgressBar，在加载ImageView是我们通过ProgressBar来提醒用户等待。

2、创建我们的Activity对象：

```java
public class AsyncTaskImager extends Activity {
    private ProgressBar pb;
    private ImageView image;
    private static final String url = "https://www.baidu.com/img/bdlogo.png";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imager);
        init();
        new ImageAsyncTask().execute(url);
    }
    private void init() {
        pb = (ProgressBar) findViewById(R.id.progressbar);
        image = (ImageView) findViewById(R.id.img);
    }
    class ImageAsyncTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            URLConnection conn = null;
            String url = params[0];
            try {
                conn = new URL(url).openConnection();
                InputStream in = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(in);
                bitmap = BitmapFactory.decodeStream(bis);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            pb.setVisibility(View.GONE);
            image.setImageBitmap(result);
        }
    }
}
```

到这里我们的大功已经基本完成了，最后不要忘记在AndroidManifest.xml中进行一下声明。

3、添加网络操作权限：

    <uses-permission android:name="android.permission.INTERNET"/>

因为我们需要使用到网络连接，所以我们需要在AndroidManifest.xml中添加一个网络访问权限。
