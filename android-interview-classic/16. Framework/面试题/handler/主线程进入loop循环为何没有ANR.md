# 主线程进入loop循环为何没有ANR

1. 了解ANR触发的原理
2. 了解应用大致启动流程
3. 了解线程的消息循环机制
4. 了解应用和系统服务通信过程

## ANR

### 显示ANR

1. 弹窗类：AppNotRespondingDialog
2. 弹出的进程：AMS中，即SystemServer进程。

### ANR场景

1. Service Timeout
2. BroadcastQueue Timeout
3. ContentProvider Timeout
4. InputDispatching Timeout

### 检测ANR

#### ActiveServices

``` java
// How long we wait for a service to finish executing.
static final int SERVICE_TIMEOUT = 20 * 1000 * Build.HW_TIMEOUT_MULTIPLIER;

// How long we wait for a service to finish executing.
static final int SERVICE_BACKGROUND_TIMEOUT = SERVICE_TIMEOUT * 10;

// How long the startForegroundService() grace period is to get around to
// calling startForeground() before we ANR + stop it.
static final int SERVICE_START_FOREGROUND_TIMEOUT = 10 * 1000 * Build.HW_TIMEOUT_MULTIPLIER;
```

##

``` java
public class InputConstants {
    // 默认的触摸事件分发超时时间
    public static final int DEFAULT_DISPATCHING_TIMEOUT_MILLIS =
            IInputConstants.UNMULTIPLIED_DEFAULT_DISPATCHING_TIMEOUT_MILLIS
            * Build.HW_TIMEOUT_MULTIPLIER;
}
```
