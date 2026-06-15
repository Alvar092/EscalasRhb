# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

# --- Gson ---
# Gson uses generic type information stored in the class file when working with
# fields and TypeToken. R8 strips this by default, causing
# "TypeToken must be created with a type argument" crashes at runtime.
-keepattributes Signature
-keepattributes *Annotation*

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

# Keep fields of the domain models (de)serialized via Gson so JSON stored in Room
# stays compatible across obfuscated builds.
-keep class com.aentrena.escalasrhb.domain.model.scales.** { <fields>; }