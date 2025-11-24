package io.github.kalist28.datetime.ext.translater.ru

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

class RussianDateTimeTranslatorTest {
    @Test
    fun testMonthName() {
        assertEquals("Январь", RussianDateTimeTranslator.getMonthName(Month.JANUARY))
        assertEquals("Февраль", RussianDateTimeTranslator.getMonthName(Month.FEBRUARY))
        assertEquals("Декабрь", RussianDateTimeTranslator.getMonthName(Month.DECEMBER))
    }

    @Test
    fun testMonthNameAbbreviated() {
        assertEquals("Янв", RussianDateTimeTranslator.getMonthName(Month.JANUARY, abbreviated = true))
        assertEquals("Фев", RussianDateTimeTranslator.getMonthName(Month.FEBRUARY, abbreviated = true))
        assertEquals("Дек", RussianDateTimeTranslator.getMonthName(Month.DECEMBER, abbreviated = true))
    }

    @Test
    fun testDayOfWeekName() {
        assertEquals("Понедельник", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.MONDAY))
        assertEquals("Вторник", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.TUESDAY))
        assertEquals("Воскресенье", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.SUNDAY))
    }

    @Test
    fun testDayOfWeekNameAbbreviated() {
        assertEquals("Пн", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.MONDAY, abbreviated = true))
        assertEquals("Вт", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.TUESDAY, abbreviated = true))
        assertEquals("Вс", RussianDateTimeTranslator.getDayOfWeekName(DayOfWeek.SUNDAY, abbreviated = true))
    }
}


