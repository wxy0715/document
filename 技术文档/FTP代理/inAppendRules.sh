#!/bin/bash
#增加规则通道脚本

execCommend.setIp(properties.getLocalIp());
RespData<Boolean> resultLocal = execCommend.startPassageway(passageway);
execCommend.setIp(properties.getTargetIp());
RespData<Boolean> resultTarget = execCommend.startPassageway(passageway);

内网(监听地址) ===(服务器in)网闸(服务器out)====外网(目标地址)
in端
内->外
# Destination network  address translate (dnat)
iptables -t nat -A PREROUTING  -p $pro -d in端外网ip  --dport $listen_port -j DNAT --to out端外网ip:$out_port
iptables -t nat -A PREROUTING  -p $pro -d 监听地址  --dport $listen_port -j DNAT --to out端外网ip:$out_port
iptables -A FORWARD -p $pro -d out端外网ip --dport $out_port -j ACCEPT
iptables -t nat -A OUTPUT -p $pro -d in端外网ip --dport $listen_port -j DNAT --to out端外网ip:$out_port
iptables -t nat -A OUTPUT -p $pro -d 监听地址 --dport $listen_port -j DNAT --to out端外网ip:$out_port
# source network address translate (snat)
iptables -t nat -A POSTROUTING -p $pro -d out端外网ip --dport $  -j SNAT --to in端外网ip

out端
内->外
# Destination network  address translate (dnat)
iptables -t nat -A PREROUTING  -p $pro -d 链接地址  --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -t nat -A PREROUTING  -p $pro -d out端外网ip  --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -A FORWARD -p $pro -d 目标地址 --dport $target_port -j ACCEPT
iptables -t nat -A OUTPUT -p $pro -d 链接地址 --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -t nat -A OUTPUT -p $pro -d out端外网ip --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
# source network address translate (snat)
iptables -t nat -A POSTROUTING -p $pro -d 目标地址 --dport $target_port -j SNAT --to 链接地址




---------------------------------------------------------------------------------------------------------------------------


out端
外->内
iptables -t nat -A PREROUTING  -p $pro -d out端外网ip  --dport $listen_port -j DNAT --to in端外网ip:$out_port
iptables -t nat -A PREROUTING  -p $pro -d 监听网卡ip  --dport $listen_port -j DNAT --to in端外网ip:$out_port
iptables -A FORWARD -p $pro -d in端外网ip --dport $out_port -j ACCEPT
iptables -t nat -A OUTPUT -p $pro -d in端外网ip --dport $listen_port -j DNAT --to in端外网ip:$out_port
iptables -t nat -A OUTPUT -p $pro -d 监听网卡ip --dport $listen_port -j DNAT --to in端外网ip:$out_port
# source network address translate (snat)
iptables -t nat -A POSTROUTING -p $pro -d in端外网ip --dport $out_port -j SNAT --to out端外网ip

in端
外->内
# applicationProperties.inIbIp + " " + pp.getMonitorPort() + " " + pp.getLinkIp() + " " + pp.getTargetIp() + " " + pp.getTargetPort()+" "+protocol;
iptables -t nat -A PREROUTING  -p $pro -d 链接地址  --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -t nat -A PREROUTING  -p $pro -d in端外网ip  --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -A FORWARD -p $pro -d 目标地址 --dport $target_port -j ACCEPT
iptables -t nat -A OUTPUT -p $pro -d 链接地址 --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
iptables -t nat -A OUTPUT -p $pro -d in端外网ip --dport $listen_port -j DNAT --to 目标地址:$target_port_rp
# source network address translate (snat)
iptables -t nat -A POSTROUTING -p $pro -d 目标地址 --dport $target_port -j SNAT --to 链接地址




Lan口:10.1.1.254/24 eth0
Lan内web server: 10.1.1.1:80
Wan口:60.1.1.1/24 eth1
目的：对内部server进行端口转发实现internet用户访问内网服务器
iptables -t nat -A PREROUTING -d 60.1.1.1 -p tcp --dport 80 -j DNAT --to 10.1.1.1:80
iptables -A FORWARD -d 10.1.1.1 -p tcp --dport 80 -j ACCEPT
iptables -t nat -A POSTROUTING -d 10.1.1.1 -p tcp --dport 80 -j SNAT --to 10.1.1.254