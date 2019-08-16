package com.xzy.test.xml

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xzy.test.R
import com.xzy.utils.toast.ToastUtils
import com.xzy.utils.xml.XmlUtils

class XmlUtilsTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xml_utils_test)

        val map = XmlUtils.parseXMLFromAsserts(this, "test.xml")
        val realName = map.get("realName").toString()
        ToastUtils.showShort(realName)
    }
}
