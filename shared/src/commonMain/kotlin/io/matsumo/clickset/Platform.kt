package io.matsumo.clickset

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform