package com.example.mf_demo.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.mf_demo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onSettingClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(title) },
//        actions = {
//            IconButton(onClick = { onSettingClick() }) {
//                Icon(
//                    Icons.Default.Settings,
//                    contentDescription = stringResource(R.string.desc_btn_setting)
//                )
//            }
//        }
    )
}
