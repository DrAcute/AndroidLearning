### 概述

基于MVP架构结合自己工作经验形成的一套快速开发解决方案。

### 基本架构

![architecture](https://rawgit.com/DrAcute/AndroidLearning/master/Images/20160308_1.png)

### Model层
1.CacheSystem 缓存系统
2.DataBase 数据库
3.FileSystem 文件系统
4.NetServiceManager/NetService 网络请求模块

### Presenter层
View层与Model层不能直接交互，必须通过Presenter层
以基本业务抽象出BasePresent/BaseListPresent/BaseCacheListPresent等

### View层
用户交互部分

### 未来的修改
加入主题控制
加入插件化系统
强化日志收集系统

### 使用的library

```java
compile 'de.greenrobot:eventbus:2.4.0'

// orm
compile 'de.greenrobot:greendao:1.3.7'

// butter knife
compile 'com.jakewharton:butterknife:7.0.1'

// json
compile 'com.alibaba:fastjson:1.2.4'

// retrofit
compile 'com.squareup.retrofit:retrofit:2.0.0-beta2'
compile 'com.squareup.okhttp:okhttp:2.7.0'
compile 'com.squareup.retrofit:adapter-rxjava:2.0.0-beta2'

// ReactiveX
compile 'io.reactivex:rxjava:1.0.16'
compile 'io.reactivex:rxandroid:1.0.1'

// other
compile 'com.orhanobut:logger:1.11'
```
