### 配置JRE和JDK

#### 下载安装JRE和JDK

```linux
sudo apt-get update
sudo apt-get install default-jre
sudo apt-get install default-jdk
```

#### 配置环境变量

通过这个命令可以看到java安装路径：
```
sudo update-alternatives --config java
```

打开环境变量配置文件
```
sudo gedit /etc/environment
```

在文件中添加
```
JAVA_HOME="/usr/lib/jvm/java-7-openjdk-amd64"
```

立即使环境变量生效
```
source /etc/environment
```

测试
```
echo $JAVA_HOME
java -version
```

### 配置 Android SDK

#### 下载Android SDK

Android SDK中的adb程序是32位的，Ubuntu x64系统需要安装32位库文件，用于兼容32位的程序。如果不安装，adb会出错：java.io.IOException: error=2

```
sudo apt-get install -y libc6-i386 lib32stdc++6 lib32gcc1 lib32ncurses5 lib32z1
```

官方下载页面，选择“USE AN EXISTING IDE”，下载不含IDE的纯SDK：官网 http://developer.android.com/sdk/index.html ，国内镜像 http://gmirror.org/#android-sdk-tools-only

```
cd ~/Android/
sudo wget http://dl.gmirror.org/android/android-sdk_r24.3.4-linux.tgz
sudo tar -zxvf android-sdk_r24.3.4-linux.tgz
```

#### 配置Android SDK 环境变量

打开环境变量配置文件
```
sudo gedit $HOME/.bashrc
```

在文件中添加
```
export ANDROID_SDK="/Android/android-sdk-linux"
export PATH="$PATH:$ANDROID_SDK/tools:$ANDROID_SDK/platform-tools"
```

使用bash打开android sdk manager

```
cd /Android/android-sdk-linux
sudo bash android
```

###　安装NDK

```
sudo apt-get install p7zip-full
7z x android-ndk-r10e-linux-x86_64.bin
```

在.bashrc 加入
```
export ANDROID_NDK="/Android/android-ndk-r10e"
```
