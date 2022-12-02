package com.beyza.kayip_esyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.beyza.kayip_esyam.databinding.ActivityKayitBinding
import com.beyza.kayip_esyam.databinding.ActivitySecondactBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
//import kotlin.android.synthetic.main.activity_kayit.*

class kayit : AppCompatActivity() {
        private lateinit var binding: ActivityKayitBinding
        private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKayitBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        auth= Firebase.auth


    }
    fun kayitoll(view: View){
        val email=binding.emailtextt.text.toString()
        val sifre=binding.sifretextt.text.toString()
        if(email.equals("")||sifre.equals("")){
            Toast.makeText(this, "şifreni ve epostanı gir", Toast.LENGTH_SHORT).show()
        }
        else{
            auth.createUserWithEmailAndPassword(email,sifre).addOnSuccessListener {
                val intent=Intent(this,giris::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }

    }
}