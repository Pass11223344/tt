# 静态内部类/非静态内部类

## 概述

静态内部类不持有外部类的引用，非静态内部类持有；

静态内部类对象创建不依赖外部类实例，非静态内部类对象创建依赖外部类实例。

## 详述

在Java中，静态内部类和非静态内部类存在显著的差异，涵盖它们的实例化方式、访问规则等方面。下面是对两者的详细区别的描述：

### 1. 静态内部类（Static Inner Class）
- **独立性**：静态内部类是独立于外部类的，它不持有外部类的引用。
- **访问**：静态内部类只能访问外部类的静态成员和方法，不能访问非静态的。
- **对象实例化**：不需要依赖外部类的实例就可以创建静态内部类的实例。
- **用途**：通常用作外部类的静态成员，提供一种更有组织的代码结构。

```java
public class OuterClass {
    static class StaticInnerClass {
        // ...
    }
}
// 实例化静态内部类
OuterClass.StaticInnerClass staticInnerInstance = new OuterClass.StaticInnerClass();
```

### 2. 非静态内部类（Non-Static Inner Class，也称为成员内部类）
- **关联性**：非静态内部类持有外部类的实例的引用。
- **访问**：非静态内部类可以访问外部类的所有成员（包括私有和非静态的）。
- **对象实例化**：需要依赖外部类的实例来创建非静态内部类的实例。
- **用途**：通常用于和外部类实例关联更为紧密的场景。

```java
public class OuterClass {
    class NonStaticInnerClass {
        // ...
    }
}
// 实例化非静态内部类
OuterClass outerInstance = new OuterClass();
OuterClass.NonStaticInnerClass nonStaticInnerInstance = outerInstance.new NonStaticInnerClass();
```

### 对比总结
- **实例化方式**：静态内部类的实例化不依赖外部类的实例，而非静态内部类的实例化需要依赖外部类的实例。
- **访问外部类成员**：静态内部类无法直接访问外部类的非静态成员，非静态内部类可以访问所有外部类成员。
- **内存占用**：由于非静态内部类持有外部类的引用，可能存在更多的内存占用。
- **用途和设计**：静态内部类通常用于提供一些与外部类关系不是很紧密的功能模块，而非静态内部类通常与外部类的实例有较强的关联。

在设计内部类时，应根据其用途和需求来选择使用静态内部类还是非静态内部类。