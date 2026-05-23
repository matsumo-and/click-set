package io.matsumo.clickset

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
@Preview
fun App() {
    MaterialTheme {
        MetronomeScreen()
    }
}

@Composable
fun MetronomeScreen() {
    var bpm by remember { mutableStateOf(120) }
    var isPlaying by remember { mutableStateOf(false) }
    var beatOn by remember { mutableStateOf(false) }

    LaunchedEffect(isPlaying, bpm) {
        if (isPlaying) {
            while (true) {
                beatOn = true
                delay(80)
                beatOn = false
                val remaining = (60_000L / bpm) - 80
                if (remaining > 0) delay(remaining)
            }
        } else {
            beatOn = false
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding()
                .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = "click set",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        BeatIndicator(beatOn = beatOn)
        BpmDisplay(bpm = bpm)
        BpmControls(bpm = bpm, onBpmChange = { bpm = it })
        PlayStopButton(isPlaying = isPlaying, onClick = { isPlaying = !isPlaying })
    }
}

@Composable
private fun BeatIndicator(beatOn: Boolean) {
    val beatColor by animateColorAsState(
        targetValue = if (beatOn) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        animationSpec = tween(durationMillis = 80),
        label = "beat",
    )
    Box(
        modifier =
            Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(beatColor),
    )
}

@Composable
private fun BpmDisplay(bpm: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$bpm",
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "BPM",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun BpmControls(
    bpm: Int,
    onBpmChange: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        FilledTonalButton(
            onClick = { if (bpm > 40) onBpmChange(bpm - 1) },
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text("−", fontSize = 20.sp)
        }
        Slider(
            value = bpm.toFloat(),
            onValueChange = { onBpmChange(it.toInt()) },
            valueRange = 40f..240f,
            modifier = Modifier.weight(1f),
        )
        FilledTonalButton(
            onClick = { if (bpm < 240) onBpmChange(bpm + 1) },
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            Text("+", fontSize = 20.sp)
        }
    }
}

@Composable
private fun PlayStopButton(
    isPlaying: Boolean,
    onClick: () -> Unit,
) {
    val containerColor: Color =
        if (isPlaying) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
    Button(
        onClick = onClick,
        modifier =
            Modifier
                .size(80.dp)
                .clip(CircleShape),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor),
    ) {
        Text(
            text = if (isPlaying) "■" else "▶",
            fontSize = 28.sp,
        )
    }
}
