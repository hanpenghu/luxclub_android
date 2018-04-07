# 指定proguard对你的代码进行迭代优化的次数
# 首先要明白optimization会对代码进行各种优化，每次优化后的代码还可以再次优化，所以就产生了 优化次数的问题，
# 这里面的 passes 应该翻译成 ‘次数’  而不是 ‘通道’。
# 默认写 5 ，应该是做Android的，关于Android里面为什么写 5 ，因为作者本来写 99 ，
# 但是每次迭代时间都很长团队成员天天抱怨，就改成 5 了，迭代会在最后一次无法优化的时候停止，也就是虽然你写着 99 ，
# 但是可能就优化了几次，一般情况下迭代10次左右的时候代码已经不能再次优化了。
# -optimizationpasses 5
# 不优化输入的类文件
-dontoptimize
# 不压缩输入的类文件
-dontshrink
# 不使用大小写混合的类名
-dontusemixedcaseclassnames
# 不忽略引入的jar包中的classes
-dontskipnonpubliclibraryclasses
# 不做预校验
-dontpreverify
# 记录日志
-verbose
# 优化参数
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# 以下指定类不混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
# java nio
-keep class java.nio.**{*;}
# okhttp
-keep public class com.squareup.okhttp.**{*;}
-dontwarn com.squareup.okhttp.**
# okio
-keep public class okio.**{*;}
-dontwarn okio.**

# 指定属性不混淆
-keepattributes Exceptions,InnerClasses,Signature
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable

# java类中，有native声明的JNI方法不混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# java类中，指定规则的方法不混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

# java类中，指定规则的方法不混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 枚举类中，指定规则的方法不混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 指定java类中，指定规则的方法不混淆
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 实现序列化接口的类中，所有public方法不混淆
-keep public class * implements java.io.Serializable {
    public *;
}
# 实现序列化接口的类中，所有指定规则的方法不混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 下面指定的类，去掉警告
-dontwarn android.webkit.WebView
-dontwarn android.support.**
# 2016-4-5 报错：Warning:there were xxx instances of library classes depending on program classes.
-keep class org.apache.http.** { *; }
-keep class android.net.http.** { *; }
-dontwarn org.apache.http.**
-dontwarn android.net.http.**
# google地图
-dontwarn com.google.android.maps.**
# fastjson
-dontwarn com.alibaba.fastjson.**

# 引入的jar中，指定规则的类或方法不混淆
# -libraryjars libs/android-support-v4.jar
# -dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

# -libraryjars libs/universal-image-loader-x.x.x.jar
-keep class com.nostra13.universalimageloader.** { *; }
-keep class com.nineoldandroids.** { *; }

# fastjson
-keep class com.alibaba.fastjson.** { *; }
-keep class * implements java.io.Serializable { *; }
-keepattributes *Annotation
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**

# fastjson相关，排除所有实体类， 防止json转模型出错
-keep class com.hsbank.luxclub.model.** { *; }
-keep class com.hsbank.luxclub.util.check_update.** { *; }
-keepclassmembers class com.hsbank.luxclub.model.** {
	private *;
}

-keep class com.hsbank.util.project.provider.** {
	private *;
}

-keep class com.hsbank.luxclub.util.JavaScriptWeb {
    public <fields>;
    public <methods>;
}

-keep class com.hsbank.luxclub.util.JavaScriptHsbank {
    public <fields>;
    public <methods>;
}

# -libraryjars libs/gson-v2.1.0.jar
-keep class com.google.gson.** { *; }
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }

# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# eventbus
-keepclassmembers class ** {
    public void onEvent*(***);
}
# Only required if you use AsyncExecutor  eventbus
-keepclassmembers class * extends de.greenrobot.event.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

# 信鸽推送
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep class com.tencent.android.tpush.**  {* ;}
-dontwarn com.tencent.android.tpush.**
-keep class com.tencent.mid.**  {* ;}

# 高德地图
# 3D 地图
-keep   class com.amap.api.mapcore.**{*;}
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.amap.mapcore.*{*;}
# 定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
# 搜索
-keep   class com.amap.api.services.**{*;}
# 2D地图
-keep class com.amap.api.maps2d.**{*;}
-keep class com.amap.api.mapcore2d.**{*;}
# 导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}

# retrofit
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

# RxJava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}