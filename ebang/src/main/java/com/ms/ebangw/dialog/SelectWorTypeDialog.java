package com.ms.ebangw.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.ms.ebangw.R;
import com.ms.ebangw.bean.Staff;
import com.ms.ebangw.bean.WorkType;
import com.ms.ebangw.event.OnCheckedWorkTypeEvent;
import com.ms.ebangw.utils.T;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 发布  选择工种时弹出的对话框
 */
public class SelectWorTypeDialog extends DialogFragment {
    private static final String WORK_TYPE = "workType";

    private WorkType workType;
    private ViewGroup contentLayout;



    private OnStaffSelectedListener onStaffSelectedListener;

    @Bind(R.id.et_price)
    EditText priceEt;
    @Bind(R.id.et_people_num)
    EditText peopleNumEt;
    @Bind(R.id.tv_start_date)
    TextView startDateTv;
    @Bind(R.id.tv_end_date)
    TextView endDateTv;
    @Bind(R.id.btn_ok)
    Button okBtn;
    @Bind(R.id.btn_no)
    Button noBtn;

    public static SelectWorTypeDialog newInstance(WorkType workType) {
        SelectWorTypeDialog fragment = new SelectWorTypeDialog();
        Bundle args = new Bundle();
        args.putParcelable(WORK_TYPE, workType);
        fragment.setArguments(args);
        return fragment;
    }

    public SelectWorTypeDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            workType = getArguments().getParcelable(WORK_TYPE);
        }

        setStyle(DialogFragment.STYLE_NO_FRAME, 0);
        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentLayout = (ViewGroup) inflater.inflate(R.layout.fragment_select_wor_type_dialog, container,
            false);
        ButterKnife.bind(this, contentLayout);
        initData();
        return contentLayout;
    }

    public void initData() {

        startDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //T.show("数据是"+"");
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar. MONTH , monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = format.format(cal.getTime());
//                        if(dateStr == null){
//                            T.show("数据是"+dateStr);
//                        }

                        startDateTv.setText(dateStr);
                    }
                });
                datePickerFragment.show(getFragmentManager(), "date");
            }
        });

        endDateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.setListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar. MONTH , monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateStr = format.format(cal.getTime());
                        endDateTv.setText(dateStr);
                    }
                });
                datePickerFragment.show(getFragmentManager(), "date");
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInfoCorrect() && null != onStaffSelectedListener) {
                    String price = priceEt.getText().toString().trim();
                    String peopleNum = peopleNumEt.getText().toString().trim();
                    String start = startDateTv.getText().toString().trim();
                    String end = endDateTv.getText().toString().trim();
                    Staff staff = new Staff();
                    staff.setCraft_id(workType.getId());
                    staff.setMoney(price);
                    staff.setStaff_account(peopleNum);
                    staff.setStart_time(start);
                    staff.setEnd_time(end);
                    workType.setStaff(staff);
                    //添加的
                    //EventBus.getDefault().post(new OnCheckedWorkTypeEvent(workType, true));
                    onStaffSelectedListener.onStaffSelected(workType, true);
                    dismiss();
                }
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStaffSelectedListener.onStaffSelected(workType, false);
                dismiss();
            }
        });
    }

    private boolean isInfoCorrect() {
        String price = priceEt.getText().toString().trim();
        String peopleNum = peopleNumEt.getText().toString().trim();
        String start = startDateTv.getText().toString().trim();
        String end = endDateTv.getText().toString().trim();

        if (TextUtils.isEmpty(price)) {
            T.show("请输入出价");
            return false;
        }

        if (TextUtils.isEmpty(peopleNum)) {
            T.show("请输入人数");
            return false;
        }

        if (!start.contains("-")) {
            T.show("请输入上门时间");
            return false;
        }

        if (!end.contains("-")) {
            T.show("请输入结束时间");
            return false;
        }

        return true;
    }

    public interface OnStaffSelectedListener {
         void onStaffSelected(WorkType workType, boolean isSelected);
    }

    public void setOnStaffSelectedListener(OnStaffSelectedListener onStaffSelectedListener) {
        this.onStaffSelectedListener = onStaffSelectedListener;
    }


}
