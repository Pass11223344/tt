# 可以在子线程创建Handler吗

### 会报异常

![](../img/5dbcca05.png)

### 异常原因

没有调用prepare来创建mLooper。
![](../img/e93df279.png)

![](../img/3ad9d1a1.png)

🤔 MessageQueue怎么知道要把Message分发给哪个Handler？
> Message的target字段指向了Handler对象。

