package com.videodemons.portfolio.service.zacks

import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class TxtTickers(private val tickersPath: String) : Tickers {
    
    override fun readTickers(): MutableCollection<String> =
            Files.readAllLines(Paths.get(tickersPath))

    override fun removeTicker(ticker: String): Collection<String> {
        val tickers = readTickers()
        tickers.remove(ticker)
        return saveTickers(tickers)
    }

    override fun addTicker(ticker: String) = addTickers(setOf(ticker))

    override fun addTickers(tickers: Collection<String>): Collection<String> {
        val portfolio = TreeSet(readTickers())
        portfolio.addAll(tickers)
        return saveTickers(portfolio)
    }

    override fun saveTickers(tickers: Collection<String>): Collection<String> {
        val localTickers = tickers.toMutableList()
        localTickers.sort()
        val writer = FileWriter(File(tickersPath))
        writer.use { w ->
            localTickers.forEach { ticker ->
                w.write("$ticker\n")
            }
        }
        return tickers
    }
}