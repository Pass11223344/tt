## startActivity→execStartActivity

#### Activity.java
``` java
@Override
public void startActivity(Intent intent) {
    this.startActivity(intent, null);
}
```

``` java
@Override
public void startActivity(Intent intent, @Nullable Bundle options) {
    // ...
    if (options != null) {
        startActivityForResult(intent, -1, options);
    } else {
        startActivityForResult(intent, -1);
    }
}
```

``` java
public void startActivityForResult(...) {
    // ...
    Instrumentation.ActivityResult ar = mInstrumentation.execStartActivity(...);
    // ...
}
```

#### Instrumentation.java

``` java
public ActivityResult execStartActivity(...) {
    // ...
    int result = ActivityTaskManager.getService().startActivity(...);
    // ...
    return null;
}
```