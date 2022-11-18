package com.example.myperfinance

import android.app.Activity
import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.myperfinance.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.coroutines.Dispatchers


val DEFAULT_CAT_LIST = listOf("Food", "Entertainment", "Public Transport",
   "Work", "Health", "Electronics", "Clothing", "Family", "Services")

class MainActivity : AppCompatActivity(), TransactionsAdapter.OnItemClickListener {

    companion object {
        const val REQUEST_ADD = 20
        const val REQUEST_EDIT = 21
        const val REQ_ADD_OK = 21
        const val REQ_EDIT_OK = 22
        const val EXTRA_TRANSACTION = "extra_id"
        const val EXTRA_CODE = "request_code"
    }

    private lateinit var sectionsPagerAdapter: PageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionsPagerAdapter =
            PageAdapter(this, supportFragmentManager)

        val view_pager = findViewById<ViewPager>(R.id.view_pager)
        view_pager.adapter = sectionsPagerAdapter

        var tabs = findViewById<ChipNavigationBar>(R.id.chip_app_bar)
        tabs.setupWithViewPager(view_pager)

        //Add transaction button
        val fab: FloatingActionButton = findViewById(R.id.fab)

        fab.setOnClickListener { view ->

            startAddActivity()
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_ADD) {

            if(resultCode == REQ_ADD_OK) {
                notifyFragmentsToUpdate()
            }
        } else if (requestCode == REQUEST_EDIT) {
            if(resultCode == REQ_EDIT_OK) {
                notifyFragmentsToUpdate()
            }
        }
    }

    override fun onClick(id: Int) {
        val intent = Intent(this, TransactionActivity::class.java)
        intent.putExtra(EXTRA_CODE, REQUEST_EDIT)
        intent.putExtra(EXTRA_TRANSACTION, id)
        startActivityForResult(intent, REQUEST_EDIT)
    }

    private fun startAddActivity() {
        val intent = Intent(this, TransactionActivity::class.java)
        intent.putExtra(EXTRA_CODE, REQUEST_ADD)
        startActivityForResult(intent, REQUEST_ADD)
    }

    private fun getActivePage(): PageFragment {
        val position = view_pager.currentItem
        return sectionsPagerAdapter.getRegisteredFragment(position) as PageFragment
    }

    private fun getActiveFragments(): SparseArray<Fragment> {
        return sectionsPagerAdapter.getRegisteredFragments()
    }

    fun notifyFragmentsToUpdate() {
        val fragments = getActiveFragments()
        for(i in 0 until fragments.size()) {
            (fragments.get(i) as PageFragment).notifyDataUpdate()
        }
    }
}