package com.yxd.knowledge.view._05_recyclerview.code

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yxd.R
import com.yxd.devlib.base.TestFragment

class RVFragment : TestFragment() {

    override fun init(view: View, savedInstanceState: Bundle?) {
        // 1️⃣ 初始化
        val rv = RecyclerView(requireContext())

        // 2️⃣ 设置布局管理器
        rv.layoutManager = LinearLayoutManager(requireContext())

        // 3️⃣ 准备数据
        val list = (1..100).map { "Item$it" }.toList()

        // 4️⃣ 设置适配器
        rv.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            // 创建VH
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                logt(" ============onCreateViewHolder")
                val itemView = LayoutInflater.from(requireContext()).inflate(R.layout.item_tv, parent, false)
                return object :  RecyclerView.ViewHolder(itemView){}
            }

            // 绑定VH
            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                logt("***********onBindViewHolder  $position")
                holder.itemView.findViewById<TextView>(R.id.tv).text = list[position]
            }

            override fun getItemCount(): Int {
                return list.size
            }

        }
        addView(rv, height = MP)
    }


}