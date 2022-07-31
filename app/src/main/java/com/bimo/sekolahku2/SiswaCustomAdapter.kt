package com.bimo.sekolahku2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SiswaCustomAdapter(context: Context): ArrayAdapter<Siswa>(context, R.layout.siswa_custom_adapter) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView


        var siswa = getItem(position)

        if (siswa?.gender == "Pria") {
            view = LayoutInflater.from(context).inflate(R.layout.siswa_man_custom_adapter, null)
        }else if (siswa?.gender == "Wanita") {
            view = LayoutInflater.from(context).inflate(R.layout.siswa_woman_custom_adapter, null)
        }



        var textNama = view!!.findViewById<TextView>(R.id.rowNama)
        var textJenjang = view!!.findViewById<TextView>(R.id.rowJenjang)
        var textGender = view!!.findViewById<TextView>(R.id.rowGender)
        var textNoHp = view!!.findViewById<TextView>(R.id.rowNoHp)




        textNama.setText(siswa?.namaDepan)
        textGender.setText(siswa?.gender)
        textJenjang.setText(siswa?.jenjang)
        textNoHp.setText(siswa?.noHP)



        return view
    }


}