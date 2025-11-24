@file:Suppress("unused")

package io.github.kalist28.datetime.ext.translater.ru

import kotlinx.datetime.Month
import kotlinx.datetime.format.MonthNames

private fun getRuNames(abbreviated: Boolean = false) = Month.entries
    .map { month -> RussianDateTimeTranslator.getMonthName(month, abbreviated) }

val MonthNames.Companion.RUS
    get() = getRuNames()
        .let(::MonthNames)

val MonthNames.Companion.RUS_ABBREVIATED
    get() = getRuNames(abbreviated = true)
        .let(::MonthNames)