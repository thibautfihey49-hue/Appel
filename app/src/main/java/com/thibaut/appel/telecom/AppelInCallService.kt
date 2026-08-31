package com.thibaut.appel.telecom
import android.content.Intent
import android.telecom.Call
import android.telecom.InCallService
class AppelInCallService : InCallService() {
  override fun onCallAdded(call: Call) {
    super.onCallAdded(call)
    CallManager.currentCall = call
    val i = Intent(this, InCallActivity::class.java)
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
    i.putExtra("number", call.details.handle?.schemeSpecificPart)
    startActivity(i)
    call.registerCallback(object: Call.Callback(){
      override fun onStateChanged(c: Call, state: Int){
        if(state==Call.STATE_DISCONNECTED){ CallManager.currentCall=null }
      }
    })
  }
  override fun onCallRemoved(call: Call) {
    super.onCallRemoved(call)
    CallManager.currentCall=null
  }
}
