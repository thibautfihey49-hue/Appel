package com.thibaut.appel.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable fun GlassCard(mod: Modifier = Modifier, content: @Composable ()->Unit){
  Box(mod.clip(RoundedCornerShape(24.dp)).background(Color.White).border(1.dp, Color(0xFFE5E5EA), RoundedCornerShape(24.dp))){content()}
}
