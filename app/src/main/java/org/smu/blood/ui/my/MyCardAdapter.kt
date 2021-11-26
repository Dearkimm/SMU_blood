package org.smu.blood.ui.my

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.api.database.MyCardItem
import org.smu.blood.databinding.ItemCardRequestBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital

class MyCardAdapter: RecyclerView.Adapter<MyCardAdapter.MyCardViewHolder>() {

    val request = mutableListOf<MyCardItem>()
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyCardViewHolder {
        val binding = ItemCardRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyCardViewHolder(binding)
    }

    inner class MyCardViewHolder(
        private val binding: ItemCardRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(requestInfo: MyCardItem, position: Int) {
            val rh = if (requestInfo.rhType) "+" else "-"
            var blood = ""
            var hospital = ""
            BloodType.values().forEach {
                if (requestInfo.bloodType == it.id) blood = it.bloodType
            }
            Hospital.values().forEach {
                if (requestInfo.hospitalId == it.id) hospital = it.hospitalName
            }
            binding.apply {
                reqHospital.text = hospital
                reqBlood.text = "RH${rh} ${blood}형 전혈"
                reqDate.text = "${requestInfo.startDate}~${requestInfo.endDate}"
                reqCount.text = "${requestInfo.count}명"
                circleBlood.text = "RH $blood$rh"

                itemView.setOnClickListener {
                    itemClickListener.onClick(request[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyCardViewHolder, position: Int) {
        holder.onBind(request[position], position)
    }

    override fun getItemCount(): Int = request.size

    fun setItems(newItems: MyCardItem) {
        request.clear()
        request.addAll(listOf(newItems))
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<MyCardItem>) {
        request.addAll(newItems)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(requestInfo: MyCardItem)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}