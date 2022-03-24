# Docker入门

## 安装

### 先获取docker加速

```shell
执行以下命令，打开 /etc/docker/daemon.json 配置文件。
vim /etc/docker/daemon.json
按 i 切换至编辑模式，添加以下内容，并保存。
{
"registry-mirrors": [
 "https://mirror.ccs.tencentyun.com"
]
}
```

### 清除旧的安装新的

```sh
# 清除旧的
sudo yum remove docker \
                  docker-client \
                  docker-client-latest \
                  docker-common \
                  docker-latest \
                  docker-latest-logrotate \
                  docker-logrotate \
                  docker-engine
# 下载yum工具             
sudo yum install -y yum-utils
# 配置仓库
sudo yum-config-manager \
   --add-repo \
   https://download.docker.com/linux/centos/docker-ce.repo
# 安装
sudo yum install docker-ce docker-ce-cli containerd.io
# 或者要安装特定版本的 Docker Engine,
yum list docker-ce --showduplicates | sort -r //制定版本安装输入
sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io //制定版本安装输入
# 启动
sudo systemctl start docker
# 测试
sudo docker run hello-world
```



## 镜像命令

```shell
docker --version
# 查看基本信息
docker info
# 查看镜像
docker images 
REPOSITORY    TAG       IMAGE ID       CREATED        SIZE
hello-world   latest    d1165f221234   5 months ago   13.3kB
# 查看镜像详情
docker inspect d1165f221234(IMAGE ID)
{
        "Id": "sha256:d1165f2212346b2bab48cb01c1e39ee8ad1be46b87873d9ca7a4e434980a7726",
        "RepoTags": [
            "hello-world:latest"
        ],
        "RepoDigests": [
            "hello-world@sha256:0fe98d7debd9049c50b597ef1f85b7c1e8cc81f59c8d623fcb2250e8bec85b38"
        ],
        "Parent": "",
        "Comment": "",
        "Created": "2021-03-05T23:25:25.230064203Z",
        "Container": "f5a78ef54769bb8490754e9e063a89f90cc8eee6a6c5a0a72655826e99df116e",
        "ContainerConfig": {
            "Hostname": "f5a78ef54769",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
            ],
            "Cmd": [
                "/bin/sh",
                "-c",
                "#(nop) ",
                "CMD [\"/hello\"]"
            ],
            "Image": "sha256:77fe0a37fa6ce641a004815f2761a9042618557d253f312cd3da61780e372c8f",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {}
        },
        "DockerVersion": "19.03.12",
        "Author": "",
        "Config": {
            "Hostname": "",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
            ],
            "Cmd": [
                "/hello"
            ],
            "Image": "sha256:77fe0a37fa6ce641a004815f2761a9042618557d253f312cd3da61780e372c8f",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": null
        },
        "Architecture": "amd64",
        "Os": "linux",
        "Size": 13336,
        "VirtualSize": 13336,
        "GraphDriver": {
            "Data": {
                "MergedDir": "/var/lib/docker/overlay2/ffcd0575234ce731cf4e71b7cfb11c7084b2299adb1c266cab888026c409d460/merged",
                "UpperDir": "/var/lib/docker/overlay2/ffcd0575234ce731cf4e71b7cfb11c7084b2299adb1c266cab888026c409d460/diff",
                "WorkDir": "/var/lib/docker/overlay2/ffcd0575234ce731cf4e71b7cfb11c7084b2299adb1c266cab888026c409d460/work"
            },
            "Name": "overlay2"
        },
        "RootFS": {
            "Type": "layers",
            "Layers": [
                "sha256:f22b99068db93900abe17f7f5e09ec775c2826ecfe9db961fea68293744144bd"
            ]
        },
        "Metadata": {
            "LastTagTime": "0001-01-01T00:00:00Z"
        }
    }

```

### docker search 搜索镜像

| 名称，简写        | 默认 | 描述                         |
| ----------------- | ---- | ---------------------------- |
| `--filter` , `-f` |      | 根据提供的条件过滤输出       |
| `--format`        |      | 使用 Go 模板进行漂亮打印搜索 |
| `--limit`         | `25` | 最大搜索结果数               |
| `--no-trunc`      |      | 不要截断输出                 |

| `.Name`        | 图像名称                     |
| -------------- | ---------------------------- |
| `.Description` | 图片说明                     |
| `.StarCount`   | 图像的星数                   |
| `.IsOfficial`  | 如果图片是官方的，则“OK”     |
| `.IsAutomated` | 如果图像构建是自动的，则“OK” |

```shell
# 点赞数超过3的 不截断描述
docker search --filter=stars=3 --no-trunc busybox
# 点赞数超过3的
docker search --filter stars=3 busybox
# 自docker search --filter stars=3 busybox动化的
docker search --filter is-automated=true busybox
# 官方的
docker search --filter is-official=true --filter stars=3 busybox
# 名称:点赞数
docker search --format "{{.Name}}: {{.StarCount}}" nginx
# 表格格式
docker search --format "table {{.Name}}\t{{.IsAutomated}}\t{{.IsOfficial}}" nginx
```

### docker history 镜像id

### docker pull 拉取镜像

| 名称，简写                | 默认   | 描述                                                         |
| ------------------------- | ------ | ------------------------------------------------------------ |
| `--all-tags` , `-a`       |        | 下载存储库中的所有标记图像                                   |
| `--disable-content-trust` | `true` | 跳过图像验证                                                 |
| `--platform`              |        | [**API 1.32+**](https://docs.docker.com/engine/api/v1.32/) 如果服务器支持多平台，则设置平台 |
| `--quiet` , `-q`          |        | 抑制详细输出                                                 |

```shell
# 分层拉取最新的版本
[root@VM-12-12-centos docker]# docker pull mysql /docker pull  docker.io/library/mysql:latest
Using default tag: latest
latest: Pulling from library/mysql
e1acddbe380c: Pull complete 
bed879327370: Pull complete 
03285f80bafd: Pull complete 
ccc17412a00a: Pull complete 
1f556ecc09d1: Pull complete 
adc5528e468d: Pull complete 
Digest: sha256:d45561a65aba6edac77be36e0a53f0c1fba67b951cb728348522b671ad63f926
Status: Downloaded newer image for mysql:latest
docker.io/library/mysql:latest //这个是具体的地址

```

### docker rmi 删除镜像

```shell
 docker rmi (IMAGE ID)
```

### docker images 查看镜像

```shell
https://docs.docker.com/engine/reference/commandline/images/
docker images
```



## 容器命令

### docker stats 查看资源状态

### docker run

#### docker run -d centos 坑

```shell
`docker ps 发现centos停止了`
`docker 容器后台运行,必须要有前台进程,docker发现没有前台应用会自动停止`
`nignx 容器启动后发现自己没有提供服务,就会立刻停止,就是没有程序了`
```



| 名称，简写                | 默认      | 描述                                                         |
| ------------------------- | --------- | ------------------------------------------------------------ |
| `--add-host`              |           | 添加自定义主机到 IP 映射 (host:ip)                           |
| `--attach` , `-a`         |           | 附加到 STDIN、STDOUT 或 STDERR                               |
| `--blkio-weight`          |           | Block IO（相对权重），在 10 到 1000 之间，或 0 表示禁用（默认为 0） |
| `--blkio-weight-device`   |           | 块IO权重（相对设备权重）                                     |
| `--cap-add`               |           | 添加 Linux 功能                                              |
| `--cap-drop`              |           | 删除 Linux 功能                                              |
| `--cgroup-parent`         |           | 容器的可选父 cgroup                                          |
| `--cgroupns`              |           | [**API 1.41+**](https://docs.docker.com/engine/api/v1.41/) 要使用的 Cgroup 命名空间 (host\|private) 'host'：在 Docker 主机的 cgroup 命名空间中运行容器 'private'：在其自己的私有 cgroup 命名空间中运行容器 ''：使用由 default-cgroupns-配置的 cgroup 命名空间守护进程的模式选项（默认） |
| `--cidfile`               |           | 将容器 ID 写入文件                                           |
| `--cpu-count`             |           | CPU 计数（仅限 Windows）                                     |
| `--cpu-percent`           |           | CPU 百分比（仅限 Windows）                                   |
| `--cpu-period`            |           | 限制 CPU CFS（完全公平调度程序）周期                         |
| `--cpu-quota`             |           | 限制 CPU CFS（完全公平调度程序）配额                         |
| `--cpu-rt-period`         |           | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) 以微秒为单位限制 CPU 实时周期 |
| `--cpu-rt-runtime`        |           | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) 以微秒为单位限制 CPU 实时运行时间 |
| `--cpu-shares` , `-c`     |           | CPU份额（相对权重）                                          |
| `--cpus`                  |           | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) CPU数量 |
| `--cpuset-cpus`           |           | 允许执行的 CPU (0-3, 0,1)                                    |
| `--cpuset-mems`           |           | 允许执行的 MEMs (0-3, 0,1)                                   |
| `--detach` , `-d`         |           | 在后台运行容器并打印容器 ID                                  |
| `--detach-keys`           |           | 覆盖用于分离容器的键序列                                     |
| `--device`                |           | 将主机设备添加到容器                                         |
| `--device-cgroup-rule`    |           | 向 cgroup 允许的设备列表添加规则                             |
| `--device-read-bps`       |           | 限制设备的读取速率（每秒字节数）                             |
| `--device-read-iops`      |           | 限制设备的读取速率（每秒 IO）                                |
| `--device-write-bps`      |           | 限制设备的写入速率（每秒字节数）                             |
| `--device-write-iops`     |           | 限制设备的写入速率（每秒 IO）                                |
| `--disable-content-trust` | `true`    | 跳过图像验证                                                 |
| `--dns`                   |           | 设置自定义 DNS 服务器                                        |
| `--dns-opt`               |           | 设置 DNS 选项                                                |
| `--dns-option`            |           | 设置 DNS 选项                                                |
| `--dns-search`            |           | 设置自定义 DNS 搜索域                                        |
| `--domainname`            |           | 容器NIS域名                                                  |
| `--entrypoint`            |           | 覆盖图像的默认 ENTRYPOINT                                    |
| `--env` , `-e`            |           | 设置环境变量                                                 |
| `--env-file`              |           | 读入环境变量文件                                             |
| `--expose`                |           | 公开一个端口或一系列端口                                     |
| `--gpus`                  |           | [**API 1.40+**](https://docs.docker.com/engine/api/v1.40/) 要添加到容器中的 GPU 设备（'all' 以传递所有 GPU） |
| `--group-add`             |           | 添加要加入的其他组                                           |
| `--health-cmd`            |           | 运行以检查健康状况的命令                                     |
| `--health-interval`       |           | 运行检查之间的时间（ms\|s\|m\|h）（默认为 0s）               |
| `--health-retries`        |           | 报告不健康需要连续失败                                       |
| `--health-start-period`   |           | [**API 1.29+**](https://docs.docker.com/engine/api/v1.29/) 在开始健康重试倒计时（ms\|s\|m\|h）之前容器初始化的开始时间（默认为 0s） |
| `--health-timeout`        |           | 允许运行一项检查的最长时间（ms\|s\|m\|h）（默认为 0s）       |
| `--help`                  |           | 打印使用                                                     |
| `--hostname` , `-h`       |           | 容器主机名                                                   |
| `--init`                  |           | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) 在容器内运行一个 init 来转发信号和收获进程 |
| `--interactive` , `-i`    |           | 即使未连接，也要保持 STDIN 打开                              |
| `--io-maxbandwidth`       |           | 系统驱动器的最大 IO 带宽限制（仅限 Windows）                 |
| `--io-maxiops`            |           | 系统驱动器的最大 IOps 限制（仅限 Windows）                   |
| `--ip`                    |           | IPv4 地址（例如 172.30.100.104）                             |
| `--ip6`                   |           | IPv6 地址（例如，2001:db8::33）                              |
| `--ipc`                   |           | 使用IPC模式                                                  |
| `--isolation`             |           | 容器隔离技术                                                 |
| `--kernel-memory`         |           | 内核内存限制                                                 |
| `--label` , `-l`          |           | 在容器上设置元数据                                           |
| `--label-file`            |           | 读入一行分隔的标签文件                                       |
| `--link`                  |           | 添加到另一个容器的链接                                       |
| `--link-local-ip`         |           | 容器 IPv4/IPv6 链路本地地址                                  |
| `--log-driver`            |           | 容器的日志驱动                                               |
| `--log-opt`               |           | 日志驱动程序选项                                             |
| `--mac-address`           |           | 容器 MAC 地址（例如，92:d0:c6:0a:29:33）                     |
| `--memory` , `-m`         |           | 内存限制                                                     |
| `--memory-reservation`    |           | 内存软限制                                                   |
| `--memory-swap`           |           | 交换限制等于内存加交换：“-1”启用无限交换                     |
| `--memory-swappiness`     | `-1`      | 调整容器内存交换（0 到 100）                                 |
| `--mount`                 |           | 将文件系统挂载附加到容器                                     |
| `--name`                  |           | 为容器指定名称                                               |
| `--net`                   |           | 将容器连接到网络                                             |
| `--net-alias`             |           | 为容器添加网络范围的别名                                     |
| `--network`               |           | 将容器连接到网络                                             |
| `--network-alias`         |           | 为容器添加网络范围的别名                                     |
| `--no-healthcheck`        |           | 禁用任何容器指定的 HEALTHCHECK                               |
| `--oom-kill-disable`      |           | 禁用 OOM 杀手                                                |
| `--oom-score-adj`         |           | 调整主机的 OOM 首选项（-1000 到 1000）                       |
| `--pid`                   |           | 要使用的 PID 命名空间                                        |
| `--pids-limit`            |           | 调整容器 pids 限制（设置 -1 表示无限制）                     |
| `--platform`              |           | [**API 1.32+**](https://docs.docker.com/engine/api/v1.32/) 如果服务器支持多平台，则设置平台 |
| `--privileged`            |           | 授予此容器的扩展权限                                         |
| `--publish` , `-p`        |           | 将容器的端口发布到主机                                       |
| `--publish-all` , `-P`    |           | 将所有暴露的端口发布到随机端口                               |
| `--pull`                  | `missing` | 运行前拉取镜像（“总是”\|“缺失”\|“从不”）                     |
| `--read-only`             |           | 将容器的根文件系统挂载为只读                                 |
| `--restart`               | `no`      | 容器退出时应用的重启策略                                     |
| `--rm`                    |           | 退出时自动移除容器                                           |
| `--runtime`               |           | 用于此容器的运行时                                           |
| `--security-opt`          |           | 安全选项                                                     |
| `--shm-size`              |           | /dev/shm 的大小                                              |
| `--sig-proxy`             | `true`    | 代理接收到进程的信号                                         |
| `--stop-signal`           | `SIGTERM` | 停止容器的信号                                               |
| `--stop-timeout`          |           | [**API 1.25+**](https://docs.docker.com/engine/api/v1.25/) 停止容器的超时时间（以秒为单位） |
| `--storage-opt`           |           | 容器的存储驱动程序选项                                       |
| `--sysctl`                |           | sysctl 选项                                                  |
| `--tmpfs`                 |           | 挂载一个 tmpfs 目录                                          |
| `--tty` , `-t`            |           | 分配一个伪 TTY                                               |
| `--ulimit`                |           | 超限选项                                                     |
| `--user` , `-u`           |           | 用户名或 UID（格式：<name\|uid>[:<group\|gid>]）             |
| `--userns`                |           | 要使用的用户命名空间                                         |
| `--uts`                   |           | 要使用的 UTS 命名空间                                        |
| `--volume` , `-v`         |           | 绑定挂载卷                                                   |
| `--volume-driver`         |           | 容器的可选卷驱动程序                                         |
| `--volumes-from`          |           | 从指定的容器挂载卷                                           |
| `--workdir` , `-w`        |           | 容器内的工作目录                                             |

```shell
docker pull centos
# 进入容器
docker -it --name centos /bin/bash
exit 退出
docker start 容器id
docker restart 容器id
docker stop 容器id # 停止当前正在运行的容器
docker kill 容器id # 强制停止当前的容器
```

### dcoker ps

```shell
只显示当前正再运行的
-a 显示所有
-aq 只显示容器ID
```

### coker rm 删除容器

```shell
docker rm -f $(docker ps -aq) 删除所有的
```

### 查看日志

-tf 显示全部日志

docker logs -tf 容器id

### 查看进程 docker top

```sh
[root@VM-12-12-centos ~]# docker top 2d6a854d98a9
UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
root                1201                1183                0                   11:05               pts/0               00:00:00            /bin/bash
```

### docker inspect 查看容器元数据

```shell
[root@VM-12-12-centos ~]# docker inspect 2d6a854d98a9
[
    {
        "Id": "2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09",
        "Created": "2021-08-19T03:05:13.513843732Z",
        "Path": "/bin/bash",
        "Args": [],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 1201,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2021-08-19T03:05:13.799270597Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:300e315adb2f96afe5f0b2780b87f28ae95231fe3bdd1e16b9ba606307728f55",
        "ResolvConfPath": "/var/lib/docker/containers/2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09/hostname",
        "HostsPath": "/var/lib/docker/containers/2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09/hosts",
        "LogPath": "/var/lib/docker/containers/2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09/2d6a854d98a9603709ffe6ceeca472ab69f50010ba881d9fa4d915d27c2cab09-json.log",
        "Name": "/upbeat_keller",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "default",
            "PortBindings": {},
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "CapAdd": null,
            "CapDrop": null,
            "CgroupnsMode": "host",
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "ConsoleSize": [
                0,
                0
            ],
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": null,
            "BlkioDeviceWriteBps": null,
            "BlkioDeviceReadIOps": null,
            "BlkioDeviceWriteIOps": null,
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "KernelMemory": 0,
            "KernelMemoryTCP": 0,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": null,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/dd1c8caf24e91d7e74db43bcf0c73ec3e4d12abb2e2eced1f7b4d80edd235792-init/diff:/var/lib/docker/overlay2/cd9778afa94019cf12b0e35b38eefc46a7828c31848fbf0db0b895ef43b4263a/diff",
                "MergedDir": "/var/lib/docker/overlay2/dd1c8caf24e91d7e74db43bcf0c73ec3e4d12abb2e2eced1f7b4d80edd235792/merged",
                "UpperDir": "/var/lib/docker/overlay2/dd1c8caf24e91d7e74db43bcf0c73ec3e4d12abb2e2eced1f7b4d80edd235792/diff",
                "WorkDir": "/var/lib/docker/overlay2/dd1c8caf24e91d7e74db43bcf0c73ec3e4d12abb2e2eced1f7b4d80edd235792/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [],
        "Config": {
            "Hostname": "2d6a854d98a9",
            "Domainname": "",
            "User": "",
            "AttachStdin": true,
            "AttachStdout": true,
            "AttachStderr": true,
            "Tty": true,
            "OpenStdin": true,
            "StdinOnce": true,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
            ],
            "Cmd": [
                "/bin/bash"
            ],
            "Image": "centos",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {
                "org.label-schema.build-date": "20201204",
                "org.label-schema.license": "GPLv2",
                "org.label-schema.name": "CentOS Base Image",
                "org.label-schema.schema-version": "1.0",
                "org.label-schema.vendor": "CentOS"
            }
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "11f1198d696f3d5fd55bbffe0184fc2e2b3eb72e0e316a76cb933dfb9cc0cb7d",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {},
            "SandboxKey": "/var/run/docker/netns/11f1198d696f",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "74d823bfef886f7af752f1ffa74b5fe14c5efcde42a8d58ea2551352ed45ab96",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "8f149921d4b3d771467e3a85a2490b7b87400c0aa73ebdd96dd23e680bfed0d9",
                    "EndpointID": "74d823bfef886f7af752f1ffa74b5fe14c5efcde42a8d58ea2551352ed45ab96",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]
```

###  进入容器

docker exec -it 容器id /bin/bash

````sh
docker exec -it 2d6a854d98a9 /bin/bash
````

docker attach -it 容器id /bin/bash

```shell
docker attach 容器id  
```

`docker exec 进入容器后开启一个新的终端,docker attach 进入正在执行的终端,不会开启新的进程`

### docker cp 容器内的拷贝出来

```
docker cp 容器id:路径 目标路径
```



## nginx安装

```shell
# 下载
docker pull nginx
# 查看镜像
docker images nginx 
# 后台运行和暴露端口
docker run --name nginx -d -p 5000:80 nginx
# 查看容器
docker ps
# 内部访问
curl localhost:5000
# 浏览器访问
ip:5000
# 进去容器
docker exec -it nginx /bin/bash
```

## tomcat安装

```shell
# 下载
docker run -it --rm tomcat:9.0 测试 用完了就删 不建议
docker pull tomcat:9.0
# 查看镜像
docker images tomcat
# 后台运行和暴露端口
docker run --name tomcat -d -p 5001:80 tomcat
# 查看容器
docker ps
# 内部访问
curl localhost:5001
# 浏览器访问
ip:5001
# 访问404 发现不是完整的webapps里没有东西,最小安装
# 进去容器
docker exec -it nginx /bin/bash
# webapps.dist下面的移动到webapps
```

## es+kibana部署

```shell
# 创建docker网络环境
docker network create somenetwork
# 安装后会很卡,因为elasticsearch非常占用资源
docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
# 加上jvm参数运行
docker run -d --name elasticsearch --net somenetwork -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" -e ES_JAVA_OPTS="-Xms64m -Xmx512m"  elasticsearch:7.6.2
[root@VM-12-12-centos ~]# curl localhost:9200
{
  "name" : "a45c6d5c7cd1",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "T8z5t-RvQeSY1UKjCpYmrg",
  "version" : {
    "number" : "7.6.2",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
    "build_date" : "2020-03-26T06:34:37.794943Z",
    "build_snapshot" : false,
    "lucene_version" : "8.4.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}

```

## 安装portainer可视化面板

```shell
docker volume create portainer_data
docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce
# 访问
http://81.69.172.101:9000/
# 设置用户名密码
wxy0715.. wxy0715..
```



## commit 镜像

```shell
docker commit -a "wxy0715" -m "update webapps" 2d371d4c60d3(容器ID)  tomcat0715:1.0
```



# Docker核心

## 容器数据卷

### 文件同步(双向的)

```shell
docker run -it -v /opt/wxy(外部):/opt/wxy(内部) centos /bin/bash  
# 查看元数据
docker inspect cd4c1a666583
"Mounts": [
    {
    "Type": "bind",
    "Source": "/opt/wxy",//外部地址
    "Destination": "/opt/wxy",//内部地址
    "Mode": "",
    "RW": true,
    "Propagation": "rprivate"
    }
]
```

 ### 安装mysql

```shell
MySQL的数据持久化问题!
# 搜索
docker search mariadb
# 下载
docker pull mariadb
# 运行mysql
docker run -d -p 5003:3306 -v /opt/mariadb/conf:/etc/mysql  -v /opt/mariadb/data:/var/lib/mysql  -v /opt/mariadb/logs:/var/log/mysql -e MYSQL_ROOT_PASSWORD=root --name mariadbFire mariadb
```

### 具名和匿名挂载

```shell
docker run -d  --name nginx01 -p 5003:80 -v  /etc/nginx  nginx
[root@VM-12-12-centos opt]# docker volume ls
DRIVER    VOLUME NAME
local     ab5edf37cc0549771eeb8d192273bd43db6c05a44fb148ae50780c0c7e580607
local     c9d43703821775af4d692fac762d246f52260553a3160bbc56e3e660f47ac414
local     portainer_data
# 以上都是匿名挂载, -v的时候没有写容器外的路径

# 具名挂载
docker run -d -p 5004:80 --name nginx01 -v juming-nginx:/etc/nginx:rw nginx
# 查看目录
docker inspect juming-nginx
```

### 多个容器内数据同步

```shell
docker run -it --name docker02(子) --volumes-from docker01(父) 镜像id
删除父容器,子容器数据还在
```



## DockerFile

`CMD`:可以被替换 docker run -it 容器id  [命令,该命令会替换CMD里面的]

`ENTRYPOINT`:追加命令 docker run -it 容器id  [命令,该命令会追加在ENTRYPOINT后面]

```shell
FROM centos
MAINTAINER wxy<2357191256@qq.com>

ENV MYPATH /usr/local
WORKDIR $MYPATH

RUN yum -y install vim
RUN yum -y install net-tools

EXPOSE 80

CMD $MYPATH
CMD echo "-----end----"
CMD /bin/bash

# 构建镜像
docker build -f dockerfile文件的路径 -t 镜像名:版本号 .
```

## docker push 推送

```shell
# 先登录 
docker login -uwxy0715 -p
docker push 镜像id
```



## Docker 网络

```shell
# 网络互联
docker run -d -P --name 容器名/id --link 容器名/id 镜像名/id
docker network ls    # 查看所有的docker网络
# 自定义网络 (优势:内部可以直接ping通)
docker  network create --driver 网络模式 --subnet 子网ip --gateway 网关 网络名     
# 网段不同,容器与网络之间的连接
docker network connect 网络名 容器名/id
```



## redis集群

### 设置redis集群网卡及查看

```shell
docker network create redis --subnet 172.101.0.0/16
docker network ls
docker network inspect redis
```

### redis节点创建及设置

```shell
for port in $(seq 1 6);
do
mkdir -p /opt/docker/redis/node-${port}/conf
touch /opt/docker/redis/node-${port}/conf/redis.conf
cat << EOF >/opt/docker/redis/node-${port}/conf/redis.conf
port 6379
bind 0.0.0.0
cluster-enabled yes
cluster-config-file nodes.conf
cluster-node-timeout 5000
cluster-announce-ip 172.101.0.1${port}
cluster-announce-port 6379
cluster-announce-bus-port 16379
appendonly yes
EOF
done 
```



### 拉取redis镜像并启动redis节点
```shell
# 节点1
docker run -p 6371:6379 -p 16371:16379 --name redis-1 \
 -v /opt/docker/redis/node-1/data:/data \
 -v /opt/docker/redis/node-1/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.11 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
# 节点2
docker run -p 6372:6379 -p 16372:16379 --name redis-2 \
 -v /opt/docker/redis/node-2/data:/data \
 -v /opt/docker/redis/node-2/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.12 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
# 节点3
docker run -p 6373:6379 -p 16373:16379 --name redis-3 \
 -v /opt/docker/redis/node-3/data:/data \
 -v /opt/docker/redis/node-3/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.13 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
# 节点4
docker run -p 6374:6379 -p 16374:16379 --name redis-4 \
 -v /opt/docker/redis/node-4data:/data \
 -v /opt/docker/redis/node-4/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.14 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
# 节点5
docker run -p 6375:6379 -p 16375:16379 --name redis-5 \
 -v /opt/docker/redis/node-5/data:/data \
 -v /opt/docker/redis/node-5/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.15 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
# 节点6
docker run -p 6376:6379 -p 16376:16379 --name redis-6 \
 -v /opt/docker/redis/node-6/data:/data \
 -v /opt/docker/redis/node-6/conf/redis.conf:/etc/redis/redis.conf \
 -d --net redis --ip 172.101.0.16 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf
```

### 以交互模式进入redis节点内
```shell
docker exec -it redis-1 /bin/sh
```

#### 创建redis集群
```shell
redis-cli --cluster create 172.101.0.11:6379 172.101.0.12:6379 \
172.101.0.13:6379 172.101.0.14:6379 172.101.0.15:6379 \
172.101.0.16:6379 --cluster-replicas 1
```

### 查看集群配置

```shell
/data # redis-cli -c   //-c表示集群
127.0.0.1:6379> cluster info  //集群配置
cluster_state:ok
cluster_slots_assigned:16384
cluster_slots_ok:16384
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:3
cluster_current_epoch:6
cluster_my_epoch:1
cluster_stats_messages_ping_sent:613
cluster_stats_messages_pong_sent:614
cluster_stats_messages_sent:1227
cluster_stats_messages_ping_received:609
cluster_stats_messages_pong_received:613
cluster_stats_messages_meet_received:5
cluster_stats_messages_received:1227
127.0.0.1:6379> cluster nodes //集群节点信息
887c5ded66d075b6d29602f89a6adc7d1471d22c 172.101.0.11:6379@16379 myself,master - 0 1598439359000 1 connected 0-5460
e6b5521d86abc96fe2e51e40be8fbb1f23da9fe7 172.101.0.15:6379@16379 slave 887c5ded66d075b6d29602f89a6adc7d1471d22c 0 1598439359580 5 connected
d75a9db032f13d9484909b2d0d4724f44e3f1c23 172.101.0.14:6379@16379 slave db3caa7ba307a27a8ef30bf0b26ba91bfb89e932 0 1598439358578 4 connected
b6add5e06fd958045f90f29bcbbf219753798ef6 172.101.0.16:6379@16379 slave 7684dfd02929085817de59f334d241e6cbcd1e99 0 1598439358578 6 connected
7684dfd02929085817de59f334d241e6cbcd1e99 172.101.0.12:6379@16379 master - 0 1598439360082 2 connected 5461-10922
db3caa7ba307a27a8ef30bf0b26ba91bfb89e932 172.101.0.13:6379@16379 master - 0 1598439359079 3 connected 10923-16383
```

## SpringBoot项目打包Docker镜像

```shell
1、构建SpringBoot项目
2、打包运行
mvn package
3、编写Dockerfile
FROM java:8
COPY *.jar /app.jar
CMD ["--server.port=8080"]
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
4、构建镜像
# 1.复制jar和DockerFIle到服务器
# 2.构建镜像
$ docker build -t xxxxx:xx  .
```



# Docker精通

## Docker Compose(单机)

> DockerFile需要手工操作,很是麻烦。compose是docker的开源项目,所以需要安装

### 下载

```shell
curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.5/docker-compose-`uname -s`-`uname -m` >/usr/local/bin/docker-compose
# 赋权：
[root@VM-12-12-centos bin]# sudo chmod +x /usr/local/bin/docker-compose
# 执行：
[root@VM-12-12-centos bin]# docker-compose version
docker-compose version 1.25.5, build 8a1c60f6
docker-py version: 4.1.0
CPython version: 3.7.5
OpenSSL version: OpenSSL 1.1.0l  10 Sep 2019
```

### 测试

```shell
mkdir composetest
cd composetest
# app.py
vim app.py
import time
import redis
from flask import Flask

app = Flask(__name__)
cache = redis.Redis(host='redis', port=6379)

def get_hit_count():
    retries = 5
    while True:
        try:
            return cache.incr('hits')
        except redis.exceptions.ConnectionError as exc:
            if retries == 0:
                raise exc
            retries -= 1
            time.sleep(0.5)

@app.route('/')
def hello():
    count = get_hit_count()
    return 'Hello World! I have been seen {} times.\n'.format(count)
    
# requirements.txt 
vim requirements.txt
flask
redis

# Dockerfile
vim Dockerfile
# syntax=docker/dockerfile:1
FROM python:3.7-alpine
WORKDIR /code
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
RUN apk add --no-cache gcc musl-dev linux-headers
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
CMD ["flask", "run"]

# docker-compose.yml
vim docker-compose.yml
version: "3.7" //换成自己版本的python
services:
  web:
    build: .
    ports:
      - "5000:5000"
  redis:
    image: "redis:alpine"
```

### 启动

```
 docker-compose up  / docker-compose up -d
 docker-compose ps
```

### 停止

```
docker-compose down
```

## 安装WordPress博客

```shell
cd my_wordpress/
vim docker-compose.yml
version: "3.7"
    
services:
  db:
    image: mysql:5.7
    volumes:
      - db_data:/var/lib/mysql
    restart: always
    environment:
      MYSQL_ROOT_PASSWORD: somewordpress
      MYSQL_DATABASE: wordpress
      MYSQL_USER: wordpress
      MYSQL_PASSWORD: wordpress
    
  wordpress:
    depends_on:
      - db
    image: wordpress:latest
    volumes:
      - wordpress_data:/var/www/html
    ports:
      - "8000:80"
    restart: always
    environment:
      WORDPRESS_DB_HOST: db:3306
      WORDPRESS_DB_USER: wordpress
      WORDPRESS_DB_PASSWORD: wordpress
      WORDPRESS_DB_NAME: wordpress
volumes:
  db_data: {}
  wordpress_data: {}
  
# docker-compose up -d 后台启动
# docker-compose down 停止
```





## Docker Swarm(集群)





## Docker Stack



## Docker Secret





## Docker Config



## K8s