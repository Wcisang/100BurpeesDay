package com.example.williamcisang.a100burpeesday.util

/**
 * Created by WCisang on 15/09/2018.
 */

object BurpeeCalculator {

    fun isLate(day: Int, completedBurpee: Int) : Boolean{
        var x = (day * (day + 1)) / 2
        return completedBurpee < x
    }

    fun getMissingBurpees(day: Int, completedBurpee: Int) : Int {
        var x = (day * (day + 1)) / 2
        var y  = x - day
        var z = y - completedBurpee
        var w =  z + day
        return if (w < 0) 0 else w
    }
}