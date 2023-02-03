package uz.khusinov.ieltslatestquestions.model

data class Question(
    var day:Int = 0,
    var month:Int = 0,
    var year:Int = 0,
    var type:Int = 0,
    var imageUrl:String? = null,
    var questionBody:String = "A b C d"

)