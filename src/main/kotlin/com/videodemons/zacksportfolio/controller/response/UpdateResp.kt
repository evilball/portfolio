package com.videodemons.zacksportfolio.controller.response

data class UpdateResp(
        val newTickers: Collection<ZacksTickerInfo>, 
        val allTickers: Collection<ZacksTickerInfo>)