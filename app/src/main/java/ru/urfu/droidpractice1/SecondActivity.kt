package ru.urfu.droidpractice1

import android.content.Intent
import androidx.activity.ComponentActivity
import android.os.Bundle
import android.widget.Switch
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var switchButton: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        switchButton = binding.switchButton

        if (savedInstanceState != null) {
            switchButton.isChecked = savedInstanceState.getBoolean("switch_state", false)
        }

        val previousState = intent.getBooleanExtra("CURRENT_READ_STATUS", false)
        switchButton.isChecked = previousState

        binding.toolbar.setNavigationOnClickListener {
            sendResultAndFinish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("switch_state", switchButton.isChecked)
    }

    private fun sendResultAndFinish() {
        val resultIntent = Intent()
        resultIntent.putExtra("IS_READ", switchButton.isChecked)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    override fun onBackPressed() {
        sendResultAndFinish()
        super.onBackPressed()
    }
}