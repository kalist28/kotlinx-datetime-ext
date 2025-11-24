package io.github.kalist28.datetime.ext.translater.en

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

class EnglishDateTimeTranslatorTest {
    @Test
    fun testMonthName() {
        assertEquals("January", EnglishDateTimeTranslator.getMonthName(Month.JANUARY))
        assertEquals("February", EnglishDateTimeTranslator.getMonthName(Month.FEBRUARY))
        assertEquals("December", EnglishDateTimeTranslator.getMonthName(Month.DECEMBER))
    }

    @Test
    fun testMonthNameAbbreviated() {
        assertEquals("Jan", EnglishDateTimeTranslator.getMonthName(Month.JANUARY, abbreviated = true))
        assertEquals("Feb", EnglishDateTimeTranslator.getMonthName(Month.FEBRUARY, abbreviated = true))
        assertEquals("Dec", EnglishDateTimeTranslator.getMonthName(Month.DECEMBER, abbreviated = true))
    }

    @Test
    fun testDayOfWeekName() {
        assertEquals("Monday", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.MONDAY))
        assertEquals("Tuesday", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.TUESDAY))
        assertEquals("Sunday", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.SUNDAY))
    }

    @Test
    fun testDayOfWeekNameAbbreviated() {
        assertEquals("Mon", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.MONDAY, abbreviated = true))
        assertEquals("Tue", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.TUESDAY, abbreviated = true))
        assertEquals("Sun", EnglishDateTimeTranslator.getDayOfWeekName(DayOfWeek.SUNDAY, abbreviated = true))
    }
}


