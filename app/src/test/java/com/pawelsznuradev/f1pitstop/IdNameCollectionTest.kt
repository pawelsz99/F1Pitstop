package com.pawelsznuradev.f1pitstop


import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Pawel Sznura on 28/04/2021.
 */
class IdNameCollectionTest() {

    @Test
    fun `getIdByName test1`() {
        val result = IdNameCollection(
            listOf("1", "2", "3"),
            listOf("one", "two", "three")
        ).getIdByName("two")

        val expected = "2"

        assertEquals(expected, result)
    }

    @Test
    fun `getIdByName test2`() {
        val result = IdNameCollection(
            listOf("3", "2", "1"),
            listOf("three", "two", "one")
        ).getIdByName("one")

        val expected = "1"

        assertEquals(expected, result)
    }

    @Test
    fun `getIdByName test3`() {
        val result = IdNameCollection(
            listOf("3"),
            listOf("three")
        ).getIdByName("three")

        val expected = "3"

        assertEquals(expected, result)
    }

    @Test
    fun `getIdByName test4`() {
        val result = IdNameCollection(
            listOf("3", "2", "1"),
            listOf("three", "two", "one")
        ).getIdByName("four")

        val expected = ""

        assertEquals(expected, result)
    }

    @Test
    fun `getIdByName test5`() {
        val result = IdNameCollection(
            listOf("1", "2", "3", "4"),
            listOf("one", "two", "three")
        ).getIdByName("four")

        val expected = ""

        assertEquals(expected, result)
    }

    @Test
    fun `getNameById test6`() {
        val result = IdNameCollection(
            listOf("1", "2", "3"),
            listOf("one", "two", "three")
        ).getNameById("2")

        val expected = "two"

        assertEquals(expected, result)
    }

    @Test
    fun `getNameById test7`() {
        val result = IdNameCollection(
            listOf("1", "2", "3"),
            listOf("one", "two", "three")
        ).getNameById("1")

        val expected = "one"

        assertEquals(expected, result)
    }

    @Test
    fun `getNameById test8`() {
        val result = IdNameCollection(
            listOf("3"),
            listOf("three")
        ).getNameById("3")

        val expected = "three"

        assertEquals(expected, result)
    }

    @Test
    fun `getNameById test9`() {
        val result = IdNameCollection(
            listOf("3", "2", "1"),
            listOf("three", "two", "one")
        ).getNameById("4")

        val expected = ""

        assertEquals(expected, result)
    }

    @Test
    fun `getNameById test10`() {
        val result = IdNameCollection(
            listOf("3", "2", "1"),
            listOf("four", "three", "two", "one")
        ).getNameById("4")

        val expected = ""

        assertEquals(expected, result)
    }


}