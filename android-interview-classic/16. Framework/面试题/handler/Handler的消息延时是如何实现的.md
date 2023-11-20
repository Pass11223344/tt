# Handler的消息延时是如何实现的

1. 消息队列按消息触发时间排序
2. 设置epoll_wait的超时时间，使其在特定时间唤醒
3. 关于延时精度，可能不太准

``` java
/**
 * Enqueue a message into the message queue after all pending messages
 * before (current time + delayMillis). You will receive it in
 * {@link #handleMessage}, in the thread attached to this handler.
 */
public final boolean sendMessageDelayed(@NonNull Message msg, long delayMillis) {
    if (delayMillis < 0) {
        delayMillis = 0;
    }
    return sendMessageAtTime(msg, SystemClock.uptimeMillis() + delayMillis);
}
```

在(当前时间+延时时间)之前，入队一条消息到消息队列中，在所有待处理的消息之后。

你将会在handleMessage中收到这条消息，在依附到Handler的线程中。

``` java
public boolean sendMessageAtTime(@NonNull Message msg, long uptimeMillis) {
    MessageQueue queue = mQueue;
    // ...
    return enqueueMessage(queue, msg, uptimeMillis);
}
```

``` java
private boolean enqueueMessage(@NonNull MessageQueue queue, @NonNull Message msg,
        long uptimeMillis) {
    msg.target = this;
    // ...
    return queue.enqueueMessage(msg, uptimeMillis);
}
```

``` java
boolean enqueueMessage(Message msg, long when) {
    // ...

    synchronized (this) {
        // ...
        
        msg.when = when;
        Message p = mMessages;
        boolean needWake;
        if (p == null || when == 0 || when < p.when) {
            // 作为头节点
            msg.next = p;
            mMessages = msg;
            // 唤醒阻塞队列
            needWake = mBlocked;
        } else {
            // Inserted within the middle of the queue.  Usually we don't have to wake
            // up the event queue unless there is a barrier at the head of the queue
            // and the message is the earliest asynchronous message in the queue.
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