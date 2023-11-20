## TransactionExecutor→ActivityThread

``` java
public void handleMessage(Message msg) {
    switch (msg.what) {
         // ...
         case EXECUTE_TRANSACTION:// 执行事务
            final ClientTransaction transaction = (ClientTransaction) msg.obj;
            mTransactionExecutor.execute(transaction);
            // ...
            break;
         // ...
    }
}
```

#### TransactionExecutor.java
``` java
public void execute(ClientTransaction transaction) {
    // ...

    executeCallbacks(transaction);

    executeLifecycleState(transaction);
    // ...
}
```

``` java
public void executeCallbacks(ClientTransaction transaction) {
    // ...

    final int size = callbacks.size();
    for (int i = 0; i < size; ++i) {
        // ...
        
        // item的实现类是LaunchActivityItem
        item.execute(mTransactionHandler, token, mPendingActions);
        item.postExecute(mTransactionHandler, token, mPendingActions);
        // ...
    }
}
```
#### LaunchActivityItem.java
``` java
@Override
public void execute(ClientTransactionHandler client, IBinder token,
        PendingTransactionActions pendingActions) {
    ActivityClientRecord r = client.getLaunchingActivity(token);
    // 这里的cient就是ActivityThread
    client.handleLaunchActivity(r, pendingActions, null /* customIntent */);
}
```
#### ActivityThread.java
``` java
@Override
public Activity handleLaunchActivity(ActivityClientRecord r,
        PendingTransactionActions pendingActions, Intent customIntent) {
    
    // ...
        
    // 启动一个Activity
    final Activity a = performLaunchActivity(r, customIntent);

    if (a != null) {
       // ...
    } else {
        // // 启动失败，调用ATMS停止Activity启动
        ActivityClient.getInstance().finishActivity(r.token, Activity.RESULT_CANCELED,
                null /* resultData */, Activity.DONT_FINISH_TASK_WITH_ACTIVITY);
    }
    // 将Activity返回
    return a;
}
```