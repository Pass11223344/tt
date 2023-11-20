# sendMessage消息入队

## 概览

![](img/7a1c0242.png)

Handler.java
> sendMessage → sendMessageDelayed 
→ sendMessageAtTime
→ enqueueMessage

MessageQueue.java
> enqueueMessage

***
## 源码

### 消息入队

Handler有很多sendXxxxMessage方法，最终都是调用到MessageQueue的enqueueMessage方法。

[查看源码](doc/消息入队.md)


#### Message.java
``` java
public final class Message implements Parcelable {

    // Message是单链表结构
    // 管理Message的MessageQueue也是链表
    Message next;
    
    // 目标Handler，取出消息时会用到
    Handler target;
    
    // ...
    
}
```

### MessageQueue.java

``` java
boolean enqueueMessage(Message msg, long when) {

    synchronized (this) {
        if (mQuitting) {
            msg.recycle();
            return false;
        }

        msg.markInUse();
        msg.when = when;
        Message p = mMessages;
        boolean needWake;
        if (p == null || when == 0 || when < p.when) {
            msg.next = p;
            mMessages = msg;
            needWake = mBlocked;
        } else {
            needWake = mBlocked && p.target == null && msg.isAsynchronous();
            Message prev;
            for (;;) {
                prev = p;
                p = p.next;
                if (p == null || when < p.when) {
                    break;
                }
                if (needWake && p.isAsynchronous()) {
                    needWake = false;
                }
            }
            msg.next = p; // invariant: p == prev.next
            prev.next = msg;
        }

        // We can assume mPtr != 0 because mQuitting is false.
        if (needWake) {
            nativeWake(mPtr);
        }
    }
    return true;
}
```