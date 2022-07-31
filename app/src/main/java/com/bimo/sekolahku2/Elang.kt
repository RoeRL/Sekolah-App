package com.bimo.sekolahku2

class Elang {
    var jenis_elang = ""
    var sayap = ""

    fun mangsa(mangsa_elang: String): String {
        return "Elang $jenis_elang yang bersayap $sayap memangsa $mangsa_elang"
    }
    fun speed(kecepatan_elang: Int): Int {
        return kecepatan_elang
    }
}