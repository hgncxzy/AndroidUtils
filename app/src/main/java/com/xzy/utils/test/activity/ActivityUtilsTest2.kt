package com.xzy.utils.test.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xzy.utils.R
import kotlinx.android.synthetic.main.activity_utils_test2.*

class ActivityUtilsTest2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utils_test2)
        test.setOnClickListener {
            val intent = Intent(this@ActivityUtilsTest2, ActivityUtilsTest::class.java)
            intent.putExtra("test", "xzy")
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        test2.setOnClickListener {
            val intent = Intent(this@ActivityUtilsTest2, ActivityUtilsTest::class.java)
            val bundle = Bundle()
            bundle.putString("key1","xzy")
            intent.putExtra("bundle1",bundle)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
