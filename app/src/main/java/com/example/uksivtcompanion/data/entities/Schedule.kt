package com.example.uksivtcompanion.data.entities

class Schedule {
    object Monday : Day()
    object Tuesday : Day()
    object Wednesday : Day()
    object Thursday : Day()
    object Friday : Day()
    object Saturday : Day()
}

open class Day{
    lateinit var lessons : MutableList<Lesson>
}

data class Lesson (val Name: String,
                   val Cab: Int,
                   val Time: String
)