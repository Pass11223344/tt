# final、finally、finalize的区别

## 概述

1. final用于修饰类、方法或变量，表示不可继承、覆盖或修改；
2. finally是try-catch语句块的一部分，无论是否发生异常都会执行；
3. finalize是Object类的方法，在对象被垃圾回收前调用。

## 详述

在 Java 编程中，`final`、`finally` 和 `finalize` 是三个有着截然不同功能和用途的关键字/方法，尽管它们听起来非常相似。下面是它们的基本概念和区别：

### 1. `final`
- **用途**：`final` 是一个关键字，用于声明一个实体是不可变的。
- **应用于变量**：当 `final` 修饰一个变量时，该变量成为常量，它的值在初始化后就不能被更改。
  ```java
  final int x = 10;
  ```
- **应用于方法**：当 `final` 修饰一个方法时，该方法不能在子类中被重写（override）。
  ```java
  public final void myMethod() {}
  ```
- **应用于类**：当 `final` 修饰一个类时，该类不能被继承。
  ```java
  public final class MyFinalClass {}
  ```
  
### 2. `finally`
- **用途**：`finally` 是一个块，通常与 `try` 和 `catch` 一起使用，用于确保在 `try` 块中的代码执行后，无论是否发生异常，`finally` 块中的代码总会被执行。
- **示例**：
  ```java
  try {
      // 代码块，可能抛出异常
  } catch (Exception e) {
      // 异常处理代码块
  } finally {
      // 无论是否发生异常，这个代码块都会被执行
  }
  ```
- **用途**：常用于清理资源，比如关闭文件流、网络连接等。

### 3. `finalize`
- **用途**：`finalize` 是 `Object` 类的一个方法，它在垃圾收集器删除对象之前被调用，用于执行任何清理操作。
- **示例**：
  ```java
  @Override
  protected void finalize() throws Throwable {
      try {
          // 清理代码（释放资源等）
      } finally {
          super.finalize();
      }
  }
  ```
- **注意**：依赖 `finalize()` 方法来清理资源并不推荐，因为Java平台不保证它会被及时执行；如果需要管理内存和其他资源，最好使用 `try-with-resources` 语句和合适的关闭方法。

总结：
- `final` 是一个关键字，用于声明不可变的实体。
- `finally` 是一个块，用于保证某段代码总是被执行。
- `finalize` 是一个方法，用于在对象被垃圾收集前执行清理操作。

这三者在用途和用法上截然不同，因此在实际编程中使用时要明确目标，并在恰当的情境下使用它们。