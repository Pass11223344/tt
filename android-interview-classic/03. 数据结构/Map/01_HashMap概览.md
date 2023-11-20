# HashMap

## 概述

HashMap是Java中的键值对存储结构，基于哈希表实现，提供高效的插入、查找和删除操作，适用于快速访问和存储大量键值数据的场景。

## 详述

`HashMap` 是 Java 集合框架的一部分，它位于 `java.util` 包中，并实现了 `Map` 接口。`HashMap` 是一个键值对集合，它使用哈希表来实现映射关系。

### 特性
1. **键值对映射**：`HashMap` 存储键值对（key-value pairs），允许使用键（key）检索单个元素的值（value）。
2. **无序集合**：元素不保证以任何顺序存储。
3. **允许null键值**：`HashMap` 允许使用 `null` 作为键（key）和值（value）。
4. **非同步**：`HashMap` 不是线程安全的。如果多线程同时访问并至少有一个线程修改它，它必须被同步外部。

### 常用方法
- `put(K key, V value)`: 将指定的值与此映射中的指定键关联。
- `get(Object key)`: 返回到指定键所映射的值，如果这个映射中不包含该键的映射关系，则返回 `null`。
- `remove(Object key)`: 如果存在（从可选的操作返回的），则从该映射中移除键（key）的映射关系。
- `containsKey(Object key)`: 如果这个映射中包含指定键的映射关系，则返回 `true`。
- `size()`: 返回此映射中的键-值映射关系数。
- `clear()`: 从这个映射中移除所有映射关系（可选操作）。
   
### 哈希冲突
当两个或多个键产生相同的哈希值时，会发生哈希冲突。`HashMap` 使用链表（或在某些配置下的树）来管理同一个哈希位置的多个条目。

### 性能
- **时间复杂度**：`HashMap` 提供常数时间复杂度的基本操作，即 get 和 put，假定哈希函数将元素正确分布在桶中。
- **空间复杂度**：它需要的内存是 O(n) 的，其中 n 是映射中的条目数。

### 注意事项
- 在并发环境下，应该使用 `ConcurrentHashMap` 来代替 `HashMap` 以确保线程安全，或者通过其他方式来同步 `HashMap`。
   
### 代码示例
```java
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        // Adding elements
        scores.put("Alice", 90);
        scores.put("Bob", 80);
        scores.put("Charlie", 85);

        // Accessing elements
        System.out.println("Bob's score: " + scores.get("Bob"));

        // Removing elements
        scores.remove("Alice");

        // Iterating through elements
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```
这个简单的例子展示了如何使用 `HashMap` 来存储和访问键值对，以及如何迭代映射的条目集合。