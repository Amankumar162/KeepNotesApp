package com.example.keepnotes.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.keepnotes.MainActivity;
import com.example.keepnotes.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNote extends AppCompatActivity {
    Intent data;
    EditText editNoteT,editNoteC;
    FirebaseFirestore firebaseFirestore;
    ProgressBar load;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseFirestore = firebaseFirestore.getInstance();
        load = findViewById(R.id.progressBar2);
        user = FirebaseAuth.getInstance().getCurrentUser();

        data = getIntent();
        editNoteC = findViewById(R.id.editNoteC);
        editNoteT = findViewById(R.id.editNoteT);

        String noteTitle = data.getStringExtra("titles");
        String noteContent = data.getStringExtra("content");

        editNoteT.setText(noteTitle);
        editNoteC.setText(noteContent);
        FloatingActionButton fab = findViewById(R.id.editTheNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vTitle = editNoteT.getText() .toString();
                String vContent = editNoteC.getText() .toString();

                if (vTitle.isEmpty() || vContent.isEmpty() ){
                    Toast.makeText(EditNote.this, "cannot save the Empty notes", Toast.LENGTH_SHORT).show();
                    return;
                }
                load.setVisibility(View.VISIBLE);


                // we save notes here

                DocumentReference docref = firebaseFirestore.collection("notes").document(user.getUid()).collection("myNotes").document(data.getStringExtra("noteId"));

                Map<String,Object> note = new HashMap<>();
                note.put("titles",vTitle);
                note.put("content",vContent);

                docref.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditNote.this, "Note Added", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditNote.this, "Try Again", Toast.LENGTH_SHORT).show();
                        load.setVisibility(View.VISIBLE);
                    }
                });

            }
        });

    }
}