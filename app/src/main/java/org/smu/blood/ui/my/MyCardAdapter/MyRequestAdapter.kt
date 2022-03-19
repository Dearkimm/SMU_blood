package org.smu.blood.ui.my.MyCardAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.ItemCardRequestBinding
import org.smu.blood.model.BloodType
import org.smu.blood.model.Hospital

class MyRequestAdapter: RecyclerView.Adapter<MyRequestAdapter.MyRequestViewHolder>() {

    val request = mutableListOf<MainRequest>()
    private lateinit var itemClickListener: ItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyRequestViewHolder {
        val binding = ItemCardRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyRequestViewHolder(binding)
    }

    inner class MyRequestViewHolder(
        private val binding: ItemCardRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(requestInfo: MainRequest, position: Int) {
            val rh = if (requestInfo.rhType) "-" else "+"
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

    override fun onBindViewHolder(holder: MyRequestAdapter.MyRequestViewHolder, position: Int) {
        holder.onBind(request[position], position)
    }

    override fun getItemCount(): Int = request.size

    fun setItems(newItems: List<MainRequest>) {
        request.clear()
        request.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addItems(newItems: List<MainRequest>) {
        request.addAll(newItems)
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onClick(requestInfo: MainRequest)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}