# 如何保证Service不被杀死

在 Android 系统中，由于资源管理的需要（如内存不足时），系统可能会杀死运行中的 `Service`。尽管我们不能保证 `Service` 永远不被系统杀死，但我们可以采取一些策略来提高其优先级，以及在其被杀死后进行重启。

### 1. 设置 Service 的优先级

在 Service 的 `onStartCommand()` 方法中返回 `START_STICKY` 或者 `START_REDELIVER_INTENT` 以增加 Service 的优先级。

- `START_STICKY`：Service 被杀后会被系统重新启动，但不保证 Intent 也一同被重传到 `onStartCommand()`。
- `START_REDELIVER_INTENT`：Service 被杀后会被系统重新启动，并且原始 Intent 会被重传到 `onStartCommand()`。

示例代码：
```java
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    //... 
    return START_STICKY;
}
```

### 2. 在 Service 中运行前台服务

通过 `startForeground()` 方法使 Service 变成一个前台 Service。前台服务会展示一个持续的用户通知，因此它不太可能被系统杀死。

示例代码：
```java
@Override
public int onStartCommand(Intent intent, int flags, int startId) {
    //...
    Notification notification = new Notification(R.drawable.icon, "Service running", System.currentTimeMillis());
    startForeground(NOTIFICATION_ID, notification);
    //...
}
```

### 3. 使用 AlarmManager

使用 `AlarmManager` 定期触发 Service 的启动，即便应用被杀死，通过设置的定时任务也能再次启动 Service。

示例代码：
```java
AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
Intent intent = new Intent(this, MyService.class);
PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pendingIntent);
```

### 4. 服务保活手法

利用系统进程间通信机制来复活服务，如通过 AIDL 在两个 Service 之间互相启动对方，当一个 Service 被杀时，另一个 Service 可以重新启动它。

示例代码：
在 Service A 和 Service B 间建立这样的机制：
```java
@Override
public void onDestroy() {
    super.onDestroy();
    // 重启本服务
    startService(new Intent(this, ServiceB.class));
}
```

### 5. 利用系统广播唤醒

监听系统广播（如网络变化、时间变化等），在广播接收器中启动 Service。

示例代码：
```java
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, MyService.class));
    }
}
```

注意：以上策略会增加应用的复杂性和资源消耗，因此在选择使用这些策略时应当谨慎评估，确保它们不会过度消耗系统资源或者降低用户体验。