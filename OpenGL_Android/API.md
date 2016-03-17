### Matrix.orthoM()

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
