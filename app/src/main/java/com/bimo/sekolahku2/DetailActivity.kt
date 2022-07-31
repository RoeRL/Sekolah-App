package com.bimo.sekolahku2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setTitle("Detail Siswa")

        var id = intent.getIntExtra("id", 0)//mengambil parameter yang dikirim dari list activity

        var siswaDataSource = SiswaDataSource(this)
        var siswa = siswaDataSource.getSiswa(id)

        var gender = siswa?.gender

        if (gender == "Pria") {
            profileDetailPic.setImageResource(R.drawable.ic_man)
        }else if (gender == "Wanita") {
            profileDetailPic.setImageResource(R.drawable.ic_woman)
        }

        txt_nama.setText(siswa.namaDepan+" "+siswa.namaBelakang)
        txt_no_hp.setText(siswa.noHP)
        txt_email.setText(siswa.email)
        txt_tgl_lahir.setText(siswa.tanggalLahir)
        txt_gender.setText(siswa.gender)
        txt_jenjang.setText(siswa.jenjang)
        txt_hobi.setText(siswa.hobi)
        txt_alamat.setText(siswa.alamat)
    }
}