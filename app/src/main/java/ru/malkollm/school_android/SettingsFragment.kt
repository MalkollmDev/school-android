package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.api.ScheduleAdapter
import java.io.IOException

class SettingsFragment : Fragment() {
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvTodos = requireView().findViewById<RecyclerView>(R.id.rvSchedules)
        val progressBar = requireView().findViewById<ProgressBar>(R.id.progressBarSchedule)

        setupRecyclerView(rvTodos)

        lifecycleScope.launchWhenCreated {
            progressBar.isVisible = true
            val response = try {
                RetrofitInstance.apiSchedules.getSchedules()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                progressBar.isVisible = false
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                scheduleAdapter.todos = response.body()!!
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }
            progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView(rvTodos: RecyclerView) = rvTodos.apply {
        scheduleAdapter = ScheduleAdapter()
        adapter = scheduleAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}