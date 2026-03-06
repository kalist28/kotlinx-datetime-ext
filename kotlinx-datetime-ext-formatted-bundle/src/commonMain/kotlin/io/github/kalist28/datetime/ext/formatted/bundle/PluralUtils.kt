@file:Suppress("unused")

/**
 * Utility functions for pluralization following CLDR (Common Locale Data Repository) standards.
 *
 * Supports multiple languages with their respective plural rules.
 * CLDR defines six plural categories:
 * - zero: Special form for 0 (rare in most languages)
 * - one: Singular form (e.g., 1 in English, 1, 21, 31... in Russian)
 * - two: Special form for 2 (rare, used in some languages like Welsh)
 * - few: Form for "small" numbers (e.g., 2-4 in Czech, 2-4, 22-24... in Russian)
 * - many: Form for "large" numbers (e.g., 11-99 in Maltese, 0, 5-20, 25-30... in Russian)
 * - other: Default form when no other category matches
 */
package io.github.kalist28.datetime.ext.formatted.bundle

/**
 * CLDR plural category.
 *
 * Represents one of the six plural forms defined by Unicode CLDR standard.
 *
 * This API is experimental and may change in future versions.
 */
@DateTimeFormattedBundleExperimentalApi
enum class PluralCategory {
    ZERO,
    ONE,
    TWO,
    FEW,
    MANY,
    OTHER
}

/**
 * Determines the plural category for a number in Russian language.
 *
 * Russian pluralization rules (CLDR):
 * - one: numbers ending in 1, but not 11 (1, 21, 31, ... 101, 121, ...)
 * - few: numbers ending in 2-4, but not 12-14 (2, 3, 4, 22, 23, 24, ... 102, 103, 104, ...)
 * - many: all other numbers (0, 5-20, 25-30, ... 11-14, 111-119, ...)
 *
 * This API is experimental and may change in future versions.
 *
 * @param n The number to categorize
 * @return The appropriate plural category for Russian
 */
@DateTimeFormattedBundleExperimentalApi
fun getPluralCategoryRussian(n: Long): PluralCategory {
    val mod100 = n % 100
    val mod10 = n % 10
    return when {
        mod100 in 11..19 -> PluralCategory.MANY
        mod10 == 1L -> PluralCategory.ONE
        mod10 in 2..4 -> PluralCategory.FEW
        else -> PluralCategory.MANY
    }
}

/**
 * Determines the plural category for a number in English language.
 *
 * English pluralization rules (CLDR):
 * - one: the number 1
 * - other: all other numbers (0, 2, 3, 4, ...)
 *
 * This API is experimental and may change in future versions.
 *
 * @param n The number to categorize
 * @return The appropriate plural category for English
 */
@DateTimeFormattedBundleExperimentalApi
fun getPluralCategoryEnglish(n: Long): PluralCategory =
    if (n == 1L) PluralCategory.ONE else PluralCategory.OTHER

/**
 * Pluralizes a number with Russian pluralization rules.
 *
 * Russian has three plural forms:
 * - Singular (one): for numbers ending in 1 (except 11)
 * - Few (few): for numbers ending in 2-4 (except 12-14)
 * - Many (many): for all other numbers
 *
 * This API is experimental and may change in future versions.
 *
 * @param n The number to pluralize
 * @param one The singular form (e.g., "минута")
 * @param few The few form (e.g., "минуты")
 * @param many The many form (e.g., "минут")
 * @return A formatted string with number and appropriate word form
 *
 * ```
 * pluralize(1, "минута", "минуты", "минут") // "1 минута"
 * pluralize(2, "минута", "минуты", "минут") // "2 минуты"
 * pluralize(5, "минута", "минуты", "минут") // "5 минут"
 * pluralize(21, "минута", "минуты", "минут") // "21 минута"
 * ```
 */
@DateTimeFormattedBundleExperimentalApi
object PluralUtils {
    /**
     * Pluralizes a number with Russian pluralization rules using three forms.
     *
     * Russian uses ONE, FEW, and MANY categories. ZERO and TWO are not used in Russian.
     *
     * This API is experimental and may change in future versions.
     *
     * @param n The number to pluralize
     * @param one The singular form (e.g., "минута")
     * @param few The few form (e.g., "минуты")
     * @param many The many form (e.g., "минут")
     * @return A formatted string with number and appropriate word form
     */
    @DateTimeFormattedBundleExperimentalApi
    fun pluralize(n: Long, one: String, few: String, many: String): String {
        val category = getPluralCategoryRussian(n)
        val form = when (category) {
            PluralCategory.ONE -> one
            PluralCategory.FEW -> few
            PluralCategory.MANY -> many
            else -> many // ZERO, TWO, OTHER fallback to many (not used in Russian)
        }
        return "$n $form"
    }

    /**
     * Pluralizes a number with Russian pluralization rules using all CLDR categories.
     *
     * This API is experimental and may change in future versions.
     *
     * @param n The number to pluralize
     * @param forms A map of plural categories to their corresponding word forms
     * @param defaultForm The fallback form when a category is not in the map
     * @return A formatted string with number and the selected form
     *
     * ```
     * val forms = mapOf(
     *     PluralCategory.ZERO to "нулевых минут",
     *     PluralCategory.ONE to "минута",
     *     PluralCategory.TWO to "две минуты",
     *     PluralCategory.FEW to "минуты",
     *     PluralCategory.MANY to "минут",
     *     PluralCategory.OTHER to "минут"
     * )
     * pluralizeRussian(0, forms, "минут") // "0 нулевых минут"
     * pluralizeRussian(1, forms, "минут") // "1 минута"
     * pluralizeRussian(5, forms, "минут") // "5 минут"
     * ```
     */
    @DateTimeFormattedBundleExperimentalApi
    fun pluralizeRussian(n: Long, forms: Map<PluralCategory, String>, defaultForm: String): String {
        val category = getPluralCategoryRussian(n)
        val form = forms[category] ?: defaultForm
        return "$n $form"
    }

    /**
     * Pluralizes a number with English pluralization rules.
     *
     * English has two plural forms:
     * - Singular (one): for the number 1
     * - Plural (other): for all other numbers
     *
     * This API is experimental and may change in future versions.
     *
     * @param n The number to pluralize
     * @param one The singular form (e.g., "minute")
     * @param many The plural form (e.g., "minutes")
     * @return A formatted string with number and appropriate word form
     *
     * ```
     * pluralizeEnglish(1, "minute", "minutes") // "1 minute"
     * pluralizeEnglish(2, "minute", "minutes") // "2 minutes"
     * pluralizeEnglish(5, "minute", "minutes") // "5 minutes"
     * ```
     */
    @DateTimeFormattedBundleExperimentalApi
    fun pluralizeEnglish(n: Long, one: String, many: String): String {
        val category = getPluralCategoryEnglish(n)
        val form = if (category == PluralCategory.ONE) one else many
        return "$n $form"
    }

    /**
     * Pluralizes a number with English pluralization rules using all CLDR categories.
     *
     * This API is experimental and may change in future versions.
     *
     * @param n The number to pluralize
     * @param forms A map of plural categories to their corresponding word forms
     * @param defaultForm The fallback form when a category is not in the map
     * @return A formatted string with number and the selected form
     *
     * ```
     * val forms = mapOf(
     *     PluralCategory.ZERO to "no minutes",
     *     PluralCategory.ONE to "minute",
     *     PluralCategory.TWO to "two minutes",
     *     PluralCategory.FEW to "few minutes",
     *     PluralCategory.MANY to "many minutes",
     *     PluralCategory.OTHER to "minutes"
     * )
     * pluralizeEnglishFull(0, forms, "minutes")  // "0 no minutes"
     * pluralizeEnglishFull(1, forms, "minutes")  // "1 minute"
     * pluralizeEnglishFull(2, forms, "minutes")  // "2 minutes"
     * ```
     */
    @DateTimeFormattedBundleExperimentalApi
    fun pluralizeEnglishFull(n: Long, forms: Map<PluralCategory, String>, defaultForm: String): String {
        val category = getPluralCategoryEnglish(n)
        val form = forms[category] ?: defaultForm
        return "$n $form"
    }

    /**
     * Selects the appropriate form based on plural category.
     *
     * Provides a CLDR-compliant way to select plural forms using a map of categories to forms.
     * This is useful for custom languages or when you need fine-grained control.
     *
     * This API is experimental and may change in future versions.
     *
     * @param n The number to pluralize
     * @param category The plural category function for the language
     * @param forms A map of plural categories to their corresponding word forms
     * @param defaultForm The fallback form when a category is not in the map
     * @return A formatted string with number and the selected form
     *
     * ```
     * val forms = mapOf(
     *     PluralCategory.ONE to "минута",
     *     PluralCategory.FEW to "минуты",
     *     PluralCategory.MANY to "минут"
     * )
     * selectPluralForm(5, ::getPluralCategoryRussian, forms, "минут") // "5 минут"
     * ```
     */
    @DateTimeFormattedBundleExperimentalApi
    fun selectPluralForm(
        n: Long,
        category: (Long) -> PluralCategory,
        forms: Map<PluralCategory, String>,
        defaultForm: String
    ): String {
        val cat = category(n)
        val form = forms[cat] ?: defaultForm
        return "$n $form"
    }
}
