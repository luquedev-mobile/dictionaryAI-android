package com.devluque.common.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devluque.common.R

@Composable
fun ErrorScreen(
    title: String = "Algo salió mal",
    subtitle: String = "No se cargó la información",
    mustShowRetryButton: Boolean = true,
    onClickRetry: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_face_sad),
                contentDescription = "",
                tint = com.devluque.common.theme.getColorScheme().primary,
                modifier = Modifier
                    .size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight(500)
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = subtitle,
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
        if (mustShowRetryButton) {
            Button(
                onClick = onClickRetry,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = com.devluque.common.theme.getColorScheme().primaryContainer,
                )
            ) {
                Text(text = "Volver a intentar")
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}