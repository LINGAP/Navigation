package com.fullstack.matrix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends OnBoardingBaseFragment {
    public static LoginFragment newInstance() {
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // test database connection
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("message");
        myRef.setValue("Hello, World!");

        // Inflate the layout for this fragment
        View view = super.onCreateView(inflater, container, savedInstanceState);
        submitButton.setText(R.string.login);

        // login the submitButton and register
        submitButton.setText(getString(R.string.login));
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final String password = Utils.md5Encryption(passwordEditText.getText().toString());

                database.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(username) && Objects.equals(password, dataSnapshot.child(username).child("user_password").getValue())) {
                            Config.username = username;
                            startActivity(new Intent(getActivity(), ControlPanel.class));
                        } else {
                            Toast.makeText(getActivity(),"Please try to login again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        return view;

    }



    @Override
    protected int getLayout() {
        return R.layout.fragment_login;
    }
}
