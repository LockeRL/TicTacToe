# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in getDefaultProguardFile('proguard-android-optimize.txt').


# Room
-keep class androidx.room.** { *; }
-keep @androidx.room.Database class * { *; }
-keep @androidx.room.Entity class * { *; }
-keep @androidx.room.Dao interface * { *; }
-keep class * extends androidx.room.RoomDatabase { *; }
-keep class *_Impl { *; }

# Koin
-keep class org.koin.** { *; }

# Kotlin
-keep class kotlin.Metadata { *; }

-keepattributes *Annotation*
