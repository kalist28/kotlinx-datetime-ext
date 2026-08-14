package io.github.kalist28.datetime.ext.formatted.bundle

/**
 * Marks declarations in kotlinx-datetime-fbundle as experimental.
 *
 * These APIs are subject to change and may be modified or removed in future versions.
 * Use at your own risk, as backward compatibility is not guaranteed.
 *
 * @suppress Documentation is not generated for experimental declarations.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.WARNING,
    message = "This API is experimental and may change in future versions"
)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.TYPEALIAS
)
annotation class DateTimeFormattedBundleExperimentalApi
