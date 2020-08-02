package com.videodemons.portfolio.controller

interface DropStrategy<T> {
    fun drop(currentPortfolio: List<T>): Pair<MutableList<T>, MutableList<T>>
}