package com.d.submission3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.d.submission3.fragment.TimeFragment
import com.d.submission3.model.AlarmReceiver
import kotlinx.android.synthetic.main.activity_alarm.*
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity(), View.OnClickListener, TimeFragment.DialogTimeListener {

    private lateinit var alarmReceiver: AlarmReceiver

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        btn_time.setOnClickListener(this)
        btn_set_alarm.setOnClickListener(this)
        btn_cancel_alarm.setOnClickListener(this)

        alarmReceiver = AlarmReceiver()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_time -> {
                val timePickerFragmentRepeat = TimeFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
            R.id.btn_set_alarm -> {
                val repeatTime = tv_time.text.toString()
                val repeatMessage = edt_message.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    repeatTime, repeatMessage)
            }
            R.id.btn_cancel_alarm -> alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING)
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_REPEAT_TAG -> tv_time.text = dateFormat.format(calendar.time)
            else -> {
            }
        }
    }

}