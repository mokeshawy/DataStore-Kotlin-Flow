package com.example.datastorekotlin_flow.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.datastorekotlin_flow.R
import com.example.datastorekotlin_flow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding      : ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        saveToLocal()

        lifecycleScope.launchWhenStarted {
            mainViewModel.readToLocal.collect {
                binding.tvShowName.text = it
            }
        }

    }

    private fun saveToLocal() {
        binding.apply {
            btnSaveValue.setOnClickListener {
                if(!TextUtils.isEmpty(binding.etEnterName.text.toString())){
                    mainViewModel.writeToLocal(name = etEnterName.text.toString())
                }else{
                    Toast.makeText(this@MainActivity,"fill the field",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}