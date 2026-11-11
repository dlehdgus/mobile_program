package com.example.mp0904

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrderAdapter(val context: Context, val fruit: ArrayList<Fruit>) :
    RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    private lateinit  var selectChangedListener: OrderAdapter.OnSelectChangedListener
    //ViewHolder 클래스 정의
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currentFruitImage : ImageView = itemView.findViewById(R.id.image)
        val fruitPrice : TextView = itemView.findViewById(R.id.price)
        var totaltCost: TextView = itemView.findViewById(R.id.cost)
        var subtractFruit: TextView = itemView.findViewById(R.id.minus)
        var quantityText: TextView = itemView.findViewById(R.id.quantity)
        var addFruit: TextView = itemView.findViewById(R.id.plus)
        var removeFruit: TextView= itemView.findViewById(R.id.delete_item)
    }
    //ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder {
        val  layoutInflater  =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(layoutInflater)
    }
    //ViewHolder에 데이터 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item : Fruit = fruit.get(position)
        holder.currentFruitImage.setImageResource(item.mImage)
        holder.fruitPrice.text = item.mPrice.toString()
        holder.totaltCost.text = " " + item.mPrice * item.mQuantity
        holder.quantityText.text = "x " + item.mQuantity
        //수량 증가 버튼 클릭 이벤트
        holder.addFruit.setOnClickListener {
            item.mQuantity +=1
            holder.quantityText.text = "x " + item.mQuantity
            holder.totaltCost.text = " " +item.mPrice * item.mQuantity
            selectChangedListener.onSelectChanged(fruit)
        }
        //수량 감소 버튼 클릭 이벤트
        holder.subtractFruit.setOnClickListener{
            if (item.mQuantity > 0) item.mQuantity -= 1
            holder.quantityText.text = "x " + item.mQuantity
            holder.totaltCost.text = " " + item.mPrice * item.mQuantity
            selectChangedListener.onSelectChanged(fruit)
        }
        //항목 삭제 버튼 클릭 이벤트
        holder.removeFruit.setOnClickListener {
            //바인딩 시점이 아닌 현재 위치를 사용해야 다른 행이 지워지지 않음
            val pos = holder.bindingAdapterPosition
            if (pos == RecyclerView.NO_POSITION) return@setOnClickListener
            fruit.removeAt(pos)
            notifyItemRemoved(pos)
            selectChangedListener.onSelectChanged(fruit)
        }
    }
    //항목 개수 반환
    override fun getItemCount(): Int {
        return fruit.size;
    }
    //선택 변경 리스너 인터페이스 정의
    interface OnSelectChangedListener {
        fun onSelectChanged(item : ArrayList<Fruit>)
    }
    //선택 변경 리스너 설정
    fun setOnSelectChangedListener(listener: OnSelectChangedListener) {
        selectChangedListener = listener
    }
}