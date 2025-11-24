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