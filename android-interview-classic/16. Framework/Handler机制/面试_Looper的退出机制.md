# Looper的退出机制

``` java
public static void loop() {
    final Looper me = myLooper();
    
    // ...

    for (;;) {
        if (!loopOnce(me, ident, thresholdOverride)) {
            return;
        }
    }
}
```

``` java
private static boolean loopOnce(final Looper me,
    final long ident, final int thresholdOverride) {
    // 取出消息，有可能会阻塞
    Message msg = me.mQueue.next(); 
    if (msg == null) {
        // 当msg == null时，表示消息队列正在退出
        return false;
    }
}
```

## Looper的退出
注意：退出只能在子线程中调用，主线中中调用会抛异常。
#### Looper.java
``` java
/**
 * 退出looper
 *
 * 当消息队列中没有正在处理中的消息后才会真正退出looper。
 * 
 * 调用此方法后，新消息的入队操作都会失败。
 * 例如, Handler#sendMessage(Message)方法将会返回false。
 *
 * 使用此方法可能是不安全的，因为一些消息在looper终止前可能没有分发给Handler处理。
 *
 * 考虑使用quitSafely()来替换quit()， 确保剩余的消息以有序的方式被完整地处理。
 *
 * @see #quitSafely
 */
public void quit() {
    // 默认是以不安全的方式退出
    mQueue.quit(false);
}

public void quitSafely() {
    mQueue.quit(true);
}
```

#### MessageQueue.java
``` java
void quit(boolean safe) {
    
    if (!mQuitAllowed) {
        // 主线程不允许调用quit来退出
        throw new IllegalStateException("Main thread not allowed to quit.");
    }
    
    synchronized (this) {
        if (mQuitting) {
            return;
        }
        mQuitting = true;

        if (safe) {
            // 清空MessageQueue消息池中全部的延迟消息，
            // 并将队列中全部的非延迟消息派发出去让Handler去处理
            removeAllFutureMessagesLocked();
        } else {
            // 把MessageQueue消息池中全部的消息全部清空
            removeAllMessagesLocked();
        }

        // We can assume mPtr != 0 because mQuitting was previously false.
        nativeWake(mPtr);
    }
}
```

