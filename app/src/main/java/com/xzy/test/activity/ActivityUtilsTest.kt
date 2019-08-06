package com.xzy.test.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xzy.test.MainActivity
import com.xzy.test.R
import com.xzy.utils.activity.ActivityUtils
import com.xzy.utils.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_utils_test.*

/**
 * ActivityUtils 工具测试类。
 * @author xzy
 * **/
class ActivityUtilsTest : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_utils_test)

        getActivityByView.setOnClickListener {
            ToastUtils.showShort(ActivityUtils.getActivityByView(getActivityByView).localClassName)
        }

        getActivityByContext.setOnClickListener {
            ToastUtils.showShort(ActivityUtils
                    .getActivityByContext(this@ActivityUtilsTest).localClassName)
        }

        getActivityByContext.setOnClickListener {
            ToastUtils.showShort(ActivityUtils
                    .getActivityByContext(this@ActivityUtilsTest).localClassName)
        }

        isActivityExists.setOnClickListener {
            ToastUtils.showShort(ActivityUtils
                    .isActivityExists("com.xzy.utils", ActivityUtilsTest::class.java.name)
                    .toString() + "-" + ActivityUtilsTest::class.java.name)
        }

        startActivity.setOnClickListener {
            ActivityUtils.startActivity(MainActivity::class.java)
            finish()
        }

        startActivityWithBundle.setOnClickListener {
            ActivityUtils.startActivity(MainActivity::class.java, null)
            finish()
        }

        startActivityWithAnim.setOnClickListener {
            ActivityUtils.startActivity(MainActivity::class.java, R.anim.in_from_left,
                    R.anim.in_from_right)
            finish()
        }

        startActivityWithAnimAndBundle.setOnClickListener {
            ActivityUtils.startActivity(Bundle(), MainActivity::class.java, R.anim.in_from_left,
                    R.anim.in_from_right)
            finish()
        }

        startActivityWithPkgAndCls.setOnClickListener {
            ActivityUtils.startActivity("com.xzy.utils", MainActivity::class.java.name)
            finish()
        }

        startActivityForResult.setOnClickListener {
            ActivityUtils.startActivityForResult(this@ActivityUtilsTest,
                    ActivityUtilsTest2::class.java,
                    1001)
        }

        startActivityForResultWithBundle.setOnClickListener {
            ActivityUtils.startActivityForResult(this@ActivityUtilsTest,
                    ActivityUtilsTest2::class.java,
                    1002)
        }

        startActivityForResultWithBundle.setOnClickListener {
            ActivityUtils.startActivityForResult(this@ActivityUtilsTest,
                    ActivityUtilsTest2::class.java,
                    1002,
                    R.anim.in_from_left,
                    R.anim.out_to_right)
        }

        finishActivity.setOnClickListener {
            ActivityUtils.finishActivity(this, true)
        }

        finishActivityWithAnim.setOnClickListener {
            ActivityUtils.finishActivity(this, R.anim.in_from_left, R.anim.in_from_right)
        }

        finishAllActivities.setOnClickListener {
            ActivityUtils.finishAllActivities()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("==", "requestCode:$requestCode")
        when (requestCode) {
            1001 -> ToastUtils.showShort("收到上个页面的结果test="
                    + data!!.getStringExtra("test"))
            1002 -> {
                val bundle = data!!.getBundleExtra("bundle1")
                val result = bundle?.getString("key1")
                ToastUtils.showShort("收到上个页面的结果result=$result")
            }
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent?) {
        super.onActivityReenter(resultCode, data)
        Log.d("==", "resultCode:$resultCode")
    }
}
