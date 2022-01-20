package com.xzy.utils.sp.kotlin

// 实际开发中直接使用 Sp 存储各种类型的值
object Sp : Preferences("projectName") {
    var magicNumber by intPref(0) // The property name is used as the key.
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
    var favoriteCharacter by stringOrNullPref()
}