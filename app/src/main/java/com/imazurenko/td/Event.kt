package com.imazurenko.td

class Event {
    private val _subscribers: MutableList<IEventListener> = mutableListOf()

    fun add(e: IEventListener) {
        this._subscribers.add(e)
    }

    fun remove(e: IEventListener) {
        this._subscribers.remove(e)
    }

    fun invoke() = _subscribers.forEach { it.onEvent() }
}
