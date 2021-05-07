package com.example.todolist.utilities

import java.util.*

/**
 * @param day is day of the deadline set by the user.
 * @param month is the month of the deadline set by the user.
 * @param year is the year of the deadline set by the user.
 * @param minute is the minute of the deadline set by the user.
 * @param hour is the hour of the deadline set by the user.
 *
 * Returns time in milliseconds to save in room database.
 */
fun dateToMillis(day: Int, month: Int, year: Int, minute: Int, hour: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, minute, 0)
    return calendar.timeInMillis
}

/**
 * @param timeInMilliseconds is the due date set by the user.
 *
 * The reason this function returns array list of integers is because we'll
 * need values day, month, year, hour and minute to display them correctly.
 *
 * Store order is 0 th position is day,
 * 1st position is month
 * 2nd position is year
 * 3rd position is hour
 * 4th position is minute
 *
 * List will be processed accordingly when setting values.
 *
 */
fun convertMillis(timeInMilliseconds: Long): List<Int> {
    val dateValues = arrayListOf<Int>()

    val date = Date(timeInMilliseconds)
    val calendar = Calendar.getInstance()
    calendar.time = date

    dateValues.add(calendar.get(Calendar.DAY_OF_MONTH))
    dateValues.add(calendar.get(Calendar.MONTH))
    dateValues.add(calendar.get(Calendar.YEAR))
    dateValues.add(calendar.get(Calendar.HOUR_OF_DAY))
    dateValues.add(calendar.get(Calendar.MINUTE))

    return dateValues
}

/**
 * Function to convert
 * @param month to corresponding text version.
 *
 */
fun convertNumberToMonthName(month: Int): String {
    return when (month) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> "Invalid"
    }
}

/**
 * @param title is the title of the to-do item.
 * @returns an integer which represents the integer version of the title.
 * which will be used as a key to set the alarm for specific to-do item.
 *
 */
fun stringToAscii(title: String): Int {
    var ascii = 0

    for (item in title) {
        ascii += item.toInt()
    }
    return ascii
}