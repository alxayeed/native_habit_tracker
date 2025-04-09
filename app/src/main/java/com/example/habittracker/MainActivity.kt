package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column // Import Column
import androidx.compose.foundation.layout.Spacer // Import Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height // Import height modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.HabitTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplaySupaBaseInfo(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun DisplaySupaBaseInfo(modifier: Modifier = Modifier) {
    val supabaseUrl = BuildConfig.SUPABASE_URL
    val supabaseAnonKey = BuildConfig.SUPABASE_ANON_KEY

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Supabase URL:"
        )
        Text(
            text = supabaseUrl
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Supabase Anon Key:"
        )
        Text(
            text = supabaseAnonKey
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "(Note: Displaying keys directly in the UI is generally not recommended for production apps.)"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DisplaySupaBaseInfoPreview() {
    HabitTrackerTheme {

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "URL:")
            Text(text = "preview-url")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Anon Key:")
            Text(text = "preview-key")
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "(Note: Displaying keys directly...)")
        }

    }
}