# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/yboyar/android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and orderDetailItem by changing the proguardFiles
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

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn

-keepattributes Signature
-keepattributes SetJavaScriptEnabled
-keepattributes JavascriptInterface
-keepattributes InlinedApi
-keepattributes SourceFile,LineNumberTable
-keepattributes *Annotation*
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes Exceptions

-keep class * implements java.io.Serializable

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# -dontwarn android.support.v4.**
-dontwarn android.support.v4.**
-dontskipnonpubliclibraryclassmembers
-dontskipnonpubliclibraryclasses

-keep class com.google.** { *; }
-keep class android.view.View.**


-keep class **.R
-keep class **.R$* {
    <fields>;
}

-dontwarn okio.**
-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *; }
-keep interface com.squareup.okhttp3.* { *; }
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
-dontwarn javax.annotation.**
-dontnote retrofit2.Platform
-dontwarn retrofit2.Platform$Java8

-dontwarn javax.inject.**
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-dontwarn com.googlecode.mp4parser.**

-keep public class * extends java.lang.annotation.** { *; }
-keep class com.google.errorprone.annotations.** { *; }
-dontwarn com.google.errorprone.annotations.**


