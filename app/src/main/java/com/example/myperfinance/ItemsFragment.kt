package com.example.myperfinance

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch


class ItemsFragment : Fragment() {

    private val transactionItem = mutableListOf<DisplayTransaction>()
    private lateinit var application: Application


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val recyclerView = view.findViewById<RecyclerView>(R.id.rvTransactions)
        val adapter = TransactionAdapter(requireContext(),transactionItem)
        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recyclerView.addItemDecoration(dividerItemDecoration)
        }


        lifecycleScope.launch {
            (activity?.application as MyPerFinanceApplication).db.itemsDAO().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayTransaction(
                        title = entity.title,
                        amount = entity.amount

                    )
                }.also { mappedList ->
                    transactionItem.clear()

                    Log.i("TransactionFragment", mappedList.toString())
                    transactionItem.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
            }
        }


    }
}