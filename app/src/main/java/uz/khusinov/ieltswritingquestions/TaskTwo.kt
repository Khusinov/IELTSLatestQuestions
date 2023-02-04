package uz.khusinov.ieltswritingquestions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.ieltswritingquestions.adapter.Task1Adapter
import uz.khusinov.ieltswritingquestions.adapter.Task2Adapter
import uz.khusinov.ieltswritingquestions.databinding.ActivityTaskTwoBinding
import uz.khusinov.ieltswritingquestions.model.Question

class TaskTwo : AppCompatActivity() {
    lateinit var binding: ActivityTaskTwoBinding
    val db = Firebase.firestore
    private val TAG = "TaskTwo"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.backIc.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        val list = ArrayList<Question>()




        db.collection("Question")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question = Question()
                    Log.d(TAG, "${document.id} => ${document.data}")

                    if (document.data["type"].toString() == "2") {

                        question.day = document.data["day"].toString()
                        question.month = document.data["month"].toString()
                        question.year = document.data["year"].toString()
                        question.questionBody = document.data["questionBody"].toString()
                        question.type = document.data["type"].toString()
                        question.imageUrl = document.data["imageUrl"].toString()
                        question.host = document.data["host"].toString()
                        question.sortKey = document.data["sortKey"].toString().toInt()

                        list.add(question)
                    }


                }
                // list [day , month, year ,questionBody , ...]

                Log.d(TAG, "Unsorted list $list")

                val sortedList = list.sortedByDescending { it.sortKey }

                Log.d(TAG, "Sorted list: $sortedList")

                val recyclerView = binding.recyclerview
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = Task2Adapter(sortedList)
                recyclerView.adapter = adapter

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }
}