### 原因

Fresco支持5种ABI

- armeabiv-v7a: Version 7 or higher of the ARM processor. Most Android phones released from 2011-15 are using this.
- arm64-v8a: 64-bit ARM processors. Found on new devices, like the Samsung Galaxy S6.
- armeabi: Older phones using v5 or v6 of the ARM processor.
- x86: Mostly used by tablets, and by emulators.
- x86_64: Used by 64-bit tablets.

由于大多数国内lib只在armeabiv-v7a或者armeabi下放置so文件，导致64位系统上出现`"nativeLibraryDirectories=[/data/app/com.lukouapp-1/lib/arm64, /vendor/lib64, /system/lib64]]] couldn't find "libxxxx.so" `的问题。

### 解决方案

#### 使用Gradle的分包

```
android {
  // rest of your app's logic
  splits {
    abi {
        enable true
        reset()
        include 'x86', 'x86_64', 'arm64-v8a', 'armeabi-v7a', 'armeabi'
        universalApk true
    }
  }
}
```

- include 分包的类别及所对应的ABI
- exclude 在默认的ABI之外移除所对应的ABI
- universalApk 是否生成合并包


```
If your application is not used by devices running Android 2.3 (Gingerbread), you will not need the armeabi flavor.
```
如果你的程序版本高于2.3的话，可以不用加载armeabi
