# 图解View的绘制流程
``` puml
skinparam ActivityBackgroundColor #eeeeee
skinparam backgroundColor #lightgrey 
skinparam shadowing false
|#ff6666|View|
:invlidate;
:InvalidateInternal;
|#66ff66|ViewGroup|
:invalidateChild;
:invalidateChildInParent;
->递归调用至;
|#7777ee|ViewRootImpl|
:invalidateChildInParent;
->计算出需要重绘的区域;
:scheduleTraversals;
:performTraversals;
:performDraw;
|View|
:draw;
:onDraw;
```
``` puml
skinparam ActivityBackgroundColor #eeeeee
skinparam backgroundColor #lightgrey 
skinparam shadowing false
class View
class ViewGroup
interface ViewParent
class ViewRootImpl

View <|-- ViewGroup
ViewParent <|.. ViewGroup 
ViewParent <|.. ViewRootImpl
```

