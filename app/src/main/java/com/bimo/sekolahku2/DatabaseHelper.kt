package com.bimo.sekolahku2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper (context, "sekolahku.db", null,1){

    //Modifikasi Email tanggal lahir #2 menambahkan email tgl lahir
    override fun onCreate(db: SQLiteDatabase?) {
        var sql: String = "CREATE TABLE siswa(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nama_depan TEXT, " +
                "nama_belakang TEXT, " +
                "no_hp TEXT, " +
                "gender TEXT, " +
                "jenjang TEXT, " +
                "hobi TEXT, " +
                "alamat TEXT, " +
                "email TEXT, " +
                "tanggal_lahir TEXT)"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sql = "DROP TABLE IF EXISTS siswa"
        db?.execSQL(sql)
        onCreate(db)
    }

}