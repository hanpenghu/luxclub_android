## bugly集成说明

### 账号：
1. AppID：91e039abbd
2. App Key：8e2bf069-5b9c-429f-89f0-0128bbb18e9e

### 基本SDK使用

#### 采用自动集成方案，同时集成SDK和NDK，在Module的build.gradle文件中添加依赖和属性配置：
```
android {
    defaultConfig {
        ndk {
            // 设置支持的SO库架构
            abiFilters 'armeabi' //, 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
        }
    }
}

dependencies {
    compile 'com.tencent.bugly:crashreport:latest.release' //其中latest.release指代最新Bugly SDK版本号，也可以指定明确的版本号，例如2.1.9
    compile 'com.tencent.bugly:nativecrashreport:latest.release' //其中latest.release指代最新Bugly NDK版本号，也可以指定明确的版本号，例如3.0
}
```

#### 参数配置
1. 在AndroidManifest.xml中添加权限：
```
<uses-permission android:name="android.permission.READ_PHONE_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.READ_LOGS" />
```

2. 请避免混淆Bugly，在Proguard混淆文件中增加以下配置：
```
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
```

#### 最简单的初始化
获取APP ID并将以下代码复制到项目Application类onCreate()中，Bugly会为自动检测环境并完成配置：
```
CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false); 
```

第三个参数为SDK调试模式开关，调试模式的行为特性如下：
- 输出详细的Bugly SDK的Log；
- 每一条Crash都会被立即上报；
- 自定义日志将会在Logcat中输出。

建议在测试阶段建议设置成true，发布时设置为false。

#### 测试
```
CrashReport.testJavaCrash();
```

### 符号表配置
#### 什么是符号表？
符号表是内存地址与函数名、文件名、行号的映射表。符号表元素如下所示：

<起始地址> <结束地址> <函数> [<文件名:行号>]

#### 为什么要配置符号表？
为了能快速并准确地定位用户APP发生Crash的代码位置，Bugly使用符号表对APP发生Crash的程序堆栈进行解析和还原。

#### 在项目的buid.gradle文件的dependencies（buildscript部分）中添加：
```     
     classpath 'com.tencent.bugly:symtabfileuploader:latest.release'
```

#### 添加插件和配置
在module的buid.gradle文件的顶部添加：

     apply plugin: 'bugly'
     
     bugly {
         appId = '<APP_ID>' // 注册时分配的App ID
         appKey = '<APP_KEY>' // 注册时分配的App Key
     }
     
