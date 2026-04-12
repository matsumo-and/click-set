package io.matsumo.clickset.core.infrastructure.util

import platform.Foundation.NSDate

private const val MILLIS_PER_SECOND = 1000

internal actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * MILLIS_PER_SECOND).toLong()
