
``` java
apply plugin: 'com.android.application'

static def buildTime() {
    return new Date().format("yyyyMMdd");
}

android {
    signingConfigs {
        release {
            keyAlias 'guiying712'
            keyPassword 'guiying712'
            storeFile file('/mykey.jks')
            storePassword 'guiying712'
        }
    }
    compileSdkVersion rootProject.ext.compileSdkVersion
    buildToolsVersion rootProject.ext.buildToolsVersion
    defaultConfig {
        applicationId "com.guiying.androidmodulepattern"
        minSdkVersion rootProject.ext.minSdkVersion
        targetSdkVersion rootProject.ext.targetSdkVersion
        versionCode rootProject.ext.versionCode
        versionName rootProject.ext.versionName
        multiDexEnabled false
        //打包时间
        resValue "string", "build_time", buildTime()
    }
    buildTypes {
        release {
            //更改AndroidManifest.xml中预先定义好占位符信息
            //manifestPlaceholders = [app_icon:
            "@drawable/icon"]
            // 不显示Log
            buildConfigField "boolean", "LEO_DEBUG", "false"
            //是否zip对齐
            zipAlignEnabled true
            // 缩减resource文件
            shrinkResources true
            //Proguard
            minifyEnabled true
            proguardFiles
            getDefaultProguardFile('proguard-android.txt'),
            'proguard-rules.pro'
            //签名
            signingConfig signingConfigs.release
        }
        debug {
            //给applicationId添加后缀“.debug”
            applicationIdSuffix ".debug"
            //manifestPlaceholders = [app_icon:
            "@drawable/launch_beta"]
            buildConfigField "boolean", "LOG_DEBUG",
            "true"
            zipAlignEnabled false
            shrinkResources false
            minifyEnabled false
            debuggable true
        }
    }
    
    dependencies {
        compile fileTree(dir: 'libs', include: ['*.jar'])
        annotationProcessor "com.github.mzule.activityrouter:compiler:$rootProject.annotationProcessor"
        if (isModule.toBoolean()) {
            compile project(':lib_common')
        } else {
            compile project(':module_main')
            compile project(':module_girls')
            compile project(':module_news')
        }
    }
}
```