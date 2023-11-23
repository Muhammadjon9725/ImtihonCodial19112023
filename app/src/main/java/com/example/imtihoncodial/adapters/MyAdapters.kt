package com.example.retrofit2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imtihoncodial.databinding.ItemRvBinding
import com.example.retrofit2.models.MyTodo
import retrofit2.Callback

class MyAdapters(val list:ArrayList<MyTodo>, val click: OOnClick):RecyclerView.Adapter<MyAdapters.MyVH>() {
    inner class MyVH(val itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(myTodo: MyTodo){
            itemRvBinding.nameTime.text = myTodo.oxirgi_muddat
            itemRvBinding.nameWord.text = myTodo.sarlavha
            itemRvBinding.holat.text = myTodo.holat
            itemRvBinding.btnDelete.setOnClickListener {
                click.Click(myTodo)
            }
            itemRvBinding.root.setOnClickListener {
                click.update(myTodo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVH {
        return MyVH(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyVH, position: Int) {
        holder.onBind(list[position])
    }

    interface OOnClick{
        fun Click(myTodo: MyTodo)
        fun update(myTodo: MyTodo)
    }
}