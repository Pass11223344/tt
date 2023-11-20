# Parcelable

## 概述
Parcelable是Android中的接口，用于实现高效的对象序列化和传输。

相较于Serializable，Parcelable更轻量且性能更高。通过实现Parcelable接口，对象可以被拆解成可传输的数据块，传输过程更高效。

Parcelable需要实现描述对象的方法和从Parcel中读取对象的方法。它适用于Android内部的进程间通信和数据传递，能够提供更好的性能和效率。

## 详述
`Parcelable` 是 Android 平台中的一种对象序列化的机制，通常在需要我们将对象通过 `Intent` 传递给其他 `Activity` 或者在进程间传递数据时使用。与 Java 中的 `Serializable` 接口不同，`Parcelable` 是 Android 特有的，且在 Android 平台上效率比 `Serializable` 更高。`Parcelable` 的设计初衷是为了在进程间（IPC，Inter-Process Communication）传递数据时更加高效。

### 如何使用 Parcelable

要使用 `Parcelable`，我们需要：
- 让数据类实现 `Parcelable` 接口。
- 实现 `writeToParcel(Parcel dest, int flags)` 方法，用于将对象的数据写入 `Parcel` 对象。
- 实现 `Parcelable.Creator<T>`，用于从 `Parcel` 对象创建原始对象。

下面是一个简单的例子：

```java
import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private int age;

    // Standard basic constructor for non-parcel
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Constructor to use when re-constructing object from a parcel
    public User(Parcel in) {
        this.name = in.readString();
        this.age = in.readInt();
    }

    // Serialize object
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    // Creator
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

        // Deserialize object
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    
    // Getter and setter methods
    // ...
}
```

### 使用 Parcelable 对象

**传递对象:**

```java
Intent intent = new Intent(this, TargetActivity.class);
User user = new User("Alice", 30);
intent.putExtra("user_data", user);
startActivity(intent);
```

**在目标 Activity 中读取对象:**

```java
Intent intent = getIntent();
User user = intent.getParcelableExtra("user_data");
```

### 注意点

- 由于 `Parcelable` 是为临时存储而设计的，不建议用它将数据持久化存储在磁盘上。
- `Parcelable` 通常在性能上优于 `Serializable`，因为它不使用反射来创建对象实例和序列化过程。
- 尽管 `Parcelable` 比 `Serializable` 更加复杂一些，但其在 IPC 通信上提供了更好的性能和更大的灵活性。

如果数据对象很简单，并且不需要进行 IPC 传递，你也可以考虑其他的序列化方案，例如 JSON 或者其他的序列化库（如 Protocol Buffers）来实现对象的序列化和反序列化，以适应不同的使用场景。

## 补充

### android.os.Parcel

Parcel，意为包裹、小包

Parcel提供了一套机制，可以将序列化之后的数据写入到一个共享内存中，其他进程通过Parcel可以从这块共享内存中读出字节流，并反序列化成对象。

### 基本使用
``` java
public class ParcelObj implements Parcelable {
    private String strField;
    private int intField;
 
    // 需要提供一个接收 Parcel类型参数的构造函数，执行反序列化
    protected ParcelObj(Parcel in) {
        // 反序列化参数的顺序必须和序列化时保持一致
        this.strField = in.readString();
        this.intField = in.readInt();
    }
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    // 将一个对象转换成 Parcel对象，进行序列化
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 序列化参数的顺序必须和反序列化时保持一致
        dest.writeString(this.strField);
        dest.writeInt(this.intField);
    }
 
    // 实现类必须有一个CREATOR属性，用于反序列化，将Parcel对象转换为 Pacelable
    public static final Parcelable.Creator<ParcelObj> CREATOR = new             
                Parcelable.Creator<ParcelObj>() {
        // 反序列化的方法，将Parcel还原成Java对象
        @Override
        public ParcelObj createFromParcel(Parcel parcel) {
            return new ParcelObj(parcel);
        }
 
        @Override
        public ParcelObj[] newArray(int size) {
            return new ParcelObj[0];
        }
    };
}
```

