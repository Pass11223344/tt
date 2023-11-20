# Put

## 概述

HashMap的put流程是根据键的哈希值计算索引位置，如果该位置为空则直接插入，若存在冲突则处理冲突，最后更新或插入键值对。

## 详述

`HashMap` 的 `put` 方法用于将一个键值对插入映射。以下是 `put` 方法流程的简化分析，基于 JDK 1.8 的实现（请注意，具体的实现可能会因 JDK 版本的不同而有所差异）：

### `HashMap.put(K key, V value)` 方法流程：

1. **计算键的哈希值**：首先计算键的哈希值，用于确定该键值对在数组中的存储位置。

2. **处理哈希冲突**：如果计算出的位置已经被其他键值对占用（发生了哈希冲突），则会根据存储在同一位置的键是否与新键相等（通过 `equals()` 方法判断）或是否为 `null` 来采取不同的操作。

3. **链表与树的转换**：如果链表长度超过阈值（默认为 8），且数组的容量大于 64，则将链表转换为红黑树。反之，在某些条件下，红黑树会退化为链表。

4. **插入或更新键值对**：将新的键值对插入到数组的计算位置。如果该位置已有一个键值对存在并且键相等，则新的值将覆盖旧值。

5. **扩容检查**：如果 `size > threshold`（实际元素数量大于阈值），则对 `HashMap` 进行扩容，并将现有的键值对重新哈希到新的桶数组中。

### 示例源码及注释：

```java
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    // Step 1: 如果哈希表为空，则初始化
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    // Step 2: 如果计算位置为空，则直接创建一个新节点放入
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        // Step 3: 如果节点键与传入键完全匹配（哈希值和实际对象都相同），则进行值替换
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        // Step 4: 如果该桶位形成了红黑树，则按树的方式加入节点
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        // Step 5: 如果该桶位是链表，则遍历链表
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    // Step 6: 如果链表长度超过阈值（TREEIFY_THRESHOLD， 默认为8）则转换为红黑树
                    if (binCount >= TREEIFY_THRESHOLD - 1) 
                        treeifyBin(tab, hash);
                    break;
                }
                // 如果链表中的节点键与传入键匹配，则跳出循环，进行值替换
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        // Step 7: 如果e不为空，替换值
        if (e != null) { 
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    // 结构修改计数器
    ++modCount;
    // Step 8: 检查是否需要扩容
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

**注意**：以上代码进行了简化，并添加了注释说明。实际 `HashMap` 源码包含更多的优化和处理逻辑。如果你对更多的细节感兴趣，可以直接查看 JDK 源码获取详细信息。