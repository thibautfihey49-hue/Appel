package com.thibaut.appel.telecom
import android.telecom.Call
import android.telecom.InCallService
class AppelInCallService : InCallService() {
  override fun onCallAdded(call: Call?) { super.onCallAdded(call) }
  override fun onCallRemoved(call: Call?) { super.onCallRemoved(call) }
}
