package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class DeliverySubscription extends AppCompatActivity {
    ImageView back_button;
    RadioGroup radio_grp_one, radio_grp_two, radio_grp_three;
    RadioButton selected_one, selected_every_thirty, selected_every_fortyfive, selected_every_sixty, selected_three_delivery, selected_six_delivery;
    private Button confirm_btn;
    ImageButton selected_customer;
    CheckBox selected_single_day;
    EditText custom_day_edit;
    LinearLayout custom_part_delivery;
    String user_id, user_add, day_portion, duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_subscription);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        back_button = (ImageView) toolbar.findViewById(R.id.back_button);

        radio_grp_one = (RadioGroup) findViewById(R.id.radio_grp_one);
        selected_three_delivery = (RadioButton) findViewById(R.id.selected_three_delivery);
        selected_one = (RadioButton) findViewById(R.id.selected_one);
        selected_every_thirty = (RadioButton) findViewById(R.id.selected_every_thirty);
        selected_every_fortyfive = (RadioButton) findViewById(R.id.selected_every_fortyfive);
        selected_every_sixty = (RadioButton) findViewById(R.id.selected_every_sixty);
        selected_single_day = (CheckBox) findViewById(R.id.selected_single_day);
        radio_grp_three = (RadioGroup) findViewById(R.id.radio_grp_three);
        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        custom_part_delivery = (LinearLayout) findViewById(R.id.custom_part_delivery);
        custom_part_delivery.setVisibility(View.GONE);
        custom_day_edit = (EditText) findViewById(R.id.custom_day_edit);
       /* custom_day_edit.setEnabled(true);
        custom_day_edit.setInputType(InputType.TYPE_NULL);
        custom_day_edit.setFocusable(true);*/
        Intent getUserDetails = getIntent();
        user_add = getUserDetails.getStringExtra("Address");
        user_id = getUserDetails.getStringExtra("userID");

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_grp_one.getCheckedRadioButtonId();
                int Selected_id2 = radio_grp_three.getCheckedRadioButtonId();
                //int Selected_id3=radio_grp_two.getCheckedRadioButtonId();
                selected_three_delivery = (RadioButton) findViewById(Selected_id2);
                selected_one = (RadioButton) findViewById(selectedId);
                selected_every_thirty = (RadioButton) findViewById(selectedId);
                selected_every_fortyfive = (RadioButton) findViewById(selectedId);
                selected_every_sixty = (RadioButton) findViewById(selectedId);
                day_portion = selected_one.getText().toString();
                if (duration != null) {
                    duration = selected_three_delivery.getText().toString();
                } else {
                    duration = "";
                }

                // Toast.makeText(DeliverySubscription.this, selected_one.getText().toString(),Toast.LENGTH_SHORT ).show();
                //  Toast.makeText(getApplicationContext(),selected_three_delivery.getText().toString(),Toast.LENGTH_LONG).show();


                Intent goTopayment_method = new Intent(DeliverySubscription.this, Payment.class);
                goTopayment_method.putExtra("DAY", day_portion);
                goTopayment_method.putExtra("Duration", duration);
                goTopayment_method.putExtra("User_add", user_add);
                goTopayment_method.putExtra("User_ID", user_id);
                startActivity(goTopayment_method);


            }
        });
        selected_single_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    custom_part_delivery.setVisibility(View.GONE);
                    // selected_single_day.setChecked(true);
                    selected_one.setChecked(false);
                    selected_every_fortyfive.setChecked(false);
                    selected_every_sixty.setChecked(false);
                    selected_three_delivery.setChecked(false);
                }
            }
        });

        radio_grp_one.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.selected_every_thirty) {
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    selected_single_day.setChecked(false);
                } else if (checkedId == R.id.selected_every_fortyfive) {
                    custom_part_delivery.setVisibility(View.VISIBLE);
                    selected_single_day.setChecked(false);
                } else if (checkedId == R.id.selected_every_sixty) {
                    selected_single_day.setChecked(false);
                    custom_part_delivery.setVisibility(View.VISIBLE);
                }
              /* else if(checkedId==R.id.selected_single_day) {
                    custom_part_delivery.setVisibility(View.GONE);
                }*/
                else {
                    custom_part_delivery.setVisibility(View.GONE);
                    // selected_single_day.setChecked(false);
                }
            }
        });
      /*  radio_grp_two.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
             if(checkedId==R.id.selected_single_day){
                 custom_part_delivery.setVisibility(View.GONE);
             }
             else {
                 custom_part_delivery.setVisibility(View.VISIBLE);
             }

            }
        });*/


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Fragment fm = new Searchfragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentPanel, fm, "Searchfragment")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();*/
                Intent intent = new Intent(DeliverySubscription.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    public void enableEdit(boolean state) {
        custom_day_edit.setEnabled(state);
        custom_day_edit.setFocusable(state);
        if (state) {
            custom_day_edit.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            custom_day_edit.setInputType(InputType.TYPE_NULL);
        }
    }
}
