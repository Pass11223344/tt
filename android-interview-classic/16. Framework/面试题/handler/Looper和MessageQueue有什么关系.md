# Looper和MessageQueue有什么关系

MessageQueue是在Looper构造方法中创建（包含关系）。

每个线程的Looper是唯一的，因此MessageQueue也是唯一的，即一对一关系。