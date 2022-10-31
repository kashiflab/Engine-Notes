package com.kashiflab.engine_notes.data.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

abstract class AppUtils {

    companion object{
        //this will format time into milliseconds
        fun convertDateTimeToMilliseconds(dateTime: String?): Long {
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val date = sdf.parse(dateTime)
                date.time
            } catch (e: Exception) {
                Log.i("Hello", e.message!!)
                0
            }
        }
        fun getDateTime(): String{
            val date = Calendar.getInstance().time
            val df = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault())
            return df.format(date)
        }
    }
}