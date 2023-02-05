package uz.khusinov.ieltswritingquestions

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.khusinov.ieltswritingquestions.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Tips and rules"
        val colorDrawable = ColorDrawable(Color.parseColor("#FF018786"))
        supportActionBar?.setBackgroundDrawable(colorDrawable)

        binding.writeToDev.setOnClickListener {
            val stringEmail = "ieltslatestquestions@gmail.com"
            var intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, stringEmail)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Problem with app")
            intent.putExtra(Intent.EXTRA_TEXT, "")

            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))

        }
        binding.rateApp.setOnClickListener {

        }


    }

}