package org.smu.blood.ui.board

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.R

class BoardAdapter(private val context:Context) :
RecyclerView.Adapter<BoardAdapter.ViewHolder>(),Filterable {

    //var datas = mutableListOf<BoardData>()

    //필터링 관련 변수들
    var unFilteredList = mutableListOf<BoardData>()
    var filteredList = mutableListOf<BoardData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_board,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = filteredList.size

    //필터링
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString() //내 닉네임
                filteredList = if (charString.isEmpty()) {
                    unFilteredList
                } else {
                    var filteringList = mutableListOf<BoardData>()
                    for (item in unFilteredList) {
                        if (item.nickname == charString) filteringList.add(item) //내 id = 글 id
                    }
                    filteringList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as MutableList<BoardData>
                notifyDataSetChanged()
            }
        }
    }

    // 클릭 이벤트 리스너 인터페이스 정의
    interface OnItemClickListener{
        fun onItemClick(v:View, data: BoardData, pos : Int)
    }
    private var listener : OnItemClickListener? = null
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    //꾹 누르기 이벤트 리스너 인터페이스 정의
    interface OnItemLongClickListener{
        fun onItemLongClick(v:View, data: BoardData, pos : Int)
    }
    private var longlistener : OnItemLongClickListener? = null
    fun setOnItemLongClickListener(listener : OnItemLongClickListener) {
        this.longlistener = listener
    }
    //삭제
    fun removeItem(position: Int){
        if(position>=0) {
            filteredList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = itemView.findViewById(R.id.board_title)
        private val txtNickname: TextView = itemView.findViewById(R.id.board_nickname)
        private val txtTime: TextView = itemView.findViewById(R.id.board_time)
        private val txtHeartCount: TextView = itemView.findViewById(R.id.board_heart_count)
        private val txtCommentCount: TextView = itemView.findViewById(R.id.board_commments_count)

        fun bind(item: BoardData) {
            txtTitle.text = item.title
            txtNickname.text = item.nickname
            txtTime.text = item.time
            txtHeartCount.text = item.heartcount.toString()
            txtCommentCount.text = item.commentcount.toString()

            val position = adapterPosition
            if(position!= RecyclerView.NO_POSITION)
            {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView,item,position)
                }

                itemView.setOnLongClickListener {
                    longlistener?.onItemLongClick(itemView,item,position)
                    return@setOnLongClickListener true
                }
            }

        }
    }
}