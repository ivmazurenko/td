package com.imazurenko.td

class Event<T> {
    private val _subscribers: MutableList<IEventListener<T>> = mutableListOf()

    fun add(e: IEventListener<T>) {
        this._subscribers.add(e)
    }

    fun remove(e: IEventListener<T>) {
        this._subscribers.remove(e)
    }

    fun invoke(args: T) = _subscribers.forEach { it.onEvent(args) }
}
