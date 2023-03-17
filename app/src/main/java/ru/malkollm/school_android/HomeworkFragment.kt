package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.malkollm.school_android.api.HomeworkAdapter
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.models.Group
import ru.malkollm.school_android.models.LessonItem
import java.io.IOException

class HomeworkFragment : Fragment() {
    private lateinit var homeworkAdapter: HomeworkAdapter
    private lateinit var selectedGroup: Group
    private lateinit var lessons: ArrayList<LessonItem>

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
        val actvGroupHomework =
            requireView().findViewById<AutoCompleteTextView>(R.id.actvGroupHomework)
        val actvLessonHomework =
            requireView().findViewById<AutoCompleteTextView>(R.id.actvLessonHomework)

        setupRecyclerView(rvHomework)

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
                var groupsNumber: ArrayList<Group> = arrayListOf()
                groupsNumber = (response.body() as ArrayList<Group>?)!!
                for (group in groupsNumber) {
                    groupsNumberList.add(group.number)
                }

                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_groups_item, groupsNumberList)
                actvGroupHomework.setAdapter(arrayAdapter)

                actvGroupHomework.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        CoroutineScope(Dispatchers.IO).launch {
                            selectedGroup = groupsNumber[p2]
                            val response1 =
                                RetrofitInstance.apiGroupSchedule.getGroupSchedule(selectedGroup.id)

                            withContext(Dispatchers.Main) {
                                if (response1.isSuccessful) {
                                    val lessonList: ArrayList<String> = arrayListOf()
                                    lessons = (response1.body() as ArrayList<LessonItem>?)!!
                                    for (lesson in lessons) {
                                        lessonList.add(lesson.lessonName)
                                    }

                                    val arrayAdapter = ArrayAdapter(
                                        requireContext(),
                                        R.layout.dropdown_lessons_item,
                                        lessonList
                                    )
                                    actvLessonHomework.setAdapter(arrayAdapter)
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                }
                            }
                        }
                    }
                }


                actvLessonHomework.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        CoroutineScope(Dispatchers.IO).launch {

                            val response2 =
                                RetrofitInstance.apiHomework.getHomeworks(selectedGroup.id, lessons[p2].id)

                            withContext(Dispatchers.Main) {
                                if (response2.isSuccessful) {
                                    homeworkAdapter.todos = response2.body()!!
                                    progressBarHomework.isVisible = false
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                }
                            }
                        }
                    }
                }

            } else {
                Log.e(ContentValues.TAG, "Response not successful")
            }
        }
    }

//        setupRecyclerView(rvHomework)
//
//        lifecycleScope.launchWhenCreated {
//            progressBarHomework.isVisible = true
//            val response = try {
//                RetrofitInstance.apiLessons.getLessons()
//            } catch (e: IOException) {
//                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
//                progressBarHomework.isVisible = false
//                return@launchWhenCreated
//            } catch (e: HttpException) {
//                Log.e(ContentValues.TAG, "HttpException, unexpected response")
//                progressBarHomework.isVisible = false
//                return@launchWhenCreated
//            }
//            if (response.isSuccessful && response.body() != null) {
//                homeworkAdapter.todos = response.body()!!
//            } else {
//                Log.e(ContentValues.TAG, "Response not successful")
//            }
//            progressBarHomework.isVisible = false
//        }
//    }

    private fun setupRecyclerView(rvHomework: RecyclerView) = rvHomework.apply {
        homeworkAdapter = HomeworkAdapter()
        adapter = homeworkAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}