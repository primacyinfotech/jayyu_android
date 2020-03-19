package com.medicinestall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EmptyCart extends AppCompatActivity {
  private Button shop_now;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_cart);
        shop_now=(Button)findViewById(R.id.shop_now);
        shop_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToSearch=new Intent(EmptyCart.this,SearchActivity.class);
                startActivity(goToSearch);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EmptyCart.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
        finish();
    }
}
