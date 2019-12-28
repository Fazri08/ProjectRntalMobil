package com.android.rentalmobil.Admin.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.rentalmobil.Admin.Model.Users
import com.android.rentalmobil.Login
import com.android.rentalmobil.R
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dasboard_admin.*


class DashboardAdmin : AppCompatActivity() {

    internal var firebaseUser: FirebaseUser? = null
    internal lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dasboard_admin)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser!!.uid)

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<Users>(Users::class.java)
                tv_username.text = user?.username
                if (user?.imageUrl == "default") {
                    img_profile.setImageResource(R.mipmap.ic_launcher)
                } else {
                    //chage this
                    Glide.with(applicationContext).load(user?.imageUrl).into(img_profile)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        btn_menu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        btn_list_order.setOnClickListener {
            startActivity(Intent(this, ListOrder::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(
                    Intent(
                        this@DashboardAdmin,
                        Login::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                return true
            }
        }

        return false
    }
}
