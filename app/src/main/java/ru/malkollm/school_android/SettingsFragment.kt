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
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.api.ScheduleAdapter
import ru.malkollm.school_android.models.Group
import ru.malkollm.school_android.models.LessonItem
import java.io.IOException

class SettingsFragment : Fragment() {
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSchedule = requireView().findViewById<RecyclerView>(R.id.rvSchedules)
        val progressBar = requireView().findViewById<ProgressBar>(R.id.progressBarSchedule)
        val actvGroup = requireView().findViewById<AutoCompleteTextView>(R.id.actvGroup)

        setupRecyclerView(rvSchedule)

        lifecycleScope.launchWhenCreated {
            progressBar.isVisible = true
            val response = try {
                RetrofitInstance.apiGroups.getGroups()
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
                val groupsNumberList: ArrayList<Int> = arrayListOf()
                var groupsNumber: ArrayList<Group> = arrayListOf()
                groupsNumber = (response.body() as ArrayList<Group>?)!!
                for (group in groupsNumber) {
                    groupsNumberList.add(group.number)
                }

                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_groups_item, groupsNumberList)
                actvGroup.setAdapter(arrayAdapter)

                actvGroup.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val response1 =
                                RetrofitInstance.apiGroupSchedule.getGroupSchedule(groupsNumber[p2].id)

                            withContext(Dispatchers.Main) {
                                if (response1.isSuccessful) {
                                    scheduleAdapter.todos = response1.body()!!
                                    progressBar.isVisible = false
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                }
                            }
                        }
                    }
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