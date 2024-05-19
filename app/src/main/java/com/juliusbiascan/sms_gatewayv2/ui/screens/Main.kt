package com.juliusbiascan.sms_gatewayv2.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.juliusbiascan.sms_gatewayv2.R
import com.juliusbiascan.sms_gatewayv2.ui.components.AppDropdownIconItem
import com.juliusbiascan.sms_gatewayv2.ui.components.AppDropdownMenu
import com.juliusbiascan.sms_gatewayv2.ui.components.CircularBox
import com.juliusbiascan.sms_gatewayv2.ui.components.ConnectivityStatus
import com.juliusbiascan.sms_gatewayv2.ui.theme.SMSGatewayv2Theme
import com.juliusbiascan.sms_gatewayv2.ui.theme.greenColorDark

@Composable
fun MainScreen(
    connectivityStatus: ConnectivityStatus = ConnectivityStatus.NONE,
    onToAboutScreen: () -> Unit,
    onConnectClick: () -> Unit,
    onDisconnect: () -> Unit,
    onPremiumClick: () -> Unit,
) {
    //val context = LocalContext.current

    val ipTextColor: Color by animateColorAsState(
        if (connectivityStatus.isConnected()) greenColorDark else MaterialTheme.colorScheme.error,
        label = "",
    )

    val ipText = if (!connectivityStatus.isConnected()) "× OFFLINE" else "✓ ACTIVE"

    Column(
        modifier = Modifier
            .padding(top = 5.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            /* Settings button */
            SettingsDropdownMenu(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp),
                goToAboutScreen = onToAboutScreen
            )

            Text(
                text = stringResource(R.string.app_name),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 5.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = { }
                        )
                    },
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(
                onClick = onPremiumClick,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterEnd)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_crown),
                    contentDescription = "Get premium"
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.5f))

        CircularBox(
            status = connectivityStatus,
            onClick = {
                if (connectivityStatus.isDisconnected()) {
                    onConnectClick()
                } else {
                    onDisconnect()
                }
            }
        )

        Spacer(modifier = Modifier.padding(top = 30.dp))

        Text(
            text = "Gateway Status",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = ipText,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 3.dp)
                .animateContentSize(),
            style = MaterialTheme.typography.titleSmall,
            color = ipTextColor,
        )

        Spacer(modifier = Modifier.weight(1f))

    }
}

@Composable
private fun SettingsDropdownMenu(
    modifier: Modifier = Modifier,
    goToAboutScreen: () -> Unit
) {
    val expandedState = remember { mutableStateOf(false) }

    val dismiss = remember { { expandedState.value = false } }

    IconButton(
        modifier = modifier,
        onClick = { expandedState.value = true }
    ) {
        Icon(
            tint = MaterialTheme.colorScheme.secondary,
            painter = painterResource(R.drawable.ic_gear_icon),
            contentDescription = "More Options"
        )
    }

    if (LocalInspectionMode.current) return // for preview


    AppDropdownMenu(
        title = "More Options",
        expandedState = expandedState,
        offset = DpOffset(15.dp, 0.dp)
    ) {
        AppDropdownIconItem(
            title = "Change server",
            onClick = {

                dismiss()
            }
        )
        AppDropdownIconItem(
            title = "About",
            onClick = {
                goToAboutScreen()
                dismiss()
            }
        )
    }

}

@Preview
@Composable
fun PreviewStartScreen() {
    SMSGatewayv2Theme {
        MainScreen(
            onToAboutScreen = {},
            onConnectClick = {},
            onDisconnect = {},
            onPremiumClick = {}
        )
    }
}

@Preview(widthDp = 320, heightDp = 533)
@Composable
fun PreviewStartScreenSmall() {
    SMSGatewayv2Theme {
        MainScreen(
            onToAboutScreen = {},
            onConnectClick = {},
            onDisconnect = {},
            onPremiumClick = {}
        )
    }
}