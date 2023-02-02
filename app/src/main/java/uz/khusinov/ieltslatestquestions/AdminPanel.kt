package uz.khusinov.ieltslatestquestions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import uz.khusinov.ieltslatestquestions.databinding.ActivityAdminPanelBinding
import uz.khusinov.ieltslatestquestions.model.Question

class AdminPanel : AppCompatActivity() {
    private  val TAG = "AdminPanel"

    lateinit var binding: ActivityAdminPanelBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.addButton.setOnClickListener {
            val day = binding.dayAdd.text.toString().toInt()
            val month =binding.monthAdd.text.toString().toInt()
            val year = binding.yearAdd.text.toString().toInt()
            val type = binding.questionTypeAdd.text.toString().toInt()
            val imageUrl = ""
            val questionBody = binding.questionAdd.text.toString()

            val question = Question()
            question.day = day
            question.month = month
            question.year = year
            question.type = type
            question.questionBody = questionBody
            question.imageUrl = imageUrl

            Log.d(TAG, "onCreate: clicked")
            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()

            db.collection("Question")
                .add(question)
                .addOnSuccessListener {
                    Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        }


    }
}