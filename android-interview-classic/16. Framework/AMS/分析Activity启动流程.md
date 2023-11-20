# startActivity源码剖析

![](../../component/activity/img/6eb827b8.png)

## 总线分析

启用一个Activity，可以理解为一个公司分部，向公司总部请求再建立一个分部的过程。

1. 公司分部，就是当前Activity所在的进程。
2. 公司总部就是AMS所在的SystemServer进程。

### 1. Activity→Instrumentation
1. startActivity()：启动Activity
2. execStartActivity()：通过IPC调用ATMS的startActivity。

[查看代码](../../component/activity/doc/startActivity→execStartActivity.md)

### 2. ActivityTaskManagerService→ActivityStarter
> ATMS：用于管理Activities和它们的容器(任务、显示)的系统服务。

> ActivityStarter：控制器，用于解释如何启动一个Activity。
> >这个类收集了所有的逻辑，用于决定如何将一个intent和falgs转换为一个Activity以及相关的任务和根任务。
1. startActivity()
2. execute()

[查看代码](../../component/activity/doc/ActivityTaskManagerService→ActivityStarter.md)

### 3. ActivityStarter→ActivityTaskSupervisor
1. execute()
2. startSpecificActivity()

[查看代码](../../component/activity/doc/execute→startSpecificActivity.md)


### 4. ActivityTaskSupervisor→ActivityThread
1. realStartActivityLocked() 
2. scheduleTransaction()

[查看代码](../../component/activity/doc/realStartActivityLocked.md)

### 5. TransactionExecutor→ActivityThread
> TransactionExecutor：以正确的顺序管理事务执行。
1. execute
2. handleLaunchActivity

[查看代码](../../component/activity/doc/TransactionExecutor→ActivityThread.md)

### 6. ActivityThread→Instrumentation
1. performLaunchActivity
2. callActivityOnCreate → performCreate

[查看代码](../../component/activity/doc/ActivityThread→Instrumentation.md)


<br>

***
[参考1](https://www.jianshu.com/p/cf300b69f325)




