# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontwarn okio.**
-dontwarn com.squareup.wire.**
-dontwarn com.umeng.update.**
-dontwarn android.support.v4.**
-keep class okio.** {*;}
-keep class com.squareup.wire.** {*;}
-keep class android.support.v4.** { *; }
-keep interface android.support.v4.app.** { *; }
-keepattributes *Annotation*

-keep class com.umeng.update.protobuffer.** {
        public <fields>;
        public <methods>;
}

-keep public class com.weile.zb.R$*{
    public static final int *;
}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

