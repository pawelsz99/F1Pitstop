package com.pawelsznuradev.f1pitstop

import android.graphics.Color
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.lang.NumberFormatException


/**
 * Result recycler view adapter
 *
 * @property values
 * @constructor Create empty Result recycler view adapter
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
        holder.contentView1.text = item.pos1
        holder.contentView2.text = item.pos2
        holder.contentView3.text = item.pos3


        // changing the background or text colour
        if (item.pos2 == "" && item.pos3 == "") {
            // driver name style
            holder.itemView.setBackgroundColor(Color.parseColor("#383840"))
            holder.contentView1.setTextColor(Color.WHITE)

        } else if (item.pos1 == "Total" || item.pos1 == "Difference") {
            // total and difference style
            holder.itemView.setBackgroundColor(Color.parseColor("#383840"))
            holder.contentView1.setTextColor(Color.WHITE)
            holder.contentView3.setTextColor(Color.WHITE)

        } else if (item.pos1 == "Stop") {
            // Stop Lap Duration style
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.contentView1.setTextColor(Color.BLACK)
            holder.contentView2.setTextColor(Color.BLACK)
            holder.contentView3.setTextColor(Color.BLACK)

        } else {
            // stop lap duration style
            try { // names string cannot be converted to Int
                if (item.pos1.toInt() % 2 != 0) {
                    holder.itemView.setBackgroundColor(Color.parseColor("#E8DDD5"))
                    holder.contentView1.setTextColor(Color.BLACK)
                    holder.contentView2.setTextColor(Color.BLACK)
                    holder.contentView3.setTextColor(Color.BLACK)
                } else {
                    holder.itemView.setBackgroundColor(Color.WHITE)
                    holder.contentView1.setTextColor(Color.BLACK)
                    holder.contentView2.setTextColor(Color.BLACK)
                    holder.contentView3.setTextColor(Color.BLACK)
                }

            } catch (e: NumberFormatException) {
                // set back to default
                holder.itemView.setBackgroundColor(Color.WHITE)
                holder.contentView1.setTextColor(Color.BLACK)
                holder.contentView2.setTextColor(Color.BLACK)
                holder.contentView3.setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    /**
     * View holder
     *
     * @constructor
     *
     * @param view
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView1: TextView = view.findViewById(R.id.item_number)
        val contentView2: TextView = view.findViewById(R.id.content)
        val contentView3: TextView = view.findViewById(R.id.content2)

        override fun toString(): String {
            return super.toString() + contentView1.text
        }
    }
}