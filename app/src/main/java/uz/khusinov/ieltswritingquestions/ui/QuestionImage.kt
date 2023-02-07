package uz.khusinov.ieltswritingquestions.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import uz.khusinov.ieltswritingquestions.databinding.ActivityQuestionImageBinding


class QuestionImage : AppCompatActivity() {
    lateinit var binding: ActivityQuestionImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionImageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val imageUrl = intent.extras?.getString("ImageUrl")
        val questionBody = intent.extras?.getString("QuestionBody")
        val questionNumber = intent.extras?.getString("QuestionNumber")
        val colorDrawable = ColorDrawable(Color.parseColor("#FF018786"))
        supportActionBar?.title = "$questionNumber-question with image"
        supportActionBar?.setBackgroundDrawable(colorDrawable)


        if (imageUrl == null || imageUrl == "") {
            binding.textview.text = "Image is not available"
            binding.downloadImage.visibility = View.INVISIBLE
        } else {
            binding.textview.text = questionBody
            Picasso.get().load(imageUrl).into(binding.imageView)

        }

        binding.downloadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(imageUrl)
            startActivity(intent)
        }


    }
}