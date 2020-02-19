package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PrescriptionThankYouPage extends AppCompatActivity {
    String ordID,u_id;
    private TextView order_id,date_of_delivery;
    private LinearLayout order_track_btn,help_ord_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_thank_you_page);
        Intent getOrdId=getIntent();
        ordID=getOrdId.getStringExtra("OrderID");
        order_id=(TextView)findViewById(R.id.order_id);
        order_track_btn=(LinearLayout)findViewById(R.id.order_track_btn);
        help_ord_btn=(LinearLayout)findViewById(R.id.help_ord_btn);
        order_id.setText("Order Id"+ordID+"has been successfully placed");
    }
}
