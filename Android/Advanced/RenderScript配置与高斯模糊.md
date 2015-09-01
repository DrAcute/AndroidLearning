##RenderScript是低级的高性能编程语言，用于3D渲染和处理密集型计算

### 使用

在build.gradle中加入

```java
android {
  compileSdkVersion 22
  buildToolsVersion "22.0.1"

  defaultConfig {
    renderscriptTargetApi 22
    renderscriptSupportModeEnabled true
    ...
  }
  ...
}
```

### 利用RenderScript对图像进行高斯模糊

```java
RenderScript mRs = RenderScript.create(context);
ScriptIntrinsicBlur mBlur = ScriptIntrinsicBlur.create(mRs, Element.U8_4(mRs));
```

先对图片进行降低采样处理，将图片缩小到原来的4分之一，然后进行模糊算法；模糊完成后，再将该图片放大4倍，缩减模糊时间

```java
Matrix matrix = new Matrix();
matrix.postScale(0.25f, 0.25f);
Bitmap mbmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
```

+ Allocation OverView
This class provides the primary method through which data is passed to and from RenderScript kernels. An Allocation provides the backing store for a given Type.
~总而言之，Allocation封装RenderScript输入输出的参数

```java
Allocation mInAllocation = Allocation.createFromBitmap(mRs, mbmp, Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
Allocation mOutAllocation = Allocation.createTyped(mRs, mInAllocation.getType());
mBlur.setRadius(radius / 4);
mBlur.setInput(mInAllocation);
mBlur.forEach(mOutAllocation);
mOutAllocation.copyTo(mbmp);
```
