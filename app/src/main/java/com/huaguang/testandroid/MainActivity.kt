package com.huaguang.testandroid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.huaguang.testandroid.pages.class_page.ClassificationPage
import com.huaguang.testandroid.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val TAG = "打标签喽"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedState: SharedState // 不要把依赖注入的字段变为私有，

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            RecordPage()
//            Button(onClick = { mainViewModel.insertPresetData() }) {
//                Text(text = "插入预置数据")
//            }
            ClassificationPage()
        }

        sharedState.toastMessage.observe(this) { message ->
            if (message == null) return@observe
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

    }
}
