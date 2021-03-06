package com.byteshaft.affirmations.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.byteshaft.affirmations.R;
import com.byteshaft.affirmations.affirmationdb.AppDatabase;
import com.byteshaft.affirmations.model.Affirmation;
import com.byteshaft.affirmations.utils.AppGlobals;
import com.byteshaft.affirmations.utils.Helpers;

import java.util.List;

public class CreateAffirmation extends AppCompatActivity {

    private EditText mAffirmationEditText;
    private Button mButtonSave;
    private TextView mTextView;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_affirmation);
        setTitle("Create");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAffirmationEditText = findViewById(R.id.edit_text_affirmation);
        mButtonSave = findViewById(R.id.button_save);
        mTextView = findViewById(R.id.tv_iam);
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "affirmation")
                .allowMainThreadQueries()
                .build();

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAffirmationEditText.getText().toString() != null && !mAffirmationEditText
                        .getText().toString().isEmpty()) {
                    database.affirmationDao().insertAll(new Affirmation(
                            "I am " + mAffirmationEditText.getText().toString()));
                    Toast.makeText(CreateAffirmation.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    mAffirmationEditText.getText().clear();
                    database = Room.databaseBuilder(AppGlobals.getContext(), AppDatabase.class, "affirmation")
                            .allowMainThreadQueries()
                            .build();
                    final List<Affirmation> affirmationList = database.affirmationDao().getAllAffirmations();
                    if (affirmationList.size() > 0 && !Helpers.isAlarmSet(getApplicationContext())) {
                        Helpers.start(getApplicationContext());
                    }
                } else {
                    Toast.makeText(CreateAffirmation.this, "Please enter some affirmation", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
