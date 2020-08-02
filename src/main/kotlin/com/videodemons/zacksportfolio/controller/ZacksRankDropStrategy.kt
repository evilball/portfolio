package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.controller.response.ZacksTickerInfo
import org.springframework.stereotype.Component

@Component
class ZacksRankDropStrategy: DropStrategy<ZacksTickerInfo> {
    override fun drop(currentPortfolio: List<ZacksTickerInfo>):
            Pair<MutableList<ZacksTickerInfo>, MutableList<ZacksTickerInfo>> {
        val filteredPortfolio = arrayListOf<ZacksTickerInfo>()
        val droppedTickers = arrayListOf<ZacksTickerInfo>()
        currentPortfolio.forEach {
            if (it.rank > 3) {
                droppedTickers.add(it)
            } else {
                filteredPortfolio.add(it)
            }
        }
        return Pair(filteredPortfolio, droppedTickers)
    }
}