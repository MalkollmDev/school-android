package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.HttpException
import ru.malkollm.school_android.api.HomeworkAdapter
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.models.User
import java.io.IOException

class HomeworkFragment(private var user: User) : Fragment() {
    private lateinit var homeworkAdapter: HomeworkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_homework, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvHomework = requireView().findViewById<RecyclerView>(R.id.rvHomework)
        val progressBarHomework = requireView().findViewById<ProgressBar>(R.id.progressBarHomework)

        setupRecyclerView(rvHomework)

        lifecycleScope.launchWhenCreated {
            val resp = try {
                RetrofitInstance.apiHomework.getHomeworksByGroup(
                    user.groupId
                )
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (resp.isSuccessful && resp.body() != null) {
                homeworkAdapter.todos = resp.body()!!
                progressBarHomework.isVisible = false
            } else {
                Log.e("RETROFIT_ERROR", resp.code().toString())
            }
        }
    }

    private fun setupRecyclerView(rvHomework: RecyclerView) = rvHomework.apply {
        homeworkAdapter = HomeworkAdapter()
        adapter = homeworkAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}