package com.example.mf_demo.util.extension

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.example.mf_demo.util.constant.CConstant
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A clickable modifier that prevents multiple rapid clicks
 * @param enabled Whether the click is enabled
 * @param debounceTime Time in milliseconds to wait before allowing another click (default: 500ms)
 * @param onClick The click callback
 */
fun Modifier.debouncedClickable(
    enabled: Boolean = true,
    debounceTime: Long = CConstant.TIME_CLICK_ENABLE,
    onClick: () -> Unit
): Modifier = composed {
    var isClickEnabled by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    val interactionSource = remember { MutableInteractionSource() }

    this.clickable(
        enabled = enabled && isClickEnabled,
        interactionSource = interactionSource,
        indication = null
    ) {
        if (isClickEnabled) {
            isClickEnabled = false
            onClick()
            coroutineScope.launch {
                delay(debounceTime)
                isClickEnabled = true
            }
        }
    }
} 