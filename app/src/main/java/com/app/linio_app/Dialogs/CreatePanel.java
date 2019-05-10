package com.app.linio_app.Dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.app.linio_app.Adapters.PanelsAdapter;
import com.app.linio_app.Models.ImageModel;
import com.app.linio_app.Models.PanelsModel;
import com.app.linio_app.R;
import com.app.linio_app.Services.ErrorDialogs;
import com.app.linio_app.Services.Firebase_Services.ImageCreator;
import com.app.linio_app.Services.Firebase_Services.PanelCreator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreatePanel extends AppCompatDialogFragment{

    private EditText title;
    private Button uploadLogo;
    private ImageView logo;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseAuth auth;
    private DatabaseReference database;

    PanelCreator panelCreator;
    ImageCreator imageCreator;
    PanelsAdapter panelsAdapter;

    PanelsModel panelsModel;
    ImageModel imageModel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_panel_dialog,null);

        title = view.findViewById(R.id.title);
        uploadLogo = view.findViewById(R.id.panel_logo);
        logo = view.findViewById(R.id.logo);

        uploadLogo.setEnabled(false);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        panelCreator = new PanelCreator(database, getContext());
        imageCreator = new ImageCreator(database,getContext());

        panelsModel = new PanelsModel();

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) uploadLogo.setEnabled(true);
                else uploadLogo.setEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        builder.setView(view)
                .setTitle("Create Panel")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) { }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        create();
                    }
                });

        uploadLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseLogo();
            }
        });

        return builder.create();
    }

    private void chooseLogo() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Panel Logo"),PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
            filePath = data.getData();
            if (filePath != null) {
                final StorageReference ref = storageReference.child("panels/" + auth.getUid()).child(title.getText().toString());
                UploadTask uploadTask;
                uploadTask = (UploadTask) ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        // get the image Url of the file uploaded
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                try {
                                    if (!panelsModel.getTitle().isEmpty()) {
                                        panelsModel.setImageUrl(uri.toString());
                                        panelCreator.save(panelsModel,title.getText().toString());
                                        title.setText("");
                                    }
                                } catch (NullPointerException e) { new ErrorDialogs(getContext()).getFailedPanelCreation();
                                }
                            }
                        });

                    }
                });
            }
        }
    }

    private void create() {
        String finalTitle = title.getText().toString();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        if (finalTitle.length() > 0) {
            panelsModel.setTitle(finalTitle);
            panelsModel.setCreated(date);
            if (panelCreator.save(panelsModel,finalTitle));
        } else Toast.makeText(getContext(),"Cannot create panel: missing title",Toast.LENGTH_LONG).show();
    }

}