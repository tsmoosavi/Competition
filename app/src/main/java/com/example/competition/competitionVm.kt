package com.example.competition

import androidx.lifecycle.ViewModel

class competitionVm: ViewModel() {
    var score=0
    var questionNumber=0
    var maxScore=0
    var a =-1
    var b =-1
    fun randomNumberA():Int{
        a = (1 .. 100).random()
        return a
    }
    fun randomNumberB():Int{

        b = (1 .. 10).random()
        return b
    }
    fun getRandom():Int{
        return (1 .. 10).random()
    }
    fun answer():Int{
        return a % b
    }

}