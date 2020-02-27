package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Legal extends AppCompatActivity {
    private ImageView back_button;
    private CardView privacy_policy,terms_condition,disclaimer;
    private TextView content_privacy,content_terms,content_disclaimer;
    private Animation animationUp1,animationUp2,animationUp3;
    private Animation animationDown1,animationDown2,animationDown3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        privacy_policy=(CardView)findViewById(R.id.privacy_policy);
        terms_condition=(CardView)findViewById(R.id.terms_condition);
        disclaimer=(CardView)findViewById(R.id.disclaimer);
        content_privacy=(TextView)findViewById(R.id.content_privacy);
        content_terms=(TextView)findViewById(R.id.content_terms);
        content_disclaimer=(TextView)findViewById(R.id.content_disclaimer);
        content_privacy.setVisibility(View.GONE);
        content_terms.setVisibility(View.GONE);
        content_disclaimer.setVisibility(View.GONE);
        animationUp1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animationUp2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animationUp3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animationDown3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToAccount=new Intent(Legal.this,AccountPage.class);
                startActivity(backToAccount);
                overridePendingTransition(0,0);
                finish();
            }
        });
        privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content_privacy.isShown()){
                    content_privacy.setVisibility(View.GONE);
                    content_privacy.startAnimation(animationUp1);
                }
                else {
                    content_privacy.setVisibility(View.VISIBLE);
                    content_privacy.startAnimation(animationDown1);
                }
            }
        });
        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(content_terms.isShown()){
                    content_terms.setVisibility(View.GONE);
                    content_terms.startAnimation(animationUp2);
                }
                else {
                    content_terms.setVisibility(View.VISIBLE);
                    content_terms.startAnimation(animationDown2);
                }
            }
        });

     disclaimer.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(content_disclaimer.isShown()){
                 content_disclaimer.setVisibility(View.GONE);
                 content_disclaimer.startAnimation(animationUp3);
             }
             else {
                 content_disclaimer.setVisibility(View.VISIBLE);
                 content_disclaimer.startAnimation(animationDown3);
             }
         }
     });

    }
    @Override
    public void onBackPressed() {
        Intent backToAccount=new Intent(Legal.this,AccountPage.class);
        startActivity(backToAccount);
        overridePendingTransition(0,0);
        finish();
    }
}
