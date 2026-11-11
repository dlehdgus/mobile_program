package com.example.mp0901

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(val itemList : ArrayList<MyItem>) : BaseAdapter() {
    //리스트 뷰에 표시할 항목의 총 개수를 반환하는 메서드
    override fun getCount(): Int {
        return itemList.size
    }
    //지정된 항목(position)에 해당하는 데이터를 반환하는 메서드
    override fun getItem(p0: Int): Any {
        return itemList[p0]
    }
    //항목의 ID를 반환하는 메서드
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    //리스트 뷰의 각 항목을 표시할 때 데이터를 화면에 연결하는 중요한 메서드
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val item = this.itemList[p0]
        var convertView = p1
        convertView = LayoutInflater.from(p2?.context).inflate(R.layout.listview_item, p2, false)
        val itemName: TextView = convertView.findViewById(R.id.textView1)
        val itemChar: TextView = convertView.findViewById(R.id.textView2)
        val itemWeight: TextView = convertView.findViewById(R.id.textView3)
        val itemIamge: ImageView = convertView.findViewById(R.id.imageView)
        itemName.text = item.name
        itemChar.text = item.character
        itemWeight.text = item.weight
        itemIamge.setImageResource(item.image)
        return convertView
    }

}