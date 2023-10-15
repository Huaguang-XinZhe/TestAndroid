package com.huaguang.testandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "打标签喽"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedState: SharedState // 不要把依赖注入的字段变为私有，不支持

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecordPage()
        }

        sharedState.toastMessage.observe(this) { message ->
            if (message == null) return@observe
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }
}
