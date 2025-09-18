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

# Hilt/Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class com.google.dagger.** { *; }
-keep class com.google.dagger.hilt.** { *; }
-keep class xyz.hanabinoir.githubuserslab.di.** { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltComponents { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltModules { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltInject { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPoint { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltWrapper { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltBinding { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltFactory { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltViewModel { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltModule { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltComponent { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltSubcomponent { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltScope { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltQualifier { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltAnnotation { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltGenerated { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPoint { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointModule { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointComponent { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointSubcomponent { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointScope { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointQualifier { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointAnnotation { *; }
-keep class xyz.hanabinoir.githubuserslab.**_HiltEntryPointGenerated { *; }

# Retrofit & OkHttp
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class kotlinx.serialization.** { *; }

# Coil
-keep class coil.** { *; }

# FontAwesome Compose
-keep class com.guru.fontawesomecomposelib.** { *; }

# kotlinx.serialization
-keep class kotlinx.serialization.** { *; }

# AndroidX Compose
-keep class androidx.compose.** { *; }