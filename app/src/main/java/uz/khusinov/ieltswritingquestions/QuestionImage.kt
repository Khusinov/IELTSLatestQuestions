package uz.khusinov.ieltswritingquestions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import uz.khusinov.ieltswritingquestions.databinding.ActivityQuestionImageBinding

class QuestionImage : AppCompatActivity() {
    lateinit var binding: ActivityQuestionImageBinding
    private val TAG = "QuestionImage"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Question with image"

        val imageUrl = intent.extras?.getString("ImageUrl")
        val questionBody = intent.extras?.getString("QuestionBody")
        Log.d(TAG, "onCreate: $imageUrl")

        if (imageUrl == null) {
            binding.textview.text = "Image not available"
        } else {
            binding.textview.text = questionBody
            Picasso.get().load(imageUrl).into(binding.imageView)
        }


    }
}