package com.assignment.demoproject.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assignment.demoproject.models.ResponseItem
import com.assignment.demoproject.viewmodel.MainViewModel

object MainUI {

    @Composable
    fun MainComUI(viewModel:MainViewModel){
        val data =remember{ viewModel.data }

        LazyColumn(modifier=Modifier.fillMaxSize()) {
            items(items=data){
                ItemUI(it)
            }
        }
    }
    @Composable
    fun ItemUI(item:ResponseItem?){
        Text(modifier= Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.LightGray)
                .padding(10.dp), text = "${item?.name?:"Name field is Empty"}\n${item?.email?:"Email field is Empty"}\n${item?.phone?:"Phone field is Empty"}\n${item?.company?:"Company field is Empty"}\n${item?.address?:"Address field is Empty"}")
    }

}