package com.example.myperfinance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemsFragment : PageFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.rvitems, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DBOpenHelper(activity!!.applicationContext)
        val cursor = db.getAllTransactions(true)

        val transaction_list = view.findViewById<RecyclerView>(R.id.transaction_list)
        if(cursor != null) {
            transaction_list.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TransactionsAdapter(cursor, activity as TransactionsAdapter.OnItemClickListener)
            }
        }
    }

    override fun notifyDataUpdate() {
        val db = DBOpenHelper(activity!!.applicationContext)
        val cursor = db.getAllTransactions(true)

        if(cursor != null) {
            val recyclerViewAdapter = view?.findViewById<RecyclerView>(R.id.transaction_list)?.adapter as TransactionsAdapter
            recyclerViewAdapter.swapCursor(cursor)
        }
        view?.findViewById<RecyclerView>(R.id.transaction_list)?.adapter?.notifyDataSetChanged()
    }

}
