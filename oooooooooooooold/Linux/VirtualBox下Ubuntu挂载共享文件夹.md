#### 安装支持

```linux
sudo apt-get install build-essential
sudo apt-get install virtualbox-guest-dkms
```

#### 在VirtualBox配置共享文件夹

### 挂载共享文件夹

```linux
sudo mkdir /mnt/shared
sudo mount -t vboxsf Ubuntu_Share /mnt/shared
```

其中"Ubuntu_Share"是之前创建的共享文件夹的名字。OK，现在Ubuntu和主机可以互传文件了。

要想自动挂载的话，可以在/etc/fstab中添加一项

```linux
share /mnt/shared vboxsf rw,gid=100,uid=1000,auto 0 0
```

卸载的话使用下面的命令：

```linux
sudo umount -f /mnt/shared
```

**注意：**
**共享文件夹的名称千万不要和挂载点的名称相同。比如，上面的挂载点是/mnt/shared，如果共享文件夹的名字也是shared的话，在挂载的时候就会出现如下的错误信息：/sbin/mount.vboxsf: mounting failed with the error: Protocol error**
