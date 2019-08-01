package com.xzy.utils

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter

import androidx.appcompat.app.AppCompatActivity
import com.xzy.utils.activity.ActivityUtils
import com.xzy.utils.common.Utils
import com.xzy.utils.demo.activity.ActivityUtilsTest
import com.xzy.utils.demo.constant.Configs
import com.xzy.utils.toast.ToastUtils

import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import java.util.HashMap

/**
 * 程序入口,包含测试用例入口
 * @author xzy
 * */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val simpleAdapter = SimpleAdapter(
                this, getData(), R.layout.gridview_item, arrayOf("text"),
                intArrayOf(R.id.item_tv)
        )
        gridView.adapter = simpleAdapter
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    ActivityUtils.startActivity(Intent(this,
                            ActivityUtilsTest::class.java))
                }

            }
        }
    }

    private fun getData(): List<Map<String, Any>> {
        val dataList = ArrayList<Map<String, Any>>()
        val len = Configs.Number.items.size - 1
        for (i in 0..len) {
            val map = HashMap<String, Any>()
            map["text"] = Configs.Number.items[i]
            dataList.add(map)
        }
        return dataList
    }

}
