# LinkedList

## 概述

LinkedList是Java中的双向链表实现的List，支持高效插入和删除操作，但随机访问较慢，适用于频繁的插入和删除操作的场景。

## 详述

`LinkedList` 是 `List` 接口的链表实现，它实现了 `List<E>`、`Deque<E>` 接口。在 Java 集合框架中，`LinkedList` 是一个使用双链表实现的元素有序、可重复的集合类，它允许我们在列表的开始和结束进行快速的插入和删除操作。

### 特性：

1. **有序且可重复**：`LinkedList` 中的元素按照它们的插入顺序进行存储，并允许元素的重复。
   
2. **双向链表**：每个元素（节点）中都包含两个链接，一个指向前一个元素，另一个指向下一个元素。

3. **支持`null`元素**：`LinkedList` 中可以包含 `null` 元素。

### 主要方法：

- `add(E e)`: 将指定元素追加到此列表的末尾。

- `add(int index, E element)`: 在此列表中指定的位置插入指定的元素。

- `remove(int index)`: 移除此列表中指定位置上的元素。

- `get(int index)`: 返回此列表中指定位置上的元素。

- `addFirst(E e)`: 将指定元素插入此列表的开头。

- `addLast(E e)`: 将指定元素追加到此列表的末尾（与 `add(E e)` 功能相同）。

- `removeFirst()`: 移除并返回此列表的第一个元素。

- `removeLast()`: 移除并返回此列表的最后一个元素。

### 性能特点：

- **快速的插入和删除**：`LinkedList` 在列表的开头、中间或者末尾插入或删除元素时，时间复杂度为 O(1)。

- **按索引访问较慢**：获取中间一个元素或者用索引位置进行插入、删除操作时，`LinkedList` 需要从头开始或从尾开始逐个节点遍历，时间复杂度为 O(n)。

### 示例代码：

```java
LinkedList<String> list = new LinkedList<>();

// 添加元素
list.add("Apple");
list.add("Banana");
list.addFirst("Pineapple");
list.addLast("Mango");

// 获取元素
String firstItem = list.getFirst();
String lastItem = list.getLast();
String item = list.get(1);

// 删除元素
list.removeFirst();
list.removeLast();
list.remove(1);

// 遍历
for (String fruit : list) {
    System.out.println(fruit);
}
```

### 注意事项：

- 虽然 `LinkedList` 在插入和删除时表现优越，但在随机访问元素时表现不佳。所以在选择使用 `ArrayList` 还是 `LinkedList` 时，需要基于实际的使用场景做决定。

- 当执行大量随机 `get(int index)` 操作时，`ArrayList` 通常是一个更好的选择；而当执行大量的插入和删除操作时，特别是在列表开始和中间位置时，`LinkedList` 可能提供更好的性能。

- `LinkedList` 也可用作队列（`Queue`）或双端队列（`Deque`）使用。
