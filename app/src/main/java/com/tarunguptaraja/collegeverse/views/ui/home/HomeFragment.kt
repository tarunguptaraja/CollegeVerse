package com.tarunguptaraja.collegeverse.views.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.adapters.TimeTableAdapter
import com.tarunguptaraja.collegeverse.databinding.FragmentHomeBinding
import com.tarunguptaraja.collegeverse.model.User
import kotlinx.coroutines.runBlocking


class HomeFragment : Fragment() {

    private var viewBinding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val json: String? = sharedPreferences.getString("USER", "")
        val user: User = gson.fromJson(json, User::class.java)
        homeViewModel.user=user


        binding.recycleView.layoutManager=LinearLayoutManager(context)

        val data = runBlocking { homeViewModel.getTimetable() }
        val adapter=TimeTableAdapter(data)

        binding.recycleView.adapter=adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }
}