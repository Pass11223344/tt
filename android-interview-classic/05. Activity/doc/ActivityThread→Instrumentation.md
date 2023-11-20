## ActivityThread→Instrumentation

#### ActivityThread.java
``` java
// 启动Activity的核心实现
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
    //获取ActivityInfo类
    ActivityInfo aInfo = r.activityInfo;
    if (r.packageInfo == null) {
      //获取APK文件的描述类LoadedApk
      r.packageInfo = getPackageInfo(aInfo.applicationInfo, r.compatInfo,
              Context.CONTEXT_INCLUDE_CODE);
    }
    // 启动的Activity的ComponentName类
    ComponentName component = r.intent.getComponent();
    
    //创建要启动Activity的上下文环境
    ContextImpl appContext = createBaseContextForActivity(r);
    Activity activity = null;
    try {
      java.lang.ClassLoader cl = appContext.getClassLoader();
      //用类加载器来创建该Activity的实例
      activity = mInstrumentation.newActivity(
              cl, component.getClassName(), r.intent);
      // ...
    } catch (Exception e) {
      // ...
    }
    
    try {
      // 创建Application
      Application app = r.packageInfo.makeApplication(false, mInstrumentation);
    
      if (activity != null) {
          // 初始化Activity
          activity.attach(appContext, this, getInstrumentation(), r.token,
                  r.ident, app, r.intent, r.activityInfo, title, r.parent,
                  r.embeddedID, r.lastNonConfigurationInstances, config,
                  r.referrer, r.voiceInteractor, window, r.configCallback,
                  r.assistToken);
    
          ...
          // 调用Instrumentation的callActivityOnCreate方法来启动Activity
          if (r.isPersistable()) {
              mInstrumentation.callActivityOnCreate(activity, r.state, r.persistentState);
          } else {
              mInstrumentation.callActivityOnCreate(activity, r.state);
          }
        ...
      }
    ...
    
    } catch (SuperNotCalledException e) {
      throw e;
    
    } catch (Exception e) {
    ...
    }
    return activity;
}
```

#### Instrumentation.java
``` java
public void callActivityOnCreate(Activity activity, Bundle icicle) {
    prePerformCreate(activity);
    activity.performCreate(icicle);
    postPerformCreate(activity);
}
```

``` java
final void performCreate(Bundle icicle) {
    performCreate(icicle, null);
}
```

``` java
final void performCreate(Bundle icicle, PersistableBundle persistentState) {
    // ...
    
    if (persistentState != null) {
        onCreate(icicle, persistentState);
    } else {
        onCreate(icicle);
    }
    
    // ...
}
```