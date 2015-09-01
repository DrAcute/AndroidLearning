###Class General Hierarchy

两者区别在于SpannableString传入String后无法更改内容，也无法拼接多个SpannableString；SpanableStringBuilder则可以；类似String与StringBuilder的区别。



void setSpan (Object what, int start, int end, int flags)

 参数说明：

- object what ： 对应各种Span

- int start：指定Span起始位置，索引从0开始

- int end：结束位置，不包括end指定的位置

- int flag：

 - Spannable.SPAN_EXCLUSIVE_EXCLUSIVE 前后都不包括，在指定的范围前或后插入新字符都不应用新样式

 - Spannable.SPAN_EXCLUSIVE_INCLUSIVE 前面不包括，后面包括，在范围指定字符后插入新字符时应用新样式

 - Spannable.SPAN_INCLUSIVE_EXCLUSIVE 前面包括，后面不包括

 - Spannable.SPAN_INCLUSIVE_INCLUSIVE 前后都包括

###Span样式

- 字体颜色

        setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 字体背景颜色

        setSpan(new BackgroundColorSpan(Color.YELLOW), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 字体大小

        setSpan(new AbsoluteSizeSpan(16), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 字体样式（粗体、斜体）

        setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 删除线

        setSpan(new StrikethroughSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 下划线

        setSpan(new UnderlineSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

- 图片

        Drawable d = getResources().getDrawable(R.drawable.ic_launcher);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
        setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
