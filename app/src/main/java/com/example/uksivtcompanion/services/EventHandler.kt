package com.example.uksivtcompanion.services

interface  EventHandler<T> {
    fun obtainEvent(event: T)
}