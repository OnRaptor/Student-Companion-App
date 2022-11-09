package com.example.uksivtcompanion.data.entities

data class Schedule(
    val Monday: Day = Day(),
    val Tuesday: Day = Day(),
    val Wednesday: Day = Day(),
    val Thursday: Day = Day(),
    val Friday: Day = Day(),
    val Saturday: Day = Day(),
    val Sunday: Day = Day()
)


open class Day{
    var lessons : List<Lesson> = emptyList()
}

data class Lesson(
    var Name: String,
    var Cab: String,
    var Time: String
)