
``` java
void startSpecificActivity(ActivityRecord r, boolean andResume, boolean checkConfig) {
    // 此Activity的进程是否已经运行
    final WindowProcessController wpc =
            mService.getProcessController(r.processName, r.info.applicationInfo.uid);

    boolean knownToBeDead = false;
    if (wpc != null && wpc.hasThread()) { // 进程已存在
        try {
            // 真实启动Activity
            realStartActivityLocked(r, wpc, andResume, checkConfig);
            // 注意这里会return掉
            return;
        } catch (RemoteException e) {
            Slog.w(TAG, "Exception when starting activity "
                    + r.intent.getComponent().flattenToShortString(), e);
        }

        // If a dead object exception was thrown -- fall through to
        // restart the application.
        knownToBeDead = true;
    }

    r.notifyUnknownVisibilityLaunchedForKeyguardTransition();

    final boolean isTop = andResume && r.isTopRunningActivity();
    // 异步开启进程来启动Activity，最终还是调用到realStartActivityLocked
    mService.startProcessAsync(r, knownToBeDead, isTop, isTop ? "top-activity" : "activity");
}
```

[查看异步开启进程](异步开启进程.md)

``` java
// 启动Activity的进程存在，则执行此方法 
  boolean realStartActivityLocked(ActivityRecord r, WindowProcessController proc,
                                  boolean andResume, boolean checkConfig) throws RemoteException {

      // ...

      // 调度事务
      // 内部调用的是IApplicationThread的scheduleTransaction方法
      // 又回到了原先的进程
      mService.getLifecycleManager().scheduleTransaction(clientTransaction);

      // ...
      return true;
  }

}
```

IApplicationThread的实现类是ApplicationThread。

也就是IApplicationThread是AMS向ActivityThread发起通信的桥梁。

#### ApplicationThread.java
``` java
@Override
public void scheduleTransaction(ClientTransaction transaction) throws RemoteException {
    ActivityThread.this.scheduleTransaction(transaction);
}
```
