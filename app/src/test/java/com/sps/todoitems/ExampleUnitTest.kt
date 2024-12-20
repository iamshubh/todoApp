package com.sps.todoitems

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun myRepeat(count: Int, action: (iteration: Int) -> Unit) {
       for (i in 1..count) {
           action.invoke(i)
       }
    }

}