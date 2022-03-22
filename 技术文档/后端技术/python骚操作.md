## pyhton

```
py 编译为pyc -> python3 -m compileall {name}
```

## Python在windows下编译成exe文件

```
1. pip install pyinstaller
2. 在Terminal下输入：“pyinstaller -F -w *.py” 就可以生成exe。
  -F（注意大写）是所有库文件打包成一个exe
　-w是不出黑色控制台窗口。
```



## 获取进程PID

```perl
def get_process_id(name):
    child = subprocess.Popen(['pgrep', '-f', name], stdout=subprocess.PIPE, shell=False)
    response = child.communicate()[0]
    return [int(pid) for pid in response.split()]
```



## 获取主机地址

```perl
def get_host_ip():
    try:
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        s.connect(('8.8.8.8', 80))
        ip = s.getsockname()[0]
    finally:
        s.close()
    return ip
```

