package com.example.apps

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apps.interfaces.changes
import com.example.apps.databinding.ActivityMainBinding
import com.example.apps.databinding.BottomSheetDialogBinding
import com.example.apps.roomdatabase.UserApplication
import com.example.apps.roomdatabase.entity.Users
import com.example.apps.roomdatabase.viewmodel.UsersViewModel
import com.example.apps.roomdatabase.viewmodel.UsersViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity(),changes {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomSheetDialog : BottomSheetDialog
    private lateinit var userAdapter : UsersAdapter
    private lateinit var userList : ArrayList<Users>
    private val usersViewModel : UsersViewModel by viewModels {
        UsersViewModelFactory((this.application as UserApplication).repository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userList = arrayListOf()
        binding.toolbar.addItem.setOnClickListener {
            showBottomSheetDialog()
        }
        userAdapter = UsersAdapter(this,userList,this)
        binding.userList.adapter = userAdapter
        getDetails()

    }
    private fun showBottomSheetDialog(){
        bottomSheetDialog = BottomSheetDialog(this)
        val view = BottomSheetDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(view.root)
        view.saveRepoButton.setOnClickListener {
            val repo = view.etRepo.text.toString()
            val repoOwner = view.etRepoOwnerName.text.toString()
            if(authData(repo,repoOwner)){
                searchQuery(repo,repoOwner)
            }
            else
            {
                Toast.makeText(this,"Please Enter Details",Toast.LENGTH_SHORT).show()
            }
        }
        bottomSheetDialog.show()
    }
    private fun searchQuery(repo: String,repoOwner: String){
        val repositoryurl = "https://api.github.com/repos/$repoOwner/$repo"
        val url = repositoryurl

        val queue = Volley.newRequestQueue(this)
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->
                // get the JSON object
                var description : String = ""
                val obj = JSONObject(response)
                description = obj.getString("description")
                println(description)
                saveData(repo,repoOwner,description)
            },
            {Toast.makeText(this,"That doesn't work",Toast.LENGTH_SHORT).show()})
        queue.add(stringReq)
        bottomSheetDialog.dismiss()
    }
    private fun saveData(repo: String,repoOwner: String,description:String){
        lifecycleScope.launch {
            usersViewModel.insert(Users(0,repoOwner,repo,description))
        }
    }
    private fun authData(repo: String,repoOwner: String) : Boolean{
        var flag = true
        if(repo.isNullOrBlank()){
            flag = false
        }
        if(repoOwner.isNullOrBlank()){
            flag = false
        }
        return flag
    }
    private fun getDetails(){
        usersViewModel.Userslist.observe(this, Observer {
            userList.clear()
            userList.addAll(it)
            userAdapter.notifyDataSetChanged()
        })
    }

    override fun share(usersName :String,userRepo :String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, usersName);
        intent.putExtra(Intent.EXTRA_TEXT, userRepo);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    override fun delete(userId: Int) {
        lifecycleScope.launch {
            usersViewModel.delete(userId)
        }
    }

    override fun urlshare(usersName :String,userRepo :String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("https://github.com/$usersName/$userRepo"))
        startActivity(intent)
    }
}