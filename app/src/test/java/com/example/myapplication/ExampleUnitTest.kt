package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testFirstInFirstOut() {
        val result = firstInFirstOutAlgorithm(
            listOf("1", "2", "1", "2", "3", "4"),
            3
        )
        println(result)
        assertEquals(4, 2 + 2)
    }

    fun leastRecentlyUsed() {
        val result = leastRecentlyUsedAlgorithm(
            listOf("7", "0", "1", "2", "0", "3", "0", "4", "2", "3", "0", "3", "2"),
            3
        )
        println(result)
        assertEquals(4, 2 + 2)
    }

    fun optimal() {
        val result = optimalAlgorithm(
            listOf("7", "0", "1", "2", "0", "3", "0", "4", "2", "3"),
            3
        )
        println(result)
        assertEquals(4, 2 + 2)
    }

}