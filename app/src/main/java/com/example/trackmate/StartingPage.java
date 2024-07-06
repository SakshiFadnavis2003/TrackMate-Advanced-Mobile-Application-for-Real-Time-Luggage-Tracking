

package com.example.trackmate;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class StartingPage extends AppCompatActivity {

    EditText loginIdEditText, passwordEditText;
    Button loginButton, signupButton;

    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

        dbHelper = new MyDatabaseHelper(this);

        loginIdEditText = findViewById(R.id.login_id_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingPage.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser() {
        String loginId = loginIdEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username=? AND password=?", new String[]{loginId, password});

        if (cursor.moveToFirst()) {
            // User exists, login successful
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(StartingPage.this, NewLuggage.class));

            // Here you can navigate to another activity or perform other actions
        } else {
            // No such user found
            Toast.makeText(this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}
