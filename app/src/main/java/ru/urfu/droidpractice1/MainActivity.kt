package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import ru.urfu.droidpractice1.content.MainActivityScreen
import ru.urfu.droidpractice1.content.MainViewModel

class MainActivity : ComponentActivity() {
    private val getSecondActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val isRead = result.data?.getBooleanExtra("IS_READ", false) ?: false
            onSecondActivityResult?.invoke(isRead)
        }
    }

    private var onSecondActivityResult: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = MainViewModel()

            onSecondActivityResult = { isRead ->
                if (isRead) {
                    viewModel.markRead()
                } else {
                    viewModel.resetRead()
                }
            }

            MainActivityScreen(
                viewModel = viewModel,
                onNavigateToSecond = {
                    val intent = Intent(this, SecondActivity::class.java)
                    intent.putExtra("CURRENT_READ_STATUS", viewModel.isRead)
                    getSecondActivityResult.launch(intent)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        onSecondActivityResult = null
    }
}