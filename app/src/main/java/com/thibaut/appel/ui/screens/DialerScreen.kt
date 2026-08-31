package com.thibaut.appel.ui.screens
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thibaut.appel.ui.Glass
@Composable fun DialerScreen(){
  var num by remember{mutableStateOf("")}
  val ctx=LocalContext.current
  val keys = listOf("1","2","3","4","5","6","7","8","9","*","0","#")
  Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment=Alignment.CenterHorizontally){
    Glass(Modifier.fillMaxWidth()){ TextField(num,{num=it},Modifier.fillMaxWidth(),label={Text("Numéro")}, singleLine=true) }
    Spacer(Modifier.height(16.dp))
    LazyVerticalGrid(columns=GridCells.Fixed(3), modifier=Modifier.weight(1f), verticalArrangement=Arrangement.spacedBy(12.dp), horizontalArrangement=Arrangement.spacedBy(12.dp)){
      items(keys){ k->
        Button(onClick={num+=k}, modifier=Modifier.aspectRatio(1f), shape=MaterialTheme.shapes.extraLarge){ Text(k, fontSize=28.sp, fontWeight=FontWeight.Bold) }
      }
    }
    Spacer(Modifier.height(12.dp))
    Row(horizontalArrangement=Arrangement.spacedBy(12.dp)){
      OutlinedButton(onClick={num=""}){Text("Effacer")}
      Button(onClick={ctx.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$num")))}, enabled=num.isNotBlank(), modifier=Modifier.weight(1f)){Text("Appeler $num")}
    }
  }
}
@Composable fun RecentsScreen(){
  val ctx=LocalContext.current
  val calls = remember{ com.thibaut.appel.data.loadCalls(ctx) }
  androidx.compose.foundation.lazy.LazyColumn(Modifier.padding(12.dp)){ items(calls.size){ i-> val c=calls[i]; Glass(Modifier.fillMaxWidth().padding(bottom=8.dp)){Column(Modifier.padding(12.dp)){Text(c.name?:c.number, fontWeight=FontWeight.Bold); Text(android.text.format.DateFormat.format("dd/MM HH:mm", c.date).toString(), style=MaterialTheme.typography.bodySmall)}} } }
}
@Composable fun ContactsScreen(){
  val ctx=LocalContext.current
  val contacts = remember{ com.thibaut.appel.data.loadContacts(ctx) }
  var q by remember{mutableStateOf("")}
  Column(Modifier.padding(12.dp)){ TextField(q,{q=it},Modifier.fillMaxWidth(),label={Text("Rechercher")}); Spacer(Modifier.height(8.dp)); androidx.compose.foundation.lazy.LazyColumn{ items(contacts.filter{it.name.contains(q, true) || it.number.contains(q)}.size){ idx-> val c=contacts.filter{it.name.contains(q,true)||it.number.contains(q)}[idx]; Glass(Modifier.fillMaxWidth().padding(bottom=8.dp)){Row(Modifier.padding(12.dp), verticalAlignment=Alignment.CenterVertically){Column(Modifier.weight(1f)){Text(c.name.ifBlank{c.number}, fontWeight=FontWeight.Bold); Text(c.number, style=MaterialTheme.typography.bodySmall)}; Button(onClick={ctx.startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:${c.number}")))}){Text("Appel")}}} } } }
}
