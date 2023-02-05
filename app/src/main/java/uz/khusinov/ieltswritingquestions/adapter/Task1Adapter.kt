package uz.khusinov.ieltswritingquestions.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import uz.khusinov.ieltswritingquestions.ui.QuestionImage
import uz.khusinov.ieltswritingquestions.databinding.Recyclerview1ItemBinding

import uz.khusinov.ieltswritingquestions.model.Question


class Task1Adapter(val question: List<Question>) :
    RecyclerView.Adapter<Task1Adapter.MyViewHolder>() {
    private val TAG = "Task1Adapter"

    class MyViewHolder(val binding: Recyclerview1ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "onCreateViewHolder: Called adapter")
        return MyViewHolder(
            Recyclerview1ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.d(TAG, "onBH: Called adapter")
        holder.binding.questionBody.text = question[position].questionBody
        holder.binding.questionNumber.text = "${position + 1}"
        val givenDay =
            question[position].day + "." + question[position].month + "." + question[position].year
        holder.binding.givenDate.text = givenDay
        holder.binding.hostName.text = question[position].host
        val imageUrl = question[position].imageUrl
//        if (imageUrl != null) {
//            Picasso.get().load(imageUrl).into(holder.binding.image)
//        } else {
//            holder.binding.image.visibility = View.GONE
//        }
        holder.binding.root.setOnClickListener {
            val intent = Intent(it.context, QuestionImage::class.java)
            intent.putExtra("ImageUrl", imageUrl)
            intent.putExtra("QuestionNumber", "${position+1}")
            intent.putExtra("QuestionBody", question[position].questionBody)
            Log.d(TAG, "onBindViewHolder: $imageUrl")
            startActivity(it.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "onCreateViewHolder: Called adapter")
        return question.size

    }
}