package com.example.myperfinance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ItemsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DBOpenHelper(activity!!.applicationContext)
        val cursor = db.getAllTransactions(true)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.transaction_list)
        val adapter = cursor?.let { TransactionsAdapter(it) }
        recyclerView?.adapter = adapter

        if(cursor != null) {
            recyclerView?.layoutManager = LinearLayoutManager(requireContext()).also {
                val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
                recyclerView?.addItemDecoration(dividerItemDecoration)
            }
            adapter?.notifyDataSetChanged()
            }
        }


    }

