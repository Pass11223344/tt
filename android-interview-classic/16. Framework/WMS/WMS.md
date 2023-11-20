## 图解Activity、Window和View的关系

``` puml
skinparam ActivityBackgroundColor #eeeeee
skinparam backgroundColor #lightgrey 
skinparam shadowing false
|#ff6666|ActivityThread\n|
:main;
:scheduleLaunchActivity;
:handleLaunchActivity;
:?;
|#purple|Instrumentation|
:?;
|#66ff66|Activity|
#orange:?;
note right
创建和绑定Window
end note
|#9999ff|WindowManagerImpl|
:初始化并设置给Window;
|Instrumentation|
:?;
|Activity|
#yellow:?;
#orange:?;
note right
解析XML布局文件
创建decorView
end note
|ActivityThread\n|
:handleResumeActivity;
:?;
|Activity|
:?;
|Instrumentation|
:?;
|Activity|
#yellow:onResume();
:mWindowAdded=true;
|#9999ff|WindowManagerImpl|
#orange:? ;
note right
绑定Window
和decorView
end note
group 扩展
|#a34f66|ViewRootImpl|
:初始化;
:setView;
|#7741aa|WindowSession|
:addToDisplay;
|#grey|WMS|
->IPC 远程代理;
:addWindow;
endgroup
```

``` puml
skinparam ActivityBackgroundColor #eeeeee
skinparam backgroundColor #lightgrey 
skinparam shadowing false
|#ff6666|ActivityThread\n|
:main;
:scheduleLaunchActivity;
:handleLaunchActivity;
:performLaunchActivity;
|#purple|Instrumentation|
:newActivity;
|#66ff66|Activity|
#orange:attach;
note right
创建和绑定Window
end note
|#9999ff|WindowManagerImpl|
:初始化并设置给Window;
|Instrumentation|
:callActivityOnCreate;
|Activity|
#yellow:onCreate;
#orange:setContentView;
note right
解析XML布局文件
创建decorView
end note
|ActivityThread\n|
:handleResumeActivity;
:perfromResumeActivity;
|Activity|
:performResume;
|Instrumentation|
:callActivityOnResume;
|Activity|
#yellow:onResume();
:mWindowAdded=true;
|#9999ff|WindowManagerImpl|
#orange:addView(decor, l) ;
note right
绑定Window
和decorView
end note
group 扩展
|#a34f66|ViewRootImpl|
:初始化;
:setView;
|#7741aa|WindowSession|
:addToDisplay;
|#grey|WMS|
->IPC 远程代理;
:addWindow;
endgroup
```

## Window的初始化
#### ActivityThread.java
``` java
private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent) {
    // ...
    Window window = null;
    // ...
    activity.attach(appContext, this, getInstrumentation(), r.token,
            ... window, ...);
    // ...
}
```
#### Activity.java
``` java
// 添加Winodw到Activity中
final void attach(...) {
    // ...
    
    // 初始化PhoneWindow
    mWindow = new PhoneWindow(this, window, activityConfigCallback);
    mWindow.setWindowControllerCallback(mWindowControllerCallback);
    mWindow.setCallback(this);
    mWindow.setOnWindowDismissedCallback(this);
    mWindow.getLayoutInflater().setPrivateFactory(this);
    if (info.softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
        mWindow.setSoftInputMode(info.softInputMode);
    }
    if (info.uiOptions != 0) {
        mWindow.setUiOptions(info.uiOptions);
    }
    
    // ...
    
    // 设置窗口管理器
    mWindow.setWindowManager(
            (WindowManager)context.getSystemService(Context.WINDOW_SERVICE),
            mToken, mComponent.flattenToString(),
            (info.flags & ActivityInfo.FLAG_HARDWARE_ACCELERATED) != 0);
    if (mParent != null) {
        mWindow.setContainer(mParent.getWindow());
    }
    mWindowManager = mWindow.getWindowManager();
    mCurrentConfig = config;

    mWindow.setColorMode(info.colorMode);
    mWindow.setPreferMinimalPostProcessing(
            (info.flags & ActivityInfo.FLAG_PREFER_MINIMAL_POST_PROCESSING) != 0);

    // ...
}
```