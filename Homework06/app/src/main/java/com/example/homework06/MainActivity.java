package com.example.homework06;

/*
* Group 29
* Homework 06
* Mayuri Jain, Narendra Pahuja
* MainActivity.java
* */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements Myprofile.OnFragmentInteractionListener, SelectAvatar.OnFragmentInteractionListener
        , DisplayProfileFragment.OnFragmentInteractionListener {
    EditText etStudentID, etLastName, etFirstName;
    Button btnSave;
    ImageView selectIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        prefs.edit().remove("studentJson").commit();
        if(prefs.contains("studentJson")){
            getFragmentManager().beginTransaction().replace(R.id.mainContainer,new DisplayProfileFragment(),"DisplayProfileFragment").commit();
        }else{
            getFragmentManager().beginTransaction().replace(R.id.mainContainer, new Myprofile(), "Myprofile").commit();
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void UpdateSelectedImage() {
        View view=getSupportFragmentManager().findFragmentByTag("Myprofile").getView();
        ImageView iv = view.findViewById(R.id.selectIv);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());


        final String studentJsonobject = prefs.getString("studentJson", null);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        StudentProfile studentobject = gson.fromJson(studentJsonobject, StudentProfile.class);


        switch (studentobject.Image) {

            case "1":
                iv.setImageResource(R.drawable.avatar_f_1);
                break;

            case "2":
                iv.setImageResource(R.drawable.avatar_f_2);
                break;

            case "3":
                iv.setImageResource(R.drawable.avatar_f_3);
                break;

            case "4":
                iv.setImageResource(R.drawable.avatar_m_1);
                break;

            case "5":
                iv.setImageResource(R.drawable.avatar_m_2);
                break;

            case "6":
                iv.setImageResource(R.drawable.avatar_m_3);
                break;

        }

    }

}
