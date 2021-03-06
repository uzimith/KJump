package com.werfad

import org.junit.Assert.assertEquals
import org.junit.Test

class KeyTagsGeneratorTest {

    @Test
    fun testCreateKeyTagsTree() {
        val res = KeyTagsGenerator.createTagsTree(756, "abcdefghijklmnopqrstuvwxyz")

        assertEquals(res.size.toLong(), 756)
        println(res.size)
        println(res)

        val res1 = KeyTagsGenerator.createTagsTree(0, "abcdefghijklmnopqrstuvwxyz")
        assertEquals(res1.size, 0)
    }
}