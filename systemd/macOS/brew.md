# brew

## Official Website

https://brew.sh/index_zh-cn

## Measure

```bash
xcode-select --install
/bin/zsh -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"
# 选择合适的下载源，再选择 Y ，之后输入Password进行验证
# 重启终端 或者 运行 source /Users/shizc/.zprofile ，否则可能无法使用
```





## Application

### neofetch

快速获取系统信息

```bash
brew install neofetch
```





### NEOVIM

```bash
pip3 install pynvim
brew install msgpack tree-sitter neovim


nvim --version
NVIM v0.6.1
Build type: Release
LuaJIT 2.1.0-beta3
Compiled by brew@HMBRW-A-001-M1-004.local

Features: +acl +iconv +tui
See ":help feature-compile"

   system vimrc file: "$VIM/sysinit.vim"
  fall-back for $VIM: "/opt/homebrew/Cellar/neovim/0.6.1/share/nvim"
  
cd /opt/homebrew/Cellar/neovim/0.6.1/share/nvim/
touch sysinit.vim
```





### NTFS-3G-MAC

```bash
brew tap gromgit/homebrew-fuse
brew install --cask macfuse
brew install ntfs-3g-mac

# 两者选其一
brew install --cask osxfuse
brew install ntfs-3g


diskutil list
/dev/disk0 (internal):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:      GUID_partition_scheme                         500.3 GB   disk0
   1:             Apple_APFS_ISC ⁨⁩                        524.3 MB   disk0s1
   2:                 Apple_APFS ⁨Container disk3⁩         494.4 GB   disk0s2
   3:        Apple_APFS_Recovery ⁨⁩                        5.4 GB     disk0s3

/dev/disk3 (synthesized):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:      APFS Container Scheme -                      +494.4 GB   disk3
                                 Physical Store disk0s2
   1:                APFS Volume ⁨Macintosh HD⁩            19.9 GB    disk3s1
   2:              APFS Snapshot ⁨com.apple.os.update-...⁩ 19.9 GB    disk3s1s1
   3:                APFS Volume ⁨Preboot⁩                 405.8 MB   disk3s2
   4:                APFS Volume ⁨Recovery⁩                2.0 GB     disk3s3
   5:                APFS Volume ⁨Data⁩                    155.5 GB   disk3s5
   6:                APFS Volume ⁨VM⁩                      4.3 GB     disk3s6

/dev/disk4 (external, physical):
   #:                       TYPE NAME                    SIZE       IDENTIFIER
   0:     FDisk_partition_scheme                        *512.1 GB   disk4
   1:               Windows_NTFS ⁨DAOCHI⁩                  512.1 GB   disk4s1

vi /etc/fstab
LABEL=disk4s1 none ntfs rw,auto,nobrowse

sudo ln -s /Volumes ~/Desktop/Volumes



```



### QEMU

```bash
xcode-select --install
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
brew install ninja pkgconfig glib pixman
# python@3.9
brew install gdbm mpdecimal readline sqlite xz
brew install --build-from-source python@3.9

git clone --depth 1 https://git.qemu.org/git/qemu.git
cd qemu
mkdir build
cd build
../configure --target-list=aarch64-softmmu --enable-cocoa
make -j

brew install libslirp libssh ncurses vde
brew search qemu
brew install qemu


# Options
brew install --cask gcc-arm-embedded
brew install sphinx sphinx-doc
echo 'export PATH="/opt/homebrew/opt/sphinx-doc/bin:$PATH"' >> ~/.zshrc
```



### KVM

```bash
# python@3.9
brew install gdbm mpdecimal readline sqlite xz
brew install --build-from-source python@3.9


# libvirt
brew install libffi pcre glib gnu-sed gmp bdw-gc m4 libtool guile libtasn1 nettle p11-kit libevent c-ares jemalloc libev nghttp2 unbound gnutls grep libgpg-error libgcrypt cunit libiscsi libssh2 yajl

# To have launchd start libvirt now and restart at login:
brew services start libvirt
#Or, if you don't want/need a background service you can just run:
libvirtd


# libdaemon
brew install libdaemon


# virt-manager
brew install intltool libpng freetype fontconfig libpthread-stubs xorgproto libxau libxdmcp libxcb libx11 
libxext libxrender lzo pixman cairo jpeg libtiff gdk-pixbuf fribidi gobject-introspection graphite2 icu4c harfbuzz pango librsvg adwaita-icon-theme atk gsettings-desktop-schemas hicolor-icon-theme libepoxy gtk+3 gtk-vnc gtksourceview4 glib-networking libpsl webp gd jasper netpbm gts graphviz vala libsoup osinfo-db usb.ids libosinfo libvirt-glib libxml2 py3cairo pygobject3 aom dav1d frei0r lame libass libbluray libsoxr  libvidstab libogg libvorbis libvpx opencore-amr little-cms2 openjpeg opus rav1e flac libsndfile libsamplerate rubberband sdl2 snappy speex srt giflib leptonica tesseract theora x264 x265 xvid libsodium zeromq zimg ffmpeg graphene gstreamer orc gst-plugins-base gst-libav faac faad2 libmms libnice libusrsctp libcuefile libreplaygain musepack rtmpdump srtp gst-plugins-bad libshout taglib gst-plugins-good gst-plugins-ugly json-glib libusb lz4 spice-protocol usbredir spice-gtk vte3
python3.9 -m pip install --upgrade pip
# run it
brew tap jeffreywildman/homebrew-virt-manager
brew install virt-manager
# or run it
brew untap jeffreywildman/homebrew-virt-manager
brew tap Krish-sysadmin/homebrew-virt-manager
brew install virt-manager virt-viewer

These open issues may also help:
virt-manager installation error https://github.com/jeffreywildman/homebrew-virt-manager/issues/184
virt-manager broken on Big Sur after minor update https://github.com/jeffreywildman/homebrew-virt-manager/issues/170
virt-manager: command not found https://github.com/jeffreywildman/homebrew-virt-manager/issues/177
bump virt-manager/view-viewer and deps https://github.com/jeffreywildman/homebrew-virt-manager/pull/166
virt-manager crashed when opening a VM on remote host https://github.com/jeffreywildman/homebrew-virt-manager/issues/180
virt-manager: command not found https://github.com/jeffreywildman/homebrew-virt-manager/issues/169
Error installing virt-manager https://github.com/jeffreywildman/homebrew-virt-manager/issues/145
Virt-manager wont build. Update to libvirt-python https://github.com/jeffreywildman/homebrew-virt-manager/pull/171
Assertion Error When Running Virt-Manager on MacOS BigSur https://github.com/jeffreywildman/homebrew-virt-manager/issues/174
cannot install virt-manager, error with python version despite having python 3.9 installed https://github.com/jeffreywildman/homebrew-virt-manager/issues/172
osx virt-manager console screen is black https://github.com/jeffreywildman/homebrew-virt-manager/issues/113
virt-manager crashing with Python quit unexpectedly while using the libgdk-3.0.dylib https://github.com/jeffreywildman/homebrew-virt-manager/issues/62


brew install gnu-getopt
brew install virt-viewer

```



### MAVEN

```bash
shizc@MacBook-Pro ~ % brew install maven

# 查看版本
shizc@MacBook-Pro ~ % mvn -v
Apache Maven 3.8.3 (ff8e977a158738155dc465c6a97ffaf31982d739)
Maven home: `/opt/homebrew/Cellar/maven/3.8.3/libexec`
Java version: 17, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/17/libexec/openjdk.jdk/Contents/Home
Default locale: zh_CN_#Hans, platform encoding: UTF-8
OS name: "mac os x", version: "12.0.1", arch: "aarch64", family: "mac"

# 修改配置文件 settings.xml
# /opt/homebrew/Cellar/maven/3.8.3/libexec/conf/settings.xml
vi settings.xml
# /<mirrors>
<mirror>
    <id>aliyunmaven</id>
    <mirrorOf>*</mirrorOf>
    <name>阿里云公共仓库</name>
    <url>https://maven.aliyun.com/repository/public</url>
</mirror>



```



## About

### Term

- Formulae：软件包，包括了这个软件的依赖、源码位置及编译方法等；
- Casks：已经编译好的应用包，如图形界面程序等。

### Folder

- bin：用于存放所安装程序的启动链接（相当于快捷方式）
- etc：brew安装程序的配置文件默认存放路径
- Library：Homebrew 系统自身文件夹
- Cellar：通过brew安装的程序将以 [程序名/版本号] 存放于本目录下

##  Command

1. 本地软件库列表

```bash
brew ls
brew list
```

2. 查看软件库版本

```bash
brew list --versions
```

3. 查找软件

```bash
brew search xxx
```

4. 查看brew版本

```bash
brew -v 
```

5. 更新brew版本

```bash
brew update
```

6. 命令行软件包

```bash
# 安装
brew install xxx

# 更新
brew upgrade

# 卸载
brew uninstall xxx
```

7. cask软件

```bash
# 安装
brew install --cask xxx
# 卸载
brew cask uninstall xxx
```

8. 查找软件安装位置

```bash
which xxx
```

9. 查看帮助信息

```bash
brew --help
```

10. 常用软件

```bash
brew install telnet
brew install git
brew install wget
```

## Reference

 ```bash
 https://zhuanlan.zhihu.com/p/111014448
 https://docs.brew.sh
 ```

