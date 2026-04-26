package io.matsumo.clickset.core.infrastructure.util

import platform.Foundation.NSProcessInfo

private const val NANOS_PER_SECOND = 1_000_000_000.0

internal actual fun monotonicTimeNanos(): Long = (NSProcessInfo.processInfo.systemUptime * NANOS_PER_SECOND).toLong()
