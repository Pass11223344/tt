# 线程安全之volatile

## 概述

volatile是Java中的关键字，用于声明变量，在多线程环境下确保变量的可见性，禁止指令重排序，但不保证原子性操作。

## 详述

在 Java 中，`volatile` 是一个类型修饰符，它提供了一种轻量级的同步机制，用来保证共享变量的可见性，并阻止指令重排序，但它并不保证原子性。

### 关键特性：

1. **可见性**：一旦一个共享变量被一个线程修改，其它线程可以立即看到这个修改。如果一个字段被声明为 `volatile`，Java 线程内存模型确保所有线程看到这个变量的值是一致的。

2. **防止指令重排序**：编译器在不改变单线程执行结果的前提下，有权重新排序指令执行顺序。声明一个变量为 `volatile`，将禁止 JVM 对其进行指令重排序。

### 不保证：

- **原子性**：`volatile` 不保证操作的原子性。例如，自增操作（`i++`）并不是原子的，即使变量 `i` 被声明为 `volatile`，也不能确保它的原子性。

### 使用 `volatile` 的适用情况：

你可以在以下情况下考虑使用 `volatile`：

- 写入变量不依赖其当前值，或者你能确保只有单个线程更新变量的值。
  
- 变量不需要与其他的状态变量共同参与不变约束。

- 访问变量时不需要加锁。

### 示例代码：

```java
public class SharedResource {
    private volatile boolean flag = false;
    
    public void setFlagTrue() {
        flag = true;
    }
    
    public boolean checkFlag() {
        return flag;
    }
}
```

### `volatile` 和 `synchronized` 的比较：

- **性能**：一般来说，`volatile` 变量的操作性能会比 `synchronized` 锁的操作性能更高，因为它不引起线程上下文的切换和调度。

- **用途**：如果代码复杂度只涉及到一个共享变量的简单读/写，用 `volatile` 是更简单一些的；如果涉及到多个共享变量的同步控制，就必须使用 `synchronized` 来进行同步控制。

### 总结：

虽然 `volatile` 提供了一种相对轻量级的同步策略，但在多线程控制的复杂应用场景下，要确保数据的一致性和原子性，通常推荐使用更加完善的同步工具类或者关键字（如 `synchronized` 或 `java.util.concurrent` 包下的类）。

