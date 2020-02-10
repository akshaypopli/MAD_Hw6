package com.example.hw_06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener, SelectAvatar.OnFragmentInteractionListener, DisplayFragment.OnFragmentInteractionListener {

    // Akshay Popli and Neel Solanki
    // Groups1-3
    // MainActivity
    // HW_06

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        prefs.edit().remove("profileJson").commit();


        if (prefs.contains("profileJson")) {
            setTitle("Display My Profile");
            getFragmentManager().beginTransaction().replace(R.id.container, new DisplayFragment(), "DisplayFragment").commit();
        } else {
            setTitle("My Profile");
            getFragmentManager().beginTransaction().replace(R.id.container, new MainFragment(), "profileFragment").commit();
        }


    }

    @Override
    public void onDisplayFragmentInteraction() {

    }

    @Override
    public void myProfileInterface() {

    }

    @Override
    public void onAvatarSelect() {
        View view = getSupportFragmentManager().findFragmentByTag("profileFragment").getView();
        ImageView iv_avatar = view.findViewById(R.id.iv_avatar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String profileObjectStr = prefs.getString("profileJson", null);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();
        Gson gson = builder.create();
        Profile profile = gson.fromJson(profileObjectStr, Profile.class);


        switch (profile.image) {

            case "f1":
                iv_avatar.setImageResource(R.drawable.avatar_f_1);
                break;

            case "f2":
                iv_avatar.setImageResource(R.drawable.avatar_f_2);
                break;

            case "f3":
                iv_avatar.setImageResource(R.drawable.avatar_f_3);
                break;

            case "m1":
                iv_avatar.setImageResource(R.drawable.avatar_m_1);
                break;

            case "m2":
                iv_avatar.setImageResource(R.drawable.avatar_m_2);
                break;

            case "m3":
                iv_avatar.setImageResource(R.drawable.avatar_m_3);
                break;

        }
    }
}
