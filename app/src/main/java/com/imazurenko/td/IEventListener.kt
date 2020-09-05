package com.imazurenko.td

interface IEventListener<T> {
    fun onEvent(args: T)
}