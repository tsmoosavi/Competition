package com.example.competition

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.competition.databinding.FragmentPlayBinding

class playFragment : Fragment() {
    lateinit var binding: FragmentPlayBinding
    val playVm: competitionVm by activityViewModels()
    var  btnArray = arrayListOf<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "Game"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showDetaile() // create the page like it was before turning the screen
        add() // adding 4 button for answering to btnArray
        startGame() // check for make some buttons and views visible or unvisible
        diceClick() // manage events after clicking on dice button every time

    }

    private fun showDetaile() {
        if (playVm.startSentence){
            binding.start?.visibility = View.VISIBLE
            binding.leftOver.visibility = View.GONE
        }else{
            binding.leftOver.visibility = View.VISIBLE
            binding.start?.visibility = View.GONE
        }
        binding.aNumberTxv .text = playVm.a.toString()
        binding.bNumberTxv .text = playVm.b.toString()
        binding.answer1Btn .text = playVm.buttonAnswer1Text
        binding.answer2Btn .text = playVm.buttonAnswer2Text
        binding.answer3Btn .text = playVm.buttonAnswer3Text
        binding.answer4Btn .text = playVm.buttonAnswer4Text
        binding.answer1Btn .isClickable = playVm.enableStatus
        binding.answer2Btn .isClickable = playVm.enableStatus
        binding.answer3Btn .isClickable = playVm.enableStatus
        binding.answer4Btn .isClickable = playVm.enableStatus
        binding.scoreTxv.text = playVm.score.toString()
        binding.levelTxv?.text = playVm.questionNumber.toString()
        for (i in 0 until btnArray.size ){
            if (i == playVm.chosenButton){
                btnArray[i].setBackgroundColor(resources.getColor(playVm.color))
            }else{
                btnArray[i].setBackgroundColor(resources.getColor(R.color.purple_500))
            }
        }



//        if (playVm.green > -1){
//            for (i in 0 until btnArray.size ){
//                if (i == playVm.green){
//                    btnArray[i].setBackgroundColor(resources.getColor(R.color.green))
//                }else{
//                    btnArray[i].setBackgroundColor(resources.getColor(R.color.purple_500))
//                }
//            }
//        }
//        if (playVm.red > -1){
//            for (i in 0 until btnArray.size ){
//                if (i == playVm.red){
//                    btnArray[i].setBackgroundColor(resources.getColor(R.color.red))
//                }else{
//                    btnArray[i].setBackgroundColor(resources.getColor(R.color.purple_500))
//                }
//            }
//        }



//        binding.answer1Btn.setBackgroundColor(resources.getColor(playVm.color))
//        binding.answer2Btn.setBackgroundColor(resources.getColor(playVm.color))
//        binding.answer3Btn.setBackgroundColor(resources.getColor(playVm.color))
//        binding.answer4Btn.setBackgroundColor(resources.getColor(playVm.color))

    }

    private fun saveDetaile() {
        playVm.buttonAnswer1Text = binding.answer1Btn .text.toString()
        playVm.buttonAnswer2Text = binding.answer2Btn .text .toString()
        playVm.buttonAnswer3Text = binding.answer3Btn .text.toString()
        playVm.buttonAnswer4Text = binding.answer4Btn .text.toString()
    }

    private fun startGame() {

        if (playVm.questionNumber == 0){
            playVm.startSentence = true
            binding.start?.visibility = View.VISIBLE
            binding.aNumberTxv.visibility = View.GONE
            binding.bNumberTxv.visibility = View.GONE
            binding.leftOver.visibility = View.GONE
            for (button in btnArray){
                button.visibility = View.GONE
            }
            dice()
        }
    }

    private fun diceClick() {
        playVm.chosenButton = -1
        playVm.enableStatus = true
        binding.diceBtn.setOnClickListener {
            playVm.startSentence = false
            playVm.questionNumber++
            binding.start?.visibility = View.GONE
            binding.aNumberTxv.visibility = View.VISIBLE
            binding.bNumberTxv.visibility = View.VISIBLE
            binding.leftOver.visibility = View.VISIBLE
            for (button in btnArray){
                button.visibility = View.VISIBLE
            }
            if (playVm.questionNumber>=6){
                if (playVm.maxScore < playVm.score){
                    playVm.maxScore = playVm.score
                }
                playVm.startSentence = true
                playVm.questionNumber=0
               findNavController().navigate(R.id.action_playFragment_to_resultFragment)
            }
            else {
                enableButton()
                for (button in btnArray){
                    button.setBackgroundColor(resources.getColor(R.color.purple_500))
                }
                dice()
            }
        }
    }


    private fun dice() {
        playVm.enableStatus = true
        playVm.numberList.clear()
        binding.levelTxv?.text = playVm.questionNumber.toString()
        playVm.a = playVm.randomNumberA()
        playVm.b =playVm.randomNumberB()
        playVm.mode=playVm.a%playVm.b
        playVm.numberList.add(playVm.mode)
        binding.aNumberTxv.text = playVm.a.toString()
        binding.bNumberTxv.text = playVm.b.toString()
        binding.scoreTxv.text = playVm.score.toString()
        val numRandom = (0..3).random()
        setTextButton(numRandom)
        saveDetaile()
//        for (button in btnArray){
//            button.setOnClickListener {  }
//        }
        binding.answer1Btn.setOnClickListener {
            correctAnswer(binding.answer1Btn,1)
        }
        binding.answer2Btn.setOnClickListener {
            correctAnswer(binding.answer2Btn,2)
        }
        binding.answer3Btn.setOnClickListener {
            correctAnswer(binding.answer3Btn,3)
        }
        binding.answer4Btn.setOnClickListener {
            correctAnswer(binding.answer4Btn,4)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun correctAnswer(button: Button, indice: Int) {
        if(button.text == playVm.mode.toString()){
        Toast.makeText(context,"correct",Toast.LENGTH_SHORT).show()
        playVm.score+=5
        binding.scoreTxv.text=playVm.score.toString()
        button.setBackgroundColor(resources.getColor(R.color.green))
            playVm.chosenButton = indice
            playVm.color = R.color.green
//        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
        disableButton()
    } else{
        Toast.makeText(context,"incorrect",Toast.LENGTH_SHORT).show()
        playVm.score-=2
        binding.scoreTxv.text = playVm.score.toString()
        button.setBackgroundColor(resources.getColor(R.color.red))
            playVm.chosenButton = indice
//      button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
            playVm.color = R.color.red
//            for(i in 0 until btnArray.size){
//                if(button.text == btnArray[i].text ) {
//                    playVm.red = i
//                }
//            }
        disableButton()
    }
}
    private fun enableButton() {
        for (button in btnArray){
            button.isClickable = true
            playVm.enableStatus = true
        }
    }

    private fun disableButton() {
        for (button in btnArray){
            button.isClickable =false
            playVm.enableStatus = false
        }
    }

    fun setTextButton(number:Int){
        var j = 0
        for (i in btnArray.indices){
            if (number == i){
                btnArray[i].text = playVm.mode.toString()

            }else{
                var duplicate = true
                while (duplicate){
                   var randomNumber = playVm.getRandom()
                    if (randomNumber !in playVm.numberList) {
                        playVm.numberList.add(randomNumber)
                        btnArray[i].text = randomNumber.toString()
                        duplicate = false
                    }
                }
            }
        }
    }

    fun add(){
        btnArray.add(binding.answer1Btn)
        btnArray.add(binding.answer2Btn)
        btnArray.add(binding.answer3Btn)
        btnArray.add(binding.answer4Btn)
    }
}