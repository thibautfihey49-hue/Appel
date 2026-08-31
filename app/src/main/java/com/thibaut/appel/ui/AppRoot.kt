package com.thibaut.appel.ui
import android.app.role.RoleManager
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.thibaut.appel.ui.screens.*
@OptIn(ExperimentalPermissionsApi::class)
@Composable fun AppRoot(){
  val ctx=LocalContext.current
  val perms = rememberMultiplePermissionsState(listOf(android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_CALL_LOG, android.Manifest.permission.CALL_PHONE))
  var isDefault by remember{mutableStateOf(false)}
  fun checkDefault(): Boolean {
    return if(Build.VERSION.SDK_INT>=29){
      val rm=ctx.getSystemService(RoleManager::class.java)
      rm.isRoleHeld(RoleManager.ROLE_DIALER)
    } else {
      val tm=ctx.getSystemService(android.telecom.TelecomManager::class.java)
      ctx.packageName==tm.defaultDialerPackage
    }
  }
  LaunchedEffect(Unit){ if(!perms.allPermissionsGranted) perms.launchMultiplePermissionRequest(); isDefault=checkDefault() }
  if(!perms.allPermissionsGranted){
    Box(Modifier.fillMaxSize().padding(24.dp), contentAlignment=Alignment.Center){ Button(onClick={perms.launchMultiplePermissionRequest()}, colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF22C55E), contentColor=Color.Black)){Text("Autoriser", color=Color.Black)} }; return
  }
  var tab by remember{mutableStateOf(0)}
  Scaffold(containerColor=Color(0xFFF2F2F7),
    topBar={ if(!isDefault) Surface(color=Color(0xFF22C55E), modifier=Modifier.fillMaxWidth()){Row(Modifier.padding(12.dp), verticalAlignment=Alignment.CenterVertically){Text("Appel n'est pas l'appli par défaut", Modifier.weight(1f), color=Color.Black, style=MaterialTheme.typography.bodyMedium); Button(onClick={
      if(Build.VERSION.SDK_INT>=29){
        val rm=ctx.getSystemService(RoleManager::class.java)
        ctx.startActivity(rm.createRequestRoleIntent(RoleManager.ROLE_DIALER))
      } else {
        val i=Intent(android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER); i.putExtra(android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, ctx.packageName); ctx.startActivity(i)
      }
    }, colors=ButtonDefaults.buttonColors(containerColor=Color.Black, contentColor=Color.White)){Text("Définir")} }} },
    bottomBar={NavigationBar(containerColor=Color.White){NavigationBarItem(selected=tab==0,onClick={tab=0},icon={Text("⌨")},label={Text("Clavier", color=Color.Black)});NavigationBarItem(selected=tab==1,onClick={tab=1},icon={Text("🕘")},label={Text("Récents", color=Color.Black)});NavigationBarItem(selected=tab==2,onClick={tab=2},icon={Text("👤")},label={Text("Contacts", color=Color.Black)})}}) { p-> Box(Modifier.padding(p)){ when(tab){0->DialerScreen();1->RecentsScreen();2->ContactsScreen()} } }
}
