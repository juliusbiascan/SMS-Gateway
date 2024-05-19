package com.juliusbiascan.sms_gatewayv2.extensions

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.debugInspectorInfo

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.clickableNoIndication(onClick: () -> Unit) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "clickableNoIndication"
        properties["onClick"] = onClick
    }
) {
    clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
}