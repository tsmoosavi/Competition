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
//    var a=0
//    var b=0
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
//        saveDetaile()

        showDetaile()

        binding.levelTxv?.text = playVm.questionNumber.toString()
        binding.scoreTxv.text = playVm.score.toString()
        binding.aNumberTxv .text = playVm.a.toString()
        binding.bNumberTxv .text = playVm.b.toString()
        if (playVm.startSentence){
            binding.start?.visibility = View.VISIBLE
            binding.leftOver.visibility = View.GONE
        }else{
            binding.leftOver.visibility = View.VISIBLE
            binding.start?.visibility = View.GONE
        }
        add()
        startGame()
        diceClick()

    }

    private fun showDetaile() {
        binding.aNumberTxv .text = playVm.a.toString()
        binding.bNumberTxv .text = playVm.b.toString()
        binding.answer1Btn .text = playVm.buttonAnswer1Text
        binding.answer2Btn .text = playVm.buttonAnswer2Text
        binding.answer3Btn .text = playVm.buttonAnswer3Text
        binding.answer4Btn .text = playVm.buttonAnswer4Text
        binding.scoreTxv.text = playVm.score.toString()

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
                for (button in btnArray) {
//                    button.setBackgroundColor(resources.getColor(R.color.purple_500))
                }
                enableButton()
                for (button in btnArray){
                    button.setBackgroundColor(resources.getColor(R.color.purple_500))
                }
                dice()
            }
        }
    }

    private fun enableButton() {
        for (button in btnArray){
            button.isEnabled=true
        }
    }

    private fun dice() {
        binding.levelTxv?.text = playVm.questionNumber.toString()
        playVm.a = playVm.randomNumberA()
        playVm.b =playVm.randomNumberB()
        playVm.mode=playVm.a%playVm.b
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
            correctAnswer(binding.answer1Btn)
        }
        binding.answer2Btn.setOnClickListener {
            correctAnswer(binding.answer2Btn)
        }
        binding.answer3Btn.setOnClickListener {
            correctAnswer(binding.answer3Btn)
        }
        binding.answer4Btn.setOnClickListener {
            correctAnswer(binding.answer4Btn)
        }
    }

    @SuppressLint("ResourceAsColor")
    fun correctAnswer(button: Button) = if(button.text == playVm.mode.toString()){
        Toast.makeText(context,"correct",Toast.LENGTH_SHORT).show()
        playVm.score+=5
        binding.scoreTxv.text=playVm.score.toString()
        button.setBackgroundColor(resources.getColor(R.color.green))
//        button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))

        disableButton()
    } else{
        Toast.makeText(context,"incorrect",Toast.LENGTH_SHORT).show()
        playVm.score-=2
        binding.scoreTxv.text = playVm.score.toString()
        button.setBackgroundColor(resources.getColor(R.color.red))
//      button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
        disableButton()
    }

    private fun disableButton() {
        for (button in btnArray){
            button.isEnabled=false
        }
    }

    fun setTextButton(number:Int){
        var j = 0
        for (i in btnArray.indices){
            if (number == i){
                btnArray[i].text = playVm.mode.toString()
            }else{
                btnArray[i].text = playVm.getRandom().toString()
//                playVm.numberList[j] = btnArray[i].text.toString().toInt()
//                j++
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