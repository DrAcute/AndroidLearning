### 正交矩阵 Matrix.orthoM()

![正交矩阵](http://www.forkosh.com/mathtex.cgi? \begin{bmatrix}\frac{2}{right-left}&0&0&-\frac{right+left}{right-left}\\ 0&\frac{2}{top-bottom}&0&-\frac{top+bottom}{top-bottom}\\0&0&\frac{-2}{far-near}&-\frac{far+near}{far-near}\\0&0&0&1\end{bmatrix})

Matrix.orthoM()
```java
public static void orthoM(float[] m, int mOffset, float left, float right, float bottom, float top, float near, float far)
```

创建正交投影矩阵

- float[] m : 目标数组，这个数组长度至少有16个元素
- int mOffset : 结果矩阵的起始偏移值，即数组m从哪一位开始存放正交
- float left : x轴的左边界
- float right : x轴的右边界
- float bottom : y轴的下边界
- float top : y轴的上边界
- float near : z轴的下边界
- float far : z轴的上边界

### 投影矩阵 Matrix.frustumM()/Matrix.perspectiveM()

注:`Matrix.frustumM()`有缺陷 而 `Matrix.perspectiveM()`在4.0后才可使用

令焦距 `a = 1.0f / (float) Math.tan(fovy * (Math.PI / 360.0)`

有投影矩阵

![投影矩阵](http://www.forkosh.com/mathtex.cgi?\begin{bmatrix}\frac{a}{aspect}&0&0&0 \\0&a&0&0 \\0&0&-\frac{far+near}{far-near}&-\frac{2far*near}{far-near} \\0&0&-1&0\end{bmatrix})

Matrix.perspectiveM()
```java
public static void perspectiveM(float[] m, int offset,
          float fovy, float aspect, float zNear, float zFar)
```

创建投影矩阵

- float[] m : 目标数组，这个数组长度至少有16个元素
- int mOffset : 结果矩阵的起始偏移值，即数组m从哪一位开始存放正交
- float fovy : y轴上的视野(角度)
- float aspect : 屏幕的宽高比 宽度/高度
- zNear ：到近处平面的距离
- zFar ： 到远处平面的距离
