package com.app.linio_app.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.linio_app.R;
import com.app.linio_app.Services.ErrorDialogs;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Fragment implements View.OnClickListener {

    EditText in_email, in_password;
    Button login;
    TextView create;

    FirebaseAuth auth;

    public Login() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        in_email = view.findViewById(R.id.email);
        in_password = view.findViewById(R.id.password);
        login = view.findViewById(R.id.login);
        create = view.findViewById(R.id.create_account);

        login.setOnClickListener(this);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, new SignUp());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        auth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onClick(View v) {
        final String email = in_email.getText().toString();
        final String password = in_password.getText().toString();
        switch (v.getId()) {
            case R.id.login:
                if (email.isEmpty() && password.isEmpty()) new ErrorDialogs(getContext()).getMissingEmailAndPasswordLogin();
                else if (email.isEmpty()) new ErrorDialogs(getContext()).getMissingEmailLogin();
                else if (password.isEmpty()) new ErrorDialogs(getContext()).getMissingPasswordLogin();
                else {
                    auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) new ErrorDialogs(getContext()).getInvalidCredentials();
                                    else {
                                        FragmentTransaction ft = null;
                                        if (getFragmentManager() != null) {
                                            ft = getFragmentManager().beginTransaction();
                                            ft.replace(R.id.fragmentContainer, new Panels());
                                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                            ft.addToBackStack(null);
                                            ft.commit();
                                        }
                                    }
                                }
                            });
                }
        }
    }
}
