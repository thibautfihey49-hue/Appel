package com.thibaut.appel.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.thibaut.appel.ui.screens.*
@OptIn(ExperimentalPermissionsApi::class)
@Composable fun AppRoot(){
  val perms = rememberMultiplePermissionsState(listOf(android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_CALL_LOG, android.Manifest.permission.CALL_PHONE))
  var tab by remember{mutableStateOf(0)}
  LaunchedEffect(Unit){ if(!perms.allPermissionsGranted) perms.launchMultiplePermissionRequest() }
  if(!perms.allPermissionsGranted){
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment=Alignment.Center){
      Column(horizontalAlignment=Alignment.CenterHorizontally){
        Text("L'app a besoin des contacts et appels")
        Spacer(Modifier.height(12.dp))
        Button(onClick={perms.launchMultiplePermissionRequest()}){Text("Autoriser")}
      }
    }
    return
  }
  Scaffold(bottomBar={NavigationBar{NavigationBarItem(selected=tab==0,onClick={tab=0},icon={Text("⌨")},label={Text("Clavier")});NavigationBarItem(selected=tab==1,onClick={tab=1},icon={Text("🕘")},label={Text("Récents")});NavigationBarItem(selected=tab==2,onClick={tab=2},icon={Text("👤")},label={Text("Contacts")})}}) { p-> Box(Modifier.padding(p)){ when(tab){0->DialerScreen();1->RecentsScreen();2->ContactsScreen()} } }
}
