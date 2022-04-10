package com.example.artcollectionapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.artcollectionapp.R
import com.example.artcollectionapp.model.department.DepartmentX

class DepartmentAdapter(
    private val departmentClickAdapter: DepartmentClickAdapter,
    private val departmentList: MutableList<DepartmentX> = mutableListOf()
): RecyclerView.Adapter<DepartmentViewHolder>() {

    fun addList(listOfDepartments: List<DepartmentX>){
        departmentList.clear()
        departmentList.addAll(listOfDepartments)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartmentViewHolder {
        val departmentView = LayoutInflater.from(parent.context)
            .inflate(R.layout.department_items, parent, false)
        return DepartmentViewHolder(departmentView)
    }

    override fun onBindViewHolder(holder: DepartmentViewHolder, position: Int) {
        val department = departmentList[position]

        holder.itemView.setOnClickListener(){
            departmentClickAdapter.onDepartmentClicked(department)
        }

        holder.bind(department)
    }

    override fun getItemCount(): Int = departmentList.size

}

class DepartmentViewHolder(
    private val artView: View
): RecyclerView.ViewHolder(artView){

    private val department: TextView = artView.findViewById(R.id.departmentName)

        fun bind(departmentX: DepartmentX){

            department.text = departmentX.displayName

        }
}