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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.accompanist.permissions.*
import com.thibaut.appel.ui.screens.*
@OptIn(ExperimentalPermissionsApi::class)
@Composable fun AppRoot(){
  val ctx=LocalContext.current
  val perms = rememberMultiplePermissionsState(listOf(android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.READ_CALL_LOG, android.Manifest.permission.CALL_PHONE))
  val lifecycle = LocalLifecycleOwner.current.lifecycle
  var isDefault by remember{mutableStateOf(false)}
  fun checkDefault(): Boolean {
    return try{
      if(Build.VERSION.SDK_INT>=29){
        val rm=ctx.getSystemService(RoleManager::class.java)
        rm.isRoleHeld(RoleManager.ROLE_DIALER)
      } else {
        val tm=ctx.getSystemService(android.telecom.TelecomManager::class.java)
        ctx.packageName==tm.defaultDialerPackage
      }
    }catch(_:Exception){false}
  }
  // refresh quand tu reviens de l'écran système "Définir par défaut"
  DisposableEffect(lifecycle){
    val obs = LifecycleEventObserver{_,e-> if(e==Lifecycle.Event.ON_RESUME) isDefault=checkDefault() }
    lifecycle.addObserver(obs); onDispose{lifecycle.removeObserver(obs)}
  }
  LaunchedEffect(Unit){ isDefault=checkDefault() }

  var tab by remember{mutableStateOf(0)}
  val Green = Color(0xFF22C55E)

  // ÉCRAN AUTORISATIONS AVEC BOUTON DÉFAUT INCLUS
  if(!perms.allPermissionsGranted || !isDefault){
    Column(Modifier.fillMaxSize().padding(24.dp), verticalArrangement=Arrangement.Center, horizontalAlignment=Alignment.CenterHorizontally){
      Text("Autorisations requises", style=MaterialTheme.typography.titleLarge, color=Color.Black)
      Spacer(Modifier.height(16.dp))
      if(!perms.allPermissionsGranted){
        Text("Contacts + Journal d'appels pour afficher tes contacts", color=Color.Black)
        Spacer(Modifier.height(8.dp))
        Button(onClick={perms.launchMultiplePermissionRequest()}, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Green, contentColor=Color.Black)){Text("Autoriser contacts", color=Color.Black, fontWeight=androidx.compose.ui.text.font.FontWeight.Bold)}
        Spacer(Modifier.height(16.dp))
      }
      if(!isDefault){
        Text("Pour voir tout le clavier et remplacer le téléphone système", color=Color.Black)
        Spacer(Modifier.height(8.dp))
        Button(onClick={
          try{
            if(Build.VERSION.SDK_INT>=29){
              val rm=ctx.getSystemService(RoleManager::class.java)
              ctx.startActivity(rm.createRequestRoleIntent(RoleManager.ROLE_DIALER))
            } else {
              val i=Intent(android.telecom.TelecomManager.ACTION_CHANGE_DEFAULT_DIALER); i.putExtra(android.telecom.TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, ctx.packageName); ctx.startActivity(i)
            }
          }catch(_:Exception){}
        }, modifier=Modifier.fillMaxWidth(), colors=ButtonDefaults.buttonColors(containerColor=Color.Black, contentColor=Color.White)){Text("Définir comme appli d'appel par défaut")}
      }
      if(perms.allPermissionsGranted && isDefault){
        Spacer(Modifier.height(16.dp)); Text("Parfait !", color=Green)
      }
    }
    if(!perms.allPermissionsGranted) return
    // si seulement pas défaut, on laisse quand même accéder via bouton "Continuer"
    if(!isDefault){
      Spacer(Modifier.height(12.dp))
      TextButton(onClick={tab=0; isDefault=true}){Text("Continuer sans définir", color=Color.Black)}
    }
  }

  Scaffold(containerColor=Color(0xFFF2F2F7), bottomBar={NavigationBar(containerColor=Color.White){NavigationBarItem(selected=tab==0,onClick={tab=0},icon={Text("⌨")},label={Text("Clavier", color=Color.Black)});NavigationBarItem(selected=tab==1,onClick={tab=1},icon={Text("🕘")},label={Text("Récents", color=Color.Black)});NavigationBarItem(selected=tab==2,onClick={tab=2},icon={Text("👤")},label={Text("Contacts", color=Color.Black)})}}) { p-> Box(Modifier.padding(p)){ when(tab){0->DialerScreen();1->RecentsScreen();2->ContactsScreen()} } }
}
