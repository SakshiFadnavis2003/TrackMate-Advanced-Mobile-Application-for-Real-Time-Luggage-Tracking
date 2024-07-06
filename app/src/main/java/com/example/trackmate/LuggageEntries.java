package com.example.trackmate;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LuggageEntries extends AppCompatActivity {

    EditText userIdEditText, companyNameEditText, colorEditText, capacityEditText, heightEditText, widthEditText;
    Button submitButton;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luggage_entries);

        dbHelper = new MyDatabaseHelper(this);

        userIdEditText = findViewById(R.id.editTextUserID);
        companyNameEditText = findViewById(R.id.editTextCompanyName);
        colorEditText = findViewById(R.id.editTextColour);
        capacityEditText = findViewById(R.id.editTextCapacity);
        heightEditText = findViewById(R.id.editTextHeight);
        widthEditText = findViewById(R.id.editTextWidth);

        submitButton = findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLuggageEntry();
            }
        });
    }

    private void addLuggageEntry() {
        String userId = userIdEditText.getText().toString();
        String companyName = companyNameEditText.getText().toString();
        String color = colorEditText.getText().toString();
        int capacity = Integer.parseInt(capacityEditText.getText().toString());
        double height = Double.parseDouble(heightEditText.getText().toString());
        double width = Double.parseDouble(widthEditText.getText().toString());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", userId);
        values.put("company", companyName);
        values.put("color", color);
        values.put("capacity", capacity);
        values.put("height", height);
        values.put("width", width);

        long newRowId = db.insert("luggage", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Luggage entry added successfully", Toast.LENGTH_SHORT).show();
            // Clear input fields
            userIdEditText.setText("");
            companyNameEditText.setText("");
            colorEditText.setText("");
            capacityEditText.setText("");
            heightEditText.setText("");
            widthEditText.setText("");
        } else {
            Toast.makeText(this, "Error adding luggage entry", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
