package com.example.competition

import androidx.lifecycle.ViewModel

class competitionVm: ViewModel() {
    var randomeNumber1 = -1
    var randomeNumber2 = -1
    var randomeNumber3 = -1
    var startSentence = true
    var numberList = arrayListOf<Int>()
    var score=0
    var mode=-1
    var questionNumber=0
    var maxScore=0
    var a =-1
    var b =-1
    var buttonAnswer1Text = ""
    var buttonAnswer2Text = ""
    var buttonAnswer3Text = ""
    var buttonAnswer4Text = ""

    fun randomNumberA():Int{
        a = (1 .. 100).random()
        return a
    }
    fun randomNumberB():Int{

        b = (1 .. 10).random()
        return b
    }
    fun getRandom():Int{
        return (1 .. 9).random()
    }
    fun answer():Int{
        return a % b
    }

}