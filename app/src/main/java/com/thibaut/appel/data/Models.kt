package com.thibaut.appel.data
import android.content.Context
import android.provider.CallLog
import android.provider.ContactsContract
data class Contact(val id:Long,val name:String,val number:String,val photo: String? = null)
data class CallEntry(val name:String?,val number:String,val date:Long,val duration:Int,val type:Int)

fun loadContacts(c:Context): List<Contact> {
  val l=mutableListOf<Contact>()
  val cur=c.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC")
  cur?.use{
    val idI=it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
    val nI=it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
    val uI=it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
    while(it.moveToNext()){ l.add(Contact(it.getLong(idI),it.getString(nI)?:"",it.getString(uI)?:"")) }
  }
  return l.distinctBy{it.number}
}
fun loadCalls(c:Context): List<CallEntry> {
  val l=mutableListOf<CallEntry>()
  val cur=c.contentResolver.query(CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE+" DESC LIMIT 100")
  cur?.use{
    val nI=it.getColumnIndex(CallLog.Calls.CACHED_NAME); val numI=it.getColumnIndex(CallLog.Calls.NUMBER)
    val dI=it.getColumnIndex(CallLog.Calls.DATE); val durI=it.getColumnIndex(CallLog.Calls.DURATION); val tI=it.getColumnIndex(CallLog.Calls.TYPE)
    while(it.moveToNext()) l.add(CallEntry(it.getString(nI),it.getString(numI)?:"",it.getLong(dI),it.getInt(durI),it.getInt(tI)))
  }
  return l
}
