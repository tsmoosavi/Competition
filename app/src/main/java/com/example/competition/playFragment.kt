package com.example.competition

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.competition.databinding.FragmentPlayBinding

class playFragment : Fragment() {
    var a=0
    var b=0
    var mode=-1
    lateinit var binding: FragmentPlayBinding
    val playVm: competitionVm by activityViewModels()
    var  btnArray = arrayListOf<Button>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "play"

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
        binding.scoreTxv.text = playVm.score.toString()
        add()
        if (playVm.questionNumber == 0){
                dice()
        }
        diceClick()
    }

    private fun diceClick() {
        binding.diceBtn.setOnClickListener {
            playVm.questionNumber++
            if (playVm.questionNumber>=6){
                if (playVm.maxScore < playVm.score){
                    playVm.maxScore = playVm.score
                }
                playVm.questionNumber=1
               findNavController().navigate(R.id.action_playFragment_to_resultFragment)
            }
            else {
                for (button in btnArray) {
//                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.purple_500))
                }
                enableButton()
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
        a = playVm.randomNumberA()
        b =playVm.randomNumberB()
        mode=a%b
        binding.aNumberTxv.text = a.toString()
        binding.bNumberTxv.text = b.toString()
        binding.scoreTxv.text=playVm.score.toString()
        val numRandom = (0..3).random()
        setTextButton(numRandom)

        for (button in btnArray){
            button.setOnClickListener {  }
        }

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

    fun correctAnswer(button: Button){
        if(button.text==mode.toString()){
            //Toast.makeText(this,"correct",Toast.LENGTH_SHORT).show()
            playVm.score+=5
            binding.scoreTxv.text=playVm.score.toString()
//            button.setBackgroundColor(ContextCompat.getColor(this, R.color.green))

            disableButton()
        } else{
            //Toast.makeText(this,"incorrect",Toast.LENGTH_SHORT).show()
            playVm.score-=2
            binding.scoreTxv.text = playVm.score.toString()
//            button.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
            disableButton()
        }

    }

    private fun disableButton() {
        for (button in btnArray){
            button.isEnabled=false
        }
    }

    fun setTextButton(number:Int){
        for (i in btnArray.indices){
            if (number == i){
                btnArray[i].text =mode.toString()
            }else{
                btnArray[i].text=playVm.getRandom().toString()
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