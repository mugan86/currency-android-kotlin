package amldev.currency.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by anartzmugika on 17/9/17.
 */
object DateTime {

    private val ALL_DATA_FORMAT = "yyyy-MM-dd"
    private val ALL_DATA_WITH_TIME_FORMAT = "${ALL_DATA_FORMAT} HH:mm:ss"
    val currentData: String
        get() {
            val dateFormat = SimpleDateFormat(ALL_DATA_FORMAT, Locale.ENGLISH)
            val cal = Calendar.getInstance()
            return dateFormat.format(cal.getTime())
        }

    val currentDataCalendar: Calendar
        get() = Calendar.getInstance()


    val currentDataTime: String
        get() {
            val dateFormat = SimpleDateFormat(ALL_DATA_WITH_TIME_FORMAT)
            val date = Date()
            return dateFormat.format(date)
        }

    val yesterdayData: String
        get() {
            val dateFormat = SimpleDateFormat(ALL_DATA_FORMAT, Locale.ENGLISH)
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -1)
            return dateFormat.format(cal.getTime())
        }

    val firstDayNextYearAndLastDayBeforeYear: ArrayList<String>
        get() {
            val datas = ArrayList<String>(2)
            val dateFormat = SimpleDateFormat(ALL_DATA_FORMAT, Locale.ENGLISH)
            var cal = Calendar.getInstance()
            cal.add(Calendar.YEAR, 1)
            cal.set(Calendar.DAY_OF_MONTH, 1)
            cal.set(Calendar.MONTH, 0)
            datas[0] = dateFormat.format(cal.getTime())
            cal = Calendar.getInstance()
            cal.add(Calendar.YEAR, -1)
            cal.set(Calendar.DAY_OF_MONTH, 31)
            cal.set(Calendar.MONTH, 11)
            datas[1] = dateFormat.format(cal.getTime())
            return datas
        }

    fun getCurrentDataWithAddSetMonthValue(first_data: String, add_month: Int): String {
        val dateFormat = SimpleDateFormat(ALL_DATA_FORMAT, Locale.ENGLISH)
        val cal = Calendar.getInstance()
        if (first_data != "")
        //add first_data in calendar object to correct asign in second data
        {
            val urtea = Integer.parseInt(first_data.substring(0, 4))
            val hilabetea = Integer.parseInt(first_data.substring(5, 7)) - 1
            val eguna = Integer.parseInt(first_data.substring(8, 10))

            cal.set(urtea, hilabetea, eguna)
        }

        cal.add(Calendar.MONTH, add_month)  //Add two months to first data
        return dateFormat.format(cal.getTime())

    }

    fun getCurrentDataWithAddSetBeforeDays(first_data: String, add_month: Int): String {
        val dateFormat = SimpleDateFormat(ALL_DATA_FORMAT, Locale.ENGLISH)
        val cal = Calendar.getInstance()
        if (first_data != "")
        //add first_data in calendar object to correct asign in second data
        {
            val urtea = Integer.parseInt(first_data.substring(0, 4))
            val hilabetea = Integer.parseInt(first_data.substring(5, 7)) - 1
            val eguna = Integer.parseInt(first_data.substring(8, 10))

            cal.set(urtea, hilabetea, eguna)
        }

        cal.add(Calendar.DAY_OF_WEEK, -add_month)  //DownDays value to first data
        return dateFormat.format(cal.getTime())
    }

    fun getAllDataTimeCalendarObject(date: String, hour_string: String): Calendar {
        //Extract details data
        val year = Integer.parseInt(date.substring(0, 4))
        val month = Integer.parseInt(date.substring(5, 7)) - 1
        val day = Integer.parseInt(date.substring(8, 10))

        val hour_int = getHourMinutesSecondsInfo(hour_string)

        println(year.toString() + "/" + month + "/" + day + " " + hour_int[0] + ":" + hour_int[1] + ":" + hour_int[2])
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour_int[0], hour_int[1], hour_int[2])
        return calendar
    }

    fun getHourMinutesSecondsInfo(hour_string: String): IntArray {
        val hour = hour_string.substring(0, 2)
        val hour_int: Int
        if (hour[0] == '0') {
            hour_int = Integer.parseInt(hour.substring(1))
        } else {
            hour_int = Integer.parseInt(hour)
        }

        val minute_int = Integer.parseInt(hour_string.substring(3, 5))
        return intArrayOf(hour_int, minute_int, 0)
    }

    fun setFormatData(year: Int, monthOfYear: Int, dayOfMonth: Int): ArrayList<String>? {
        //year/month/day
        val format_data = ArrayList<String>(3)

        format_data[0] = year.toString()
        if (monthOfYear + 1 < 10) {
            format_data[1] = "0${(monthOfYear + 1).toString()}"
        } else {
            format_data[1] = (monthOfYear + 1).toString()
        }

        if (dayOfMonth < 10) {
            format_data[2] = "0${dayOfMonth.toString()}"
        } else {
            format_data[2] = dayOfMonth.toString()
        }
        return format_data
    }
}