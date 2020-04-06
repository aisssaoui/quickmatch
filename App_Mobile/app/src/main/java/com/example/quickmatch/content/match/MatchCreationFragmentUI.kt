package com.example.quickmatch.content.match

import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.quickmatch.BaseFragment

import com.example.quickmatch.R
import com.example.quickmatch.databinding.FragmentMatchCreationBinding
import com.example.quickmatch.utils.FormatUtils
import timber.log.Timber
import java.util.*

class MatchCreationFragmentUI : BaseFragment() {

    private lateinit var viewModel: MatchCreationFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding : FragmentMatchCreationBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_match_creation, container, false)
        viewModel = ViewModelProviders.of(this).get(MatchCreationFragmentViewModel::class.java)

        binding.viewModel = viewModel

        var clubNames : Array<String> = arrayOf("Pas de club à suggérer")

        /* fill suggestions list with the clubs from the database */
        viewModel.clubsNames.observe(this, Observer {
            clubNames = if(it.isNotEmpty()) it.toTypedArray() else clubNames
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, clubNames!!).also { adapter ->
                binding.autoCompleteInput.setAdapter(adapter)
            }
            Timber.i(clubNames.toString())
        })


        /* get current date to show it in date picker */
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)


        /* clear the edit text for club name if it's wrong */
        binding.autoCompleteInput.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (binding.autoCompleteInput.text.toString() !in clubNames )
                    binding.autoCompleteInput.text.clear()
            }
        }

        /* show time picker when clicking the edit text for start and end hours */

        binding.editStartHour.setOnFocusChangeListener { _, hasFocus ->

            if(hasFocus) {
                /* create the date picker */
                val datePickerStart = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    binding.editStartHour.setText("$hourOfDay:$minute")
                }, currentHour, currentMinute, true)

                datePickerStart.setTitle(resources.getString(R.string.start_hour_text))
                datePickerStart.show()
            }
        }

        binding.editEndHour.setOnFocusChangeListener { _, hasFocus ->

            if(hasFocus) {
                /* create the date picker */
                val datePickerEnd = TimePickerDialog(context!!, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    binding.editEndHour.setText("$hourOfDay:$minute")
                }, currentHour, currentMinute, true)

                datePickerEnd.setTitle(resources.getString(R.string.end_hour_text))
                datePickerEnd.show()
            }
        }

        binding.buttonCreateMatch.setOnClickListener {
            val repeatDays = listOf(
                binding.checkMonday.isChecked,
                binding.checkTuesday.isChecked,
                binding.checkWednesday.isChecked,
                binding.checkThursday.isChecked,
                binding.checkFriday.isChecked,
                binding.checkSaturday.isChecked,
                binding.checkSunday.isChecked)

            viewModel.processMatchCreation(
                binding.editStartHour.text.toString(),
                binding.editEndHour.text.toString(),
                binding.pickerMin.value,
                binding.pickerMax.value,
                binding.editLocation.text.toString(),
                repeatDays)
        }

        /* settings for teams' size pickers */

        binding.pickerMin.minValue = FormatUtils.MINIMUM_TEAM_SIZE
        binding.pickerMin.maxValue = FormatUtils.MAXIMUM_TEAM_SIZE
        binding.pickerMin.value = FormatUtils.MINIMUM_TEAM_SIZE

        binding.pickerMin.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.pickerMax.minValue = newVal
        }

        binding.pickerMax.minValue = FormatUtils.MINIMUM_TEAM_SIZE
        binding.pickerMax.maxValue = FormatUtils.MAXIMUM_TEAM_SIZE
        binding.pickerMin.value = FormatUtils.MINIMUM_TEAM_SIZE

        binding.pickerMax.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.pickerMin.maxValue = newVal
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getActionBar()?.title = "Créer un match"
    }
}
