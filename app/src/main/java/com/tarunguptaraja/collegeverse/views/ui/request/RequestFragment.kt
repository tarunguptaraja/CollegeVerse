package com.tarunguptaraja.collegeverse.views.ui.request

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.tarunguptaraja.collegeverse.adapters.RequestAdapter
import com.tarunguptaraja.collegeverse.databinding.FragmentRequestBinding
import com.tarunguptaraja.collegeverse.model.User
import com.tarunguptaraja.collegeverse.views.RequestVerificationActivity
import kotlinx.coroutines.runBlocking

class RequestFragment : Fragment(), RequestAdapter.RequestItemClicked {

    private var viewBinding: FragmentRequestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = viewBinding!!
    private lateinit var gson:Gson

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[RequestViewModel::class.java]

        viewBinding = FragmentRequestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("MySharedPref", AppCompatActivity.MODE_PRIVATE)
        gson = Gson()
        val json: String? = sharedPreferences.getString("USER", "")
        val user: User = gson.fromJson(json, User::class.java)
        viewModel.user=user

        binding.recycleView.layoutManager= LinearLayoutManager(context)

        val data = runBlocking { viewModel.getRequests() }
        val adapter = RequestAdapter(data,this)
        binding.recycleView.adapter=adapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onItemClicked(item: User) {
        val intent = Intent(context,RequestVerificationActivity::class.java)
        val json:String = gson.toJson(item)
        intent.putExtra("userRequest",json)
        startActivity(intent)
    }
}