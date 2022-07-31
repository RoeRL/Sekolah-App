package com.bimo.sekolahku2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase

class SiswaDataSource(context: Context) {
    private var help = DatabaseHelper(context)
    private var sqlite: SQLiteDatabase? = null

        fun openAccess() {
            sqlite = help.writableDatabase
        }
        fun closeAccess() {
            help.close()
        }

    fun insertSiswa(siswa: Siswa) {
        openAccess()
        var cv = ContentValues()

        cv.put("nama_depan", siswa.namaDepan)
        cv.put("nama_belakang", siswa.namaBelakang)
        cv.put("no_hp", siswa.noHP)
        cv.put("gender", siswa.gender)
        cv.put("jenjang", siswa.jenjang)
        cv.put("hobi", siswa.hobi)
        cv.put("alamat", siswa.alamat)
        //Modifikasi Email tanggal lahir #3 menambahkan email tgl lahir
        cv.put("email", siswa.email)
        cv.put("tanggal_lahir", siswa.tanggalLahir)
        sqlite?.insert("siswa", null,cv)
        closeAccess()
    }

    fun fetchRow(cursor: Cursor) : Siswa {
        var siswa = Siswa()
        siswa.id = cursor.getInt(0)
        siswa.namaDepan = cursor.getString(1)
        siswa.namaBelakang = cursor.getString(2)                // Pemasukan data kedalam tabel array
        siswa.noHP = cursor.getString(3)
        siswa.gender = cursor.getString(4)
        siswa.jenjang = cursor.getString(5)
        siswa.hobi = cursor.getString(6)
        siswa.alamat = cursor.getString(7)
        //Modifikasi Email tanggal lahir #4
        siswa.email = cursor.getString(8)
        siswa.tanggalLahir = cursor.getString(9)
        return siswa
    }

    fun getAll(): ArrayList<Siswa> {

        openAccess()
        var sql = "SELECT * FROM siswa" //pengambilan data siswa oleh sql
        var cursor = sqlite?.rawQuery(sql, null)//pengonvertan jika cursor adalah sql
        cursor?.moveToFirst()//mulai dari yg awal

        var listSiswa = ArrayList<Siswa>()//menyiapkan arraylist kosong

        while (!cursor!!.isAfterLast) {// = Jika cursor bukanlah yg terakhir, maka list siswa yg kosong, di tambah data array fetchRow cursor
            listSiswa.add(fetchRow(cursor))//tidak akan berhenti kecuali sudah menyentuh data paling terakhir
            cursor.moveToNext()
        }

        //data yang bernilai "null" hanya akan di kosong kan saja, karena jika di tambahkan nilai null, akan terjadi error

        cursor.close()
        closeAccess()
        return listSiswa

    }

    fun getSiswa(id:Int) : Siswa {
        openAccess()
        val sql = "SELECT * FROM siswa WHERE id = $id"
        val cursor = sqlite!!.rawQuery(sql, null)
        cursor?.moveToFirst()

        var siswa = fetchRow(cursor)
        closeAccess()

        return siswa
    }
    fun search(keyword: String): ArrayList<Siswa> {

        openAccess()
        var sql = "SELECT * FROM siswa WHERE nama_depan LIKE ? OR nama_belakang LIKE ? OR no_hp LIKE ? OR alamat LIKE ?"//query binding
        var selection: Array<String> = arrayOf("%${keyword}%", "%${keyword}%", "%${keyword}%", "%${keyword}%")
        var cursor = sqlite?.rawQuery(sql, selection)
        cursor?.moveToFirst()

        var listSiswa = ArrayList<Siswa>()

        while (!cursor!!.isAfterLast) {
            listSiswa.add(fetchRow(cursor))
            cursor.moveToNext()
        }


        cursor.close()
        closeAccess()
        return listSiswa

    }
    fun deleteSiswa(id: Int?) {
        openAccess()
        sqlite?.delete("siswa", "id = $id", null)
        closeAccess()
    }
    fun udateSiswa(siswa: Siswa, id: Int) {
        openAccess()
        var cv = ContentValues()

        cv.put("nama_depan", siswa.namaDepan)
        cv.put("nama_belakang", siswa.namaBelakang)
        cv.put("no_hp", siswa.noHP)
        cv.put("gender", siswa.gender)
        cv.put("jenjang", siswa.jenjang)
        cv.put("hobi", siswa.hobi)
        cv.put("alamat", siswa.alamat)
        //Modifikasi Email tanggal lahir #3 menambahkan email tgl lahir
        cv.put("email", siswa.email)
        cv.put("tanggal_lahir", siswa.tanggalLahir)
        sqlite?.update("siswa", cv, "id = $id", null)
        closeAccess()
    }
}