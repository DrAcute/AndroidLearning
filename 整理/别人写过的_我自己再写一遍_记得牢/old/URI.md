
## URI

> Uniform Resource Identifier
> There are two types of URIs: URLs and URNs.
> See RFC 1630: Universal Resource Identifiers in WWW: A Unifying Syntax for the Expression of Names and Addresses of Objects on the Network as used in the WWW.


URI翻译为统一资源标识，它是以某种标准化的方式标识资源的字符串。这种字符串以scheme开头，语法如下：

```
[scheme:] scheme-specific-part
```

URI以scheme和冒号开头。冒号把scheme与scheme-specific-part分开，并且scheme-specific-part的语法由URI的scheme决定。例如*http://www.cnn.com* ，其中http是scheme，//www.cnn.com 是 scheme-specific-part。

URI分为绝对（absolute）或相对（relative）两类。绝对URI指以scheme（后面跟着冒号）开头的URI。前面提到的*http://www.cnn.com* 就是绝对的URI的一个例子，其它的例子还有 *mailto:jeff@javajeff.com*、*news:comp.lang.java.help* 和 *xyz://whatever*。可以把绝对URI看作是以某种方式引用某种资源，而对环境没有依赖。如果使用文件系统作类比，绝对URI类似于从根目录开始的某个文件的路径。相对URI不以scheme开始，一个例子是 *articles/articles.html* 。可以把相对URI看作是以某种方式引用某种资源，而这种方式依赖于标识符出现的环境。如果用文件系统作类比，相对URI类似于从当前目录开始的文件路径。

URI可以进一步分为不透明的（opaque）和分层（hierarchical）的两类。不透明的URI指scheme-specific-part不是以‘/’开头的绝对的URI。其例子有news:comp.lang.java和前面的mailto:jeff@javajeff.com。不透明的URI不能做进一步的解析，不需要验证scheme-specific-part的有效性。与它不同的是，分层的URI是以‘/’开头的绝对的URI或相对的URL。分层的URI的scheme-specific-part必须被分解为几个组成部分。分层的URI的scheme-specific-part必须符合下面的语法：

```
[//authority] [path] [?query] [#fragment]
```

可选的授权机构（authority）标识了该URI名字空间的命名机构。如果有这一部分则以‘//’开始。它可以是基于服务器或基于授权机构的。基于授权机构有特定的语法（本文没有讨论，因为很少使用它），而基于服务器的语法如下：

```
[userinfo@] host [:port]
```

基于服务器的authority以用户信息（例如用户名）开始，后面跟着一个@符号，紧接着是主机的名称，以及冒号和端口号。例如jeff@x.com:90就是一个基于服务器的authority，其中jeff为用户信息，x.com为主机，90为端口。

可选的path根据authority（如果提供了）或schema（如果没有authority）定义资源的位置。路径（path）可以分成一系列的路径片断（path segment），每个路径片断使用‘/’与其它片断隔开。如果第一个路径片断以‘/’开始，该路径就被认为是绝对的，否则路径就被认为是相对的。例如，/a/b/c由三个路径片断a、b和c组成，此外这个路径是绝对的，因为第一个路径片断（a）的前缀是‘/’。

可选的query定义要传递给资源的查询信息。资源使用该信息获取或生成其它的的数据传递回调用者。例如，http://www.somesite.net/a?x=y, x=y就是一个query，在这个查询中x是某种实体的名称，y是该实体的值。

最后一个部分是fragment。当使用URI进行某种检索操作时，后面执行操作的软件使用fragment聚焦于软件感兴趣的资源部分。

分析一个例子ftp://george@x.com:90/public/notes?text=shakespeare#hamlet

上面的URI把ftp识别为schema，把george@x.com:90识别为基于服务器的authority（其中george是用户信息，x.com是主机，90是端口），把/public/notes识别为路径，把text=shakespeare识别为查询，把hamlet识别为片断。本质上它是一个叫做george的用户希望通过/public/notes路径在服务器x.com的90端口上检索shakespeare文本的hamlet信息。

#### URI的标准化（normalize）

标准化可以通过目录术语来理解。假定目录x直接位于根目录之下，x有子目录a和b，b有文件memo.txt，a是当前目录。为了显示memo.txt中的内容，你可能输入type \x\.\b\memo.txt。你也可能输入type \x\a\..\b\memo.txt，在这种情况下，a和..的出现是没有必要的。这两种形式都不是最简单的。但是如果输入\x\b\memo.txt，你就指定了最简单的路径了，从根目录开始访问memo.txt。最简单的\x\b\memo.txt路径就是标准化的路径。

通常通过base + relative URI访问资源。Base URI是绝对URI，而Relative URI标识了与Base URI相对的资源。因此有必要把两种URI通过解析过程合并，相反地从合并的URI中提取Relative URI也是可行的。

假定把x://a/作为Base URI，并把b/c作为Relative URI。Resolve这个相对URI将产生x://a/b/c。根据x://a/相对化（Relative）x://a/b/c将产生b/c。

URI不能读取/写入资源，这是统一的资源定位器（URL）的任务。URL是一种URI，它的schema是已知的网络协议，并且它把URI与某种协议处理程序联系起来（一种与资源通讯的读/写机制）。

URI一般不能为资源提供持久不变的名称。这是统一的资源命名（URN）的任务。URN也是一种URI，但是全球唯一的、持久不便的，即使资源不再存在或不再使用。

#### 使用URI

Java API通过提供URI类（位于java.net包中），使我们在代码中使用URI成为可能。URI的构造函数建立URI对象，并且分析URI字符串，提取URI组件。URI的方法提供了如下功能：1）决定URI对象的URI是绝对的还是相对的；2）决定URI对象是opaque还是hierarchical；3）比较两个URI对象；4）标准化（normalize）URI对象；5）根据Base URI解析某个Relative URI；6）根据Base URI计算某个URI的相对URI；7）把URI对象转换为URL对象。
在URI里面有多个构造函数，最简单的是URI(String uri)。这个构造函数把String类型的参数URI分解为组件，并把这些组件存储在新的URI对象中。如果String对象的URI违反了RFC 2396的语法规则，将会产生一个java.net.URISyntaxException。

下面的代码演示了使用URI(String uri)建立URI对象：
```java
URI uri = new URI ("http://www.cnn.com");
```

如果知道URI是有效的，不会产生URISyntaxException，可以使用静态的create(String uri)方法。这个方法分解uri，如果没有违反语法规则就建立URI对象，否则将捕捉到一个内部URISyntaxException，并把该对象包装在一个IllegalArgumentException中抛出。
下面的代码片断演示了create(String uri)：
```java
URI uri = URI.create ("http://www.cnn.com");
```

URI构造函数和create(String uri)方法试图分解出URI的authority的用户信息、主机和端口部分。对于正确形式的字符串会成功，对于错误形式的字符串，他们将会失败。如果想确认某个URI的authority是基于服务器的，并且能分解出用户信息、主机和端口，这时候可以调用URI的parseServerAuthority()方法。如果成功分解出URI，该方法将返回包含用户信息、主机和端口部分的新URI对象，否则该方法将产生一个URISyntaxException。

下面的代码片断演示了parseServerAuthority()：
```java
// 下面的parseServerAuthority()调用出现后会发生什么情况？
URI uri = new URI ("//foo:bar").parseServerAuthority();
```

一旦拥有了URI对象，你就可以通过调用getAuthority()、getFragment()、getHost()、getPath()、getPort()、getQuery()、getScheme()、getSchemeSpecificPart()和 getUserInfo()方法提取信息。以及isAbsolute()、isOpaque()等方法。

- 程序1: URIDemo1.java

```java
import java.net.*;

public class URIDemo1 {
 public static void main (String [] args) throws Exception {
    if (args.length != 1) {
      System.err.println ("usage: java URIDemo1 uri");
      return;
    }
    URI uri = new URI (args [0]);

    System.out.println ("Authority = " +uri.getAuthority ());
    System.out.println ("Fragment = " +uri.getFragment ());
    System.out.println ("Host = " +uri.getHost ());
    System.out.println ("Path = " +uri.getPath ());
    System.out.println ("Port = " +uri.getPort ());
    System.out.println ("Query = " +uri.getQuery ());
    System.out.println ("Scheme = " +uri.getScheme ());
    System.out.println ("Scheme-specific part = " + uri.getSchemeSpecificPart ());
    System.out.println ("User Info = " +uri.getUserInfo ());
    System.out.println ("URI is absolute: " +uri.isAbsolute ());
    System.out.println ("URI is opaque: " +uri.isOpaque ());
 }
}
```

输入java URIDemo1命令后，输出结果如下：

```
query://jeff@books.com:9000/public/manuals/appliances?stove#ge
Authority = jeff@books.com:9000
Fragment = ge
Host = books.com
Path = /public/manuals/appliances
Port = 9000
Query = stove
Scheme = query
Scheme-specific part = //jeff@books.com:9000/public/manuals/appliances?stove
User Info = jeff
URI is absolute: true
URI is opaque: false
```

URI类支持基本的操作，包括标准化（normalize）、分解（resolution）和相对化（relativize）。下例演示了normalize()方法。

- 程序2: URIDemo2.java

```java
import java.net.*;

class URIDemo2 {
 public static void main (String [] args) throws Exception {
    if (args.length != 1) {
      System.err.println ("usage: java URIDemo2 uri");
      return;
    }
    URI uri = new URI (args [0]);
    System.out.println ("Normalized URI = " + uri.normalize());
 }
}
```

在命令行输入java URIDemo2 x/y/../z/./q，将看到下面的输出：
Normalized URI = x/z/q

上面的输出显示y、..和.消失了。

URI通过提供resolve(String uri)、resolve(URI uri)和relativize(URI uri)方法支持反向解析和相对化操作。如果指定的URI违反了RFC 2396语法规则，resolve(String uri)通过的内部的create(String uri)调用间接地产生一个IllegalArgumentException。下面的代码演示了resolve(String uri)和relativize(URI uri)。

- 程序3: URIDemo3.java

```java
import java.net.*;

class URIDemo3 {
 public static void main (String [] args) throws Exception {
    if (args.length != 2) {
      System.err.println ("usage: " + "java URIDemo3 uriBase uriRelative");
      return;
    }

    URI uriBase = new URI (args [0]);
    System.out.println ("Base URI = " +uriBase);

    URI uriRelative = new URI (args [1]);
    System.out.println ("Relative URI = " +uriRelative);

    URI uriResolved = uriBase.resolve (uriRelative);
    System.out.println ("Resolved URI = " +uriResolved);

    URI uriRelativized = uriBase.relativize (uriResolved);
    System.out.println ("Relativized URI = " +uriRelativized);
 }
}
```

编译URIDemo3后，在命令行输入java URIDemo3 http://www.somedomain.com/ x/../y，输出如下：

```
Base URI = http://www.somedomain.com/
Relative URI = x/../y
Resolved URI = http://www.somedomain.com/y
Relativized URI = y
```
