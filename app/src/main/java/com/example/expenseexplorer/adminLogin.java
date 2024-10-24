package com.example.expenseexplorer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class adminLogin extends AppCompatActivity {

    EditText adminUsername,adminPassword;
    TextView adminLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adminLoginBtn=findViewById(R.id.adminloginBtn);
        adminUsername=findViewById(R.id.adminloginUsername);
        adminPassword=findViewById(R.id.adminloginPassword);


        adminLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(adminUsername.getText().toString().isEmpty() || adminPassword.getText().toString().isEmpty()){
                    Toast.makeText(adminLogin.this, "Empty fields are not allow", Toast.LENGTH_SHORT).show();
                }
                else {
                      if(adminUsername.getText().toString().equals("mishuk@gmail.com") && adminPassword.getText().toString().equals("Mishuk@1228")){
                          Toast.makeText(adminLogin.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(adminLogin.this,adminDashboard.class);

                          startActivity(intent);
                      }
                      else {
                          Toast.makeText(adminLogin.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                      }
                }
            }
        });


    }
}