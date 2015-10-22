package com.yaowen.dateselector;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button dataButton;
    private Button timeButton;
    private EditText editTextDate;
    private EditText editTextTime;
    private final static int DATE_DIALOG = 0;
    private final static int TIME_DIALOG = 1;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextDate = (EditText) findViewById(R.id.et);
        editTextTime= (EditText) findViewById(R.id.et2);
        dataButton = (Button) findViewById(R.id.dateBtn);
        timeButton = (Button) findViewById(R.id.timeBtn);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG);
            }
        });
    }

    /**
     * 创建日期及时间选择对话框
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DATE_DIALOG: {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextDate.setText("您选择了：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
                            }
                        },
                        calendar.get(Calendar.YEAR),//传入年
                        calendar.get(Calendar.MONTH),//传入月
                        calendar.get(Calendar.DAY_OF_MONTH)//传入日
                );
                break;
            }
            case TIME_DIALOG:{
                calendar = Calendar.getInstance();
                dialog = new TimePickerDialog(
                        this,
                        new TimePickerDialog.OnTimeSetListener(){

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                editTextTime.setText("您选择了：" + hourOfDay + "时" + minute + "分");
                            }
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        false
                );
                break;
            }
        }
        return dialog;
    }
}
