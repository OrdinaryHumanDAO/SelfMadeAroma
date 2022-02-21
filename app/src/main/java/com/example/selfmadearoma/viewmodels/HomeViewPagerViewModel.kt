package com.example.selfmadearoma.viewmodels

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.selfmadearoma.data.AppDatabase
import com.example.selfmadearoma.data.myArom.MyAroma
import com.example.selfmadearoma.data.myArom.MyAromaRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

class HomeViewPagerViewModel @ViewModelInject constructor(
    private val myAromaRepository: MyAromaRepository
): ViewModel() {


    val myAromas: LiveData<List<MyAroma>> = myAromaRepository.getMyAromas().asLiveData()


}