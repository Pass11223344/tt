# 扩容机制

## 概述

HashMap的初始容量是16，扩容机制是在元素数量达到当前容量的75%时，自动扩容为原容量的两倍，并重新计算元素的位置，以保持较低的哈希冲突和更好的性能。

## 详述

### HashMap的初始容量

在Java的`HashMap`中，初始容量（initial capacity）是HashMap底层数组的初始大小。当创建一个HashMap实例时，你可以指定初始容量。

```java
HashMap<Integer, String> map = new HashMap<>(32);
```

其中`32`是初始容量。如果不指定初始容量，HashMap会使用一个默认的初始容量，根据JDK的版本，这个默认值可能有所不同，在JDK 1.8中，默认的初始容量为`16`。

### HashMap的扩容机制

当HashMap中的元素数量达到某一个阈值时，HashMap会进行扩容。这个阈值与装载因子（load factor）有关。装载因子是一个浮点数，其定义为：“HashMap的容量乘以装载因子得到的一个值”表示HashMap在其容量自动增加之前可以达到多满的一种尺度。

装载因子的默认值为`0.75`，这意味着当HashMap中75%的位置被占据时，就会触发扩容操作。用数学表达式描述为：

\[ \text{阈值} = \text{当前容量} \times \text{装载因子} \]

#### 扩容过程详解：

1. **新容量计算**：当HashMap进行扩容时，新的容量通常是当前容量的两倍。

2. **重新哈希**：由于容量变化，原来存储在HashMap中的数据需要重新计算其在数组中的位置，并放到新的位置上，这个过程称为重新哈希（rehashing）。

3. **阈值调整**：扩容后，阈值也会相应地调整，通常情况下新的阈值是“新的容量”乘以“装载因子”。

#### 示例源码：

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        // 如果旧容量大于0，新容量通常是旧容量的两倍
        newCap = oldCap << 1;
        newThr = oldThr << 1; // 相当于: newThr = oldThr * 2;
    } 
    // 其他情况省略...
    // ...
    
    // 设置新阈值和新容量
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    
    // 如果旧表不为空，需要重新哈希
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // 链表结构
                    // 代码省略...
                    // ...
                }
            }
        }
    }
    return newTab;
}
```

### 为什么要扩容？

- **避免冲突**：通过扩容，HashMap可以将存储的数据分散到更多的桶中，从而减少哈希冲突，使得查找等操作的速度更快。

- **均匀分布**：保证数据在HashMap中的均匀分布，从而在查找时可以更加均匀地分布在所有桶中，避免某些桶的数据特别多造成的性能问题。

### 注意点

- **性能开销**：由于扩容涉及到复制数据和重新计算哈希的操作，是一项代价相对昂贵的操作。如果能预测到数据量，尽可能在初始化时就分配一个足够大的空间，以减少以后的扩容操作。

- **选择合适的装载因子**：装载因子太小会造成空间的浪费，而太大又会增加查找的成本，选择一个合适的装载因子可以平衡空间和时间的消耗。

理解`HashMap`的初始容量和扩容机制，可以帮助我们在使用它的时候做出更加合理的决策，从而在保证性能的前提下节省内存空间。