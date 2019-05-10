package com.app.linio_app.Services;

import android.app.AlertDialog;
import android.content.Context;

public class ErrorDialogs {

    private Context context;

    public ErrorDialogs(Context context) {
        this.context = context;
    }

    private void dialogTemplate(String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void getInvalidCredentials() {
        dialogTemplate("Unable to login","Credentials are invalid");
    }

    public void getMissingEmailAndPasswordLogin() {
        dialogTemplate("Unable to login","Missing Email and Password");
    }

    public void getMissingEmailLogin() {
        dialogTemplate("Unable to login","Missing Email");
    }

    public void getMissingPasswordLogin() {
        dialogTemplate("Unable to login","Missing Password");
    }

    public void getMissingEmailAndPasswordSignUp() {
        dialogTemplate("Unable to sign up","Missing Email and Password");
    }

    public void getMissingEmailSignUp() {
        dialogTemplate("Unable to sign up","Missing Email");
    }

    public void getMissingPasswordSignUp() {
        dialogTemplate("Unable to sign up","Missing Password");
    }

    public void getMissingRepeatPasswordSignUp() {
        dialogTemplate("Unable to sign up","Missing Repeat Password");
    }

    public void getMismatchPasswordsSignUp() {
        dialogTemplate("Unable to sign up","Passwords don't match");
    }

    public void getFailedAccountSignUp() {
        dialogTemplate("Unable to sign up","Account Creation Failure");
    }

    public void getMissingEmailReset() {
        dialogTemplate("Unable to reset password","Missing Email");
    }

    public void getFailedPasswordReset() {
        dialogTemplate("Unable to reset password","Could not locate email");
    }

    public void getFailedPanelCreation() {
        dialogTemplate("Unable to create panel","Data could not be saved. " +
                "This is a bug in the code I could not work out.  To avoid this, add a title  " +
                "then upload image and immediately save. :)");
    }

}
