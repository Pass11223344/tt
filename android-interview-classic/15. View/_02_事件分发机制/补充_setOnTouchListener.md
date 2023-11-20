# setOnTouchListener


## onTouch的返回值


<p style="font-size:20px;font-weight:bold;">🤔：setOnTouchListener中的onTouch方法的返回值返回true和false有什么区别？</p>

在 Android 中，当你为一个 `View` 设置了 `OnTouchListener` 并实现了 `onTouch()` 方法，这个 `onTouch()` 方法的返回值（`true` 或 `false`）会影响事件的后续传递和处理。具体来说：

### 返回 `true`：

- 如果 `onTouch()` 方法返回 `true`，表示当前的触摸事件被这个 `View` 消费了。
- 后续的触摸事件（如 `ACTION_MOVE` 和 `ACTION_UP`）将会继续传递给这个 `View` 的 `onTouch()` 方法进行处理。
- `View` 的 `onTouchEvent()` 方法将不会被调用。

### 返回 `false`：

- 如果 `onTouch()` 方法返回 `false`，表示当前的触摸事件没有被这个 `View` 完全消费。
- 当前的触摸事件（例如 `ACTION_DOWN`）将会被传递给这个 `View` 的 `onTouchEvent()` 方法进一步处理。
- 如果当前 `View` 也没有处理这个事件（即 `onTouchEvent()` 也返回 `false`），事件将会向上层的 `View`（或 `ViewGroup`）传递，并经过相同的处理流程。

### 示例说明：

```java
view.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "OnTouch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "OnTouch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "OnTouch ACTION_UP");
                break;
        }
        return false;
    }
});

view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d(TAG, "View clicked");
    }
});
```

在这个示例中：

- 如果 `onTouch()` 返回 `false`，在触摸事件为 `ACTION_DOWN` 的情况下，如果其他条件允许，`OnClickListener` 的 `onClick` 方法将会被触发。
- 如果 `onTouch()` 返回 `true`，`OnClickListener` 的 `onClick` 方法永远不会被触发，因为触摸事件在 `onTouch()` 方法中就已经被消费了。

根据具体的需求和使用场景，可以灵活地设置 `onTouch()` 方法的返回值，来实现期望的事件处理效果。

## 源码分析

在 Android 中, 触摸事件的传递和处理主要由 `View` 类中的几个关键方法控制，主要包括 `dispatchTouchEvent()`, `onTouchEvent()` 和通过 `OnTouchListener` 接口实现的 `onTouch()` 方法。以下的分析基于 Android 源码，我将简要解析 `View` 类中这些方法是如何影响事件分发和处理的。

### `dispatchTouchEvent(MotionEvent event)` 方法：

这个方法是触摸事件传递的入口。在 `dispatchTouchEvent` 方法中，首先会检查是否有 `OnTouchListener` 设置并且当前 `View` 是 enable 的。如果满足条件，`onTouch()` 方法会被调用，并根据 `onTouch()` 的返回值决定是否继续传递事件。

```java
// View.java (部分源码，进行了简化和注释)
public boolean dispatchTouchEvent(MotionEvent event) {
    // mListener 是在 setOnTouchListener 时设置的
    if (mListener != null && isEnabled() && mListener.onTouch(this, event)) {
        return true;
    }
    // 如果 onTouch 返回 false，继续处理 onTouchEvent
    return onTouchEvent(event);
}
```

从这段代码我们可以看到：

- 当 `onTouch()` 返回 `true` 时，`dispatchTouchEvent()` 方法返回 `true`，表示事件被消费，不再进行后续的传递和处理。
- 当 `onTouch()` 返回 `false` 时，事件传递给 `onTouchEvent(event)` 方法进行进一步处理。

### `onTouchEvent(MotionEvent event)` 方法：

如果 `onTouch()` 方法返回 `false` 或者没有设置 `OnTouchListener`，则 `onTouchEvent()` 方法会被调用。

```java
// View.java (部分源码，进行了简化和注释)
public boolean onTouchEvent(MotionEvent event) {
    final float x = event.getX();
    final float y = event.getY();

    switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
            // 此处处理 ACTION_UP 事件，例如进行点击事件的判断和触发
            if (mPrivateFlags & PFLAG_PRESSED) {
                final boolean focusTaken = !isSoundEffectsEnabled()
                        || (isHapticFeedbackEnabled() && (
                                (mViewFlags & FOCUSABLE_IN_TOUCH_MODE) == FOCUSABLE_IN_TOUCH_MODE));
                if (!focusTaken) {
                    // 执行点击事件
                    performClick();
                }
            }
            break;
    }
    // ... 其他的处理代码
}
```

在 `onTouchEvent()` 方法中：

- 当触摸事件为 `ACTION_UP` 时（即手指离开屏幕），若之前的状态是被按下的状态，将会进行点击事件的判断和处理，例如触发 `performClick()` 方法。

### 综合分析：

- **当 `onTouch()` 返回 `true`**：事件在 `onTouch()` 方法中被消费，后续的触摸事件（如 `ACTION_MOVE`, `ACTION_UP`）将继续传递给 `onTouch()` 方法，而 `onTouchEvent()` 方法不会被调用，从而点击事件的处理逻辑不会被触发。
  
- **当 `onTouch()` 返回 `false`**：事件会传递到 `onTouchEvent()` 方法，由 `onTouchEvent()` 方法根据事件的类型进行进一步的处理，例如触发点击事件等。

这就是为什么 `onTouch()` 方法的返回值会影响事件处理和点击事件触发的基本原理。希望这段分析对你有所帮助！