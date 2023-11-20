# 什么是AIDL

## 定义

1. 英文全称：<font color=#dea32c>**Android Interface Definition Language**</font>；
2. 定义服务端和客户端通信接口的一种描述语言；
3. 可以拿来生成用于IPC的代码；
4. AIDL文件以 .aidl 为后缀名
5. 方法传参支持的数据类型
    1. 基本数据类型（short除外）
    2. String，CharSequence
    3. List，Map
    4. Parcelable
   
## 基础使用

### IMyAidlInterface.aidl
``` java
interface IMyAidlInterface {
    int add(int value1, int value2);
}
```
### MyAidlService.java
在onBind方法中返回iBinder
``` java
// 服务端与客户端通信的工具就是Stub

// Stub继承自android.os.Binder
// 里面定义了外部接口IMyAidlInterface中定义的add方法
private IBinder iBinder = new IMyAidlInterface.Stub(){
     @Override
     public int add(int value1, int value2) throws RemoteException {
         return value1 + value2;
     }
 };
```
### MainActivity.java
传入bindService(intent, conn, Context.BIND_AUTO_CREATE);
``` java
// 客户端要远程调用服务端的方法，都是先获取一个服务端的代理对象，再调方法
private ServiceConnection conn = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        // asInterface会返回IMyAidlInterface的实现类Proxy。
        // 也就是aidl变量实际类型是IMyAidlInterface.Stub.Proxy
        IMyAidlInterface aidl = IMyAidlInterface.Stub.asInterface(service);
        // 调用方法
        aidl.add(1, 2)
    }
    // ...
};
```

### IMyAidlInterface.Stub.Proxy

``` java
@Override 
public int add(int a, int b) throws android.os.RemoteException
{
  // 从同步池中取出一个parcel对象data
  android.os.Parcel _data = android.os.Parcel.obtain();
  // 从同步池中取出一个parcel对象reply
  android.os.Parcel _reply = android.os.Parcel.obtain();
  int _result;
  try {
    _data.writeInterfaceToken(DESCRIPTOR);
    _data.writeInt(a);
    _data.writeInt(b);
    // 传输数据
    boolean _status = mRemote.transact(Stub.TRANSACTION_add, _data, _reply, 0);
    if (!_status && getDefaultImpl() != null) {
      return getDefaultImpl().add(a, b);
    }
    _reply.readException();
    _result = _reply.readInt();
  }
  finally {
    _reply.recycle();
    _data.recycle();
  }
  return _result;
}
```

