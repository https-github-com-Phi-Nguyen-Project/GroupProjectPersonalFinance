package com.example.myperfinance

import android.app.Activity
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myperfinance.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers


val DEFAULT_CAT_LIST = listOf("Food", "Entertainment", "Public Transport",
   "Work", "Health", "Electronics", "Clothing", "Family", "Services")

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_ADD = 20
        const val REQUEST_EDIT = 21
        const val REQ_ADD_OK = 21
        const val REQ_EDIT_OK = 22
        const val EXTRA_TRANSACTION = "extra_id"
        const val EXTRA_CODE = "request_code"
    }

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val itemsFragment = ItemsFragment()
        val accountFragment = AccountFragment()
        val invesmentFragment = InvestmentFragment()
        val settingFragment = SettingFragment()

        binding.chipAppBar.setItemSelected(R.id.ic_transaction,true)
        makeCurrentFragment(itemsFragment)


        binding.chipAppBar.setOnItemSelectedListener { //when the bottom nav clicked
            when (it){
                R.id.ic_transaction -> {
                    makeCurrentFragment(itemsFragment)
                    Toast.makeText(this, "transaction",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_account -> {
                    makeCurrentFragment(accountFragment)
                    Toast.makeText(this, "item",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_investment -> {
                    makeCurrentFragment(invesmentFragment)
                    Toast.makeText(this, "Investment",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_setting -> {
                    makeCurrentFragment(settingFragment)
                    Toast.makeText(this, "setting",Toast.LENGTH_SHORT).show()
                }
            }
            val b = true
            b
        }

        val btnAdd: FloatingActionButton = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener{
            startTransactionActivity()
        }
    }

    private fun startTransactionActivity() {
        val intent = Intent(this, Transaction::class.java)
        intent.putExtra(EXTRA_CODE, REQUEST_ADD)
        startActivityForResult(intent, REQUEST_ADD)
    }


    private fun makeCurrentFragment(fragment: Fragment) { //method 2
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, fragment)
            commit()
        }
    }




}



