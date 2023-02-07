package com.example.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apps.interfaces.changes
import com.example.apps.roomdatabase.entity.Users

class UsersAdapter(context: Context,userList: ArrayList<Users>,changes: changes) : RecyclerView.Adapter<UsersAdapter.DataViewHolder>(){
    val context : Context
    val userList  : ArrayList<Users>
    val changes : changes
    var p0 : Int = 0
    init {
        this.context = context
        this.userList = userList
        this.changes = changes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.users_list,
            parent, false
        )

        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val current = userList[position]
        p0 = position
        holder.user_Name.text = current.userName
        holder.user_Repo.text = current.userRepos
        holder.repo_desc.text = current.desc

    }

    override fun getItemCount(): Int {
        return  userList.size
    }
    inner class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val user_Name  :TextView = itemView.findViewById(R.id.userName)
        val user_Repo : TextView = itemView.findViewById(R.id.repos)
        val repo_desc : TextView = itemView.findViewById(R.id.description)
        val share : ImageButton = itemView.findViewById(R.id.share)
        val delete : ImageButton = itemView.findViewById(R.id.delete)
        init {
            user_Name.setOnClickListener(this)
            share.setOnClickListener(this)
            delete.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            when(v?.id){
                R.id.share -> {
                    changes.share(user_Name.text.toString(),user_Repo.text.toString())
                }
                R.id.delete -> {
                    changes.delete(userList[position].userId)
                }
                R.id.userName -> {
                    changes.urlshare(user_Name.text.toString(),user_Repo.text.toString())
                }
            }
        }

    }

}