package com.juliusbiascan.sms_gatewayv2.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties

@Composable
fun AppDropdownMenu(
    title: String,
    expandedState: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
) {

    DropdownMenu(
        expanded = expandedState.value,
        modifier = modifier
            .background(MaterialTheme.colorScheme.primaryContainer),
        onDismissRequest = { expandedState.value = false },
        offset = offset,
        properties = properties,
        content = {
            Text(
                text = title,
                modifier = Modifier.padding(horizontal = 10.dp),
                style = MaterialTheme.typography.labelSmall,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Divider()
            content()
        }
    )
}

@Composable
fun AppDropdownRadioButtonItem(text: String, checked: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {
                onClick()
            })
    ) {
        RadioButton(selected = checked, onClick = onClick)
        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 10.dp),
            color = MaterialTheme.colorScheme.onSecondary,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 15.sp)
        )
    }
}

@Composable
fun AppDropdownIconItem(
    title: String,
    onClick: () -> Unit
) {
  DropdownMenuItem(text = { Text(title) }, onClick = onClick)
}