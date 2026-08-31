package com.thibaut.appel.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.thibaut.appel.ui.screens.*
@Composable fun AppRoot(){
  var tab by remember{mutableStateOf(0)}
  Scaffold(bottomBar={NavigationBar{NavigationBarItem(selected=tab==0,onClick={tab=0},icon={Text("⌨")},label={Text("Clavier")});NavigationBarItem(selected=tab==1,onClick={tab=1},icon={Text("🕘")},label={Text("Récents")});NavigationBarItem(selected=tab==2,onClick={tab=2},icon={Text("👤")},label={Text("Contacts")})}}) { p->
    Box(Modifier.padding(p)){ when(tab){0->DialerScreen();1->RecentsScreen();2->ContactsScreen()} }
  }
}
