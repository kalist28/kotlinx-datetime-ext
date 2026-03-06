![kotlin-version](https://img.shields.io/badge/kotlin-2.2.0-blue.svg)
![kotlinx-datetime-version](https://img.shields.io/badge/kotlinx--datetime-0.7.1-blue.svg)

![badge-iOS](https://img.shields.io/badge/platform-iOS-lightgrey.svg)
![badge-Android](https://img.shields.io/badge/platform-Android-green.svg)
![badge-JVM](https://img.shields.io/badge/platform-JVM-orange.svg)
![badge-macOS](https://img.shields.io/badge/platform-macOS-lightgrey.svg)
![badge-Linux](https://img.shields.io/badge/platform-Linux-yellow.svg)
![badge-Windows](https://img.shields.io/badge/platform-Windows-blue.svg)
![badge-Web](https://img.shields.io/badge/platform-Web-red.svg)
![badge-Wasm](https://img.shields.io/badge/platform-Wasm-purple.svg)

# kotlinx-datetime-ext

Kotlin Multiplatform library extending `kotlinx-datetime` functionality. Provides convenient extensions and utilities for working with dates and times.

## Usage

This package is published to Maven Central Repository: [kotlinx-datetime-ext on Maven Central](https://central.sonatype.com/artifact/io.github.kalist28/kotlinx-datetime-ext)

Depend on the latest version by declaring this in `libs.versions.toml`:

```toml
[versions]
kotlinx-datetime-ext = "0.1.0"

[libraries]
kotlinx-datetime-ext = { group = "io.github.kalist28", name = "kotlinx-datetime-ext", version.ref = "kotlinx-datetime-ext" }
```

Or directly in `build.gradle.kts`:

```kotlin
dependencies {
    implementation("io.github.kalist28:kotlinx-datetime-ext:0.1.0")
}
```

## Features

### 📅 Extensions for getting current date and time

```kotlin
val now = instantNow()           // Instant
val today = dateNow()            // LocalDate
val time = timeNow()             // LocalTime
val dateTime = dateTimeNow()     // LocalDateTime
val utcDateTime = dateTimeNow(TimeZone.UTC)  // LocalDateTime in UTC
```

### 🔄 Converters between date/time and timestamp

```kotlin
// LocalDateTime/LocalDate → timestamp
val timestamp = dateTime.timestamp        // UTC timestamp in milliseconds
val timestampUtc = dateTime.timestampUtc  // System timezone timestamp

// timestamp → LocalDateTime/LocalDate
val dateTime = 1672574400000L.toDateTime()        // System timezone
val dateTimeUtc = 1672574400000L.toLocalDateTime() // System timezone
val date = 1672574400000L.toLocalDate()           // System timezone
val dateTime2 = 1672574400000L.asDateTime         // System timezone (property)
```

### ➕ Mathematical operations

```kotlin
// Adding and subtracting Duration
val future = dateTime + 5.days
val past = dateTime - 3.hours
val futureDate = date + 2.days
val pastTime = time - 1.hours
```

### ❓ Queries and checks

```kotlin
// Time checks
val isPast = dateTime.isPast        // true if in the past
val isPastDate = date.isPast        // true if date is in the past

// Day of week checks
val isHoliday = date.isHoliday      // true if Saturday or Sunday
val isWeekday = date.isWeekday      // true if weekday

// Year checks
val isLeap = date.isLeapYear        // true if leap year
val isLeapYear = 2020.isLeap()      // true if leap year

// Day counts
val daysInMonth = date.daysInMonth  // Number of days in month
val daysInYear = date.daysInYear    // Number of days in year (365 or 366)
```

### 🛠️ Helper functions

```kotlin
// Start and end of day
val start = date.atStartOfDay()     // LocalDateTime(2023, 12, 17, 0, 0)
val end = date.atEndOfDay()         // LocalDateTime(2023, 12, 17, 23, 59, 59, 999999999)

// Duration calculation
val duration = first durationUntil second  // Duration between two LocalDateTime
val daysDuration = date1 durationUntil date2  // Duration between two LocalDate
```

### ⏰ LocalTime extensions

```kotlin
// Conversion to minutes/seconds
val minutes = time.minutes          // Minutes since midnight
val seconds = time.seconds          // Seconds since midnight
val timeFromMinutes = 150.asLocalTime  // LocalTime from minutes

// Arithmetic
val diff = time1 minus time2        // Difference between times
val sum = time1 plus time2          // Sum of times (wraps around 24 hours)

// Rounding and formatting
val rounded = time.roundToHour     // Round to nearest hour
val formatted = time.formatToClock()  // Format to "HH:mm"
val parsed = "09:05".clockToLocalTime()  // Parse from "HH:mm" or "HH:mm:ss" string
```

### 📦 DateTimeBundleFormatter

Formats `LocalDateTime` into a comprehensive `DateTimeBundle` with all representations at once. Requires adding the `kotlinx-datetime-ext-formatted-bundle` dependency:

```toml
[versions]
kotlinx-datetime-ext-formatted-bundle = "0.1.0"

[libraries]
kotlinx-datetime-ext-formatted-bundle = { group = "io.github.kalist28", name = "kotlinx-datetime-ext-formatted-bundle", version.ref = "kotlinx-datetime-ext-formatted-bundle" }
```

```kotlin
val formatter = DateTimeBundleFormatter(
    timezone = TimeZone.of("Europe/Moscow"),
    strings = RussianDateTimeBundleStrings() // or EnglishDateTimeBundleStrings()
)

val bundle = formatter.from(LocalDateTime(2025, 9, 20, 19, 11, 8))

bundle.timestamp            // 1726851068
bundle.diffForHumans        // "5 минут назад" / "5 minutes ago"
bundle.dateForHuman         // "2025-09-20"
bundle.dateForHumanFull     // "20 сентября 2025г." / "20 September 2025"
bundle.dateForHumanFullWithTime // "20 сентября 2025г. в 19:11:08" / "20 September 2025 at 19:11:08"
bundle.iso                  // "2025-09-20T19:11:08.000000+03:00"
bundle.time                 // "19:11:08"
bundle.timeShort            // "19:11"
bundle.isFuture             // false
```

#### DateTimeBundle methods

```kotlin
// Date/time checks
bundle.isToday(TimeZone.UTC)              // true if today
bundle.isYesterday(TimeZone.UTC)          // true if yesterday
bundle.isTomorrow(TimeZone.UTC)           // true if tomorrow
bundle.isThisWeek(TimeZone.UTC)           // true if in same week
bundle.isThisYear(TimeZone.UTC)           // true if in same year

// Comparisons
bundle.isBefore(otherBundle)              // timestamp comparison
bundle.isAfter(otherBundle)               // timestamp comparison

// Date formatting
bundle.dateShort(strings, tz)             // "20 сен" (day and short month)
bundle.dateWithDayOfWeek(strings, tz)     // "Суббота, 20 сентября" (day of week + date)
bundle.relative()                         // "5 минут назад"

// Time calculations
bundle.minutesUntil()                     // Minutes until/since (negative if past)
bundle.hoursUntil()                       // Hours until/since
bundle.daysUntil()                        // Days until/since
```

#### Built-in localization

| Class | Language |
|-------|----------|
| `RussianDateTimeBundleStrings` | Русский |
| `EnglishDateTimeBundleStrings` | English |

Custom localization: implement `DateTimeBundleStrings` interface.

### 🔢 Pluralization utilities

Helper functions for proper pluralization in different languages following CLDR (Common Locale Data Repository) standards.

```kotlin
// Russian pluralization (3 forms)
PluralUtils.pluralize(1, "минута", "минуты", "минут")    // "1 минута"
PluralUtils.pluralize(5, "минута", "минуты", "минут")    // "5 минут"
PluralUtils.pluralize(21, "минута", "минуты", "минут")   // "21 минута"

// English pluralization (2 forms)
PluralUtils.pluralizeEnglish(1, "minute", "minutes")     // "1 minute"
PluralUtils.pluralizeEnglish(5, "minute", "minutes")     // "5 minutes"

// Advanced: using CLDR categories
val forms = mapOf(
    PluralCategory.ONE to "минута",
    PluralCategory.FEW to "минуты",
    PluralCategory.MANY to "минут"
)
PluralUtils.pluralizeRussian(2, forms, "минут")          // "2 минуты"

// Determine plural category
getPluralCategoryRussian(1)   // PluralCategory.ONE
getPluralCategoryRussian(5)   // PluralCategory.MANY
getPluralCategoryEnglish(2)   // PluralCategory.OTHER
```

## Supported Platforms

| Platform   | Status |
|------------|--------|
| Android    | ✅     |
| iOS        | ✅     |
| JVM        | ✅     |
| macOS      | ✅     |
| Linux      | ✅     |
| Windows    | ✅     |
| JavaScript | ✅     |
| Wasm       | ✅     |