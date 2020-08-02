package com.videodemons.zacksportfolio.controller.response

import org.slf4j.LoggerFactory

enum class ZacksScore(val score: Int) {
    A(1), B(2), C(3), D(4), F(5);


    companion object {
        val log = LoggerFactory.getLogger(ZacksScore::class.java)
        
        fun fromString(s: String): ZacksScore =
                try {
                    valueOf(s)
                } catch (e: Exception) {
                    log.error("{}", e)
                    F
                }
    }
}