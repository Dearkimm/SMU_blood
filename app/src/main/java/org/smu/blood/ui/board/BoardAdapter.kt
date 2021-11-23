package org.smu.blood.ui.board

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R

class BoardAdapter(private val context:Context) :
RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var datas = mutableListOf<BoardData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_board,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtTitle: TextView = itemView.findViewById(R.id.board_title)
        private val txtNickname: TextView = itemView.findViewById(R.id.board_nickname)
        private val txtTime: TextView = itemView.findViewById(R.id.board_time)
        private val txtHeartCount: TextView = itemView.findViewById(R.id.board_heart_count)


        fun bind(item: BoardData) {
            txtTitle.text = item.title
            txtNickname.text = item.nickname
            txtTime.text = item.time
            txtHeartCount.text = item.heartcount.toString()

            itemView.setOnClickListener {

            }



        }
    }
}