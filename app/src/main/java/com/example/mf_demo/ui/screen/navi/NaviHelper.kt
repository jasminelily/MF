package com.example.mf_demo.ui.screen.navi

import androidx.navigation.NavHostController
import com.example.mf_demo.util.constant.CConstant
import com.example.mf_demo.util.constant.CType

class NaviHelper(
    private val navController: NavHostController
) {

    fun navigateTo(screen: CType.ScreenType, data: ScreenNaviData) {
        navController.currentBackStackEntry?.savedStateHandle?.set(CConstant.KEY_ARG_ID, data)
        navController.navigate(screen.type)
    }

    fun navigateFrom(): ScreenNaviData? {
        return navController.previousBackStackEntry?.savedStateHandle?.get<ScreenNaviData>(CConstant.KEY_ARG_ID)
    }

    fun goBack() {
        navController.popBackStack()
    }

}