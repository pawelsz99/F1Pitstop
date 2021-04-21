package com.pawelsznuradev.f1pitstop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.lang.NumberFormatException


/**
 * [RecyclerView.Adapter] that can display a [ResultData].
 */
class ResultRecyclerViewAdapter(
    private val values: List<ResultData>
) : RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_result, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.pos1
        holder.contentView.text = item.pos2
        holder.contentView2.text = item.pos3

        // driver name style
        if (item.pos2 == "" && item.pos3 == "") {
//            Log.e("driver name style", "pos1 = ${holder.idView.text} pos2 = ${holder.contentView.text} pos3 = ${holder.contentView2.text}")
//            Log.e("driver name style items", "pos1 = ${item.pos1} pos2 = ${item.pos2} pos3 = ${item.pos3}")
            holder.itemView.setBackgroundColor(Color.parseColor("#383840"))
            holder.idView.setTextColor(Color.WHITE)
        }

        // total and difference style
        if (item.pos1 == "Total" || item.pos1 == "Difference") {
            holder.itemView.setBackgroundColor(Color.parseColor("#383840"))
            holder.idView.setTextColor(Color.WHITE)
            holder.contentView2.setTextColor(Color.WHITE)
        }

        // stop lap duration style
        try { // names cannot be converted to Int
            if (item.pos1.toInt() % 2 != 0) {
                holder.itemView.setBackgroundColor(Color.parseColor("#E8DDD5"))
            }
        } catch (e: NumberFormatException) {
            // do nothing
        }


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val contentView2: TextView = view.findViewById(R.id.content2)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}