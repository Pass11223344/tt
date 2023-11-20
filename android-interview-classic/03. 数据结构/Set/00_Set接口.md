# Set接口

## 概述

Set接口是Java中的集合接口，不允许重复元素，提供高效的查找和去重功能，常见实现类有HashSet、TreeSet和LinkedHashSet。

## 详述

在Java中，`Set` 接口是 `java.util` 包下的一部分。它主要用于存储不允许重复的元素集。它继承了 `Collection` 接口，并提供了集的特性和方法，但不包含重复的元素。

### 特性：
1. **无重复元素**：`Set` 不允许集合中有重复的元素。
2. **无序集合**：`Set` 不保证元素的顺序。尽管某些实现（如 `LinkedHashSet`）可能有序。
3. **null元素**：根据实现，`Set` 可以包含一个 `null` 元素（例如 `HashSet` 允许，但 `TreeSet` 不允许）。

### 常用方法：
- **`add(E e)`**：将指定的元素添加到此 set（可选操作）。
- **`clear()`**：移除此 set 中的所有元素（可选操作）。
- **`contains(Object o)`**：如果此 set 包含指定的元素，则返回 `true`。
- **`isEmpty()`**：如果此 set 不包含元素，则返回 `true`。
- **`iterator()`**：返回在此 set 的元素上按适当顺序进行迭代的迭代器。
- **`remove(Object o)`**：如果指定的元素存在（或者通过调用 `c.remove(null)` 从此 set 中移除 null 元素），则从此 set 中移除它（可选操作）。
- **`size()`**：返回此 set 中的元素数量。
- **`equals(Object o)`**：比较此 set 与指定对象的相等性。
- **`hashCode()`**：返回此 set 的哈希码值。

### 主要实现类：
- **`HashSet`**：基于哈希表的 `Set` 接口的实现。它不保证 set 的迭代顺序；特别是它不保证该顺序恒久不变。
- **`LinkedHashSet`**：继承自 `HashSet`，并添加了维护运行次序的能力。
- **`TreeSet`**：基于 NavigableMap 的 `Set` 接口的实现。该 set 按元素的自然顺序进行排序，或者由在 set 创建时提供的 `Comparator` 进行排序，具体取决于使用的构造方法。

### 示例代码：
```java
Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Banana");
set.add("Cherry");
set.add("Apple");  // 重复元素，不会被添加

System.out.println(set.contains("Banana"));  // true
System.out.println(set.size());  // 3
```
选择哪种 `Set` 实现类依赖于你的具体需求。例如，如果你需要一个有序的 set，并且会频繁进行排序操作，`TreeSet` 可能是一个好的选择。如果你要求常数时间的添加和检索，`HashSet` 或 `LinkedHashSet` 则更为合适。