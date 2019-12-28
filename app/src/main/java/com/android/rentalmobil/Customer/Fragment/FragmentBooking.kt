package com.android.rentalmobil.Customer.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rentalmobil.Admin.Activity.DetailOrder
import com.android.rentalmobil.Admin.Model.Menu
import com.android.rentalmobil.Customer.Activity.ConfirmOrder
import com.android.rentalmobil.Customer.Activity.Quantity
import com.android.rentalmobil.Customer.Adapter.CartAdapterCustomer
import com.android.rentalmobil.Customer.Adapter.MenuAdapterCustomer
import com.android.rentalmobil.Customer.Model.Chart
import com.android.rentalmobil.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_customer_detail.*
import kotlinx.android.synthetic.main.activity_detail_order.*
import kotlinx.android.synthetic.main.activity_detail_order.total_price
import kotlinx.android.synthetic.main.activity_detail_order.total_quantity
import kotlinx.android.synthetic.main.fragment_car.recyclerview

class FragmentBooking : Fragment() {

    lateinit var totQuantity: TextView
    lateinit var totHarga: TextView
    lateinit var btn_checkout: Button
    lateinit var recyclerView: RecyclerView
    lateinit var cartList: MutableList<Chart>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_cart, container, false)

        totQuantity=view.findViewById(R.id.total_quantity)
        totHarga=view.findViewById(R.id.total_price)
        btn_checkout = view.findViewById(R.id.btn_checkout)
        recyclerView=view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        cartList = mutableListOf()

        getData()

        btn_checkout.setOnClickListener {
            val totalquantity = totQuantity.text.toString()
            val totalharga = totHarga.text.toString()
            startActivity(Intent(activity,ConfirmOrder::class.java).putExtra("totalquantity",totalquantity)
                .putExtra("totalharga",totalharga))

        }

        return view
    }

    private fun getData() {
        val databaseRef = FirebaseDatabase.getInstance().getReference("Chart")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var tt_quantity = 0
                var tt_price = 0
                if (dataSnapshot.exists()) {//before fetch we have clear the list not to show duplicate value
                    cartList.clear()
                    // fetch data & add to list
                    for (data in dataSnapshot.children) {
                        val std = data.getValue(Chart::class.java)
                        cartList.add(std!!)

                        val t_quantity = data.getValue(Chart::class.java)?.quantity?.toInt()
                        tt_quantity += t_quantity!!

                        val t_price = data.getValue(Chart::class.java)?.total?.toInt()
                        tt_price += t_price!!
                    }

                    recyclerView.adapter = activity?.let { CartAdapterCustomer(cartList, it) }

                    totQuantity.text = tt_quantity.toString()
                    totHarga.text = tt_price.toString()

                } else {
                    // if no data found or you can check specefici child value exist or not here

                }

            }

        })
    }
}