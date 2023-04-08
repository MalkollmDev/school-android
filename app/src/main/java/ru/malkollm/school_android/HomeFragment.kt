package ru.malkollm.school_android

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.api.ScheduleAdapter
import ru.malkollm.school_android.api.ScheduleDatesAdapter
import ru.malkollm.school_android.models.ScheduleDate
import ru.malkollm.school_android.models.User
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment(private var user: User) : Fragment() {
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var scheduleDatesAdapter: ScheduleDatesAdapter
    private lateinit var dates: ArrayList<ScheduleDate>

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
        val actvDates =
            requireView().findViewById<AutoCompleteTextView>(R.id.actvDates)

        setupRecyclerView(rvSchedule)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        val resDate = currentDate + "T00:00:00"

        lifecycleScope.launchWhenCreated {
            val resp = try {
                RetrofitInstance.apiGroupSchedule.getSchedule(
                    resDate,
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
                scheduleDatesAdapter.todos = resp.body()!!
            }
        }

        if (user.roleId == 1 || user.roleId == 2 || user.roleId == 5 || user.roleId == 6) {
            lifecycleScope.launchWhenCreated {
                progressBar.isVisible = true
                val response = try {
                    RetrofitInstance.apiGroupSchedule.getScheduleAll()
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
                val response = try {
                    RetrofitInstance.apiGroupSchedule.getScheduleDates()
                } catch (e: IOException) {
                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(ContentValues.TAG, "HttpException, unexpected response")
                    return@launchWhenCreated
                }
                if (response.isSuccessful && response.body() != null) {
                    val datesList: ArrayList<String> = arrayListOf()
                    dates = (response.body() as ArrayList<ScheduleDate>?)!!
                    for (date in dates) {
                        datesList.add(date.date.substringBefore(delimiter = "T"))
                        datesList.sort()
                    }

                    val arrayAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.dropdown_dates_item,
                        datesList
                    )
                    actvDates.setAdapter(arrayAdapter)
                }

                actvDates.onItemClickListener = object : AdapterView.OnItemSelectedListener,
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
                                RetrofitInstance.apiGroupSchedule.getSchedule(
                                    dates[p2].date,
                                    user.groupId
                                )

                            withContext(Dispatchers.Main) {
                                if (response1.isSuccessful) {
                                    scheduleDatesAdapter.todos = response1.body()!!
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
        scheduleDatesAdapter = ScheduleDatesAdapter()
        adapter = scheduleDatesAdapter
        layoutManager = LinearLayoutManager(activity)
    }
}