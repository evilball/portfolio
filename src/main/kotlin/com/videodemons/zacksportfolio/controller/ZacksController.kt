package com.videodemons.zacksportfolio.controller

import com.videodemons.zacksportfolio.service.zacks.ZacksTickerInfoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickers/zacks")
class ZacksController(
        private val zacksTickerInfoService: ZacksTickerInfoService
) {

    @GetMapping("{ticker}")
    fun ticker(@PathVariable("ticker") ticker: String) =
            ResponseEntity.ok(zacksTickerInfoService.get(ticker))
}