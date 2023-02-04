package uz.khusinov.ieltswritingquestions.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.khusinov.ieltswritingquestions.databinding.Recyclerview2ItemBinding
import uz.khusinov.ieltswritingquestions.model.Question


class Task2Adapter(val question: List<Question>) :
    RecyclerView.Adapter<Task2Adapter.MyViewHolder>() {
    private val TAG = "Task2Adapter"

    class MyViewHolder(val binding: Recyclerview2ItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        Log.d(TAG, "onCreateViewHolder: Called adapter")
        return MyViewHolder(
            Recyclerview2ItemBinding.inflate(
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
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "onCreateViewHolder: Called adapter")
        return question.size

    }
}