package com.example.mf_demo.ui.screen.navi


import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mf_demo.ui.screen.user.UserDetailScreen
import com.example.mf_demo.ui.screen.user.UserListScreen
import com.example.mf_demo.util.constant.CType.ScreenType
import com.example.mf_demo.util.helper.OpenHelper

@Composable
fun ScreenNavi() {
    val navController = rememberNavController()
    val appNavigator = remember(navController) { NaviHelper(navController) }
    val context = LocalContext.current

    NavHost(
        navController,
        startDestination = ScreenType.UserList.type
    ) {
        composable(ScreenType.UserList.type) {
            UserListScreen(
                onItemClick = { username ->
                    appNavigator.navigateTo(ScreenType.UserDetail, ScreenNaviData(username))
                }
            )
        }
        composable(ScreenType.UserDetail.type) { _ ->
            val userName = appNavigator.navigateFrom()?.key ?: return@composable
            UserDetailScreen(
                userName,
                onRepoClick = { url ->
                    OpenHelper.openURL(context, url)
                },
                onBackClick = {
                    appNavigator.goBack()
                })
        }
    }
}
