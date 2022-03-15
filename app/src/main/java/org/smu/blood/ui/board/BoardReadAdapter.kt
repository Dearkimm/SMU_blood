package org.smu.blood.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R

class BoardReadAdapter (private val context: Context) : //댓글 리사이클러뷰 어댑터
RecyclerView.Adapter<BoardReadAdapter.ViewHolder>(){
    lateinit var currentId : String
    var datas = mutableListOf<CommentData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardReadAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_comments,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    // 리사이클러뷰 클릭 이벤트
    interface OnItemClickListener{
        fun onItemClick(v:View, data: CommentData, pos : Int)
        fun onEditClick(v:View, data: CommentData, pos : Int)
        fun onDeleteClick(v:View, data: CommentData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    //댓글 삭제
    fun removeItem(position: Int){
        if(position>=0) {
            datas.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtNickname: TextView = itemView.findViewById(R.id.comment_nickname)
        private val txtTime: TextView = itemView.findViewById(R.id.comment_time)
        private val txtComment: TextView = itemView.findViewById(R.id.comment_body)
        private val commentEdit: Button = itemView.findViewById(R.id.comment_edit)
        private val commentDelete: Button = itemView.findViewById(R.id.comment_delete)

        fun bind(item: CommentData) {
            txtNickname.text = item.nickname
            txtTime.text = item.time
            txtComment.text = item.comment

            // 현재 사용자 아이디 == 댓글 아이디이면 수정/삭제 visible
            if(currentId == item.userId){
                commentEdit.visibility = VISIBLE
                commentDelete.visibility = VISIBLE
            }

            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION) {
                commentEdit.setOnClickListener {
                    listener?.onEditClick(itemView,item,position) //수정버튼클릭
                }
                commentDelete.setOnClickListener {
                    listener?.onDeleteClick(itemView,item,position) //삭제버튼클릭
                }
            }
        }

    }
}
