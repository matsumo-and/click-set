package io.matsumo.clickset.core.infrastructure.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.matsumo.clickset.core.ClickSetDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver =
        NativeSqliteDriver(
            schema = ClickSetDatabase.Schema,
            name = "clickset.db",
        )
}
