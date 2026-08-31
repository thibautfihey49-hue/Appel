package com.thibaut.appel.ui.theme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
private val Bg = Color(0xFF0A0A0E)
private val Surface = Color(0xFF181822)
private val Purple = Color(0xFF7C5CFF)
@Composable fun AppelTheme(c:@Composable ()->Unit){
  MaterialTheme(colorScheme=darkColorScheme(background=Bg,surface=Surface,primary=Purple,onBackground=Color.White), typography=Typography(bodyLarge=TextStyle(fontSize=16.sp)), shapes=Shapes(extraLarge=RoundedCornerShape(28.dp)), content=c)
}
