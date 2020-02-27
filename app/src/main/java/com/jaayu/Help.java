package com.jaayu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.HelpAdapter;
import Model.HelpModel;

public class Help extends AppCompatActivity {
private RecyclerView help_list;
    private ImageView back_button;
    HelpAdapter helpAdapter;
    ArrayList<HelpModel> helpModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        helpModels=new ArrayList<>();
        back_button=(ImageView)toolbar.findViewById(R.id.back_button);
        help_list=(RecyclerView)findViewById(R.id.help_list);
    }
}
