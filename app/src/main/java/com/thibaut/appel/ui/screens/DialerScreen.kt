package com.thibaut.appel.ui.screens
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thibaut.appel.ui.GlassCard
@Composable fun DialerScreen(){
  var num by remember{mutableStateOf("")}
  val ctx=LocalContext.current
  val keys = listOf("1" to "","2" to "ABC","3" to "DEF","4" to "GHI","5" to "JKL","6" to "MNO","7" to "PQRS","8" to "TUV","9" to "WXYZ","*" to "","0" to "+","#" to "")
  val Green = Color(0xFF22C55E)
  Column(Modifier.fillMaxSize().background(Color(0xFFF2F2F7)).statusBarsPadding().padding(20.dp), horizontalAlignment=Alignment.CenterHorizontally){
    GlassCard(Modifier.fillMaxWidth()){ Column(Modifier.padding(18.dp).fillMaxWidth(), horizontalAlignment=Alignment.CenterHorizontally){
      Text(if(num.isBlank()) "Nouvel appel" else num, fontSize=32.sp, fontWeight=FontWeight.Bold, color=Color.Black)
      if(num.isBlank()) Text("Entrer un numéro", color=Color.Black.copy(0.6f), fontSize=14.sp)
    }}
    Spacer(Modifier.height(28.dp))
    LazyVerticalGrid(columns=GridCells.Fixed(3), modifier=Modifier.weight(1f), verticalArrangement=Arrangement.spacedBy(14.dp), horizontalArrangement=Arrangement.spacedBy(14.dp)){
      items(keys){ (n,letters)->
        Surface(onClick={num+=n}, shape=MaterialTheme.shapes.extraLarge, color=Color.White, modifier=Modifier.aspectRatio(1f).border(1.dp, Color(0xFFE5E5EA), MaterialTheme.shapes.extraLarge)){
          Column(Modifier.fillMaxSize(), verticalArrangement=Arrangement.Center, horizontalAlignment=Alignment.CenterHorizontally){
            Text(n, fontSize=34.sp, fontWeight=FontWeight.Bold, color=Color.Black)
            if(letters.isNotEmpty()) Text(letters, fontSize=11.sp, color=Color.Black, fontWeight=FontWeight.Bold)
          }
        }
      }
    }
    Spacer(Modifier.height(16.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(12.dp)){
      OutlinedButton(onClick={if(num.isNotEmpty()) num=num.dropLast(1)}, modifier=Modifier.weight(1f).height(56.dp), shape=MaterialTheme.shapes.large, colors=ButtonDefaults.outlinedButtonColors(contentColor=Color.Black)){Text("Effacer", color=Color.Black)}
      Button(onClick={ctx.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$num")))}, enabled=num.isNotBlank(), modifier=Modifier.weight(1.6f).height(56.dp), shape=MaterialTheme.shapes.large, colors=ButtonDefaults.buttonColors(containerColor=Green, contentColor=Color.Black, disabledContainerColor=Color.LightGray)){Text("Appeler", fontSize=17.sp, color=Color.Black, fontWeight=FontWeight.Bold)}
    }
  }
}
@Composable fun RecentsScreen(){
  val ctx=LocalContext.current
  val calls = remember{ com.thibaut.appel.data.loadCalls(ctx) }
  androidx.compose.foundation.lazy.LazyColumn(Modifier.fillMaxSize().background(Color(0xFFF2F2F7)).padding(16.dp)){ items(calls.size){ i-> val c=calls[i]; GlassCard(Modifier.fillMaxWidth().padding(bottom=10.dp)){Column(Modifier.padding(16.dp)){Text(c.name?:c.number, fontWeight=FontWeight.Bold, color=Color.Black); Text(c.number, color=Color.Black); Text(android.text.format.DateFormat.format("dd/MM HH:mm", c.date).toString(), style=MaterialTheme.typography.bodySmall, color=Color.Black.copy(0.6f))}} } }
}
@Composable fun ContactsScreen(){
  val ctx=LocalContext.current
  val contacts = remember{ com.thibaut.appel.data.loadContacts(ctx) }
  var q by remember{mutableStateOf("")}
  val Green = Color(0xFF22C55E)
  Column(Modifier.fillMaxSize().background(Color(0xFFF2F2F7)).padding(16.dp)){
    GlassCard(Modifier.fillMaxWidth()){ OutlinedTextField(q,{q=it},Modifier.fillMaxWidth().padding(8.dp),shape=MaterialTheme.shapes.large,label={Text("Rechercher", color=Color.Black)}, colors=TextFieldDefaults.colors(focusedTextColor=Color.Black, unfocusedTextColor=Color.Black, focusedContainerColor=Color.Transparent, unfocusedContainerColor=Color.Transparent)) }
    Spacer(Modifier.height(12.dp))
    androidx.compose.foundation.lazy.LazyColumn(verticalArrangement=Arrangement.spacedBy(10.dp)){ items(contacts.filter{it.name.contains(q,true)||it.number.contains(q)}.size){ idx-> val c=contacts.filter{it.name.contains(q,true)||it.number.contains(q)}[idx]; GlassCard(Modifier.fillMaxWidth()){Row(Modifier.padding(16.dp), verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(c.name.ifBlank{c.number}, fontWeight=FontWeight.Bold, color=Color.Black); Text(c.number, color=Color.Black)}; Button(onClick={ctx.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${c.number}")))}, colors=ButtonDefaults.buttonColors(containerColor=Green, contentColor=Color.Black)){Text("Appel", color=Color.Black, fontWeight=FontWeight.Bold)}} } } }
  }
}
