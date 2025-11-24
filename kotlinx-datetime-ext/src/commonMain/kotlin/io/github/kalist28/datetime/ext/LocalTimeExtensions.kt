@file:Suppress("unused")

/**
 * Extensions for [LocalTime] operations.
 *
 * Provides utility functions for working with LocalTime, including
 * conversions to/from minutes, arithmetic operations, and formatting.
 */
package io.github.kalist28.datetime.ext

import kotlinx.datetime.LocalTime
import kotlin.math.max
import kotlin.math.min

/**
 * Type alias for minutes as Int.
 */
typealias Minutes = Int

/**
 * Type alias for seconds as Int.
 */
typealias Seconds = Int

/**
 * Converts this [LocalTime] to total minutes since midnight.
 *
 * ```
 * val time = LocalTime(2, 30)
 * val minutes = time.minutes // 150
 * ```
 */
val LocalTime.minutes: Minutes
    get() = hour * 60 + minute

/**
 * Converts this [LocalTime] to total seconds since midnight.
 *
 * ```
 * val time = LocalTime(2, 30)
 * val seconds = time.seconds // 150
 * ```
 */
val LocalTime.seconds: Minutes
    get() = minutes * 60

/**
 * Converts minutes since midnight to [LocalTime].
 *
 * ```
 * val minutes = 150
 * val time = minutes.asLocalTime // LocalTime(2, 30)
 * ```
 */
val Minutes.asLocalTime: LocalTime
    get() = LocalTime(this / 60, this % 60)

/**
 * Subtracts [other] from this [LocalTime].
 *
 * Returns a new [LocalTime] representing the difference.
 * If the result would be negative, it wraps around to the previous day.
 *
 * ```
 * val time1 = LocalTime(10, 30)
 * val time2 = LocalTime(8, 15)
 * val diff = time1 minus time2 // LocalTime(2, 15)
 * ```
 */
infix fun LocalTime.minus(other: LocalTime): LocalTime {
    val allMinutes = minutes - other.minutes
    val resultMinutes = if (allMinutes < 0) allMinutes + 1440 else allMinutes
    return LocalTime(max(0, resultMinutes / 60), max(0, resultMinutes % 60))
}

/**
 * Adds [other] to this [LocalTime].
 *
 * Returns a new [LocalTime]. The result wraps around at 24 hours.
 *
 * ```
 * val time1 = LocalTime(22, 30)
 * val time2 = LocalTime(2, 15)
 * val sum = time1 plus time2 // LocalTime(0, 45) (wraps around)
 * ```
 */
infix fun LocalTime.plus(other: LocalTime): LocalTime {
    val allMinutes = (minutes + other.minutes) % 1440
    return LocalTime(max(0, allMinutes / 60), max(0, allMinutes % 60))
}

/**
 * Rounds this [LocalTime] to the nearest hour.
 *
 * If minutes >= 30, rounds up to the next hour.
 * Otherwise, rounds down to the current hour.
 *
 * ```
 * val time = LocalTime(10, 45)
 * val rounded = time.roundToHour // LocalTime(11, 0)
 *
 * val time2 = LocalTime(10, 15)
 * val rounded2 = time2.roundToHour // LocalTime(10, 0)
 * ```
 */
val LocalTime.roundToHour: LocalTime
    get() = if (minute >= 30) {
        LocalTime(min(hour + 1, 23), 0)
    } else {
        LocalTime(hour, 0)
    }

/**
 * Creates a [LocalTime] from hour, minute, and optional second and nanosecond.
 *
 * ```
 * val time = localTimeFrom(10, 30) // LocalTime(10, 30, 0, 0)
 * val timeWithSeconds = localTimeFrom(10, 30, 45) // LocalTime(10, 30, 45, 0)
 * ```
 */
fun localTimeFrom(
    hour: Int = 0,
    minute: Int = 0,
    second: Int = 0,
    nanosecond: Int = 0
): LocalTime = LocalTime(hour, minute, second, nanosecond)

/**
 * Empty [LocalTime] constant (00:00:00).
 */
val emptyLocalTime = localTimeFrom()

/**
 * Formats this [LocalTime] to a clock string (HH:mm).
 *
 * ```
 * val time = LocalTime(9, 5)
 * val formatted = time.formatToClock() // "09:05"
 * ```
 */
fun LocalTime.formatToClock(): String {
    val hourStr = if (hour > 9) hour.toString() else "0$hour"
    val minuteStr = if (minute > 9) minute.toString() else "0$minute"
    return "$hourStr:$minuteStr"
}

/**
 * Parses a clock string (HH:mm or HH:mm:ss) to [LocalTime].
 *
 * ```
 * val time = "09:05".clockToLocalTime() // LocalTime(9, 5, 0)
 * val timeWithSeconds = "09:05:30".clockToLocalTime() // LocalTime(9, 5, 30)
 * ```
 */
fun String.clockToLocalTime(): LocalTime {
    val normalized =
        if (isBlank()) {
            "00:00:00"
        } else {
            if (this.count { it == ':' } == 1) "$this:00" else this
        }
    return LocalTime.parse(normalized)
}
