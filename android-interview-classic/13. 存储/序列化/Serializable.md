# Serializable

## 概述

Serializable是Java中的接口，用于实现对象的序列化和反序列化。

通过实现Serializable接口，可以将对象转换为字节序列，以便在网络传输或持久化存储中使用。

Serializable提供了一个标记接口，表示对象可以被序列化。被标记为Serializable的类的对象可以通过流的形式进行读写，实现对象的保存和恢复。

## 详述

`Serializable` 是 Java 中的一个接口，用于标记一个类的对象可被序列化。序列化是一个过程，它将对象的状态信息转换为可存储或传输的形式，例如转化为字节流。在 Java 中，通过实现 `Serializable` 接口的类，对象可以被写入到输出流（OutputStream），之后可以从输入流（InputStream）中再次读取出来（反序列化）。

### 使用 `Serializable`

使用 `Serializable` 非常简单，只需要让你的类实现 `Serializable` 接口即可。这个接口是一个标记接口（Marker Interface），没有方法需要你去实现。

示例：
```java
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int age;
    
    // 省略构造方法、getter 和 setter
}
```

### 序列化过程

在对象进行序列化和反序列化过程中，你可以使用 `ObjectOutputStream` 和 `ObjectInputStream`。

**序列化：**
```java
try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.ser"))) {
    User user = new User("Alice", 30);
    oos.writeObject(user);
} catch (IOException e) {
    e.printStackTrace();
}
```

**反序列化：**
```java
try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.ser"))) {
    User user = (User) ois.readObject();
    System.out.println("Name: " + user.getName() + ", Age: " + user.getAge());
} catch (IOException | ClassNotFoundException e) {
    e.printStackTrace();
}
```

### 需要注意的事项

- 所有要序列化的对象的类必须实现 `Serializable` 接口。
- 当你改变一个已序列化类的定义时，添加一个 `serialVersionUID` 字段可以帮助保持版本的兼容性。
- 并不是所有的对象都可以序列化，对于不支持序列化的对象，可以使用 `transient` 关键字声明，这样在对象序列化过程中它的状态就不会被序列化。
- 静态变量属于类的状态，因此它们不会参与到对象的序列化过程中。
- 对于复杂的对象图，`Serializable` 可能不是最佳的解决方案，特别是当你需要高效率或深定制化的序列化过程时。

最后，虽然 `Serializable` 使用起来很简单，但在某些场景下，例如在 Android 开发中，使用它可能并不是最佳选择，由于其性能不是最优，并且易于引入 bug。在 Android 开发中，一般建议使用 `Parcelable` 接口来实现对象的序列化。

## 思考

1. 序列化前和序列化后是同一个对象吗？
    > 答：不是。深拷贝的关系，调用equals还是为true。
   
2. 为什么用Bundle而不用HashMap作为传输数据的载体？
    > 1. Bundle的内部实现是ArrayMap，适合存储小容量的键值对。
   > 2. Bundle实现了Parcelable接口，在内存中传输效率更高。
   
3. SerialVersionID的作用是什么？
    > SerialVersionID意为序列版本ID，防止之后添加了新字段反序列化失败。
   
