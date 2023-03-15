package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.HttpException
import ru.malkollm.school_android.api.LessonAdapter
import ru.malkollm.school_android.api.RetrofitInstance
import java.io.IOException

class HomeworkFragment : Fragment() {
    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_homework, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actv = requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.apiGroups.getGroups()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                val groupsNumberList: ArrayList<Int> = arrayListOf()
                for(group in response.body()!!){
                    groupsNumberList.add(group.number)
                }

                val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_groups_item, groupsNumberList)
                actv.setAdapter(arrayAdapter)
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }
        }


//        val feelengs = resources.getStringArray(R.array.feelings)



//        val rvTodos = requireView().findViewById<RecyclerView>(R.id.rvLessons)
//        val progressBar = requireView().findViewById<ProgressBar>(R.id.progressBarLesson)
//
//        setupRecyclerView(rvTodos)
//
//        lifecycleScope.launchWhenCreated {
//            progressBar.isVisible = true
//            val response = try {
//                RetrofitInstance.apiLessons.getLessons()
//            } catch (e: IOException) {
//                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
//                progressBar.isVisible = false
//                return@launchWhenCreated
//            } catch (e: HttpException) {
//                Log.e(ContentValues.TAG, "HttpException, unexpected response")
//                progressBar.isVisible = false
//                return@launchWhenCreated
//            }
//            if (response.isSuccessful && response.body() != null) {
//                lessonAdapter.todos = response.body()!!
//            } else {
//                Log.e(ContentValues.TAG, "Response not successful")
//            }
//            progressBar.isVisible = false
//        }
    }

    private fun setupRecyclerView(rvTodos: RecyclerView) = rvTodos.apply {
        lessonAdapter = LessonAdapter()
        adapter = lessonAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}