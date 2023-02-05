package uz.khusinov.ieltswritingquestions.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.ieltswritingquestions.adapter.Task1Adapter
import uz.khusinov.ieltswritingquestions.databinding.ActivityMainBinding
import uz.khusinov.ieltswritingquestions.model.Question

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val TAG = "MainActivity"

    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()

        binding.backIc.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val list = ArrayList<Question>()


        fun setProgressDialog(context: Context, message:String): AlertDialog {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            ll.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam

            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam

            llParam = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = message
            tvText.setTextColor(Color.parseColor("#FF000000"))
            tvText.textSize = 20.toFloat()
            tvText.layoutParams = llParam

            ll.addView(progressBar)
            ll.addView(tvText)

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)

            val dialog = builder.create()
            val window = dialog.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window?.attributes = layoutParams
            }
            return dialog
        }

        val dialog = setProgressDialog(this, "Downloading..")
        dialog.show()
//            val mProgressDialog = ProgressDialog(this)
//            mProgressDialog.setTitle("Downloading...")
//            mProgressDialog.show()


        db.collection("Question")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question = Question()
                    Log.d(TAG, "${document.id} => ${document.data}")

                    if (document.data["type"].toString() == "1") {

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
                dialog.hide()

                Log.d(TAG, "Unsorted list $list")

                val sortedList = list.sortedByDescending { it.sortKey }

                Log.d(TAG, "Sorted list: $sortedList")

                val recyclerView = binding.recyclerview
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = Task1Adapter(sortedList)
                recyclerView.adapter = adapter

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

}
