package com.videodemons.zacksportfolio.controller

interface DropStrategy<T> {
    fun drop(currentPortfolio: List<T>): Pair<MutableList<T>, MutableList<T>>
}