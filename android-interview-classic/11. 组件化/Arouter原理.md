# Arouter原理

ARouter通过Apt技术，生成保存路径(路由path)和被注解(@Router)的组件类的映射关系的类，

利用映射类，Arouter根据用户的请求postcard（明信片）寻找到要跳转的目标地址(class),使用Intent跳转。

## 什么是APT

Annotation Processing Tool，注解处理器。

在编译期间可以处理注解然后做处理，也可以在编译时生成一些文件之类的。

> ButterKnife和EventBus都使用了APT技术

## 简单使用

声明类似下面@Route注解，我们称之为路由地址。

``` java
@Route(path = "/main/homepage")
public class HomeActivity extends BaseActivity {
		
}
```

在需要跳转的时候调用

``` java
Arouter.getInstance().build("main/hello").navigation;
```

### 背后

通过APT技术，找到所有带注解@Router的组件，将其注解值path和对应的Activity保存到一个map里

``` java
class RouterMap {
    public Map getAllRoutes {
        Map map = new HashMap<String,Class<?>>;
        map.put("/main/homepage",HomeActivity.class);
        map.put("/main/setting",SettingActivity.class);
        map.put("/login/register",LoginRegisterActivity.class);
        ....
              
        return map;
    }
}
```

然后在工程代码中将这个map加载到内存中，需要的时候直接get(path)就可以了。

## 路由文件、跳转原理
