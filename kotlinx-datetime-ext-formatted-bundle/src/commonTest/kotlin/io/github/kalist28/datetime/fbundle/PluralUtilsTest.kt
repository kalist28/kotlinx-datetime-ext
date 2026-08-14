package io.github.kalist28.datetime.fbundle

import io.github.kalist28.datetime.ext.formatted.bundle.DateTimeFormattedBundleExperimentalApi
import io.github.kalist28.datetime.ext.formatted.bundle.PluralCategory
import io.github.kalist28.datetime.ext.formatted.bundle.PluralUtils
import io.github.kalist28.datetime.ext.formatted.bundle.getPluralCategoryEnglish
import io.github.kalist28.datetime.ext.formatted.bundle.getPluralCategoryRussian
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(DateTimeFormattedBundleExperimentalApi::class)
class PluralUtilsTest {

    @Test
    fun testSingularForm() {
        assertEquals("1 минута", PluralUtils.pluralize(1, "минута", "минуты", "минут"))
        assertEquals("21 минута", PluralUtils.pluralize(21, "минута", "минуты", "минут"))
        assertEquals("101 минута", PluralUtils.pluralize(101, "минута", "минуты", "минут"))
    }

    @Test
    fun testFewForm() {
        assertEquals("2 минуты", PluralUtils.pluralize(2, "минута", "минуты", "минут"))
        assertEquals("3 минуты", PluralUtils.pluralize(3, "минута", "минуты", "минут"))
        assertEquals("4 минуты", PluralUtils.pluralize(4, "минута", "минуты", "минут"))
        assertEquals("22 минуты", PluralUtils.pluralize(22, "минута", "минуты", "минут"))
    }

    @Test
    fun testManyForm() {
        assertEquals("0 минут", PluralUtils.pluralize(0, "минута", "минуты", "минут"))
        assertEquals("5 минут", PluralUtils.pluralize(5, "минута", "минуты", "минут"))
        assertEquals("10 минут", PluralUtils.pluralize(10, "минута", "минуты", "минут"))
        assertEquals("100 минут", PluralUtils.pluralize(100, "минута", "минуты", "минут"))
    }

    @Test
    fun testSpecialCases() {
        // 11-19 always use "many" form
        assertEquals("11 минут", PluralUtils.pluralize(11, "минута", "минуты", "минут"))
        assertEquals("12 минут", PluralUtils.pluralize(12, "минута", "минуты", "минут"))
        assertEquals("13 минут", PluralUtils.pluralize(13, "минута", "минуты", "минут"))
        assertEquals("14 минут", PluralUtils.pluralize(14, "минута", "минуты", "минут"))
    }

    @Test
    fun testRussianEdgeCases() {
        // Test 111 (mod 100 = 11 → many)
        assertEquals("111 минут", PluralUtils.pluralize(111, "минута", "минуты", "минут"))
        // Test 114 (mod 100 = 14 → many)
        assertEquals("114 минут", PluralUtils.pluralize(114, "минута", "минуты", "минут"))
    }

    @Test
    fun testEnglishSingular() {
        assertEquals("1 minute ago", PluralUtils.pluralizeEnglish(1, "minute ago", "minutes ago"))
        assertEquals("1 minute", PluralUtils.pluralizeEnglish(1, "minute", "minutes"))
    }

    @Test
    fun testEnglishPlural() {
        assertEquals("2 minutes ago", PluralUtils.pluralizeEnglish(2, "minute ago", "minutes ago"))
        assertEquals("5 minutes ago", PluralUtils.pluralizeEnglish(5, "minute ago", "minutes ago"))
        assertEquals("11 minutes ago", PluralUtils.pluralizeEnglish(11, "minute ago", "minutes ago"))
        assertEquals("21 minutes ago", PluralUtils.pluralizeEnglish(21, "minute ago", "minutes ago"))
    }

    @Test
    fun testGetPluralCategoryRussian() {
        // ONE category: numbers ending in 1 (except 11)
        assertEquals(PluralCategory.ONE, getPluralCategoryRussian(1))
        assertEquals(PluralCategory.ONE, getPluralCategoryRussian(21))
        assertEquals(PluralCategory.ONE, getPluralCategoryRussian(101))

        // FEW category: numbers ending in 2-4 (except 12-14)
        assertEquals(PluralCategory.FEW, getPluralCategoryRussian(2))
        assertEquals(PluralCategory.FEW, getPluralCategoryRussian(3))
        assertEquals(PluralCategory.FEW, getPluralCategoryRussian(4))
        assertEquals(PluralCategory.FEW, getPluralCategoryRussian(22))

        // MANY category: all other numbers
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(0))
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(5))
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(11))
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(12))
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(13))
        assertEquals(PluralCategory.MANY, getPluralCategoryRussian(14))
    }

    @Test
    fun testGetPluralCategoryEnglish() {
        // ONE category: only number 1
        assertEquals(PluralCategory.ONE, getPluralCategoryEnglish(1))

        // OTHER category: all other numbers
        assertEquals(PluralCategory.OTHER, getPluralCategoryEnglish(0))
        assertEquals(PluralCategory.OTHER, getPluralCategoryEnglish(2))
        assertEquals(PluralCategory.OTHER, getPluralCategoryEnglish(5))
        assertEquals(PluralCategory.OTHER, getPluralCategoryEnglish(11))
        assertEquals(PluralCategory.OTHER, getPluralCategoryEnglish(21))
    }

    @Test
    fun testSelectPluralForm() {
        val russianForms = mapOf(
            PluralCategory.ONE to "минута",
            PluralCategory.FEW to "минуты",
            PluralCategory.MANY to "минут"
        )

        assertEquals("1 минута", PluralUtils.selectPluralForm(1, ::getPluralCategoryRussian, russianForms, "минут"))
        assertEquals("2 минуты", PluralUtils.selectPluralForm(2, ::getPluralCategoryRussian, russianForms, "минут"))
        assertEquals("5 минут", PluralUtils.selectPluralForm(5, ::getPluralCategoryRussian, russianForms, "минут"))
    }

    @Test
    fun testSelectPluralFormEnglish() {
        val englishForms = mapOf(
            PluralCategory.ONE to "minute",
            PluralCategory.OTHER to "minutes"
        )

        assertEquals("1 minute", PluralUtils.selectPluralForm(1, ::getPluralCategoryEnglish, englishForms, "minutes"))
        assertEquals("2 minutes", PluralUtils.selectPluralForm(2, ::getPluralCategoryEnglish, englishForms, "minutes"))
    }

    @Test
    fun testPluralizeRussianWithAllCategories() {
        val forms = mapOf(
            PluralCategory.ZERO to "нулевых минут",
            PluralCategory.ONE to "минута",
            PluralCategory.TWO to "две минуты",
            PluralCategory.FEW to "минуты",
            PluralCategory.MANY to "минут",
            PluralCategory.OTHER to "минут"
        )

        // Test ONE category
        assertEquals("1 минута", PluralUtils.pluralizeRussian(1, forms, "минут"))
        assertEquals("21 минута", PluralUtils.pluralizeRussian(21, forms, "минут"))

        // Test FEW category
        assertEquals("2 минуты", PluralUtils.pluralizeRussian(2, forms, "минут"))
        assertEquals("4 минуты", PluralUtils.pluralizeRussian(4, forms, "минут"))

        // Test MANY category (0 and numbers >= 5, and 11-19)
        // Note: In Russian, 0 falls under MANY category, not ZERO
        assertEquals("0 минут", PluralUtils.pluralizeRussian(0, forms, "минут"))
        assertEquals("5 минут", PluralUtils.pluralizeRussian(5, forms, "минут"))
        assertEquals("11 минут", PluralUtils.pluralizeRussian(11, forms, "минут"))
    }

    @Test
    fun testPluralizeRussianWithPartialCategories() {
        // Test with only some categories defined
        val forms = mapOf(
            PluralCategory.ONE to "минута",
            PluralCategory.FEW to "минуты",
            PluralCategory.MANY to "минут"
        )

        assertEquals("1 минута", PluralUtils.pluralizeRussian(1, forms, "минут"))
        assertEquals("2 минуты", PluralUtils.pluralizeRussian(2, forms, "минут"))
        assertEquals("5 минут", PluralUtils.pluralizeRussian(5, forms, "минут"))
    }

    @Test
    fun testPluralizeEnglishFullWithAllCategories() {
        val forms = mapOf(
            PluralCategory.ZERO to "no minutes",
            PluralCategory.ONE to "minute",
            PluralCategory.TWO to "two minutes",
            PluralCategory.FEW to "few minutes",
            PluralCategory.MANY to "many minutes",
            PluralCategory.OTHER to "minutes"
        )

        // Test ONE category
        assertEquals("1 minute", PluralUtils.pluralizeEnglishFull(1, forms, "minutes"))

        // Test OTHER category (all other numbers)
        // Note: In English, 0 and all numbers != 1 fall under OTHER category, not ZERO
        assertEquals("0 minutes", PluralUtils.pluralizeEnglishFull(0, forms, "minutes"))
        assertEquals("2 minutes", PluralUtils.pluralizeEnglishFull(2, forms, "minutes"))
        assertEquals("5 minutes", PluralUtils.pluralizeEnglishFull(5, forms, "minutes"))
    }

    @Test
    fun testPluralizeEnglishFullWithPartialCategories() {
        // Test with only ONE and OTHER categories
        val forms = mapOf(
            PluralCategory.ONE to "minute",
            PluralCategory.OTHER to "minutes"
        )

        assertEquals("1 minute", PluralUtils.pluralizeEnglishFull(1, forms, "minutes"))
        assertEquals("2 minutes", PluralUtils.pluralizeEnglishFull(2, forms, "minutes"))
    }

    @Test
    fun testPluralizeRussianFallbackToDefault() {
        // Test fallback to defaultForm when category is not in map
        val forms = mapOf(
            PluralCategory.ONE to "минута"
        )

        assertEquals("2 минут", PluralUtils.pluralizeRussian(2, forms, "минут"))
        assertEquals("5 минут", PluralUtils.pluralizeRussian(5, forms, "минут"))
    }
}
