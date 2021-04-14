package com.pawelsznuradev.f1pitstop

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * [RecyclerView.Adapter] that can display a [ResultData].
 * TODO: Replace the implementation with code for your data type.
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