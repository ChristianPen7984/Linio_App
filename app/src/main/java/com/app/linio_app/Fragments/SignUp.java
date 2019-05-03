package com.app.linio_app.Fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.app.linio_app.R;
import com.app.linio_app.Services.ErrorDialogs;
import com.app.linio_app.Services.Firebase_Services.Account;

public class SignUp extends Fragment implements View.OnClickListener {

    EditText in_email, in_password, in_re_password;
    Button sign_up;
    TextView forgot;

    public SignUp() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        in_email = view.findViewById(R.id.email);
        in_password = view.findViewById(R.id.password);
        in_re_password = view.findViewById(R.id.repeat_password);
        sign_up = view.findViewById(R.id.signup);
        forgot = view.findViewById(R.id.forgot_password);

        sign_up.setOnClickListener(this);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, new ForgotPassword());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        final String email = in_email.getText().toString();
        final String password = in_password.getText().toString();
        String re_password = in_re_password.getText().toString();
        if (email.isEmpty() && password.isEmpty() && re_password.isEmpty()) new ErrorDialogs(getContext()).getMissingEmailAndPasswordSignUp();
        else if (email.isEmpty()) new ErrorDialogs(getContext()).getMissingEmailSignUp();
        else if (password.isEmpty()) new ErrorDialogs(getContext()).getMissingPasswordSignUp();
        else if (re_password.isEmpty()) new ErrorDialogs(getContext()).getMissingRepeatPasswordSignUp();
        else if (!password.equals(re_password)) new ErrorDialogs(getContext()).getMismatchPasswordsSignUp();
        else {
            FragmentTransaction ft = null;
            if (getFragmentManager() != null) {
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, new Panels());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
            Account account = new Account(getContext());
            account.createAccount(email,password);
        }
    }

}
