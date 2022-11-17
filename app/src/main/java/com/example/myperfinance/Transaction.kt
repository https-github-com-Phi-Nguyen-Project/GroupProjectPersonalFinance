package com.example.myperfinance

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import com.example.myperfinance.DEFAULT_CAT_LIST
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*




class Transaction : AppCompatActivity() {

    private lateinit var selectedDate: Date
    private var requestCode = 0
    private var calendar = Calendar.getInstance()
    private var selectedCategoryId = 0
    private var transactionId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        selectedDate = Calendar.getInstance().time

        val requestIntent = intent
        requestCode = requestIntent.getIntExtra(MainActivity.EXTRA_CODE, MainActivity.REQUEST_ADD)

        val date_spinner = findViewById<TextView>(R.id.date_spinner)
        date_spinner.text = convertDate(selectedDate)


        val dateListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate()
        }

        date_spinner!!.setOnClickListener {
            DatePickerDialog(
                this@Transaction,
                dateListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        var catSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item,
            DEFAULT_CAT_LIST)

        val groupSpiner = findViewById<Spinner>(R.id.group_spinner)

        catSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(groupSpiner) {
            adapter = catSpinnerAdapter
        }

        if(requestCode == MainActivity.REQUEST_EDIT) {
            transactionId = intent.getIntExtra(MainActivity.EXTRA_TRANSACTION, MainActivity.REQUEST_EDIT)
            val db = DBOpenHelper(this)
            val transaction = db.getTransaction(transactionId)
            extractData(transaction)
        }


        groupSpiner?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedCategoryId = 0
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.d(this.javaClass.name, "Category spinner: $position selected")
                selectedCategoryId = position
            }

        }
        findViewById<Button>(R.id.btnOk).setOnClickListener {
            saveAndFinish()
        }

    }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        if(requestCode == MainActivity.REQUEST_EDIT) {
            menuInflater.inflate(R.menu.transaction_menu, menu)
        }
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when(item.itemId) {
            R.id.menu_delete_item -> {
                deleteAndFinish()
                true
            } else -> {
                false
            }
        }
    }
*/

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun extractData(data: Items) {
        findViewById<EditText>(R.id.et_input).setText(data.value.toString())
        findViewById<EditText>(R.id.et_title).setText(data.title)
        selectedCategoryId = data.category
        findViewById<Spinner>(R.id.group_spinner).setSelection(data.category, false)
        findViewById<Switch>(R.id.expense_switch).isChecked = data.isExpense
        calendar.time = data.date
        updateDate()
        findViewById<EditText>(R.id.description).setText(data.desc)
    }

    private fun convertDate(date: Date): String {
        var format = SimpleDateFormat("dd-MM-YYYY")
        return format.format(date)
    }

    private fun updateDate() {
        val date = convertDate(calendar.time)
        val date_spinner = findViewById<TextView>(R.id.date_spinner)
        date_spinner!!.text = (date)
        selectedDate = calendar.time
    }

    private fun saveAndFinish() {
        val value = findViewById<EditText>(R.id.et_input).text.toString().toFloat()
        val db = DBOpenHelper(this)
        var resultCode = -1
        if(requestCode == MainActivity.REQUEST_ADD) {
            db.insertTransaction(value, findViewById<Switch>(R.id.expense_switch).isChecked, selectedDate, findViewById<EditText>(R.id.et_title).text.toString(),
                selectedCategoryId, findViewById<EditText>(R.id.description).text.toString())
            resultCode = MainActivity.REQ_ADD_OK
        } else if (requestCode == MainActivity.REQUEST_EDIT) {
            db.updateTransaction(
                Items(transactionId, value, findViewById<EditText>(R.id.et_title).text.toString(),
                    findViewById<Switch>(R.id.expense_switch).isChecked, selectedDate, findViewById<EditText>(R.id.description).text.toString(), selectedCategoryId))
            resultCode = MainActivity.REQ_EDIT_OK
        }

        val resultIntent = Intent()
        setResult(resultCode, resultIntent)
        finish()
    }

    private fun deleteAndFinish() {
        if(requestCode == MainActivity.REQUEST_EDIT) {
            val db = DBOpenHelper(this)
            db.deleteTransaction(transactionId)
        }
        val resultIntent = Intent()
        setResult(MainActivity.REQ_EDIT_OK, resultIntent)
        finish()
    }

}

