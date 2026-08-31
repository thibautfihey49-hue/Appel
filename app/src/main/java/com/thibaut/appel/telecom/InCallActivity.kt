package com.thibaut.appel.telecom
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thibaut.appel.ui.theme.AppelTheme
class InCallActivity : ComponentActivity(){
  override fun onCreate(b:Bundle?){
    super.onCreate(b)
    val num = intent.getStringExtra("number") ?: "Appel..."
    setContent{
      AppelTheme{
        var state by remember{mutableStateOf("Appel en cours")}
        Box(Modifier.fillMaxSize().background(Color(0xFFF2F2F7)), contentAlignment=Alignment.Center){
          Column(horizontalAlignment=Alignment.CenterHorizontally, modifier=Modifier.padding(24.dp)){
            Text(num, fontSize=28.sp, fontWeight=FontWeight.Bold, color=Color.Black)
            Spacer(Modifier.height(8.dp))
            Text(state, color=Color.Black.copy(0.6f))
            Spacer(Modifier.height(48.dp))
            Row(horizontalArrangement=Arrangement.spacedBy(24.dp)){
              Button(onClick={ CallManager.reject(); finish() }, modifier=Modifier.size(80.dp), shape=CircleShape, colors=ButtonDefaults.buttonColors(containerColor=Color.Red)){Text("Refus", color=Color.White)}
              Button(onClick={ CallManager.answer(); state="En communication" }, modifier=Modifier.size(80.dp), shape=CircleShape, colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF22C55E))){Text("Décrocher", color=Color.Black)}
              Button(onClick={ CallManager.hangup(); finish() }, modifier=Modifier.size(80.dp), shape=CircleShape, colors=ButtonDefaults.buttonColors(containerColor=Color.Black)){Text("Fin", color=Color.White)}
            }
          }
        }
      }
    }
  }
}
