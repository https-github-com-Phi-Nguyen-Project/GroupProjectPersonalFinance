package com.example.myperfinance

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import com.example.myperfinance.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    private val newTransactionRequestCode = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val transactionFragment = ItemsFragment()
        val accountFragment = AccountFragment()
        val investimentFragment = InvestmentFragment()
        val settingFragment = SettingFragment()

        binding.chipAppBar.setItemSelected(R.id.ic_transaction,true)
        makeCurrentFragment(transactionFragment)
        binding.chipAppBar.setOnItemSelectedListener { //when the bottom nav clicked
            when (it){
                R.id.ic_transaction -> {
                    makeCurrentFragment(transactionFragment)
                    Toast.makeText(this, "Transaction",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_account -> {
                    makeCurrentFragment(accountFragment)
                    Toast.makeText(this, "Account",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_investment -> {
                    makeCurrentFragment(investimentFragment)
                    Toast.makeText(this, "Investment",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_setting -> {
                    makeCurrentFragment(settingFragment)
                    Toast.makeText(this, "Setting",Toast.LENGTH_SHORT).show()
                }

            }
            val b = true
            b
        }


        /*
        val fragmentManager: FragmentManager = supportFragmentManager

        findViewById<ChipNavigationBar>(R.id.chip_app_bar).setOnItemSelectedListener {
                item ->
            var fragmentToShow: Fragment? = null

            when(item.itemId){
                R.id.ic_transaction -> {
                    // Navigate to list of Transactions
                    fragmentToShow = ItemsFragment()
                    Toast.makeText(this, "Transaction",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_account -> {
                    // Navigate to Account page
                    fragmentToShow = AccountFragment()
                    Toast.makeText(this, "Account",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_investment -> {
                    // Navigate to Investment page
                    fragmentToShow = InvestmentFragment()
                    Toast.makeText(this, "Investment",Toast.LENGTH_SHORT).show()
                }
                R.id.ic_setting -> {
                    // Navigate to list of food view
                    fragmentToShow = SettingFragment()
                    Toast.makeText(this, "Setting",Toast.LENGTH_SHORT).show()
                }
            }
            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer,fragmentToShow).commit()
            }

            true
        }
        // Set default selection
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.ic_transaction
*/
    }
    private fun makeCurrentFragment(fragment: Fragment) { //method 2
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContainer, fragment)
            commit()
        }
    }


    fun floating_button(view: View){

        val intent = Intent(this, Transaction::class.java)

        startActivityForResult(intent, newTransactionRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)


        if (requestCode == newTransactionRequestCode && resultCode == Activity.RESULT_OK) {

            intentData.let{data ->
                lifecycleScope.launch(Dispatchers.IO) {
                    (application as MyPerFinanceApplication).db.itemsDAO().insertAll(
                        Items(
                            id = 0,
                            title = data?.getStringExtra(Transaction.EXTRA_TITLE),
                            amount = data?.getStringExtra(Transaction.EXTRA_AMOUNT)?.toDouble()
                        )
                    )
                }
            }

        } else {
            Toast.makeText(
                this,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }


}