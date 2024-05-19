package com.juliusbiascan.sms_gatewayv2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.juliusbiascan.sms_gatewayv2.ui.theme.Dimen.spNormal
import com.juliusbiascan.sms_gatewayv2.ui.theme.SMSGatewayv2Theme

@Composable
fun ThemeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = spNormal,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        )
    ) {
        Text(
            fontSize = fontSize,
            text = text,
            color = if (enabled) textColor else textColor.copy(
                alpha = 0.5f
            )
        )
    }
}

@Composable
fun ThemeOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = spNormal,
    borderColor: Color = MaterialTheme.colorScheme.onSurface,
    textColor: Color = MaterialTheme. colorScheme.onSecondary,
    enabled: Boolean = true
) {
    OutlinedButton(
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
        ),
        border = BorderStroke(
            width = ButtonDefaults.outlinedButtonBorder.width,
            color = if (enabled) borderColor else borderColor.copy(

            )
        ),
        onClick = onClick
    ) {
        Text(
            fontSize = fontSize,
            text = text,
            color = if (enabled) textColor else textColor.copy(
                alpha = 0.5f
            )
        )
    }
}

@Preview
@Composable
fun PreviewThemeButton() {
    SMSGatewayv2Theme {
        Column {
            ThemeButton(onClick = {}, text = "BUTTON")
            Spacer(modifier = Modifier.height(20.dp))
            ThemeButton(onClick = {}, text = "BUTTON", enabled = false)
        }
    }
}

@Preview
@Composable
fun PreviewThemeOutlinedButton() {
    SMSGatewayv2Theme {
        Column {
            ThemeOutlinedButton(onClick = {}, text = "BUTTON")
            Spacer(modifier = Modifier.height(20.dp))
            ThemeOutlinedButton(onClick = {}, text = "BUTTON", enabled = false)
        }
    }
}