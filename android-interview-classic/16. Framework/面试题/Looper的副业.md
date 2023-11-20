# Looper的副业

***

## <font color=orange>**Loop()的工作**</font>

![](img/63927a60.png)
> 1. 获取当前线程looper。
> 2. 获取looper的消息队列。
> 3. 循环执行消息出队和分发。

![](img/1c6349e6.png)
> 执行出队时，若没有消息则执行nativePollOnce阻塞。