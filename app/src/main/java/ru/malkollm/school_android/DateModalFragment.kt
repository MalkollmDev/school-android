package ru.malkollm.school_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.malkollm.school_android.databinding.FragmentDateModalBinding

class DateModalFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentDateModalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDateModalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var date = ""
        binding.calendarView
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
                date = (year.toString() + "-0" + (month + 1) + "-" + dayOfMonth.toString())

                binding.dateView.visibility = 1
                binding.dateView.text = date
            }

        binding.btnSaveDate.setOnClickListener {
            passData(date)
        }
    }

    private fun passData(date: String) {
        val bundle = Bundle()
        bundle.putString("date", date)

        val transaction = this.parentFragmentManager.beginTransaction()
        val frag2 = AddHomeworkFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.frame_layout, frag2)
        transaction.addToBackStack(null)
        transaction.remove(this)
        transaction.commit()
    }
}