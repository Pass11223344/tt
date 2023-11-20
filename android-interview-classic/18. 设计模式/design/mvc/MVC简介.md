# MVC简介

``` puml
skinparam backgroundColor #lightgrey 
skinparam shadowing false
(View)
(Controller)
(Model)
View -> Controller

Controller -> Model

Model --> View
```

View：XML布局文件。
> Android的XML文件视图功能太弱，使得Activity兼顾了不少View层的功能。

Model：实体模型（数据的获取、存储、数据状态变化）。

Controller：对应于Activity，处理数据、业务和UI。


