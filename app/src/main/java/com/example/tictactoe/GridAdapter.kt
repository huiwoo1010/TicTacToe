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
    private val onUndoClickListener: (Int) -> Unit,
    var winner: String?
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
        val turnText: TextView = itemView.findViewById(R.id.turn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        Log.d("GridAdapter", "Binding position: $position, History List Size: ${historyList.size}")

        holder.itemView.setOnClickListener {
            onUndoClickListener(position)
        }

        val currentHistory = historyList[position]

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
        holder.turnText.text = "${position+1}턴"
        if (winner != null && position == historyList.size - 1) {
            holder.undoText.text = winner
        }
    }

    override fun getItemCount(): Int {
        Log.d("GridAdapter", "Current history list size: ${historyList.size}")
        return historyList.size
    }

}
