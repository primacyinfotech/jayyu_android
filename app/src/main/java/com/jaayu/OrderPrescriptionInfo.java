package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class OrderPrescriptionInfo extends AppCompatActivity {

    CheckBox order_by_press,for_days,during_doctor,specify_medicin,call_me;
    EditText edt_days,edit_specify_medicin;
    LinearLayout first_child;
    String value,val;
    ImageView back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_prescription_info);

        order_by_press=(CheckBox)findViewById(R.id.order_by_press);
        for_days=(CheckBox)findViewById(R.id.for_days);
        during_doctor=(CheckBox)findViewById(R.id.during_doctor);
        specify_medicin=(CheckBox)findViewById(R.id.specify_medicin);
        call_me=(CheckBox)findViewById(R.id.call_me);
        edit_specify_medicin=(EditText)findViewById(R.id.edit_specify_medicin);
        edt_days=(EditText)findViewById(R.id.edt_days);
        first_child=(LinearLayout)findViewById(R.id.first_child);
        back_button=(ImageView)findViewById(R.id.back_button);
       // first_child.setVisibility(View.GONE);
        //edt_days.setEnabled(false);
        edit_specify_medicin.setVisibility(View.GONE);
         order_by_press.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(order_by_press.isChecked()){
                    first_child.setVisibility(View.VISIBLE);
                    specify_medicin.setChecked(false);
                    call_me.setChecked(false);
                    order_by_press.setChecked(true);
                }
                else {
                    first_child.setVisibility(View.GONE);


                }
             }
         });
         for_days.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(for_days.isChecked()){
                    edt_days.setEnabled(true);
                    during_doctor.setEnabled(false);
                }
                else {
                    during_doctor.setEnabled(true);
                    edt_days.setEnabled(false);

                }

             }
         });
         during_doctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(during_doctor.isChecked()){
                    edt_days.setEnabled(false);
                    for_days.setEnabled(false);
                }
                else {
                    edt_days.setEnabled(true);
                    for_days.setEnabled(true);
                }

             }

         });
        specify_medicin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(specify_medicin.isChecked()){
                    edit_specify_medicin.setVisibility(View.VISIBLE);
                    order_by_press.setChecked(false);
                    call_me.setChecked(false);
                    specify_medicin.setChecked(true);
                }
                else {
                    edit_specify_medicin.setVisibility(View.GONE);

                }
            }
        });
    call_me.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (call_me.isChecked()) {

                specify_medicin.setChecked(false);
                order_by_press.setChecked(false);
                call_me.setChecked(true);
            }
           /* else {
                specify_medicin.setChecked(true);
                order_by_press.setChecked(true);
                call_me.setChecked(false);
            }*/
        }
    });
    back_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent goToBack=new Intent(OrderPrescriptionInfo.this,OnlyUploadPrescription.class);
            startActivity(goToBack);
            overridePendingTransition(0,0);
            finish();
        }
    });

    }
    @Override
    public void onBackPressed() {
        Intent goToBack=new Intent(OrderPrescriptionInfo.this,OnlyUploadPrescription.class);
        startActivity(goToBack);
        overridePendingTransition(0,0);
        finish();
    }
}
