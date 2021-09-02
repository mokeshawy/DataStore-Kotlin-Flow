package com.example.datastorekotlin_flow.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.datastorekotlin_flow.R
import com.example.datastorekotlin_flow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding      : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // call function save to local.
        saveToLocal()

        // read data using flow.
        lifecycleScope.launchWhenStarted {
            mainViewModel.readToLocal.collect {
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
                        mainViewModel.clearData()
                    }
                    // call fun timer.
                    timer()
                    mainViewModel.writeToLocal(etEnterName.text.toString())
                }else{
                    Toast.makeText(this@MainActivity,"fill the field",Toast.LENGTH_SHORT).show()
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
                binding.tvTimer.text = "done!"
            }
        }.start()
    }
}