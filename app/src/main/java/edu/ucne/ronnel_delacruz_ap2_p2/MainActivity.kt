package edu.ucne.ronnel_delacruz_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.ronnel_delacruz_ap2_p2.presentation.navigation.MainNavigationDisplay
import edu.ucne.ronnel_delacruz_ap2_p2.ui.theme.Ronnel_DeLaCruz_AP2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ronnel_DeLaCruz_AP2_P2Theme {
                MainNavigationDisplay()
            }
        }
    }
}