# 线程安全之ThreadLocal

## 概述

ThreadLocal是Java中的类，用于实现线程局部变量，每个线程都有自己的变量副本，互不干扰，提供了线程封闭和线程安全的机制。

## 详述

`ThreadLocal` 是 Java 中一种实现线程局部变量（thread-local variables）的机制。这个类提供了一种通过使用线程 (thread) 作为键来存储变量的方法，每个线程可以通过该键来访问和修改自己的变量副本。

### 主要特点和用途：

1. **线程隔离**：每个线程存储自己的 `ThreadLocal` 变量副本，变量在不同线程之间互不影响。
   
2. **减少共享资源同步的需要**：由于变量不被多线程共享，不存在多线程竞争，所以无需使用`synchronized`等同步机制。

3. **保存线程状态**：例如在 Web 开发中，可以用来保存一个线程的用户登录状态信息。

### 如何使用：

#### 1. 创建 ThreadLocal 变量
你可以通过创建 `ThreadLocal` 的实例来在每个线程中存储一个“线程局部”变量。

```java
private ThreadLocal<Integer> threadLocalCounter = new ThreadLocal<>();
```

#### 2. 访问 ThreadLocal 变量
你可以通过 `get()` 和 `set(T value)` 方法来访问和修改与当前线程关联的变量的值。

```java
threadLocalCounter.set(1);
Integer counter = threadLocalCounter.get();
```

#### 3. 初始化 ThreadLocal 变量
`ThreadLocal` 可以通过重写 `initialValue()` 方法来提供变量的初始值。

```java
private ThreadLocal<Integer> threadLocalCounter = new ThreadLocal<Integer>() {
    @Override
    protected Integer initialValue() {
        return 0;
    }
};
```

#### 4. 清理
在不再需要使用 `ThreadLocal` 时，可以调用 `remove()` 方法清除与当前线程关联的变量值，有助于减少内存泄漏的风险。

```java
threadLocalCounter.remove();
```

### 注意事项：

1. **内存泄漏**：虽然每个线程都有自己的变量副本，但如果线程执行完毕而变量副本没有被移除，就可能导致内存泄漏。尤其在使用线程池的情况下，因为这些线程常驻内存并会被反复利用。

2. **使用 InheritableThreadLocal**：`InheritableThreadLocal` 是 `ThreadLocal` 的子类，它提供了一种在所有线程中保留值的副本的能力，即使子线程也是如此。

### 总结：
`ThreadLocal` 是一个用于实现线程内部存储的类，在创建的时候可以选择性地提供一个初始值提供器。在多线程编程中，`ThreadLocal` 是一种避免复杂同步问题的优雅方案，尤其在你想避免同步锁定对象时。