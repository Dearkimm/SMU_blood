package org.smu.blood.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R
import org.w3c.dom.Comment

class BoardReadAdapter (private val context: Context) : //댓글 리사이클러뷰 어댑터
RecyclerView.Adapter<BoardReadAdapter.ViewHolder>(){

    var datas = mutableListOf<CommentData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardReadAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comments,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    // 리사이클러뷰 클릭 이벤트
    interface OnItemClickListener{
        fun onItemClick(v:View, data: CommentData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtNickname: TextView = itemView.findViewById(R.id.comment_nickname)
        private val txtTime: TextView = itemView.findViewById(R.id.comment_time)
        private val txtComment: TextView = itemView.findViewById(R.id.comment_body)

        fun bind(item: CommentData) {
            txtNickname.text = item.nickname
            txtTime.text = item.time
            txtComment.text = item.comment

            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,position)
                }
            }

        }
    }
}
