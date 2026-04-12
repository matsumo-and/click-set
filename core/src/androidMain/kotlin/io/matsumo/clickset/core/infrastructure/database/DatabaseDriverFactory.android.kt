package io.matsumo.clickset.core.infrastructure.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.matsumo.clickset.core.ClickSetDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = ClickSetDatabase.Schema,
            context = context,
            name = "clickset.db",
        )
}
