package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class User  extends AppCompatActivity {
    private Button returnButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        returnButton = (Button) findViewById(R.id.returnBack);

    }
    public void back_to_login(View view) {
        //setContentView(R.layout.login);
        Intent intent3 = new Intent(User.this, LoginActivity.class);
        startActivity(intent3);
        finish();
    }
}
