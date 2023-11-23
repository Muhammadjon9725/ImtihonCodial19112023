package com.example.imtihoncodial.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.imtihoncodial.R
import com.example.imtihoncodial.databinding.FragmentHomeBinding
import com.example.retrofit2.adapters.MyAdapters
import com.example.retrofit2.models.MyTodo
import com.example.retrofit2.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(),MyAdapters.OOnClick {
    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    lateinit var adapters: MyAdapters

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()
        binding.refreshSwipe.isRefreshing = true
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.addFragment, bundleOf())
        }


        return binding.root
    }

    override fun Click(myTodo: MyTodo) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Ogohlantirish")
        dialog.setMessage("${myTodo.sarlavha}ni o'chirmoqchimisiz")
        dialog.setPositiveButton("ha", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                ApiClient.getApiService().delete(myTodo.id)
                    .enqueue(object : Callback<Int> {
                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                            Toast.makeText(requireContext(), "O'chirildi", Toast.LENGTH_SHORT).show()
                        }

                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    })
                loadData()
                binding.refreshSwipe.isRefreshing = true
            }
        })
        dialog.show()
    }

    override fun update(myTodo: MyTodo) {
        findNavController().navigate(R.id.addFragment, bundleOf("key" to myTodo,"post" to true))
    }

    fun loadData(){

        val allTodo = ApiClient.getApiService().getAllTodo()
        allTodo.enqueue(object : retrofit2.Callback<List<MyTodo>>{

            override fun onResponse(call: Call<List<MyTodo>>, response: Response<List<MyTodo>>) {
                binding.refreshSwipe.isRefreshing = false
                val list = response.body()
                adapters = MyAdapters(list as ArrayList<MyTodo>,this@HomeFragment)
                binding.recy.adapter = adapters
            }
            override fun onFailure(call: Call<List<MyTodo>>, t: Throwable) {

            }
        })
    }

}