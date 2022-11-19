package com.example.myperfinance
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText


class Transaction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        val button = findViewById<Button>(R.id.btnOk)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(findViewById<EditText>(R.id.et_title).text)
                && TextUtils.isEmpty(findViewById<EditText>(R.id.et_input).text) ) {

                setResult(Activity.RESULT_CANCELED, replyIntent)

            } else {
                val amount = findViewById<EditText>(R.id.et_input).text.toString()
                val tittle = findViewById<EditText>(R.id.et_title).text.toString()

                replyIntent.putExtra(EXTRA_AMOUNT, amount)
                replyIntent.putExtra(EXTRA_TITLE,tittle)
                setResult(Activity.RESULT_OK, replyIntent)

            }
            finish()
        }
    }

    companion object {
        const val EXTRA_TITLE = "TITLE_EXTRA"
        const val EXTRA_AMOUNT = "AMOUNT_EXTRA"
    }
}

