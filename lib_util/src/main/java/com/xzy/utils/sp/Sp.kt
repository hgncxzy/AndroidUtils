package com.xzy.ui.kotlindemo.sp

import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xzy.ui.kotlindemo.RootApp


object Sp {
    private var sp: SharedPreferences? = null
    /**
     * 存入
     */
    fun put(key: String, userList: List<User>) {
        if (sp == null) {
            sp = RootApp.getContext().getSharedPreferences("config", MODE_PRIVATE)
        }
        val editor = sp?.edit()
        val gson = Gson()
        val json = gson.toJson(userList)
        editor?.putString(key, json)
        editor?.apply()
    }

    /**
     * 读取
     */
    fun get(key:String): List<User> {
        if (sp == null) {
            sp = RootApp.getContext().getSharedPreferences("config", MODE_PRIVATE)
        }
        val gson = Gson()
        val json = sp?.getString(key, null)
        val type = object : TypeToken<List<User>>() {

        }.type
        return gson.fromJson(json, type)
    }
}