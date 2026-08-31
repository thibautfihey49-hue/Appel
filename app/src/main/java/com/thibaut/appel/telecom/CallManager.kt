package com.thibaut.appel.telecom
import android.telecom.Call
object CallManager {
  var currentCall: Call? = null
  fun answer(){ currentCall?.answer(0) }
  fun hangup(){ currentCall?.disconnect() }
  fun reject(){ currentCall?.reject(false,"") }
}
