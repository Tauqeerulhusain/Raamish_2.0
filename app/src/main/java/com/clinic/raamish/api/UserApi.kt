package com.clinic.raamish.api

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.clinic.raamish.models.Patient
import com.clinic.raamish.models.User
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import javax.inject.Inject

class UserApi @Inject constructor() {
    var database = Firebase.database("https://raameshtest-default-rtdb.asia-southeast1.firebasedatabase.app/")
    var databaseProd = Firebase.database("https://raamesh-31e8b-default-rtdb.firebaseio.com/")

    fun saveFirebase() : Unit{
        try {
            val myRef = database.getReference("message")
            myRef.setValue("I got blessed with a baby boy")
        }
        catch (exc: Exception){
            var test = exc
        }
    }

    fun addUser(){
        val myRef = database.getReference("Users")
        var phone1 = "92728374214"
        var phone2 = "80871391051"
        val users = User("Khalid",21)
        myRef.child(phone2).setValue(users).addOnSuccessListener {
            Log.d(ContentValues.TAG, "Value is:")
        }.addOnFailureListener{
            Log.d(ContentValues.TAG, "Value is:")
        }
    }

    fun updateUser(){
        val myRef = database.getReference("Users")
        val user = mapOf<String,String>(
            "name" to "Saad",
            "operator" to "Airtel",
            "location" to "Pune"
        )
        var phone2 = "80871391051"
        myRef.child(phone2).updateChildren(user).addOnSuccessListener {

        }.addOnFailureListener{

        }
    }

    fun getUser(){
        val myRef = database.getReference("Users")
        var phone2 = "80871391051"
        myRef.child(phone2).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val operator = it.child("operator").value
                val location = it.child("location").value
            }
        }.addOnFailureListener{
        }
    }

    fun getAllUsers(){
        val myRef = database.getReference("Users")
        var user: User?
        myRef.get().addOnSuccessListener {
            if (it.exists()){
                for(patientSnapshot in it.children){
                    user = patientSnapshot.getValue<User>()
                }
            }
        }.addOnFailureListener{
        }
    }

    fun getAllUsersLive(){
        val myRef = database.getReference("Users")
        var user: User?
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(userSnapshot in dataSnapshot.children){
                    user = userSnapshot.getValue<User>()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}