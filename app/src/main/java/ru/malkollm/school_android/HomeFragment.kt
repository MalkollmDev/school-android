package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.api.ScheduleAdapter
import ru.malkollm.school_android.models.User
import java.io.IOException

class HomeFragment(private var user: User) : Fragment() {
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSchedule = requireView().findViewById<RecyclerView>(R.id.rvSchedules)
        val progressBar = requireView().findViewById<ProgressBar>(R.id.progressBarSchedule)
        val tvNumberGroup = requireView().findViewById<TextView>(R.id.tvNumberGroup)

        setupRecyclerView(rvSchedule)

        if (user.roleId == 1 || user.roleId == 2 || user.roleId == 5 || user.roleId == 6) {
            lifecycleScope.launchWhenCreated {
                progressBar.isVisible = true
                val response = try {
                    RetrofitInstance.apiGroupSchedule.getSchedule()
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
                    tvNumberGroup.text = "Администратор"
                    progressBar.isVisible = false
                    scheduleAdapter.todos = response.body()!!
                    progressBar.isVisible = false
                }
            }
        } else {
            lifecycleScope.launchWhenCreated {
                progressBar.isVisible = true
                val response = try {
                    RetrofitInstance.apiGroupSchedule.getGroupSchedule(user.groupId)
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
                    val group = user.groupId.toString() + " класс"
                    tvNumberGroup.text = group
                    progressBar.isVisible = false
                    scheduleAdapter.todos = response.body()!!
                    progressBar.isVisible = false
                }
            }
        }
    }

    private fun setupRecyclerView(rvSchedule: RecyclerView) = rvSchedule.apply {
        scheduleAdapter = ScheduleAdapter()
        adapter = scheduleAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}