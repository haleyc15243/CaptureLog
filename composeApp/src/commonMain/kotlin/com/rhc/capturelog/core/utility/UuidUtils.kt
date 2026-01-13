package com.rhc.capturelog.core.utility

import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

object UuidUtils {
    /**
     * Converts a string to a deterministic Uuid that will always be the same for the given input.
     *
     * @param input The string to convert to a Uuid
     * @return A Uuid that is consistently generated from the input string
     */
    @OptIn(ExperimentalUuidApi::class)
    fun stringToUuid(input: String): Uuid {
        val bytes = input.encodeToByteArray()

        val paddedBytes = if (bytes.size >= 16) {
            bytes.sliceArray(0 until 16)
        } else {
            ByteArray(16).also {
                bytes.copyInto(it)
                for (i in bytes.size until 16) {
                    it[i] = ((bytes.foldIndexed(0) { index, acc, b ->
                        acc + (b.toInt() * (index + 1 + i))
                    }) % 256).toByte()
                }
            }
        }
        paddedBytes[6] = (paddedBytes[6].toInt() and 0x0F or 0x40).toByte()
        paddedBytes[8] = (paddedBytes[8].toInt() and 0x3F or 0x80).toByte()
        return Uuid.fromByteArray(paddedBytes)
    }
}