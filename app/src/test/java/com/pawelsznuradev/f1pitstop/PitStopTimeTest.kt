package com.pawelsznuradev.f1pitstop


import org.junit.Test

import org.junit.Assert.*

/**
 * Created by Pawel Sznura on 28/04/2021.
 */
class PitStopTimeTest() {

    @Test
    fun `getTotalTime test1`() {
        val result = PitStopTime("1:00.000").getTotalTime()
        val expected = 60.000F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test2`() {
        val result = PitStopTime("0:32.456").getTotalTime()
        val expected = 32.456F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test3`() {
        val result = PitStopTime("1:32.456").getTotalTime()
        val expected = 92.456F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test4`() {
        val result = PitStopTime("0:00.000").getTotalTime()
        val expected = 0.0F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test5`() {
        val result = PitStopTime("23.000").getTotalTime()
        val expected = 23.0F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test6`() {
        val result = PitStopTime("12.234").getTotalTime()
        val expected = 12.234F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test7`() {
        val result = PitStopTime("2.234").getTotalTime()
        val expected = 2.234F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test8`() {
        val result = PitStopTime("0.001").getTotalTime()
        val expected = 0.001F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test9`() {
        val result = PitStopTime("1").getTotalTime()
        val expected = 1.0F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test10`() {
        val result = PitStopTime("abc").getTotalTime()
        val expected = 0.0F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test11`() {
        val result = PitStopTime("abc").getTotalTime()
        val expected = 0.0F

        assertEquals(expected, result)
    }

    @Test
    fun `getTotalTime test12`() {
        val result = PitStopTime("-1").getTotalTime()
        val expected = -1.0F

        assertEquals(expected, result)
    }


}