package org.smu.blood.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.smu.blood.api.database.MainRequest
import org.smu.blood.databinding.ItemCardRequestBinding
import org.smu.blood.model.BloodType

class MainRequestAdapter: RecyclerView.Adapter<MainRequestAdapter.MainRequestViewHolder>() {

    private val requestInfo = mutableListOf<MainRequest>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRequestViewHolder {
        val binding = ItemCardRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainRequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainRequestViewHolder, position: Int) {
        holder.onBind(requestInfo[position], holder.itemView.context)
    }

    override fun getItemCount(): Int = requestInfo.size

    fun setItems(newItems: List<MainRequest>) {
        requestInfo.clear()
        requestInfo.addAll(newItems)
        notifyDataSetChanged()
    }

    class MainRequestViewHolder(
        private val binding: ItemCardRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(requestInfo: MainRequest, context: Context) {
            binding.apply {
//                reqHospital.text = requestInfo.hospitalId
                BloodType.values().forEach {
                    if (requestInfo.bloodType == it.id) {
                        reqBlood.text = "${it.bloodType}형 전혈"
                        circleBlood.text = "RH ${it.bloodType}"
                    }
                }
                reqRh.text = "RH${if (requestInfo.rhType) "+" else "-"}"
                circleRh.text = if (requestInfo.rhType) "+" else "-"
                reqDate.text = "${requestInfo.startDate}~${requestInfo.endDate}"
                reqCount.text = "${requestInfo.count}명"
            }
        }
    }
}