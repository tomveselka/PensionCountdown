package com.tomveselka.pensioncountdown

import com.tomveselka.pensioncountdown.utils.PensionAgeCalculator
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class PensionAgeCalculatorTest {

    var pensionAgeCalculator = PensionAgeCalculator()
    @Test
    fun male_1970() {
        val gender ="M"
        val dateOfBirth ="01/01/1970"
        val numberOfChildren="2"
        assertEquals("65r", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun male_1955() {
        val gender ="M"
        val dateOfBirth ="01/01/1955"
        val numberOfChildren="2"
        assertEquals("63r+4m", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun male_1954() {
        val gender ="M"
        val dateOfBirth ="01/01/1954"
        val numberOfChildren="2"
        assertEquals("P", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun female_1965_2_children() {
        val gender ="F"
        val dateOfBirth ="01/01/1965"
        val numberOfChildren="2"
        assertEquals("64r+8m", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun female_1960_0_children() {
        val gender ="F"
        val dateOfBirth ="01/01/1960"
        val numberOfChildren="0"
        assertEquals("64r+2m", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun female_1956_4_children() {
        val gender ="F"
        val dateOfBirth ="01/01/1956"
        val numberOfChildren="4"
        assertEquals("59r", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

    @Test
    fun female_1980_5_children() {
        val gender ="F"
        val dateOfBirth ="01/01/1980"
        val numberOfChildren="5"
        assertEquals("65", pensionAgeCalculator.calculatePension(gender, dateOfBirth,numberOfChildren))
    }

}