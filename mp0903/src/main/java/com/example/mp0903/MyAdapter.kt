package com.example.mp0903

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(val catsList : ArrayList<MyItem>) : BaseAdapter() {
    //스피너에 표시할 항목의 개수를 반환하는 메서드
    override fun getCount(): Int {
        return catsList.size
    }
    //지정된 항목(position)에 해당하는 데이터를 반환하는 메서드
    override fun getItem(p0: Int): Any {
        return catsList[p0]
    }
    //항목의 ID를 반환하는 메서드
    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }
    //스피너의 각 항목을 표시할 때 데이터를 화면에 연결하는 중요한 메서드
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val cat = this.catsList[p0]
        var convertView = LayoutInflater.from(p2?.context).inflate(R.layout.spinner_item, p2, false)
        val imgCat: ImageView = convertView.findViewById(R.id.imgCat)
        imgCat.setImageResource(cat.image)
        val tvName: TextView = convertView.findViewById(R.id.tvName)
        tvName.text = cat.name
        return convertView
    }
}