package com.rhc.capturelog.database.common

import app.cash.sqldelight.ColumnAdapter
import io.ktor.http.Url
import kotlin.time.Instant

inline fun <reified T : Any, reified U : Any> inlineValue(
    crossinline toDb: (T) -> U,
    crossinline fromDb: (U) -> T
): ColumnAdapter<T, U> {
    return object : ColumnAdapter<T, U> {
        override fun decode(databaseValue: U) = fromDb(databaseValue)
        override fun encode(value: T) = toDb(value)
    }
}

inline fun <reified T : Enum<T>> enumStringValue(): ColumnAdapter<T, String> {
    return object : ColumnAdapter<T, String> {
        override fun decode(databaseValue: String): T = enumValues<T>().first { it.name == databaseValue }
        override fun encode(value: T): String = enumValues<T>().first { it == value }.name
    }
}

inline fun <reified T : Enum<T>> enumIntValue(): ColumnAdapter<T, Long> {
    return object : ColumnAdapter<T, Long> {
        override fun decode(databaseValue: Long): T = enumValues<T>().first { it.ordinal == databaseValue.toInt() }
        override fun encode(value: T): Long = value.ordinal.toLong()
    }
}

val urlAdapter = object : ColumnAdapter<Url, String> {
    override fun decode(databaseValue: String): Url = Url(databaseValue)
    override fun encode(value: Url): String = value.toString()
}

val instantAdapter = object : ColumnAdapter<Instant, Long> {
    override fun decode(databaseValue: Long): Instant = Instant.fromEpochMilliseconds(databaseValue)
    override fun encode(value: Instant): Long = value.toEpochMilliseconds()
}