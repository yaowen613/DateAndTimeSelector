
package com.yaowen.classUtil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.yaowen.dateselector.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePicker extends FrameLayout implements View.OnClickListener {
    //private String value;
    private String dateFormat;
    private Context context;
    private EditText editText;
    private ImageButton buttonSetDate;
    private Calendar calendar;
    private Dialog finalDialog;


    public DatePicker(Context context) {
        super(context, null);
        this.context = context;
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context, attrs);
    }

    public DatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);


    }

    /**
     * 初始化控件函数
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.datepickerlayout, this);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.MyDatePicker);
        //value = typeArray.getString(R.styleable.MyDatePicker_value);
        dateFormat = typeArray.getString(R.styleable.MyDatePicker_dateFormat);
        editText = (EditText) view.findViewById(R.id.et);
        buttonSetDate = (ImageButton) view.findViewById(R.id.dateBtn);
        buttonSetDate.setOnClickListener(this);
        if (dateFormat == null) {
            dateFormat = "yyyy-mm-dd";
        }
        typeArray.recycle();
    }

    /**
     * 设置edittext的值
     *
     * @param value String类型
     **/
    public void setEditText(String value) {
        editText.setText(value);
    }

    /***
     * 格式化传入的值
     *
     * @param value String类型
     */
    public void setValue(Date value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(value.getTime());
        setEditText(date);
    }

    /***
     * 格式化传入的值
     *
     * @param value      String类型
     * @param dateFormat String
     */
    public void setValue(Date value, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(value.getTime());
        setEditText(date);
    }

    /***
     * 获取传入edittext的值
     *
     * @return value String类型
     */
    private String getValue() {
        String value = null;
        value = editText.getText().toString();
        return value;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dateBtn) {
            createDialog().show();
        }
    }

    /**
     * 创建日期选择对话框
     *
     * @return finalDialog 对话框；
     * 如果finalDialog为空时创建对话框，否则，把对话框存起来
     */
    public Dialog createDialog() {
        if (finalDialog == null) {
            calendar = Calendar.getInstance();
            finalDialog = new DatePickerDialog(
                    context,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
                            int minute = calendar.get(Calendar.MINUTE);
                            int second = calendar.get(Calendar.SECOND);
                            calendar.set(year, monthOfYear, dayOfMonth, hourOfDay, minute, second);
                            setValue(calendar.getTime());
                        }
                    },
                    calendar.get(Calendar.YEAR),//传入年份
                    calendar.get(Calendar.MONTH),//传入月份
                    calendar.get(Calendar.DAY_OF_MONTH)//传入日期
            );
        }
        return finalDialog;
    }
}
