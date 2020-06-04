
package com.xzy.utils.calendar

import java.util.Calendar

@Suppress("unused")
object CalendarUtil {

    fun getCalendar(): String {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        return (year.toString().substring(2, 4) + String.format("%02d", month + 1) +
                String.format("%02d", day))
    }
}