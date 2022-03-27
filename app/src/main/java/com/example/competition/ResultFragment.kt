package com.example.competition


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.competition.databinding.FragmentResultBinding


class ResultFragment : Fragment() {
    lateinit var binding: FragmentResultBinding
    val resultVm: competitionVm by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "Result"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }
    private fun initView() {
        binding.highScore.text="the best recore is  ${resultVm.maxScore}"
        binding.score.text="your score is  ${resultVm.score}"

        binding.exitBtn.setOnClickListener {
            exitApplication()
        }


        binding.replyBtn.setOnClickListener {
            resultVm.questionNumber=0
            resultVm.score=0
             findNavController().navigate(R.id.action_resultFragment_to_playFragment)
        }

    }

    fun exitApplication() {
        activity?.finish()
    }
}