package com.android.rentalmobil.Customer.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.rentalmobil.Constant
import com.android.rentalmobil.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_confirm_order.*
import java.util.HashMap

class ConfirmOrder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)

        val totalPrice = intent.getStringExtra(Constant.KEY_TOTAL_CHART)

        btn_confirm_order.setOnClickListener {
            val nama = et_nama.text.toString()
            val no_telp = et_no_telp.text.toString()
            val alamat = et_alamat.text.toString()

            val id_user = FirebaseAuth.getInstance().currentUser?.uid

            val databaseOrder = FirebaseDatabase.getInstance().getReference("Order").push()
            val orderid = databaseOrder.key.toString()

            val hashMap = HashMap<String, String>()
            hashMap["user_id"] = id_user!!
            hashMap["orderid"] = orderid
            hashMap["name"] = nama
            hashMap["no_telp"] = no_telp
            hashMap["alamat"] = alamat
            hashMap["total"] = totalPrice

            databaseOrder.setValue(hashMap).addOnCompleteListener {

                val databaseref = FirebaseDatabase.getInstance().getReference("Chart").child(id_user)
                databaseref.removeValue().addOnCompleteListener(){}

                Toast.makeText(this, "Pesanan berhasil di submit", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainBottom::class.java))
                finish()
            }
        }

    }
}
