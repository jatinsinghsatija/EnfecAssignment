package com.assignment.demoproject.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.assignment.demoproject.ui.theme.DemoProjectTheme
import com.assignment.demoproject.viewmodel.MainViewModel
import com.assignment.demoproject.viewmodelfactory.MainViewModelFactory
import com.assignment.demoproject.views.MainUI.MainComUI

class MainActivity : ComponentActivity() {
    private lateinit var viewModel:MainViewModel
    private lateinit var factory:MainViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        setContent {
            DemoProjectTheme {
                MainComUI(viewModel)
            }
        }
    }

    fun initialize(){
        factory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.callDemoAPI(this)
    }
}