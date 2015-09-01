### Parcelable和Serializable接口简介

#### 为什么要序列化？

1. 永久性保存对象，保存对象的字节序列到本地文件中；

2. 通过序列化对象在 网络中传递对象 ；

3. 通过序列化在 进程间传递对象 。

#### 实现序列化的方法

Android中实现序列化有两个选择：一是实现Serializable接口（是 JavaSE 本身就支持的），一是实现Parcelable接口（是 Android特有功能 ，效率比实现Serializable接口高效，可用于Intent数据传递，也可以用于进程间通信（IPC））。实现Serializable接口非常简单，声明一下就可以了，而实现Parcelable接口稍微复杂一些，但效率更高，推荐用这种方法提高性能。

*注： Android中Intent传递对象有两种方法 ：一是Bundle.putSerializable(Key，Object)，另一种是Bundle.putParcelable(Key，Object)。当然这些Object是有一定的条件的，前者是实现了Serializable接口，而后者是实现了Parcelable接口。*

#### 选择序列化方法的原则

1. 在使用内存的时候，Parcelable比Serializable性能高，所以推荐使用Parcelable。

2. Serializable在 序列化的时候会产生大量的临时变量 ，从而引起频繁的GC。

3. Parcelable不能使用在要 将数据存储在磁盘上的情况 ，因为Parcelable不能很好的保证数据的持续性在外界有变化的情况下。尽管Serializable效率低点，但此时还是建议使用Serializable 。

#### 应用场景

需要在多个部件(Activity或Service)之间通过Intent传递一些数据，简单类型（如：数字、字符串）的可以直接放入Intent。 复杂类型 必须实现Parcelable接口。

#### Parcelable接口定义

``` java
public interface Parcelable {
    //内容描述接口，基本不用管
    public int describeContents();
    //写入接口函数，打包
    public void writeToParcel(Parcel dest, int flags);
    //读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。因为实现类在这里还是不可知的，所以需要用到模板的方式，继承类名通过模板参数传入
    //为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
    public interface Creator<T> {
        public T createFromParcel(Parcel source);
        public T[] newArray(int size);
    }
}
```

#### 实现Parcelable步骤

1. implements Parcelable

2. 重写writeToParcel方法，将你的对象序列化为一个Parcel对象，即：将类的数据写入外部提供的Parcel中，打包需要传递的数据到Parcel容器保存，以便从 Parcel容器获取数据

3. 重写describeContents方法，内容接口描述，默认返回0就可以

4. 实例化静态内部对象CREATOR实现接口Parcelable.Creator

        public static final Parcelable.Creator<T> CREATOR

    注：其中 public static final一个都不能少，内部对象CREATOR的名称也不能改变 ，必须全部大写。需重写本接口中的两个方法：createFromParcel(Parcel in) 实现从Parcel容器中读取传递数据值，封装成Parcelable对象返回逻辑层，newArray(int size) 创建一个类型为T，长度为size的数组，仅一句话即可（return new T[size]），供外部类反序列化本类数组使用。

    简而言之：通过writeToParcel将你的对象映射成Parcel对象，再通过createFromParcel将Parcel对象映射成你的对象。也可以将Parcel看成是一个流，通过writeToParcel把对象写到流里面，在通过createFromParcel从流里读取对象，只不过这个过程需要你来实现，因此写的顺序和读的顺序必须一致。

``` java
public class MyParcelable implements Parcelable {
    private int mData;
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mData);
    }
    public static final Parcelable.Creator<MyParcelable> CREATOR = new Parcelable.Creator<MyParcelable>() {
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };
    private MyParcelable(Parcel in) {
        mData = in.readInt();
    }
}
```

#### Serializable实现与Parcelabel实现的区别

1. Serializable的实现，只需要implements  Serializable 即可。这只是给对象打了一个标记，系统会自动将其序列化。

2. Parcelabel的实现，不仅需要implements  Parcelabel，还需要在类中添加一个 静态成员变量CREATOR ，这个变量需要实现 Parcelable.Creator 接口。

### 序列化、反序列化

#### 序列化、反序列化是什么？

对象的序列化 : 把 Java 对象转换为字节序列并存储至一个储存媒介的过程。

对象的反序列化 : 把字节序列恢复为Java对象的过程。

#### Parcelablehe 和Serialiable接口的区别

1. 作用

    Serializable的作用 是为了保存对象的属性到本地文件、数据库、网络流、rmi以方便数据传输，当然这种传输可以是程序内的也可以是两个程序间的

    而Android的Parcelable的设计初衷是因为Serializable效率过慢，为了在程序内不同组件间以及不同Android程序间(AIDL)高效的传输数据而设计，这些数据仅在内存中存在， Parcelable是通过IBinder通信的消息的载体。

    从上面的设计上我们就可以看出优劣了。

2. 效率及选择

    Parcelable的性能比Serializable好，在内存开销方面较小，所以在内存间数据传输时推荐使用Parcelable，如activity间传输数据，而Serializable可将数据持久化方便保存，所以在需要保存或网络传输数据时选择Serializable，因为android不同版本Parcelable可能不同，所以 不推荐使用Parcelable进行数据持久化

3. 编程实现

对于Serializable，类只需要实现Serializable接口，并提供一个序列化版本id(serialVersionUID)即可。而Parcelable则需要实现writeToParcel、describeContents函数以及静态的CREATOR变量，实际上就是将如何打包和解包的工作自己来定义，而序列化的这些操作完全由底层实现。

4. 高级功能

Serializable序列化 不保存静态变量 ，可以使用Transient关键字对部分字段不进行序列化，也可以覆盖writeObject、readObject方法以实现序列化过程自定义
