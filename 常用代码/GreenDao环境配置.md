As you can see MyDaoGenerator is recognized as Directory and not as Module like DaoExample. To change it we have to edit settings.gradle so it contains both ':DaoExample' and ':MyDaoGenerator' :

```
include ':DaoExample', ':DaoExample'
```

After doing it, please click "Sync Project with Gradle Files" button. Your directory should look like a module now in project tree.

#### connect GreenDao Generator to DaoExample (main module)

So, MyDaoGenerator is currently recognized as a module. Now we have to configure it, so we can get database files from it. The only necessary thing to be changed is outputDir parameter in build.gradle file of MyDaoGenerator module.

新建一个java工程，工程需要一个类包含main方法
在gradle中配置以下代码
```
apply plugin: 'application'
apply plugin: 'java'
mainClassName = "pl.surecase.eu.MyDaoGenerator"
// edit output direction
outputDir =
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile('de.greenrobot:DaoGenerator:1.3.0')
}
task createDocs {
    def docs = file(outputDir)
    docs.mkdirs()
}
run {
    args outputDir
}
```

Finaly our path looks like:

```
outputDir = "../DaoExample/src/main/java-gen"
```
写上输出到主目录的配置，这时候以application方式运行java项目 outputDir会从args里传入main程序

#### configure GreenDao Generator

```java
new DaoGenerator().generateAll(schema, args[0]);  
```

#### run GreenDao Generator

Pick Gradle bookmark from the right side of Android Studio interface. Choose MyDaoGenerator module and click twice on run task.

runTask
