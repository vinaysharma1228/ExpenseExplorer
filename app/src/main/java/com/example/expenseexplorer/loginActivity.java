package com.example.expenseexplorer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class loginActivity extends AppCompatActivity {

    EditText username,password;
    TextView loginBtn;
    private ProgressDialog progressDialog;
    CircleImageView admin;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        username=findViewById(R.id.loginUsername);
        password=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.loginBtn);
        admin=findViewById(R.id.AdminLoginBtn);

        // setting progress bar
        auth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(true);



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    Toast.makeText(loginActivity.this, "Empty fields are not allow ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {

                    auth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(loginActivity.this,Dashboard.class);
                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(loginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }


            }
        });


        admin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(loginActivity.this, "Long press detected. Navigating to admin login.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity.this, adminLogin.class);
                startActivity(intent);
                finish();
                return true;
            }
        });


    }
}