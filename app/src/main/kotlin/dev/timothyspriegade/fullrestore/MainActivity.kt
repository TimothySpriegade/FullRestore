package dev.timothyspriegade.fullrestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.timothyspriegade.fullrestore.core.designsystem.FullRestoreTheme
import dev.timothyspriegade.fullrestore.features.typequiz.ui.TypeQuizRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullRestoreTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TypeQuizRoute()
                }
            }
        }
    }
}
