package uz.khusinov.ieltswritingquestions


import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import uz.khusinov.ieltswritingquestions.databinding.ActivityAdminPanelBinding
import uz.khusinov.ieltswritingquestions.model.Question


class AdminPanel : AppCompatActivity() {
    private val TAG = "AdminPanel"
    var imageUri: String? = null
    lateinit var binding: ActivityAdminPanelBinding
    val db = Firebase.firestore
    lateinit var firebaseStore: FirebaseStorage
    lateinit var reference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Dexter.withContext(this)
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()



        firebaseStore = FirebaseStorage.getInstance()
        reference = firebaseStore.getReference("Images")

        binding.buttonLoadPicture.setOnClickListener {
            getImageContent.launch("image/*")
        }



        binding.addButton.setOnClickListener {
            val day = binding.dayAdd.text.toString()
            val month = binding.monthAdd.text.toString()
            val year = binding.yearAdd.text.toString()
            val type = binding.questionTypeAdd.text.toString()
            val imageUrl = imageUri
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
                .addOnFailureListener {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
        }

    }

    private var getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imageAdd.setImageURI(uri)
            val m = System.currentTimeMillis()
            val uploadTask = reference.child(m.toString()).putFile(uri!!)
            uploadTask.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    Log.d(TAG, "${it.task}: ")
                    val downloadUrl = it.metadata?.reference?.downloadUrl
                    downloadUrl?.addOnSuccessListener { imgUri ->
                        imageUri = imgUri.toString()
                        Toast.makeText(this, "Image Uploaded!", Toast.LENGTH_SHORT).show()
                    }

                }
            }.addOnFailureListener {
                Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
            }
        }


}
