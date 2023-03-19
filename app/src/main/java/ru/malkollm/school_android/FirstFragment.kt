package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.databinding.FragmentFirstBinding
import ru.malkollm.school_android.models.Group
import java.io.IOException

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLogin = binding.tvLogin
        val tvPassword = binding.tvPassword
        val progressBarLogin = binding.progressBarLogin

        lifecycleScope.launchWhenCreated {
            progressBarLogin.isVisible = true
            val response = try {
                RetrofitInstance.apiUser.getUsers()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                progressBarLogin.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                progressBarLogin.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                progressBarLogin.isVisible = false
                for (user in response.body()!!) {
                    binding.btnLogin.setOnClickListener {
                        if (
                            tvLogin.text.toString().trim() == user.login &&
                            tvPassword.text.toString().trim() == user.password
                        ) {
                            val bundle = bundleOf("GroupId" to user.groupId)
                            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
                        } else {
                            Toast.makeText(
                                activity,
                                "Ошибка авторизации, проверьте логин и пароль",
                                Toast.LENGTH_LONG
                            ).show()
                            tvLogin.text.clear()
                        }
                    }

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}