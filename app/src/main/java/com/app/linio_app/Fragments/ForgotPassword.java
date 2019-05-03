package com.app.linio_app.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.linio_app.R;
import com.app.linio_app.Services.ErrorDialogs;
import com.app.linio_app.Services.Firebase_Services.AccountReset;

public class ForgotPassword extends Fragment {

    EditText in_email;
    Button reset;

    public ForgotPassword() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        in_email = view.findViewById(R.id.email);
        reset = view.findViewById(R.id.reset);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = in_email.getText().toString();
                if (email.isEmpty()) new ErrorDialogs(getContext()).getMissingEmailReset();
                else {
                    FragmentTransaction ft = null;
                    if (getFragmentManager() != null) {
                        ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.fragmentContainer, new Panels());
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                    AccountReset accountReset = new AccountReset(getContext());
                    accountReset.resetAccount(email);
                }
            }
        });

        return view;
    }

}
