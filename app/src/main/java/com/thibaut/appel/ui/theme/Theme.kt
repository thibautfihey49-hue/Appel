package com.thibaut.appel.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
private val Green = Color(0xFF22C55E)
@Composable fun AppelTheme(c:@Composable ()->Unit){
  MaterialTheme(
    colorScheme=lightColorScheme(
      background=Color(0xFFF2F2F7), surface=Color.White, primary=Green,
      onBackground=Color.Black, onSurface=Color.Black, onPrimary=Color.Black,
      surfaceVariant=Color(0xFFE5E5EA), onSurfaceVariant=Color.Black
    ),
    shapes=Shapes(large=RoundedCornerShape(28.dp), extraLarge=RoundedCornerShape(32.dp)),
    content=c
  )
}
