package com.example.tictactoe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(
    private val historyList: List<List<String>>,
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
        val currentHistory = historyList[position]

        for (i in currentHistory.indices) {
            holder.buttons[i].text = currentHistory[i]
        }

        holder.undoText.setOnClickListener {
            onUndoClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }
}
