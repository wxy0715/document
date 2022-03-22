# 安装

```sh
# 修改服务器配置 ,如果是阿里云的
config/jvm.options
-Xms200m
-Xmx200m
一定最小和最大设置一样 

# 创建es用户
adduser  es
passwd  es
chown -R es:es elasticsearch-6.3.2/
chmod 770 elasticsearch-6.3.2/
su es

# 修改系统配置
sysctl -w vm.max_map_count=655360
 
# 打开config/es的yml
network.host:  0.0.0.0 # 设置外网可以访问

运行 ./bin/elasticsearch
```

