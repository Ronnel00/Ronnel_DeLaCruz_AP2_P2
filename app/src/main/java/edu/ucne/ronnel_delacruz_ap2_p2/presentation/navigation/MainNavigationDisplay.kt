package edu.ucne.ronnel_delacruz_ap2_p2.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import edu.ucne.ronnel_delacruz_ap2_p2.presentation.detail.DetailScreen
import edu.ucne.ronnel_delacruz_ap2_p2.presentation.list.ListScreen

@Composable
fun MainNavigationDisplay() {
    val backStack = rememberNavBackStack(Screen.GastoList)

    NavDisplay(
        backStack = backStack,
        modifier = Modifier.fillMaxSize(),
        entryProvider = entryProvider {

            entry<Screen.GastoList> {
                ListScreen(
                    onItemClick = { id ->
                        backStack.add(Screen.GastoDetail(id))
                    },
                    onCreateClick = {
                        backStack.add(Screen.GastoDetail(0))
                    }
                )
            }

            entry<Screen.GastoDetail> { key ->
                DetailScreen(
                    id = key.id,
                    onBack = {
                        if (backStack.isNotEmpty()) {
                            backStack.removeAt(backStack.size - 1)
                        }
                    }
                )
            }
        }
    )
}