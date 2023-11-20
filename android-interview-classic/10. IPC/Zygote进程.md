# Zygote进程

由init进程启动，Android系统<font color=#dea32c>**创建新进程**</font>的核心进程。

1. 负责启动Dalvik虚拟机。
2. 加载一些必要的系统资源和系统类。
3. 启动system_server进程。

> 可以把它理解为"母体"进程。

***

```
frameworks/base/cmds/app_process/App_main.cpp
frameworks/base/core/jni/AndroidRuntime.cpp

frameworks/base/core/java/com/android/internal/os/
  - Zygote.java
  - ZygoteInit.java
  - ZygoteServer.java
  - ZygoteConnection.java
```