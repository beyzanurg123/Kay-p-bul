package com.beyza.kayip_esyam.view

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.beyza.kayip_esyam.R
import com.beyza.kayip_esyam.databinding.ActivityGirisBinding
import com.beyza.kayip_esyam.databinding.FragmentProfilBinding
import com.beyza.kayip_esyam.user
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class profilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var imageUri:Uri
    private lateinit var dialog: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        auth=FirebaseAuth.getInstance()
        val uid=auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")

        binding.saveBtn.setOnClickListener {
            showProgressBar()
            val firstname=binding.etFirstName.text.toString()
            val lastname=binding.etLastName.text.toString()
            val bio=binding.etBio.text.toString()
            val user=user(firstname,lastname,bio)
            if (uid!=null){
                databaseReference.child(uid).setValue(user).addOnCanceledListener {
                        uploadProfilePic()
                }
            }
        }
        return binding.root
    }

    private fun uploadProfilePic() {
        hideProgressBar()
        imageUri=Uri.parse("android.resource://$parentFragment/${R.drawable.profile}")
        storageReference=FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        storageReference.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(getActivity(),"Profilin başarıyla güncellendi!",Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            hideProgressBar()
            Toast.makeText(getActivity(),"Profilin güncellenemedi!",Toast.LENGTH_SHORT).show();
        }
    }
    private fun showProgressBar(){
       dialog=Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_wait)
       dialog.setCanceledOnTouchOutside(false)
       dialog.show()
    }

private fun hideProgressBar(){
    dialog.dismiss()
}
}