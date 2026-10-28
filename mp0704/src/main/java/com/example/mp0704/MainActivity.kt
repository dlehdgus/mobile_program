package com.example.mp0704

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //OK	버튼과	No	버튼의	카운트를	표시할	텍스트	뷰
    lateinit	var	m_OkCount	:	TextView
    lateinit	var	m_NoCount	:   TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val	button_ok	:	Button	=	findViewById(R.id.button_ok)
        val	button_no	:	Button	=	findViewById(R.id.button_no)
        //OK	버튼	클릭	시	카운트	증가
        button_ok.setOnClickListener	{
            val	num	=	(Integer.parseInt(m_OkCount.text.toString())+1)
            m_OkCount.text	=		num.toString()
        }
        //NO	버튼	클릭	시	카운트	증가
        button_no.setOnClickListener	{
            val	num	=	(Integer.parseInt(m_NoCount.text.toString())+1)
            m_NoCount.text	=		num.toString()
        }
    }
    override	fun	onCreateOptionsMenu(menu:	Menu?):	Boolean	{
        menuInflater.inflate(R.menu.menu,menu)
        //찬성	메뉴(menu_up)를	ok_layout	레이아웃	적용
        val	item_ok	=	menu?.findItem(R.id.menu_up)
        item_ok?.setActionView(R.layout.ok_layout	)
        val	oklayout		=	item_ok?.actionView
        if	(oklayout	!=	null)	{
            m_OkCount	=	oklayout.findViewById(R.id.thumbs_up_num)
        }
        //반대	메뉴(menu_down)를	no_layout	레이아웃	적용
        val	item_no	=	menu?.findItem(R.id.menu_down)
        item_no?.setActionView(R.layout.no_layout	)
        val	nolayout		=	item_no?.actionView
        if	(nolayout	!=	null)	{
            m_NoCount	=	nolayout.findViewById(R.id.thumbs_up_num)
        }
        return	super.onCreateOptionsMenu(menu)
    }
    override	fun	onOptionsItemSelected(item:	MenuItem):	Boolean	{
        when(item.itemId)	{
            //'초기화'	메뉴	선택	시	카운트	리셋
            R.id.menu_init	->	{
                m_OkCount.text="0"
                m_NoCount.text	=	"0"
                return	true
            }
            //'종료'	메뉴	선택	시	액티비티	종료
            R.id.menu_close	->	{
                finish()
                return	true
            }
        }
        return	super.onOptionsItemSelected(item)
    }
}