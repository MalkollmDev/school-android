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
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.databinding.FragmentAddHomeworkBinding
import ru.malkollm.school_android.models.Group
import ru.malkollm.school_android.models.HomeworkDto
import ru.malkollm.school_android.models.LessonDto
import java.io.IOException

class AddHomeworkFragment() : Fragment() {
    private var _binding: FragmentAddHomeworkBinding? = null
    private val binding get() = _binding!!
    private var date: String = ""
    private var groupId: Int = 0
    private var lessonId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddHomeworkBinding.inflate(inflater, container, false)

        date = arguments?.getString("date").toString()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actvAdminGroup = requireView().findViewById<AutoCompleteTextView>(R.id.actvAdminGroup)
        val actvAdminLesson = requireView().findViewById<AutoCompleteTextView>(R.id.actvAdminLesson)
        val btnAdminDate = requireView().findViewById<Button>(R.id.btnAdminDate)

        val dateModalFragment = DateModalFragment()

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
                actvAdminGroup.setAdapter(arrayAdapter)

                actvAdminGroup.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        groupId = groupsNumber[p2].id
                    }
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.apiLesson.getLessons()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                val lessonList: ArrayList<String> = arrayListOf()
                var lessons: ArrayList<LessonDto> = arrayListOf()
                lessons = (response.body() as ArrayList<LessonDto>?)!!
                for (lesson in lessons) {
                    lessonList.add(lesson.name)
                }

                val arrayAdapter =
                    ArrayAdapter(requireContext(), R.layout.dropdown_groups_item, lessonList)
                actvAdminLesson.setAdapter(arrayAdapter)

                actvAdminLesson.onItemClickListener = object : AdapterView.OnItemSelectedListener,
                    AdapterView.OnItemClickListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        TODO("Not yet implemented")
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        lessonId = lessons[p2].id
                    }
                }
            }
        }

        btnAdminDate.setOnClickListener {
            binding.btnAdminDate.setOnClickListener {
                dateModalFragment.show(fragmentManager!!, "DateModalFragment")
            }
        }

        binding.tvDateAdded.text = date
        binding.tvDateAdded.visibility = 1

        val text = binding.etAdminHomework.text
        val resultDate = date + "T00:00:00"
        binding.btnSendHomework.setOnClickListener {
            val model = HomeworkDto(
                text = text.toString(),
                date = resultDate,
                lessonId = lessonId,
                groupId = groupId
            )

            lifecycleScope.launchWhenCreated {
                val response = try {
                    RetrofitInstance.apiHomework.addHomework(model)
                } catch (e: IOException) {
                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(ContentValues.TAG, "HttpException, unexpected response")
                    return@launchWhenCreated
                }
                if (response.isSuccessful && response.body() != null) {
                    replaceFragment(AddHomeworkFragment())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = (activity as FragmentActivity).supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}