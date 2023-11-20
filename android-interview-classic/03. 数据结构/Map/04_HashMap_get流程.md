# Get

## 概述
HashMap的get流程是根据键的哈希值找到对应的索引位置，若存在冲突则通过链表或红黑树遍历查找，最后返回对应的值，若不存在则返回null。

## 详述

`HashMap` 的 `get` 方法用于根据给定的键从映射中检索其对应的值。基于 JDK 1.8 的实现，我们来深入分析一下这个方法的基本流程（注意：实现细节可能会因版本不同而有所变化）。

### `HashMap.get(Object key)` 方法流程：

1. **计算哈希值**：利用键的哈希码来定位其在桶数组中的位置。
   
2. **查找节点**：遍历在该位置上的链表或红黑树节点，找到与给定键匹配的节点。
   
3. **返回结果**：返回找到节点的值，如果没有找到则返回 null。

### 示例源码及注释：

```java
public V get(Object key) {
    Node<K,V> e;
    return (e = getNode(hash(key), key)) == null ? null : e.value;
}

final Node<K,V> getNode(int hash, Object key) {
    Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
    // Step 1: 如果哈希表不为空并且计算位置存在节点
    if ((tab = table) != null && (n = tab.length) > 0 &&
        (first = tab[(n - 1) & hash]) != null) {
        // Step 2: 如果第一个节点就是目标节点，则直接返回
        if (first.hash == hash && 
            ((k = first.key) == key || (key != null && key.equals(k))))
            return first;
        // Step 3: 如果第一个节点后还有其他节点
        if ((e = first.next) != null) {
            // Step 4: 如果该桶位使用红黑树存储，则调用树查找方法
            if (first instanceof TreeNode)
                return ((TreeNode<K,V>)first).getTreeNode(hash, key);
            // Step 5: 遍历链表查找节点
            do {
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    return e;
            } while ((e = e.next) != null);
        }
    }
    // Step 6: 如果未找到节点，则返回null
    return null;
}
```

**注解**：

- **Step 1**: 首先检查数组是否非空并且存在一个首节点。
  
- **Step 2**: 检查首节点的键是否与要查找的键匹配。如果匹配，直接返回这个节点。

- **Step 3**: 如果首节点后还有其他节点，则进一步处理。

- **Step 4**: 如果该桶使用红黑树来存储节点，则通过树的查找方法找到对应的节点。

- **Step 5**: 如果该桶使用链表来存储节点，则通过循环遍历链表来查找匹配的节点。

- **Step 6**: 如果在哈希表中找不到匹配的节点，那么方法返回 `null`。

上述步骤演示了 `HashMap` 在不同情况下（链表、红黑树）的节点查找逻辑，为高效的数据查找提供了基础。在 `HashMap` 的 `get` 方法中有效地使用了链表和树，确保了其在不同数量级的数据下都具有良好的性能。