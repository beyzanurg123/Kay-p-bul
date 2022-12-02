package com.beyza.kayip_esyam.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.beyza.kayip_esyam.R
import com.beyza.kayip_esyam.adapter.feedrecyleradapter
import com.beyza.kayip_esyam.databinding.FragmentHomeeBinding
import com.beyza.kayip_esyam.databinding.FragmentInsanBinding
import com.beyza.kayip_esyam.model.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class homeeFragment : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var binding: FragmentHomeeBinding
    val postArrayList : ArrayList<Posts> = ArrayList()
    var adapter :feedrecyleradapter ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       binding=FragmentHomeeBinding.inflate(layoutInflater,container,false)
       val view = binding.root
        auth = Firebase.auth
        db = Firebase.firestore
        getDataFromFirestore()

        binding.Recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = feedrecyleradapter(postArrayList)
        binding.Recyclerview.adapter = adapter
       return view

}
    fun getDataFromFirestore() {

        db.collection("Posts").orderBy("date",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(requireContext(),exception.localizedMessage,Toast.LENGTH_LONG).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        postArrayList.clear()

                        val documents = snapshot.documents
                        for (document in documents) {
                            val comment = document.get("comment") as String
                            val useremail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Posts(useremail,comment, downloadUrl)
                            postArrayList.add(post)
                        }
                        adapter!!.notifyDataSetChanged()
                    }
                }

            }
        }

}}