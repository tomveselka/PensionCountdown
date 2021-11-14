package com.tomveselka.pensioncountdown.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PensionAgeCalculator {
    fun calculatePension (gender: String, dateOfBirthString: String, numberOfChildren:String): String{
        //https://www.kurzy.cz/vypocet/tabulka-odchodu-do-duchodu/
        //first row 1955, last row 1971
        val pensionTable = arrayOf(
            arrayOf("63r+4m","62r+8m","61r+4m","60r","58r+8m","57r+4m"),
            arrayOf("63r+6m","63r+2m","61r+8m","60r+4m","59r","57r+8m"),
            arrayOf("63r+8m","63r+8m","62r+2m","60r+8m","59r+4m","58r"),
            arrayOf("63r+10m","63r+10m","62r+8m","61r+2m","59r+8m","58r+4m"),
            arrayOf("64r","64r","63r+2m","61r+8m","60r+2m","58r+8m"),
            arrayOf("64r+2m","64r+2m","63r+8m","62r+2m","60r+8m","59r+2m"),
            arrayOf("64r+4m","64r+4m","64r+2m","62r+8m","61r+2m","59r+8m"),
            arrayOf("64r+6m","64r+6m","64r+6m","63r+2m","61r+8m","60r+2m"),
            arrayOf("64r+8m","64r+8m","64r+8m","63r+8m","62r+2m","60r+8m"),
            arrayOf("64r+10m","64r+10m","64r+10m","64r+2m","62r+8m","61r+2m"),
            arrayOf("65r","65r","65r","64r+8m","63r+2m","61r+8m"),
            arrayOf("65r","65r","65r","65r","63r+8m","62r+2m"),
            arrayOf("65r","65r","65r","65r","64r+2m","62r+8m"),
            arrayOf("65r","65r","65r","65r","64r+8m","63r+2m"),
            arrayOf("65r","65r","65r","65r","65r","63r+8m"),
            arrayOf("65r","65r","65r","65r","65r","64r+2m"),
            arrayOf("65r","65r","65r","65r","65r","64r+8m")
        )
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateOfBirth = LocalDate.parse(dateOfBirthString, formatter)
        val yearOfBirth=dateOfBirth.year
        if (yearOfBirth>1971){
            return "65"
        }else if (yearOfBirth<1955){
            return "P"
        }else{
            if (gender=="M"){
                return pensionTable[yearOfBirth-1955][0]
            }else{
                if (numberOfChildren.toInt()==0) {
                    return pensionTable[yearOfBirth - 1955][1]
                }else if (numberOfChildren.toInt()==1) {
                    return pensionTable[yearOfBirth - 1955][2]
                }else if (numberOfChildren.toInt()==2) {
                    return pensionTable[yearOfBirth - 1955][3]
                }else if (numberOfChildren.toInt() in 3..4) {
                    return pensionTable[yearOfBirth - 1955][4]
                }else if (numberOfChildren.toInt()==5) {
                    return pensionTable[yearOfBirth - 1955][5]
                }
            }
        }

        return "65"
    }
}