# 说说Android系统的启动

## 系统进程

由init进程启动的进程。

在init.rc中定义了很多系统进程：

![](img/ec3bdf3c.png)

## Zygote进程是怎么启动的

1. init进程fork出zygote进程
2. 启动虚拟机，注册jni函数
3. 预加载系统资源
4. 启动SystemServer
5. 进入Socket Loop

## SystemServer是怎么启动的
