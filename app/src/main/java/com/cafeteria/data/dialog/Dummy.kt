package com.cafeteria.data.dialog

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

class MyCalendar {

    companion object {

        private val currentDate: LocalDate = LocalDate.now()
        private val year = currentDate.year
        private val month = currentDate.monthValue
//        private val dayOfWeek = currentDate.dayOfWeek
        private val day = currentDate.dayOfMonth

        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val yearMonthFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월")

        fun findAllCalendar(): MutableMap<String, Any> {
            val yearMonth = YearMonth.of(year, month) // 년&월
            val daysInMonth = yearMonth.lengthOfMonth() // 해당 달의 총일수

            val total = (1..daysInMonth).map { day ->
                val date = LocalDate.of(year, month, day)
                Pair(date, date.dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, Locale.KOREAN))
            }

            val resultMap = HashMap<String, Any>()
            total.forEachIndexed { index, (date, dayOfWeek) ->
                resultMap["day${index}"] = date.dayOfMonth
                resultMap["week${index}"] = dayOfWeek.toString()
            }

            val mapList = mutableMapOf(
                "yearMonth" to yearMonth.format(yearMonthFormatter),
                "dayInMonth" to daysInMonth,
                "total" to total,
                "resultMap" to resultMap,
                "toDay" to day
            )

            return mapList
        }

    }


}

