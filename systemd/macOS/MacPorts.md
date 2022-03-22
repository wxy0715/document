

# MacPorts

## 官网

```bash
https://www.macports.org/
https://guide.macports.org/
https://trac.macports.org/wiki/Mirrors
```



```bash
sudo xcodebuild -license
# 输入agree

```





下载

```bash
https://github.com/macports/macports-base/releases/
```



```bash
# 提权
sudo -i

# 查看版本
port version

# 自更新
port selfupdate

# upgrade
port upgrade outdated


port select --set python python39
```



更换源

```bash
# Method.01
vi /opt/local/etc/macports/archive_sites.conf
#
name macports_archives
name local_archives
urls http://mirrors.ustc.edu.cn/macports/ https://pek.cn.packages.macports.org/macports/packages/
#


# Method.02
cd /opt/local/etc/macports
vi sources.conf
# 清华大学源
rsync://mirrors.tuna.tsinghua.edu.cn/macports/release/ports/ [default]

vi macports.conf
rsync_server            mirrors.tuna.tsinghua.edu.cn
rsync_dir               macports/release/base/

port -v sync
port -v selfupdate
```



## tools

### usbutils

```bash
# install
port -y install usbutils

```



### neovim

```bash
sudo -i
port install lua msgpack tree-sitter luajit luv-luajit lua51-*
port install neovim
```





### error

```bash
libalkimia
libtermkey
libvterm
unibilium
```

