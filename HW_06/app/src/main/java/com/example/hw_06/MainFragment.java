package com.example.hw_06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class MainFragment extends Fragment {

    EditText et_first;
    EditText et_last;
    EditText et_id;
    ImageView profileImage;
    ImageView iv_avatar;
    RadioGroup radioGroup;
    RadioGroup rg_department;


    private OnFragmentInteractionListener mListener;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        Profile profile;
        if (prefs.contains("profileJson")) {
            final String profileJsonobject = prefs.getString("profileJson", null);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting().serializeNulls();
            Gson gson = builder.create();
            profile = gson.fromJson(profileJsonobject, Profile.class);

            View view = getView();
            et_first = view.findViewById(R.id.et_first);
            et_last = view.findViewById(R.id.et_last);
            et_id = view.findViewById(R.id.et_id);

            profileImage = view.findViewById(R.id.iv_avatar);
            radioGroup = view.findViewById(R.id.rg_department);
            et_first.setText(profile.first);
            et_last.setText(profile.last);
            et_id.setText(profile.id);
            if (profile.department != null) {
                if (profile.department.contains("CS")) {
                    radioGroup.check(R.id.rb_cs);
                } else if (profile.department.contains("SIS")) {
                    radioGroup.check(R.id.rb_sis);
                } else if (profile.department.contains("BIO")) {
                    radioGroup.check(R.id.rb_bio);
                } else if (profile.department.contains("Other")) {
                    radioGroup.check(R.id.rb_other);
                }
            }
            switch (profile.image) {
                case "f1":
                    profileImage.setImageResource(R.drawable.avatar_f_1);
                    break;
                case "m1":
                    profileImage.setImageResource(R.drawable.avatar_m_1);
                    break;
                case "f2":
                    profileImage.setImageResource(R.drawable.avatar_f_2);
                    break;
                case "m2":
                    profileImage.setImageResource(R.drawable.avatar_m_2);
                    break;
                case "f3":
                    profileImage.setImageResource(R.drawable.avatar_f_3);
                    break;
                case "m3":
                    profileImage.setImageResource(R.drawable.avatar_m_3);
                    break;
                default:
                    profileImage.setImageResource(R.drawable.select_image);
            }

        } else {
            profile = new Profile();

            SharedPreferences.Editor editor = prefs.edit();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            String profileJson = gson.toJson(profile);
            editor.putString("profileJson", profileJson);
            editor.apply();
        }


        getView().findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = getView();
                boolean errFlag = false;
                et_first = v.findViewById(R.id.et_first);
                et_last = v.findViewById(R.id.et_last);
                et_id = v.findViewById(R.id.et_id);
                iv_avatar = v.findViewById(R.id.iv_avatar);
                rg_department = v.findViewById(R.id.rg_department);

                if (et_first.getText().toString().matches("")) {
                    et_first.setError("Invalid Input");
                    errFlag = true;
                }
                if (et_last.getText().toString().matches("")) {
                    et_last.setError("Invalid Input");
                    errFlag = true;
                }

                if (!errFlag) {
                    if (!et_id.getText().toString().isEmpty() && Integer.parseInt(et_id.getText().toString()) != 0) {
                        int id = rg_department.getCheckedRadioButtonId();

                        if (id == -1) {
                            Toast.makeText(getActivity(), "Please Select Your Department", Toast.LENGTH_SHORT).show();
                        } else {
                            String dept = "";
                            RadioButton rb = (RadioButton) v.findViewById(id);
                            if (rb != null)
                                dept = rb.getText().toString();
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                            SharedPreferences.Editor editor = prefs.edit();
                            GsonBuilder builder = new GsonBuilder();

                            builder.setPrettyPrinting().serializeNulls();
                            Gson gson = builder.create();
                            Profile profile1 = new Profile();


                            String profileJson = prefs.getString("profileJson", null);

                            builder.setPrettyPrinting().serializeNulls();
                            profile1 = gson.fromJson(profileJson, Profile.class);

                            profile1.department = dept;
                            profile1.first = et_first.getText().toString();
                            profile1.last = et_last.getText().toString();
                            profile1.id = et_id.getText().toString();


                            String profileJson1 = gson.toJson(profile1);
                            editor.putString("profileJson", profileJson1);
                            editor.apply();
                            getFragmentManager().beginTransaction().replace(R.id.container, new DisplayFragment(), "DisplayFragment").commit();
                        }
                    } else {
                        et_id.setError("Invalid Input");
                    }

                }

            }
        });

        getView().findViewById(R.id.iv_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container, new SelectAvatar(), "SelectAvatar").commit();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void myProfileInterface();
    }
}
