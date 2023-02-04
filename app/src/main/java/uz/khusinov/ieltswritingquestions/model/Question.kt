package uz.khusinov.ieltswritingquestions.model

data class Question(
    var day: String = "0",
    var month: String = "",
    var year: String = "",
    var type: String = "",
    var imageUrl: String? = null,
    var questionBody: String = "A b C d",
    var host:String = "",
    var sortKey:Int = 0

)