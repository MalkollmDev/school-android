package ru.malkollm.school_android

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.api.ScheduleAdapter
import ru.malkollm.school_android.api.TodoAdapter
import ru.malkollm.school_android.api.client.ApiClient
import ru.malkollm.school_android.api.interfaces.ScheduleApi
import ru.malkollm.school_android.models.Lesson
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), ScheduleAdapter.lessonClickListener {
    var lessonsData: ArrayList<Lesson> = ArrayList()
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = requireView().findViewById<RecyclerView>(R.id.rvSchedule)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        val apiInterface: ScheduleApi = ApiClient().getApiClient()!!.create(ScheduleApi::class.java)
        apiInterface.getHospitalsList().enqueue(object : Callback<ArrayList<Lesson>> {
            override fun onResponse(
                call: Call<ArrayList<Lesson>>,
                response: Response<ArrayList<Lesson>>
            ) {
                lessonsData = response.body()!!
                Log.d("test1", lessonsData.toString())
                recyclerView.adapter = ScheduleAdapter(response.body()!!, this@ProfileFragment)
            }

            override fun onFailure(call: Call<ArrayList<Lesson>>, t: Throwable) {
            }
        })
    }

    override fun getItem(position: Int) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.setTitle(lessonsData.get(position).name)
        alertDialog.setPositiveButton("OK") { dialog, which ->
            Toast.makeText(activity, "OK", Toast.LENGTH_SHORT).show()
        }
//        alertDialog.setNegativeButton("No") { dialog, which ->
//            Toast.makeText(this@MainActivity, "No", Toast.LENGTH_SHORT).show()
//        }
        alertDialog.show()
    }
}