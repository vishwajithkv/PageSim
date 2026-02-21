package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testFirstInFirstOut() {
        val result = firstInFirstOutAlgorithm(
            listOf("1","2","1","2","3","4"),
            3
        )
        println(result)
        assertEquals(4, 2 + 2)
    }
}