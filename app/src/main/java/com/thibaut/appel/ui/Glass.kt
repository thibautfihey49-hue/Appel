package com.thibaut.appel.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
@Composable fun Glass(mod: Modifier = Modifier, content: @Composable ()->Unit){
  androidx.compose.foundation.layout.Box(mod.clip(RoundedCornerShape(24.dp)).background(Color.White.copy(0.07f)).border(1.dp,Color.White.copy(0.12f),RoundedCornerShape(24.dp))){content()}
}
