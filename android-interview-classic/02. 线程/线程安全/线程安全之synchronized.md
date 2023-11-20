# 线程安全之synchronized

## 概述

synchronized是Java中的关键字，用于实现线程同步，确保多个线程对共享资源的安全访问，保证了代码的原子性和可见性。

## 详述

在 Java 中，`synchronized` 关键字是一种用于控制多线程访问共享资源的方式，以避免多个线程同时访问一个代码块或方法导致的数据不一致问题。使用 `synchronized` 可以确保一个线程在任意时刻只能执行一个 `synchronized` 代码块或方法，从而确保了线程安全。

### 使用 `synchronized` 的方式

1. **Synchronized 方法**
   
   你可以直接在方法的声明中使用 `synchronized` 关键字，表明这个方法是同步的。

   ```java
   public synchronized void synchronizedMethod() {
       // code block
   }
   ```
   
   对于实例方法（非 static 方法），锁定的是对象的实例；对于静态方法（static 方法），锁定的是类的 Class 对象。

2. **Synchronized 代码块**
   
   使用 `synchronized` 关键字和一个锁对象来定义一个同步代码块。

   ```java
   public void someMethod() {
       synchronized (lockObject) {
           // code block
       }
   }
   ```

   其中 `lockObject` 是一个参考对象，所有使用这个对象的 `synchronized` 块都会被同步。

### 特点

- **互斥性**：一次只允许一个线程访问共享资源。
- **可见性**：一个线程对共享变量值的修改，其他线程能立即得知。

### 注意事项

- **锁定的对象**：如果使用同步块，应确保尽可能锁定适当的对象，并尽可能仅同步必要的代码段（即临界区）以减少性能开销。
- **死锁**：在使用 `synchronized` 时，应避免死锁，当两个或更多线程等待彼此释放锁时，就会发生死锁。
- **过度同步**：过度使用 `synchronized` 可能导致线程拥堵和影响性能。
  
### 示例代码

#### Synchronized Method

```java
public synchronized void perform() {
    // Critical section here
}
```

#### Synchronized Block

```java
private final Object lock = new Object();

public void perform() {
    synchronized (lock) {
        // Critical section here
    }
}
```

### 小结

`synchronized` 提供了一种简单但表达力强的方式来进行同步控制，确保多线程环境下的数据安全和一致。在使用时，要权衡同步带来的安全与性能开销之间的平衡，并注意避免死锁等问题。
