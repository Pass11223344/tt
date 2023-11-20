# HashSet

## 概述

HashSet是Java中的集合实现，基于哈希表，无序且不允许重复元素，提供高效的插入、删除和查找操作，适用于快速查找和去重的场景。

## 详述

`HashSet` 是 `java.util` 包中的一个类，它实现了 `Set` 接口并提供了 `Set` 接口的功能特性。`HashSet` 主要用于存储不允许重复的元素，并且没有保证元素的顺序。

### 特性
- **不允许重复**：`HashSet` 不允许集合中出现重复元素。
- **不保证顺序**：`HashSet` 不保证元素的顺序，也不保证顺序会保持不变。
- **允许空值**：`HashSet` 允许存储一个 `null` 值。
- **线程不安全**：`HashSet` 非线程安全，如果多个线程同时修改 `HashSet`，必须通过某种机制来同步它。
  
### 方法
- **`add(E e)`**：添加指定元素到此集合。
- **`remove(Object o)`**：如果存在，则从此集合中移除指定元素。
- **`contains(Object o)`**：判断此集合是否包含指定元素。
- **`size()`**：获取集合中元素的数量。
- **`isEmpty()`**：判断集合是否为空。
- **`clear()`**：从此集合中移除所有元素。

### 性能
- **时间复杂度**：`HashSet` 提供常数时间复杂度的添加、删除操作，以及查询（例如 `contains(Object key)` 方法）操作，假定哈希函数将元素适当地分散在各个桶中。
- **初始化和加载因子**：当 `HashSet` 的实例创建时，可以设置初始容量和加载因子，以控制数组何时调整大小（重新哈希）。一般来说，默认加载因子 (.75) 在时间和空间成本上提供了一个很好的折衷。

### 使用示例
```java
HashSet<String> fruits = new HashSet<>();
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Cherry");

// Attempting to add a duplicate element
fruits.add("Apple");  // This will not be added as "Apple" is already in the set

// Checking if an element exists
boolean exists = fruits.contains("Banana");  // true

// Iterating through the elements
for(String fruit : fruits) {
    System.out.println(fruit);
}
```

### 底层工作原理
- `HashSet` 是基于 `HashMap` 实现的，它实际上使用一个 `HashMap` 实例来存储所有元素。
- 当你插入一个元素时，`HashSet` 会将元素作为 `HashMap` 的键插入，并使用一个常数值（`PRESENT`）作为值。因此，本质上所有的键都是唯一的。
- `HashSet` 利用哈希表的数据结构来确保不存储重复元素，并提供常数时间复杂度的基本操作。

### 小结
`HashSet` 是一种无序集合，它不存储重复元素，并且提供了常数时间复杂度的基本操作。它是通过背后的 `HashMap` 实现的，适用于存储不需要维护元素顺序的集合。