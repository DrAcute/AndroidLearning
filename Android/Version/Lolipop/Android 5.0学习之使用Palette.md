### 动态色彩

根据图片来决定标题的颜色和标题栏的背景色，这样视觉上更具有冲击力和新鲜感，而不像统一色调那样呆板。

![be0640b9-3497-407d-be3f-6b511c9fcfac.jpg]({{site.baseurl}}/Android/Version/Lolipop/be0640b9-3497-407d-be3f-6b511c9fcfac.jpg)


### Palette是什么？

它能让你从图像中提取突出的颜色。这个类能提取以下突出的颜色：

- Vibrant(充满活力的)
- Vibrant dark(充满活力的黑)
- Vibrant light(充满活力的亮)
- Muted(柔和的)
- Muted dark(柔和的黑)
- Muted lighr(柔和的亮)

### 如何使用？

要提取这些颜色，在你加载图片的后台线程中传递一个位图对象给Palette.generate()静态方法。如果你不适用线程，则调用Palette.generateAsync()方法并且提供一个监听器去替代。
你可以在Palette类中使用getter方法来从检索突出的颜色，比如Palette.getVibrantColor。

如果是Android Studio 要在你的项目中使用Palette类，增加下面的Gradle依赖到你的程序的模块(module)中：

    dependencies { 
        ... 
        compile 'com.android.support:palette-v7:21.0.+' 
    }  

然后使用generateAsync方法传入当前图片的bitmap，在传入一个监听，在监听里面我们拿到图片上颜色充满活力的颜色，最后设置标题背景和字体的颜色，代码如下：

    Palette.generateAsync(bitmap, 
        new Palette.PaletteAsyncListener() { 
        @Override 
        public void onGenerated(Palette palette) { 
            Palette.Swatch vibrant = palette.getVibrantSwatch(); 
            if (swatch != null) { 
                // If we have a vibrant color 
                // update the title TextView 
                titleView.setBackgroundColor(vibrant.getRgb()); 
                titleView.setTextColor( vibrant.getTitleTextColor()); 
            } 
        } 
    });  

