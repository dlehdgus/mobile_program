package com.example.mp1004

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context,"HKBook.db",null,1) {
    //앱 최초 실행 시 테이블 생성
    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = "CREATE TABLE IF NOT EXISTS HKBook( " +
                "SEQ INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "CATEGORY TEXT, " +
                "PURPOSE TEXT," +
                "DATE TEXT, " +
                "MONEY INTEGER," +
                "MEMO TEXT ) "
        db?.execSQL(sql)
    }
    //DB 버전 변경 시 처리(현재는 비워둠)
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
    //가계부 데이터 1건 삽입
    fun insert(vo: HouseKeeping) {
        var sql = " INSERT INTO HKBook(CATEGORY,PURPOSE,DATE,MONEY,MEMO) " +
                  " VALUES(?,?,?,?,?)"
        var db = this.writableDatabase
        //따옴표가 포함된 입력도 안전하게 저장되도록 바인딩 인자 사용
        db.execSQL(sql, arrayOf<Any>(vo.category, vo.purpose, vo.date, vo.money, vo.memo))
    }
    //특정 날짜 범위에 해당하는 가계부 데이터 조회
    fun findTime(fromDate: String, toDate: String): ArrayList<HouseKeeping> {
        var sql = " SELECT SEQ, CATEGORY, PURPOSE, DATE, MONEY, MEMO FROM HKBook WHERE DATE BETWEEN " +
                  " ? AND ?"
        var db = this.readableDatabase
        var result = db.rawQuery(sql, arrayOf(fromDate, toDate))
        var list = ArrayList<HouseKeeping>()
        //결과 셋에서 각 행의 데이터를 읽어 리스트에 추가
        while (result.moveToNext()) {
            var seq = result.getInt(0)
            var category = result.getString(1)
            var purpose = result.getString(2)
            var date = result.getString(3)
            var money = result.getInt(4)
            var memo = result.getString(5)
            list.add(HouseKeeping(seq, category, purpose, date, money, memo))
        }
        result.close()
        return list
    }
}