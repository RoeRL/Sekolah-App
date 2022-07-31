package com.bimo.sekolahku2

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_form.*
import java.time.Month
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

// Modifikasi Email tgl lahir #6 menambah date picker dialog
class FormActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    // Modifikasi Email tgl lahir #7 menambah date picker dialog
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        var tanggalLahir = "${dayOfMonth} - ${month + 1} - ${year}"
        inputTanggalLahir.setText(tanggalLahir)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        var id = intent.getIntExtra("id", 0)
        if (id > 0) {
            title = "Edit Siswa"
            tampilDataSiswa(id)
            btnSimpan.setText("update")
        }else {
            title = "Tambah Siswa"
        }

        btnSimpan.setOnClickListener {
            simpan()
        }
        //Modifikasi email tgl lahir #8
        //jika edit text diklik maka...
        inputTanggalLahir.setOnClickListener {
            showDatePickerDialog()
        }
    }

    fun tampilDataSiswa(id: Int) {
        var siswaDataSource = SiswaDataSource(this)
        var siswa = siswaDataSource.getSiswa(id)

        //set value ke form activity

        inputNamaDepan.setText(siswa.namaDepan)
        inputNamaBelakang.setText(siswa.namaBelakang)
        inputNomorHP.setText(siswa.noHP)
        inputEmail.setText(siswa.email)
        inputTanggalLahir.setText(siswa.tanggalLahir)
        inputAlamat.setText(siswa.alamat)


        //input gender value

        var gender = siswa.gender
        if(gender == "pria") {
            rbPria.isChecked = true
        }else {
            rbWanita.isChecked = true
        }
        //input spinner value
        var jenjang = siswa.jenjang
        //buka adapter spinner
        var adapter = spinnerJenjang.adapter as ArrayAdapter<String>
        var positionJenjang = adapter.getPosition(jenjang)//mencari possi yg sama dengan jenjang dri database
        spinnerJenjang.setSelection(positionJenjang)

        //hobi
        var hobi = siswa.hobi
        if (hobi!!.contains("Membaca")) {
            cbMembaca.isChecked = true
        }
        if (hobi!!.contains("Menulis")) {
            cbMenulis.isChecked = true
        }
        if (hobi!!.contains("Menggambar")) {
            cbMenggambar.isChecked = true
        }


    }

    //modifikasi email tgl lahir #9

    fun showDatePickerDialog() {
        var cal = Calendar.getInstance()
        var datePick = DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        datePick.show()
    }
    fun simpan() {

        if (validateAll() == true) {
            return
        }

        var valNamaDepan = inputNamaDepan.text.toString()
        var valNamaBelakang = inputNamaBelakang.text.toString()
        var valNoHP = inputNomorHP.text.toString()
        var valAlamat = inputAlamat.text.toString()
        var valJenjang = spinnerJenjang.selectedItem.toString()
        var valGender = getSelectedGender()
        var valHobi = getSelectedHobi()
        var valEmail = inputEmail.text.toString()
        var valTanggalLahir = inputTanggalLahir.text.toString()

        var siswa = Siswa()
        siswa.namaDepan = valNamaDepan
        siswa.namaBelakang = valNamaBelakang
        siswa.noHP = valNoHP
        siswa.alamat = valAlamat
        siswa.jenjang = valJenjang
        siswa.gender = valGender
        siswa.hobi = valHobi
        //modif email tgl lahir #10
        siswa.email = valEmail
        siswa.tanggalLahir = valTanggalLahir

        var siswaDataSource = SiswaDataSource(this)//memanggil class siswa data source
        //input siswa

        var id = intent.getIntExtra("id", 0)
        if (id > 0) {
            siswaDataSource.udateSiswa(siswa, id)
            Toast.makeText(this, "Data siswa berhasil diubah", Toast.LENGTH_SHORT).show()
        }else {
            siswaDataSource.insertSiswa(siswa)
            Toast.makeText(this, "Data siswa berhasil disimpan", Toast.LENGTH_SHORT).show()
        }



        var intent = Intent (this, ListActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun getSelectedGender(): String {
        var gender: String
        if(rbPria.isChecked) {
            gender = "Pria"
        }else {
            gender = "Wanita"
        }
        return gender
    }
    fun getSelectedHobi(): String {
        var hobi = ArrayList<String>()
        if (cbMembaca.isChecked) {
            hobi.add("Membaca")
        }
        if (cbMenulis.isChecked) {
            hobi.add("Menulis")
        }
        if (cbMenggambar.isChecked) {
            hobi.add("Menggambar")
        }
        return TextUtils.join(", ", hobi).toString()
    }
    fun validateNamaDepan(): Boolean {
        var NamaDepan = inputNamaDepan.text.toString()
        if (NamaDepan.isEmpty()) {
            inputNamaDepan.setError("Nama Depan wajib diisi")
            inputNamaDepan.requestFocus()
            return true
        }
        return false
    }
    fun validateNamaBelakang(): Boolean {
        var NamaBelakang = inputNamaBelakang.text.toString()
        if (NamaBelakang.isEmpty()) {
            inputNamaBelakang.setError("Nama Belakang wajib diisi")
            inputNamaBelakang.requestFocus()
            return true
        }
        return false
    }
    fun validateNoHP(): Boolean {
        var NoHP = inputNomorHP.text.toString()
        if(!Pattern.matches("[0-9]{10,13}$", NoHP)){
            inputNomorHP.setError("No Hp min 10 angka dan maksimal 13 angka")
            inputNomorHP.requestFocus()
            return true
        }
        return false
    }
    fun validateAlamat(): Boolean {
        var Alamat = inputAlamat.text.toString()
        if (Alamat.isEmpty()) {
            inputAlamat.setError("Alamat wajib diisi")
            inputAlamat.requestFocus()
            return true
        }
        return false
    }
    fun validateAll(): Boolean {
        if (validateNamaDepan() == true || validateNamaBelakang() == true || validateNoHP() == true || validateAlamat() == true) {
            return true
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)//mendaftarkan menu save ke form activity
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { //Jika
        if (item.itemId == R.id.save) {
            simpan()
        }
        return super.onOptionsItemSelected(item)
    }
}