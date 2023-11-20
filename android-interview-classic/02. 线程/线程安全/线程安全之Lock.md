# 线程安全之Lock

Lock在JDK5加入，相比synchronized，结构更灵活，功能更强大，性能更优越。

### 主要的实现类
1. ReentrantLock
2. ReentrantReadWriteLock.ReadLock
3. ReentrantReadWriteLock.WriteLock

## 概述

### ReentrantLock可重入锁

ReentrantLock是Java中的锁实现，支持可重入特性，提供了更灵活的锁定和解锁机制，比synchronized更具扩展性和控制性。

>  锁的可重入性：当一个线程获取到一个对象的锁后，再次请求获取该对象的锁，仍然可以获取到。假设锁不可重入，有可能会产生死锁

### ReentrantReadWriteLock读写锁
同一时间内，允许多个线程读取数据，但只允许一个线程修改数据。
> 开发者可以同时读取远程仓库的代码，但是不能同时向远程仓库push代码。

### 公平锁和非公平锁

公平锁和非公平锁是两种不同的锁获取方式。公平锁按照线程请求的顺序获取锁，非公平锁允许线程插队获取锁，提高了吞吐量但可能导致线程饥饿。

> synchronized是非公平锁，ReentrantLock默认也是非公平锁。

> 可以通过 new ReentrantLock(boolean fair) 得到公平锁。 

### 独占锁
同一时间只允许一个线程执行同步代码块。
> synchronized内部锁、ReentrantLock都是独占锁。




1. 读锁：可以被多个线程持有，读取前必须持有读锁，若其它线程持有写锁，则无法获取。
2. 写锁：当其它线程既不持有读锁也不持有写锁时，才能够获取。
> 读读共享、读写互斥、写写互斥。

## 详述-ReentrantLock

`ReentrantLock` 是 Java 中 `java.util.concurrent.locks` 包下的一个类，它提供了和 `synchronized` 相似的线程加锁能力，同时更为灵活。它实现了 `Lock` 接口，并提供了与 `synchronized` 类似的互斥性和可见性，但比 `synchronized` 更加强大。

### 特点：

#### 1. 可重入
线程可以重复获取已经持有的锁。这意味着如果一个线程尝试获取一个已经由它自己持有的锁，请求会成功。

#### 2. 公平锁 & 非公平锁
`ReentrantLock` 可以是公平的，也可以是非公平的。公平锁意味着等待获取锁的线程将会按照请求锁的顺序来获取锁，而非公平锁则不保证这一点。

#### 3. 可中断
线程试图获取锁时可以被中断。`ReentrantLock` 提供了一种能够响应中断的锁获取操作，即在锁获取等待过程中，线程可以被中断。

#### 4. 锁超时
提供带有时间限制的锁获取尝试，防止死锁。

#### 5. 锁轮询
能够响应中断的锁轮询，线程可以尝试在一个时间片段里获取锁。

### 使用方法：

#### 1. 初始化
`ReentrantLock` 可以在创建时选择是否是公平锁。

```java
ReentrantLock lock = new ReentrantLock(); // 默认非公平锁
ReentrantLock fairLock = new ReentrantLock(true); // 公平锁
```

#### 2. 加锁 & 释放锁
```java
lock.lock();
try {
    // Critical section
} finally {
    lock.unlock();
}
```

#### 3. 尝试加锁
```java
if (lock.tryLock()) {
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
}
```

#### 4. 响应中断的锁获取尝试
```java
try {
    lock.lockInterruptibly();
    try {
        // Critical section
    } finally {
        lock.unlock();
    }
} catch (InterruptedException e) {
    // Handle the interruption
}
```

### 注意事项：
- 必须确保`unlock()`被调用，通常放在`finally`块中确保其执行。
  
- `ReentrantLock` 用作显示锁，必须手动加锁和解锁，所以务必保证锁的释放，否则可能导致死锁。

### `ReentrantLock` vs `synchronized`
- `ReentrantLock` 提供更高的灵活性：能够中断等待锁的线程、能够尝试获取锁、能够定时等待锁。
  
- `ReentrantLock` 需要手动加锁和释放锁。

- `synchronized` 是 Java 关键字，并且加锁和释放锁的操作是隐式的，它会在代码块执行完成或者遇到异常后自动释放锁。

综合来说，`ReentrantLock` 提供了比 `synchronized` 更加丰富的同步机制，并且提供更大的灵活性。在性能上，早期版本中 `ReentrantLock` 的性能较 `synchronized` 好一些，但在 Java 6 之后的版本中，两者的性能差异并不显著，选择使用哪一个应该更多基于他们在功能上的区别。

## 详述-ReentrantReadWriteLock

`ReentrantReadWriteLock` 是 Java 中 `java.util.concurrent.locks` 包下的一个实现了 `ReadWriteLock` 接口的类。它允许多个线程同时读共享变量，同时只允许一个线程写共享变量，提供了比 `ReentrantLock` 更强大的并发数据访问控制。

### 特点：

#### 1. 读写锁分离
读锁和写锁分开，允许多个线程并发读，而写操作是互斥的。这是一个提高读操作并发性的机制。

#### 2. 可重入
它同时支持读锁和写锁的重入。也就是说，一个线程可以多次获取读锁，也可以多次获取写锁。写锁能够降级成读锁，意即一个持有写锁的线程可以获取读锁，但反之不成立。

#### 3. 公平锁 & 非公平锁
`ReentrantReadWriteLock` 可以是公平的或者非公平的。在公平模式下，线程将会按照它们请求锁的顺序来获得锁，而在非公平模式下，这个顺序并不一定会被保证。

### 使用方法：

#### 1. 初始化
你可以选择创建一个公平的或者非公平的锁。
```java
ReentrantReadWriteLock lock = new ReentrantReadWriteLock(); // 非公平锁
ReentrantReadWriteLock fairLock = new ReentrantReadWriteLock(true); // 公平锁
```

#### 2. 读锁 & 写锁
读锁使用 `readLock()` 方法获取，写锁使用 `writeLock()` 方法获取。
```java
ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
```

#### 3. 加锁 & 释放锁
```java
readLock.lock();
try {
    // 执行读操作
} finally {
    readLock.unlock();
}

writeLock.lock();
try {
    // 执行写操作
} finally {
    writeLock.unlock();
}
```

### 注意事项：

- 要保证锁最终能够被释放，将释放锁的操作放在 `finally` 块中是一种常见的做法。

- 不要在持有读锁的情况下尝试获取写锁，这可能会导致线程死锁。

### 使用场景：
当一个数据结构主要是读操作，只有少数的写操作来更新数据结构时，使用 `ReentrantReadWriteLock` 能够提高并发性能。多个读线程将会在没有写线程时并发执行，而写线程将会在没有读线程和写线程时串行执行。这种机制通常用于提高读操作的性能，而不是写操作。

