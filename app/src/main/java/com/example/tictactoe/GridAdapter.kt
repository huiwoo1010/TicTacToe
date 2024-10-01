package com.example.tictactoe

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(
    var historyList: List<Array<IntArray>>,
    private val onUndoClickListener: (Int) -> Unit // 되돌아가기
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttons = arrayOf(
            itemView.findViewById<Button>(R.id.button00),
            itemView.findViewById<Button>(R.id.button01),
            itemView.findViewById<Button>(R.id.button02),
            itemView.findViewById<Button>(R.id.button03),
            itemView.findViewById<Button>(R.id.button04),
            itemView.findViewById<Button>(R.id.button05),
            itemView.findViewById<Button>(R.id.button06),
            itemView.findViewById<Button>(R.id.button07),
            itemView.findViewById<Button>(R.id.button08)
        )
        val undoText: TextView = itemView.findViewById(R.id.undo_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        Log.d("GridAdapter", "Binding position: $position, History List Size: ${historyList.size}")

        val currentHistory = historyList[position]
        //var index = 0
        for (i in 0..2) {
            for (j in 0..2) {
                val index = i * 3 + j
                holder.buttons[index].text = when (currentHistory[i][j]) {
                    1 -> "X"
                    2 -> "O"
                    else -> ""
                }
            }
        }
    }

    override fun getItemCount(): Int {
        Log.d("GridAdapter", "Current history list size: ${historyList.size}")
        return historyList.size
    }


    fun updateHistoryList(newHistoryList: List<Array<IntArray>>) {
        this.historyList = newHistoryList
        notifyDataSetChanged() // 데이터 변경 사항 알림
    }

}
