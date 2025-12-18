# Keep Compose classes
-keep class androidx.compose.** { *; }
-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }

# Keep Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.github.ericomonteiro.**$$serializer { *; }
-keepclassmembers class com.github.ericomonteiro.** {
    *** Companion;
}
-keepclasseswithmembers class com.github.ericomonteiro.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**
-dontwarn kotlinx.coroutines.internal.**

# Keep JNA
-keep class com.sun.jna.** { *; }
-keep class * implements com.sun.jna.** { *; }

# Keep JNativeHook
-keep class com.github.kwhat.jnativehook.** { *; }

# Keep SQLDelight
-keep class app.cash.sqldelight.** { *; }

# Keep Koin
-keep class org.koin.** { *; }

# Keep main class
-keep class com.github.ericomonteiro.pirateparrotai.MainKt { *; }

# General rules
-dontwarn javax.**
-dontwarn java.awt.**
-dontwarn sun.**
-dontwarn org.slf4j.**
