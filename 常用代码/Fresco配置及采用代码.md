### 配置

```java
public static final int MAX_DISK_CACHE_SIZE = 100 * ByteConstants.MB;
public static final int MAX_MEMORY_CACHE_SIZE = (int) Runtime.getRuntime().maxMemory() / 4;
public static final String IMAGE_CACHE_FOLDER = "";
private void initializeFresco() {
    // 配置小图片的磁盘缓存
    DiskCacheConfig diskSmallCacheConfig = DiskCacheConfig.newBuilder().setBaseDirectoryPath(context.getApplicationContext().getCacheDir())//缓存图片基路径
                  .setBaseDirectoryName(IMAGE_PIPELINE_SMALL_CACHE_DIR)//文件夹名
                  .setMaxCacheSize(MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                  .setMaxCacheSizeOnLowDiskSpace(MAX_SMALL_DISK_LOW_CACHE_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                  .setMaxCacheSizeOnVeryLowDiskSpace(MAX_SMALL_DISK_VERYLOW_CACHE_SIZE)//缓存的最大大小,当设备极低磁盘空间
                  .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                  .build();

    // 配置默认磁盘缓存
    DiskCacheConfig cacheConfig = DiskCacheConfig.newBuilder()
            .setBaseDirectoryPath(Environment.getExternalStorageDirectory())
            .setBaseDirectoryName(IMAGE_CACHE_FOLDER)
            .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
            .build();

    // 配置内存缓存
    Supplier<MemoryCacheParams> memoryCacheSupplier = new Supplier<MemoryCacheParams>() {
        @Override
        public MemoryCacheParams get() {
            return new MemoryCacheParams(MAX_MEMORY_CACHE_SIZE, Integer.MAX_VALUE, MAX_MEMORY_CACHE_SIZE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    };
    ImagePipelineConfig pipelineConfig = ImagePipelineConfig.newBuilder(this)
            .setMainDiskCacheConfig(cacheConfig)
            .setBitmapMemoryCacheParamsSupplier(memoryCacheSupplier)
            .build();
    Fresco.initialize(this, pipelineConfig);
}
```

### 获得缓存大小

```java
Fresco.getImagePipelineFactory().getSmallImageDiskStorageCache().getSize() + Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize()
```

### 清除缓存

```java
Fresco.getImagePipelineFactory().getMainDiskStorageCache().clearAll();
```
