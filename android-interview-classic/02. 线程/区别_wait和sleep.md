# wait和sleep的区别

## 概述

|   |  wait  | sleep |
|  ----  | ----  | ----  |
|锁的释放|释放|不释放|
|所属类|Object|Thread|
|场景限制|必须在同步代码块中|不限制|
|唤醒时机|随时可唤醒|睡眠时间结束后自动唤醒|

***

## 详述

`wait()` 和 `sleep()` 是 Java 多线程编程中两个用于让线程等待的方法，但它们存在一些显著的差异，并且它们被用在不同的上下文中。下面列出了 `wait()` 和 `sleep()` 的主要区别：

### 1. 声明位置
- `wait()` 是 `Object` 类的一个方法。
- `sleep()` 是 `Thread` 类的一个静态方法。

### 2. 锁的释放
- 当一个线程调用 `wait()` 方法时，它会释放持有的对象锁，从而使其他线程可以同步在这个对象上。
- 当一个线程调用 `sleep()` 方法时，它不会释放任何锁资源。

### 3. 使用场景
- `wait()` 通常用在同步块或同步方法中，与 `notify()` 或 `notifyAll()` 方法配合，实现线程间的通信。
- `sleep()` 可以在任何上下文中使用，它仅仅让当前执行线程进入休眠状态。

### 4. 中断处理
- 当一个线程在 `wait()` 状态下被其他线程中断，它会抛出 `InterruptedException` 并清除中断标记。
- 当一个线程在 `sleep()` 状态下被其他线程中断，它同样会抛出 `InterruptedException` 并清除中断标记。

### 5. 调用方式
- `wait()` 方法必须在同步块或同步方法中被调用，否则会抛出 `IllegalMonitorStateException`。
- `sleep()` 方法不需要在同步块或同步方法中调用。

### 示例代码

#### 使用 `wait()`

```java
synchronized (lockObject) {
    while (conditionIsNotMet) {
        lockObject.wait();
    }
    // Continue the workflow
}
```

#### 使用 `sleep()`

```java
try {
    Thread.sleep(2000);  // Sleeping for 2 seconds
} catch (InterruptedException e) {
    // Handle the interruption
}
```

### 总结
- `wait()` 主要用于多个线程间的交互，并且它实际上控制线程执行的顺序。它允许一个线程等待直到其它线程让它继续执行。
- `sleep()` 主要用于让执行中的线程休眠一段时间，然后让出CPU给其他线程，但不释放对象锁，使得其他线程无法访问这个对象的同步块。
   
在实际编程中，根据你的具体需求和使用的上下文来选择使用 `wait()` 还是 `sleep()`。