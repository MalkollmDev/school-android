package ru.malkollm.school_android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import ru.malkollm.school_android.databinding.FragmentAdminNavBinding
import ru.malkollm.school_android.databinding.FragmentSecondBinding
import ru.malkollm.school_android.models.User

class AdminNavFragment : Fragment() {
    private var _binding: FragmentAdminNavBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminNavBinding.inflate(inflater, container, false)

        arguments?.let { bundle ->
            user = bundle.getParcelable("User")!!
        }

        replaceFragment(AdminFragment(user))

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(AdminFragment(user))
                R.id.homework -> replaceFragment(AddHomeworkFragment())
//                R.id.settings -> replaceFragment(SettingsFragment())

                else -> {}
            }
            true
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}