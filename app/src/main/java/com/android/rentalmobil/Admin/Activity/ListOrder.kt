package com.android.rentalmobil.Admin.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rentalmobil.Admin.Adapter.ListOrderAdapter
import com.android.rentalmobil.Customer.Model.Order
import com.android.rentalmobil.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list_order.*


class ListOrder : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var databaseRef: DatabaseReference
    lateinit var orderList: MutableList<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_order)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseRef = FirebaseDatabase.getInstance().getReference("Order")
        orderList = mutableListOf()
        LoadData()
    }

    private fun LoadData()
    {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError)
            {
                Toast.makeText(applicationContext,"Error Encounter Due to "+databaseError.message, Toast.LENGTH_LONG).show()/**/

            }

            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                if (dataSnapshot.exists())
                {//before fetch we have clear the list not to show duplicate value
                    orderList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children)
                    {
                        val std = data.getValue(Order::class.java)
                        orderList.add(std!!)
                    }

                    val adapter = ListOrderAdapter(orderList, this@ListOrder)
                    recyclerview.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
                else
                {
                    // if no data found or you can check specefici child value exist or not here
                    Toast.makeText(applicationContext,"No data Found", Toast.LENGTH_LONG).show()
                }

            }

        })
    }
}
