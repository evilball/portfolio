package com.videodemons.portfolio.controller.response

data class UpdateResp(
        val newTickers: Collection<ZacksTickerInfo>, 
        val allTickers: Collection<ZacksTickerInfo>)