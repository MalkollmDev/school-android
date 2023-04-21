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
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.fragment_add_homework.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.malkollm.school_android.api.RetrofitInstance
import ru.malkollm.school_android.databinding.FragmentAddHomeworkBinding
import ru.malkollm.school_android.models.Group
import ru.malkollm.school_android.models.Lesson
import ru.malkollm.school_android.models.LessonDto
import ru.malkollm.school_android.models.User
import java.io.IOException

class AddHomeworkFragment(private var user: User) : Fragment() {
    private var _binding: FragmentAddHomeworkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddHomeworkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actvAdminGroup = requireView().findViewById<AutoCompleteTextView>(R.id.actvAdminGroup)
        val actvAdminLesson = requireView().findViewById<AutoCompleteTextView>(R.id.actvAdminLesson)
        val btnAdminDate = requireView().findViewById<AutoCompleteTextView>(R.id.btnAdminDate)

//        val tvLogin = binding.
//        val tvPassword = binding.tvPassword
//        val progressBarLogin = binding.progressBarLogin

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
                        CoroutineScope(Dispatchers.IO).launch {
                            val response1 =
                                RetrofitInstance.apiGroupSchedule.getGroupSchedule(groupsNumber[p2].id)

                            withContext(Dispatchers.Main) {
                                if (response1.isSuccessful) {
//                                    scheduleAdapter.todos = response1.body()!!
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                }
                            }
                        }
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
                        CoroutineScope(Dispatchers.IO).launch {
                            val response1 =
                                RetrofitInstance.apiGroupSchedule.getGroupSchedule(lessons[p2].id)

                            withContext(Dispatchers.Main) {
                                if (response1.isSuccessful) {
//                                    scheduleAdapter.todos = response1.body()!!
                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                }
                            }
                        }
                    }
                }
            }
        }

        btnAdminDate.setOnClickListener{

        }

//        binding.btnLogin.setOnClickListener {
//            val login = tvLogin.text.toString().trim()
//            val password = tvPassword.text.toString().trim()
//
//            val model = User(
//                email = "",
//                firstName = "",
//                groupId = 0,
//                id = 0,
//                lastName = "",
//                login = login,
//                middleName = "",
//                password = password,
//                phone = "",
//                roleId = 0
//            )
//
//            lifecycleScope.launchWhenCreated {
//                progressBarLogin.isVisible = true
//                val response = try {
//                    RetrofitInstance.apiUser.checkUser(model)
//                } catch (e: IOException) {
//                    Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
//                    progressBarLogin.isVisible = false
//                    return@launchWhenCreated
//                } catch (e: HttpException) {
//                    Log.e(ContentValues.TAG, "HttpException, unexpected response")
//                    progressBarLogin.isVisible = false
//                    return@launchWhenCreated
//                }
//                if (response.isSuccessful && response.body() != null) {
//                    progressBarLogin.isVisible = false
//
//                    val user = response.body()!!
//                    if (user.isNotEmpty()) {
//                        val bundle = bundleOf("User" to user[0])
//                        val roleList = listOf(
//                            1, 2, 5, 6, 7
//                        )
//                        if (user[0].roleId in roleList) {
//                            goToFragment(R.id.action_FirstFragment_to_adminNavFragment, bundle)
//                        } else {
//                            goToFragment(R.id.action_FirstFragment_to_SecondFragment, bundle)
//                        }
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            "Ошибка авторизации, проверьте логин и пароль",
//                            Toast.LENGTH_LONG
//                        ).show()
//                        tvPassword.text.clear()
//                    }
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}