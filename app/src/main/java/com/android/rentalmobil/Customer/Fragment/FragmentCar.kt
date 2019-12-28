package com.android.rentalmobil.Customer.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.rentalmobil.Admin.Model.Menu
import com.android.rentalmobil.Customer.Adapter.MenuAdapterCustomer
import com.android.rentalmobil.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_car.*

class FragmentCar : Fragment() {

    private lateinit var recyclerview:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_car, container, false)

        recyclerview=view.findViewById(R.id.recyclerview)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.setOnClickListener {
            startActivity(Intent(activity, FragmentCar::class.java))
        }

        val databaseRef = FirebaseDatabase.getInstance().getReference("Menu")
        val menu: ArrayList<Menu> = ArrayList()
        recyclerview.layoutManager = GridLayoutManager(activity,1)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    menu.clear()
                    for (data in p0.children){
                        val getValue= data.getValue(Menu::class.java)
                        menu.add(getValue!!)
                    }

                    recyclerview.adapter = activity?.let { MenuAdapterCustomer(menu, it) }
                }
            }

        })

    }


}