package com.app.linio_app.Dialogs;

        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatDialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.EditText;

        import com.app.linio_app.R;

public class CreatePanelDialog extends AppCompatDialogFragment {
    private EditText title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_panel_dialog,null);

        title = view.findViewById(R.id.title);

        builder.setView(view)
                .setTitle("Create Panel")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
