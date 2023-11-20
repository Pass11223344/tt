# synchronized和Lock（ReentrantLock）的区别

## 概述

### 性质
1. Lock是<font color=#dea32c>**接口**</font>，属于API层面的锁，通过state来标识锁的状态。
2. synchronized是<font color=#dea32c>**关键字**</font>，属于JVM层面的锁，锁信息保存在对象头中。

### 灵活性
1. Lock可以<font color=#dea32c>**手动**</font>控制锁的获取和释放，
2. synchronized是只需指定锁，锁的获取和释放是<font color=#dea32c>**自动**</font>的。

### 性能
1. 线程较多时Lock性能好
2. 线程较少时synchronized性能好

### 公平 or 非公平
1. synchronized是非公平锁，当一个线程释放掉锁后，其它等待的线程均有可能获得这把锁。
2. ReentrantLock可以选择公平锁或非公平锁。

## 详述

`synchronized` 和 `ReentrantLock` 都是 Java 中用于同步的工具，允许在多线程环境下保证方法或代码块的互斥访问，从而避免数据不一致的问题。尽管它们的目标类似，但是在使用方式和功能特性上有明显的不同。

### `synchronized`

1. **简单易用**：`synchronized` 通过方法级或代码块级实现同步控制，使用较为简单。
   
2. **自动释放锁**：线程退出同步方法或代码块自动释放锁，减少死锁可能。

3. **不可中断**：一旦线程获得锁，其他等待的线程不能中断它。
   
4. **不可设置超时**：获取锁的操作不能设置超时时间。

5. **非公平锁**：`synchronized` 是非公平的，即线程获取锁的顺序不一定是请求锁的顺序。

6. **无法获取等待锁的线程信息**：无法获取等待获取锁的线程信息。

### `ReentrantLock`

1. **复杂但功能更强大**：`ReentrantLock` 需要手动锁定和解锁，但功能更加丰富。
   
2. **手动控制锁的释放**：需要调用 `lock()` 和 `unlock()` 方法来获取和释放锁，这样能实现更细粒度的锁控制。

3. **可中断锁**：`ReentrantLock` 支持锁的中断，即在等待锁的过程中可以响应中断。

4. **支持超时获取锁**：`ReentrantLock` 可以尝试在给定的超时时间内获取锁。

5. **支持公平和非公平锁**：可以在构造函数中选择是否需要公平锁。

6. **提供了锁的条件（Condition）**：与 `wait()` 和 `notify()` 方法相似的机制，但更加灵活。

7. **可获取等待锁的线程信息**：它能够显示的获取等待锁的线程信息，增强了程序的可监控性。

### 示例代码

#### 使用 `synchronized`

```java
public synchronized void syncMethod() {
    // Critical section
}

// OR

public void someMethod() {
    synchronized (lockObject) {
        // Critical section
    }
}
```

#### 使用 `ReentrantLock`

```java
private final ReentrantLock lock = new ReentrantLock();

public void lockMethod() {
    lock.lock();
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
}
```

### 结论

- 如果需要实现简单的线程同步，并不需要详细的锁控制或锁信息，那么 `synchronized` 是一个不错的选择。
- 如果需要更强大的功能，例如尝试超时、可中断的锁获取操作，或者需要更细粒度的锁控制，那么 `ReentrantLock` 是更好的选择。
