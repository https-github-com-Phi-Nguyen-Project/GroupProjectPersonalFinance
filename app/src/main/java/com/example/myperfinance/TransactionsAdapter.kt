package com.example.myperfinance

import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionsAdapter(private var data: Cursor, val itemClickListener: AdapterView.OnItemClickListener):
    RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(data.moveToPosition(position)) {
            val title = data.getString(2)
            val groupId = data.getString(6).toInt()
            val categoryName = DEFAULT_CAT_LIST[groupId]

            val transactionValue = data.getFloat(1)



            holder.bind(title, categoryName,transactionValue)
        } else {
            Log.e(this.javaClass.name, "Failed to move Cursor to $position in onBindViewHOlder" )
        }


    }

    fun swapCursor(cursor: Cursor) {
        data = cursor
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val mTitle: TextView = itemView.findViewById(R.id.tvTransactionTitle)
        private val mGroup: TextView = itemView.findViewById(R.id.tvCategory)
        private val mValue: TextView = itemView.findViewById(R.id.tvAmount)


        fun bind(title: String, category: String, value: Float) {
            mTitle.text = title
            mGroup.text = category
            mValue.text = value.toString()

        }



    }


}