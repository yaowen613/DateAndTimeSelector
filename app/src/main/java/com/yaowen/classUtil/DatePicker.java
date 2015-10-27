
package com.yaowen.classUtil;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.yaowen.dateselector.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by YAOWEN on 2015/10/24.
 */
public class DatePicker extends FrameLayout implements View.OnClickListener {

    private String dateFormat;
    private String initValue;
    private Context context;
    private EditText editText;
    private ImageButton buttonSetDate;
    private Calendar calendar;
    private Dialog finalDialog;
    private OnTextChangeListener mOnTextChangeListener;
    private OnDateChangeListener mOnDateChangeListener;


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
     * 获取当前的设置的dateFormat的格式化样式数据
     *
     * @return 返回dateFormat 当前的格式化样式数据类型
     **/
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * 设置当前的dateFormat格式化样式数据
     *
     * @param dateFormat 传入dateFormat的String类型的数据
     **/
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 初始化控件函数
     */
    private void initView(Context context, AttributeSet attrs) {
        View view = View.inflate(context, R.layout.datepickerlayout, this);
        editText = (EditText) view.findViewById(R.id.et);
        buttonSetDate = (ImageButton) view.findViewById(R.id.dateBtn);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.MyDatePicker);
        dateFormat = typeArray.getString(R.styleable.MyDatePicker_dateFormat);
        initValue = typeArray.getString(R.styleable.MyDatePicker_value);

        buttonSetDate.setOnClickListener(this);
        if (dateFormat == null) {
            //如果自定义控件没有设置好dateFormat的值，就设置为 "yyyy-mm-dd"格式
            dateFormat = "yyyy-mm-dd hh:mm:ss";
        }
        if (initValue != null) {
            editText.setText(initValue);
        }
        EditTextChangeListener listener = new EditTextChangeListener();
        editText.addTextChangedListener(listener);
        typeArray.recycle();
    }

    /**
     * 返回EditText的初始值
     **/
    public String getInitValue() {
        return initValue;
    }

    /**
     * 设置edittext的值
     *
     * @param value String类型
     **/
    public void setEditText(String value) {
        editText.setText(value);
    }

    /**
     * 返回edittext的值
     **/
    public String getEditText() {
        return editText.getText().toString();
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
     * @param dateFormat String;
     *                   <p>yy:10 y/ yyy/ yyyy:2010</p>
     *                   <p> M:1 MM:01 MMM:Jan MMMM:January MMMMM:J；</p>
     *                   <p>   d:1--31；dd:01--31；</p>
     *                   <p> h:1-12(am/pm)； hh:01-12(am/pm)；H:0-23;HH:00-23</p>
     *                   <p>  m:1-59;mm:00-59；</p>
     *                   <p>   s:0-59;ss;00-59；</p>
     */
    public void setValue(Date value, String dateFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String date = simpleDateFormat.format(value.getTime());
        setEditText(date);
    }

    /***
     * 将当前edittext的值按照控件的dateFormat转回日期
     *
     * @return Date，转化成功时返回Date，否则返回null
     */
    public Date getValue() {
        String text = getEditText();
        return getValue(dateFormat);
    }

    /***
     * 将当前edittext的值按照指定的格式化字符串转回日期
     *
     * @param dateFormat
     * @return Date，转化成功时返回Date，否则返回null
     */
    public Date getValue(String dateFormat) {
        String text = getEditText();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);//小写的mm表示的是分钟
        Date date = null;
        try {
            date = sdf.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    @Override
    //imageButton 的响应事件函数
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

    /**
     * 监听text型值的变化
     **/
    public void setOnTextChangeListener(OnTextChangeListener textListener) {
        mOnTextChangeListener = textListener;
    }

    /**
     * 监听date型值的变化
     **/
    public void setDateChangeListener(OnDateChangeListener dateListener) {
        mOnDateChangeListener = dateListener;
    }

    private void doAfterTextChanged(Editable s) {
        if (mOnTextChangeListener != null) {
            mOnTextChangeListener.OnTextChanged(this, getEditText());
        }
        if (mOnDateChangeListener != null) {
            mOnDateChangeListener.OnDateChanged(this, getValue(dateFormat));
        }
    }

    /**
     * 自定义TextWatcher类
     **/
    private class EditTextChangeListener implements android.text.TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            doAfterTextChanged(s);
        }
    }

    /**
     * 监听控件文本值变化，即edittext值的变化
     **/
    public interface OnTextChangeListener {
        /**
         * 获取字符串值改变的函数
         *
         * @param datePicker View
         * @param text       改datepicker下edittext的值
         **/
        public void OnTextChanged(View datePicker, String text);
    }

    /**
     * 监听控件日期变化，日期值是依据控件的dateFormat格式化后的值
     **/
    public interface OnDateChangeListener {
        /**
         * 获取字符串值改变的函数
         *
         * @param datePicker View
         * @param date       该datepicker下edittext的Strign类型的值转码后的Date值，有可能是null
         **/
        public void OnDateChanged(View datePicker, Date date);
    }
}
