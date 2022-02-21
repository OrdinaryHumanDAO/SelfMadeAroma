package com.example.selfmadearoma.data.aromaOil

import javax.inject.Inject

class AromaOilRepository @Inject constructor (private val dao: AromaOilDao) {

    fun getAromaOils() = dao.getAromaOils()
    fun getAromaOil(AromaOilId: Int) = dao.getAromaOil(AromaOilId)
    fun findByAromaOilNameLike(aromaOilName: String) = dao.findByAromaOilNameLike(aromaOilName)
}