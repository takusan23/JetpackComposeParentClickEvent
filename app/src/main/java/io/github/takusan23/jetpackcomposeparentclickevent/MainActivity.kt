package io.github.takusan23.jetpackcomposeparentclickevent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MaterialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) { MainScreen() }
            }
        }
    }
}

/** Android Studio のComposeプレビューで使う */
@Preview
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
private fun MainScreen() {

    // 親要素を押したときの時間
    var parentClickTime by remember { mutableStateOf(0L) }
    // ボタンを押したときの時間
    var buttonClickTime by remember { mutableStateOf(0L) }

    Column {
        Text(text = "親要素押したとき：${parentClickTime.toTimeFormat()}")
        Text(text = "子要素押したとき：${buttonClickTime.toTimeFormat()}")

        // 適当な広さの親要素を作成
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.LightGray)
                .pointerInput(Unit) {
                    // 親要素だけどクリックイベントと5000兆円ほしい！！！
                    detectParentComponentTapGestures {
                        parentClickTime = System.currentTimeMillis()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { buttonClickTime = System.currentTimeMillis() }) {
                Text(text = "おせ！")
            }
        }
    }
}

/** ミリ秒UnixTimeを日付フォーマットへ変換する拡張関数 */
private fun Long.toTimeFormat(): String? {
    val simpleDateFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())
    return simpleDateFormat.format(this)
}
