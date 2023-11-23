package com.example.imtihoncodial.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.imtihoncodial.R
import com.example.imtihoncodial.databinding.FragmentAddBinding
import com.example.retrofit2.models.MyRequestTodo
import com.example.retrofit2.models.MyTodo
import com.example.retrofit2.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddFragment : Fragment() {

    private val binding by lazy { FragmentAddBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var post = arguments?.getBoolean("post")
        if (post==true){
            val info = arguments?.getSerializable("key") as MyTodo
            binding.dateText.text = info.oxirgi_muddat
            binding.izoh.setText(info.matn)
            binding.holat.setText(info.sarlavha)
            binding.spinner.setText(info.holat)
            binding.backBtn.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            binding.btnAdd.setOnClickListener {
                val post = MyRequestTodo(binding.spinner.text.toString(), binding.izoh.text.toString(), binding.dateText.text.toString(), binding.holat.text.toString())
                ApiClient.getApiService()
                    .uptade(info.id,post)
                    .enqueue(object : Callback<MyTodo> {
                        override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                            Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.homeFragment)
                        }
                        override fun onFailure(call: Call<MyTodo>, t: Throwable) {

                        }
                    })
            }






        }else{
            val languages = resources.getStringArray(R.array.holat)
            val adapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,languages)
            binding.spinner.setAdapter(adapter)

            binding.backBtn.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            binding.btnAdd.setOnClickListener {
                addFunction()
            }

            binding.date.setOnClickListener {
                showDatePoicker()
            }
        }


            return binding.root
    }




    private fun showDatePoicker() {
        val calendar = Calendar.getInstance()

            val dataPickerDialog = DatePickerDialog(requireContext(),0,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    binding.dateText.text = "$day-$month-$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
            dataPickerDialog.show()

    }

    fun addFunction(){
        val post = MyRequestTodo(binding.spinner.text.toString(), binding.izoh.text.toString(), binding.dateText.text.toString(), binding.holat.text.toString())
        ApiClient.getApiService()
            .addTodo(post)
            .enqueue(object : Callback<MyTodo> {
                override fun onResponse(call: Call<MyTodo>, response: Response<MyTodo>) {
                    Toast.makeText(requireContext(), "Save", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)
                }
                override fun onFailure(call: Call<MyTodo>, t: Throwable) {

                }
            })
    }
}