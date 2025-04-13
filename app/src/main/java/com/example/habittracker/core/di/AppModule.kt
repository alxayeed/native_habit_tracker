package com.example.habittracker.core.di

import com.example.habittracker.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        val supabaseUrl = BuildConfig.SUPABASE_URL
        val supabaseAnonKey = BuildConfig.SUPABASE_ANON_KEY

        println("BuildConfig URL: '${BuildConfig.SUPABASE_URL}'")
        println("BuildConfig Key: '${BuildConfig.SUPABASE_ANON_KEY}'")

        if (supabaseUrl.isBlank() || supabaseAnonKey.isBlank()) {
            throw IllegalStateException(
                "Supabase URL or Anon Key is missing in BuildConfig. " +
                        "Ensure they are set in local.properties and Gradle is synced."
            )
        }

        println("Initializing Supabase with URL: $supabaseUrl") // Log for verification

        return createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseAnonKey
        ) {
            install(Auth) {
                // Configure Auth plugin if needed:
                // e.g., scheme = "your.app.scheme" // For deep linking OAuth redirects
                // e.g., host = "your.app.host"
            }
            install(Postgrest) {
                // Configure Postgrest plugin if needed:
                // e.g., defaultSchema = "public" (already default)
            }
            // Install other plugins like Realtime, Storage, Functions later if needed
            // install(Realtime)
            // install(Storage)
        }
    }

    // You can add other application-wide Singleton providers here later
    // (e.g., provideRoomDatabase, provideSharedPreferences)
}