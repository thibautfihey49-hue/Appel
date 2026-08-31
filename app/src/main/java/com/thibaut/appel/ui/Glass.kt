package com.thibaut.appel.ui
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable fun GlassButton(mod: Modifier = Modifier, content: @Composable ()->Unit){
  Box(mod.clip(RoundedCornerShape(28.dp))
    .background(Brush.linearGradient(listOf(Color.White.copy(0.85f), Color.White.copy(0.65f))))
    .border(1.dp, Color.White.copy(0.5f), RoundedCornerShape(28.dp))
    .then(if(Build.VERSION.SDK_INT>=31) Modifier.blur(0.1.dp) else Modifier)
  ){content()}
}
@Composable fun GlassCard(mod: Modifier = Modifier, content: @Composable ()->Unit){
  Box(mod.clip(RoundedCornerShape(24.dp)).background(Color.White.copy(0.7f)).border(1.dp, Color.White.copy(0.6f), RoundedCornerShape(24.dp))){content()}
}
