package com.locker.feature.gamebot.bot

import com.locker.core.models.Difficulty


object BotFactory {
    fun createBot(difficulty: Difficulty): TicTacToeBot {
        return when (difficulty) {
            Difficulty.EASY -> EasyBot()
            Difficulty.MEDIUM -> MediumBot()
            Difficulty.HARD -> HardBot()
        }
    }
}
