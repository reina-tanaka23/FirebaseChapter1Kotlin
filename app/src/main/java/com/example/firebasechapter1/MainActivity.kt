package com.example.firebasechapter1

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.example.firebasechapter1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private var fbase: FirebaseApp? = null

    private var dataText: TextView? = null
    private val db: FirebaseFirestore? = null
    private var people: CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataText = findViewById(R.id.dataText);

        setSupportActionBar(binding.toolbar)

        fbase = FirebaseApp.initializeApp(this)

        binding.fab.setOnClickListener { view ->
            val msg: String = "Firebase: " + fbase!!.name
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val db = Firebase.firestore
        people = db.collection("people")


//        people!!.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                var result = ""
//                for (document in task.result!!) {
//                    val data = document.data
//                    result += (data["name"].toString() + " ["
//                            + data["mail"].toString() + ":"
//                            + data["age"].toString() + "]\n")
//                }
//                dataText!!.setText(result)
//            } else {
//                dataText!!.setText("can't load data...")
//            }
//        }

        val docRef = db.collection("people")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("DEBUG", "DocumentSnapshot data: ${document}")
                } else {
                    Log.d("DEBUG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("DEBUG", "get failed with ", exception)
            }
    }

//    fun doAction(view: View?) {
//        val fstr: String = nameText.getText().toString() + ""
//    }


    override fun onStart() {
        super.onStart()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}