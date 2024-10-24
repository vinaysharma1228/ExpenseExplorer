package com.example.expenseexplorer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ExpensesAddActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    Spinner exp_cat,exp_payMode,exp_place;
    String selectedCategory,selectedPaymentMode,selectedPlace;
    EditText exp_name,exp_amount;
    TextView addBtn,status;
    FirebaseDatabase database;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expenses_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);

        exp_cat=findViewById(R.id.expCategory);
        exp_place=findViewById(R.id.expPlace);
        exp_payMode=findViewById(R.id.expPayMode);
        exp_name=findViewById(R.id.expName);
        exp_amount=findViewById(R.id.expAmount);
        addBtn=findViewById(R.id.addExpBtn);
        status=findViewById(R.id.addExpMessage);
        backArrow=findViewById(R.id.backBtn);



        // for expense category ;

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.expNameCollection, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exp_cat.setAdapter(adapter);


        exp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 selectedCategory= (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ExpensesAddActivity.this, "select expense category", Toast.LENGTH_SHORT).show();
            }
        });

        // for expense place
        ArrayAdapter<CharSequence> adapterPlace = ArrayAdapter.createFromResource(this,
                R.array.expPlace, android.R.layout.simple_spinner_item);
        adapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exp_place.setAdapter(adapterPlace);

        exp_place.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPlace=(String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(ExpensesAddActivity.this, "select expense category", Toast.LENGTH_SHORT).show();
            }
        });

        // for payment mode

        ArrayAdapter<CharSequence> adapterPayMode = ArrayAdapter.createFromResource(this,
                R.array.expPayMode, android.R.layout.simple_spinner_item);
        adapterPayMode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exp_payMode.setAdapter(adapterPayMode);

        exp_payMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPaymentMode=(String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                Toast.makeText(ExpensesAddActivity.this, "select expense category", Toast.LENGTH_SHORT).show();

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                String name,amount;
                double data;
                name=exp_name.getText().toString();
                amount=exp_amount.getText().toString();

                Calendar calendar = Calendar.getInstance();

                // Define the format you want
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Format the current date and time
                String formattedDateTime = sdf.format(calendar.getTime());

                DatabaseReference reference = database.getReference().child("AllExp").child(formattedDateTime);
                ExpenseAddModel exp = new ExpenseAddModel(name,selectedPlace,selectedPaymentMode,selectedCategory,amount,formattedDateTime);

                reference.setValue(exp).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            status.setText("Expense Added successfully.");
                            Toast.makeText(ExpensesAddActivity.this, "Expense added", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ExpensesAddActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

               // status.setText(formattedDateTime);


            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesAddActivity.this,adminDashboard.class);
                startActivity(intent);
            }
        });
    }
}