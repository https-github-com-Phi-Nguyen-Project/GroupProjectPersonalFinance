package com.example.myperfinance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter (private val context: Context, private val transactionItem: List<DisplayTransaction>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactionItem[position]
        holder.bind(transaction)
    }

    override fun getItemCount() = transactionItem.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val titleView = itemView.findViewById<TextView>(R.id.tvTransactionTitle)
        private val amountView = itemView.findViewById<TextView>(R.id.tvAmount)

        // helper method to help set up the onBindViewHolder method
        fun bind(transactionItem: DisplayTransaction) {
            titleView.text = transactionItem.title
            amountView.text = transactionItem.amount.toString()
        }
    }

}