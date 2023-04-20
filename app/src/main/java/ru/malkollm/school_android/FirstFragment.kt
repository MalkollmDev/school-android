package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.databinding.FragmentFirstBinding
import ru.malkollm.school_android.models.User
import java.io.IOException

class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvLogin = binding.tvLogin
        val tvPassword = binding.tvPassword
        val progressBarLogin = binding.progressBarLogin

        binding.btnLogin.setOnClickListener {
            val login = tvLogin.text.toString().trim()
            val password = tvPassword.text.toString().trim()

            val model = User(
                email = "",
                firstName = "",
                groupId = 0,
                id = 0,
                lastName = "",
                login = login,
                middleName = "",
                password = password,
                phone = "",
                roleId = 0
            )

            lifecycleScope.launchWhenCreated {
                progressBarLogin.isVisible = true
                val response = try {
                    RetrofitInstance.apiUser.checkUser(model)
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

                    val user = response.body()!!
                    if (user.isNotEmpty()) {
                        val bundle = bundleOf("User" to user[0])
                        val roleList = listOf(
                            1, 2, 5, 6, 7
                        )
                        if (user[0].roleId in roleList) {
                            goToFragment(R.id.action_FirstFragment_to_adminNavFragment, bundle)
                        } else {
                            goToFragment(R.id.action_FirstFragment_to_SecondFragment, bundle)
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Ошибка авторизации, проверьте логин и пароль",
                            Toast.LENGTH_LONG
                        ).show()
                        tvPassword.text.clear()
                    }
                }
            }
        }
    }

    private fun goToFragment(fragment: Int, bundle: Bundle) {
        findNavController().navigate(
            fragment,
            bundle
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}