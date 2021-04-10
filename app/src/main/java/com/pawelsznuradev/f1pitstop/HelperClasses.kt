package com.pawelsznuradev.f1pitstop

/**
 * Created by Pawel Sznura on 10/04/2021.
 */
data class IdNameCollection(val idList: List<String>, val nameList: List<String>) {

    fun getIdByName(name: String): String {
        val position = nameList.indexOf(name)
        return idList[position]
    }

    fun getNameById(id: String): String {
        val position = idList.indexOf(id)
        return nameList[position]
    }


}