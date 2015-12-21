此文是看 <http://a.codekk.com/detail/Android/grumoon/Volley%20%E6%BA%90%E7%A0%81%E8%A7%A3%E6%9E%90> 做的笔记
### Volley.java

Volley类有多个重载的静态方法 来创建RequestQueue

```java
public static RequestQueue newRequestQueue(Context context, HttpStack stack, int maxDiskCacheBytes)
public static RequestQueue newRequestQueue(Context context, int maxDiskCacheBytes)
public static RequestQueue newRequestQueue(Context context, HttpStack stack)
public static RequestQueue newRequestQueue(Context context)
```

如果HttpStack为空则创建默认的HttpStack

```java
if (stack == null) {
    if (Build.VERSION.SDK_INT >= 9) {
        stack = new HurlStack();
    } else {
        stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
    }
}
Network network = new BasicNetwork(stack);
```

- `HurlStack`与`HttpClientStack`是对`HttpUrlConnection`与`HttpClient`的封装，我们也可以自定义`HttpStack`来封装网络请求的实现，比如采用okhttp
- 在SDK9以前`HttpUrlConnection`有Bug, 调用 close() 函数会影响连接池，导致连接复用失效，所以在 Froyo 之前使用`HttpURLConnection`需要关闭 keepAlive。 具体查看<http://android-developers.blogspot.com/2011/09/androids-http-clients.html>

*newRequestQueue返回的是调用过start的RequestQueue*

### RequestQueue.java

#### 构造函数

```java
public RequestQueue(Cache cache, Network network, int threadPoolSize,
        ResponseDelivery delivery) {
    mCache = cache;
    mNetwork = network;
    mDispatchers = new NetworkDispatcher[threadPoolSize];
    mDelivery = delivery;
}
public RequestQueue(Cache cache, Network network, int threadPoolSize) {
    this(cache, network, threadPoolSize,
            new ExecutorDelivery(new Handler(Looper.getMainLooper())));
}
public RequestQueue(Cache cache, Network network) {
    this(cache, network, DEFAULT_NETWORK_THREAD_POOL_SIZE);
}
```
- `Volley.newRequestQueue()`方法使用的是`RequestQueue(Cache cache, Network network)`构造函数
- 默认的threadPoolSize为4

#### 主要成员变量

RequestQueue 中维护了两个基于优先级的 Request 队列，缓存请求队列和网络请求队列。
放在缓存请求队列中的 Request，将通过缓存获取数据；放在网络请求队列中的 Request，将通过网络获取数据。

```java
private final PriorityBlockingQueue<Request<?>> mCacheQueue = new PriorityBlockingQueue<Request<?>>();
private final PriorityBlockingQueue<Request<?>> mNetworkQueue = new PriorityBlockingQueue<Request<?>>();
```

维护了一个正在进行中，尚未完成的请求集合。

```java
private final Set<Request<?>> mCurrentRequests = new HashSet<Request<?>>();
```

维护了一个等待请求的集合，如果一个请求正在被处理并且可以被缓存，后续的相同 url 的请求，将进入此等待队列。

```java
private final Map<String, Queue<Request<?>>> mWaitingRequests = new HashMap<String, Queue<Request<?>>>();
```

#### 主要方法

##### start()
```java
public void start() {
    stop();  // Make sure any currently running dispatchers are stopped.
    // Create the cache dispatcher and start it.
    mCacheDispatcher = new CacheDispatcher(mCacheQueue, mNetworkQueue, mCache, mDelivery);
    mCacheDispatcher.start();

    // Create network dispatchers (and corresponding threads) up to the pool size.
    for (int i = 0; i < mDispatchers.length; i++) {
        NetworkDispatcher networkDispatcher = new NetworkDispatcher(mNetworkQueue, mNetwork,
                mCache, mDelivery);
        mDispatchers[i] = networkDispatcher;
        networkDispatcher.start();
    }
}
```
start 方法中，开启一个缓存调度线程CacheDispatcher和 n 个网络调度线程NetworkDispatcher，这里 n 默认为 4，存在优化的余地，比如可以根据 CPU 核数以及网络类型计算更合适的并发数。
缓存调度线程不断的从缓存请求队列中取出 Request 去处理，网络调度线程不断的从网络请求队列中取出 Request 去处理。

##### stop()
```java
public void stop() {
    if (mCacheDispatcher != null) {
        mCacheDispatcher.quit();
    }
    for (int i = 0; i < mDispatchers.length; i++) {
        if (mDispatchers[i] != null) {
            mDispatchers[i].quit();
        }
    }
}
```
退出缓存调度线程 和 请求调度线程

##### Request add(Request request)
