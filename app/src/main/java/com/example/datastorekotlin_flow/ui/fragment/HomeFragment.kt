package com.example.datastorekotlin_flow.ui.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.datastorekotlin_flow.R
import com.example.datastorekotlin_flow.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding
    private val homeViewModel : HomeViewModel by activityViewModels()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // call function save to local.
        saveToLocal()

        // read data using flow.
        lifecycleScope.launchWhenStarted {
            homeViewModel.readToLocal.collect {
                binding.tvName.text = it
            }
        }
    }

    // fun save data to local.
    private fun saveToLocal() {
        binding.apply {
            // btn save.
            btnSave.setOnClickListener {

                // validate input
                if(!TextUtils.isEmpty(binding.etEnterName.text.toString())){
                    // delay second for clear data.
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(10000)
                        // call fun clear data.
                        homeViewModel.clearData()
                    }
                    // call fun timer.
                    timer()
                    homeViewModel.writeToLocal(etEnterName.text.toString())
                }else{
                    Toast.makeText(requireActivity(),"fill the field", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // fun timer.
    private fun timer(){
        object : CountDownTimer(10000,1000){
            override fun onTick( millisUntilFinished : Long) {
                binding.tvTimer.text = ( millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                binding.tvTimer.text = "done.."
            }
        }.start()
    }
}