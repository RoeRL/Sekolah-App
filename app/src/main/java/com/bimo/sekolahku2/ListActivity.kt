package com.bimo.sekolahku2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    lateinit var adapter : SiswaCustomAdapter

    override fun onResume() {
        var siswaDataSource = SiswaDataSource(this)

        var listSiswa = siswaDataSource.getAll()

//        var listDataSiswa = ArrayList<String>()
//
//        for (i in listSiswa.indices) {
//            listDataSiswa.add(listSiswa.get(i).namaDepan + " " + listSiswa.get(i).namaBelakang)
//        }
//
//
//        //memasukankedalam list adapter
//        var adapterFromDb = ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, listDataSiswa)

        adapter = SiswaCustomAdapter(this)
        adapter.addAll(listSiswa)

        listViewSiswa.adapter = adapter
        adapter.notifyDataSetChanged()

        listViewSiswa.setOnItemClickListener { parent, view, position, id ->
            viewDetailSiswa(position)
        }

        searchViewSiswa.setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextChange(newText: String?): Boolean {
                        //bersih kan adapter
                        adapter.clear()
                        //isi ulang berdasarkan keyword
                        var siswaDataSource = SiswaDataSource(this@ListActivity)
                        var siswa = siswaDataSource.search(newText!!)//memakai !! karena harus ada dan newText berdasarkan fun onQueryTextChange yg bernilai newText
                        adapter.addAll(siswa)
                        return false
                    }

                    override fun onQueryTextSubmit(query: String?): Boolean {

                        return false
                    }

                }
        )
        registerForContextMenu(listViewSiswa)

        super.onResume()

    }

    fun viewDetailSiswa(position: Int) {
        var siswa = adapter.getItem(position)

        var id = siswa?.id

        var intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("id", id)
        startActivity(intent)

//        Toast.makeText(this, "$position dimiliki oleh ${siswa?.namaDepan}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        supportActionBar?.setTitle("List Data Siswa")




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu) //
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add) {
            var intent = Intent (this, FormActivity::class.java)
            startActivity(intent)
        }else {
            var intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    //generate context menu

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.menu_context, menu)//mendaftarkan menu_context.xml ke dalam ListActivity
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    //context menu action (Delete)

    override fun onContextItemSelected(item: MenuItem): Boolean {

        var menuInfo= item?.menuInfo as AdapterView.AdapterContextMenuInfo
        var position = menuInfo.position
        var siswa = adapter.getItem(position)

        if (item.itemId == R.id.delete) {
            var siswaDataSource = SiswaDataSource(this)
            siswaDataSource.deleteSiswa(siswa?.id)
            val builder = AlertDialog.Builder(this)
            //set title for alert dialog
            builder.setTitle(R.string.dialogTitle)
            //set message for alert dialog
            builder.setMessage(R.string.dialogMessage)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton("Yes"){dialogInterface, which ->
                Toast.makeText(applicationContext,"Menghapus",Toast.LENGTH_LONG).show()
                adapter.remove(siswa)
            }
            //performing cancel action
            builder.setNeutralButton("Cancel"){dialogInterface , which ->
                Toast.makeText(applicationContext,"Cancel",Toast.LENGTH_LONG).show()
            }
            //performing negative action
            builder.setNegativeButton("No"){dialogInterface, which ->
                Toast.makeText(applicationContext,"Gagal menghapus",Toast.LENGTH_LONG).show()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }

        if (item.itemId == R.id.edit){
            var intent = Intent(this, FormActivity::class.java)
            intent.putExtra("id", siswa?.id)
            startActivity(intent)
        }

        return super.onContextItemSelected(item)
    }

}