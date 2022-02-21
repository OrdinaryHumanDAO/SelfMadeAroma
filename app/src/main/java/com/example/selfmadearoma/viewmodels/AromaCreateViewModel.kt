package com.example.selfmadearoma.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.selfmadearoma.data.myArom.MyAroma
import com.example.selfmadearoma.data.myArom.MyAromaOilInfo
import com.example.selfmadearoma.data.myArom.MyAromaOilInfoRepository
import com.example.selfmadearoma.data.myArom.MyAromaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AromaCreateViewModel @ViewModelInject constructor(
    private val myAromaRepository: MyAromaRepository,
    private val myAromaOilInfoRepository: MyAromaOilInfoRepository
): ViewModel() {

    var myAromaName = ""

    var cardViewNum = 0

    var aromaOilNames: MutableLiveData<MutableList<String>> =
        MutableLiveData<MutableList<String>>().also {
            it.value = mutableListOf("アロマを選択してください")
        }

    var aromaOilAmounts: MutableLiveData<MutableList<String>> =
        MutableLiveData<MutableList<String>>().also {
            it.value = mutableListOf("0 ml")
        }

    fun deleteCardView(cardViewId: Int) {
        cardViewNum--

        val list1 = aromaOilNames.value!!
        list1.removeAt(cardViewId)
        aromaOilNames.value = list1

        val list2 = aromaOilAmounts.value!!
        list2.removeAt(cardViewId)
        aromaOilAmounts.value = list2
    }

    fun initialize() {
        cardViewNum = 0
        aromaOilNames.postValue(mutableListOf("アロマを選択してください"))
        aromaOilAmounts.postValue(mutableListOf("0 ml"))
    }

    fun addCardViewNum() {
        cardViewNum++
        aromaOilNames.value!!.add("アロマを選択してください")
        aromaOilAmounts.value!!.add("0 ml")
    }

    fun editAromaOilNames(index: Int, str: String) {
        val list = aromaOilNames.value!!
        list[index] = str
        aromaOilNames.value = list
    }

    fun editAromaOilAmounts(index: Int, str: String) {
        val list = aromaOilAmounts.value!!
        list[index] = str
        aromaOilAmounts.value = list
    }

    fun totalAromaOilAmounts(): Int {
        val list: List<Int> = aromaOilAmounts.value!!.map { it.dropLast(3).toInt() }

        return list.sum()
    }

    fun putMyAromaName(name: String) {
        myAromaName = name
    }

    fun insertMyAroma() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = MyAroma(0, myAromaName, totalAromaOilAmounts())
            myAromaRepository.insertMyAroma(data)
        }
    }

    fun insertMyAromaOilInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("aiueo", "${aromaOilNames.value}")
            Log.d("aiueo", "${aromaOilAmounts.value}")

            for(i in aromaOilNames.value!!.indices) {
                val data = MyAromaOilInfo(
                    0,
                    myAromaName,
                    aromaOilNames.value!![i],
                    aromaOilAmounts.value!![i].dropLast(3).toInt()
                )
                myAromaOilInfoRepository.insertMyAromaOilInfo(data)
            }
            initialize()
        }
    }
}