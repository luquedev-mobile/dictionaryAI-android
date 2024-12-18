package com.devluque.common.speaker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.devluque.common.R
import com.devluque.domain.SpeakerModer

@Composable
fun Speaker(
    speak: (SpeakerModer) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_sound),
            contentDescription = "",
            modifier = Modifier
                .clickable {
                    speak(SpeakerModer.Normal)
                }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_sound_slow),
            contentDescription = "",
            modifier = Modifier
                .clickable {
                    speak(SpeakerModer.Slow)
                }
                .size(20.dp)
        )
    }
}