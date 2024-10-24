package com.example.expenseexplorer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminDashboard extends AppCompatActivity {

    private RecyclerView recyclerView;
    private expenseAdapter adapter;
    private List<ExpenseAddModel> itemList;
    private DatabaseReference databaseReference;
    TextView totalExp;
    ImageView addExpBtn,depositBtn,logoutBtn,pendingMoneyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });


        totalExp=findViewById(R.id.ttl_exp);

        addExpBtn=findViewById(R.id.addExpBtn);
        depositBtn=findViewById(R.id.depositMoneyBtn);
        logoutBtn=findViewById(R.id.LogOutBtn);
        pendingMoneyBtn=findViewById(R.id.pendingMoneyBtn);



        recyclerView=findViewById(R.id.expenseRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList=new ArrayList<>();
        adapter=new expenseAdapter(itemList);
        recyclerView.setAdapter(adapter);

        databaseReference= FirebaseDatabase.getInstance().getReference("AllExp");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemList.clear();

                double ttlExpense=0;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ExpenseAddModel exp= snapshot1.getValue(ExpenseAddModel.class);
                    itemList.add(exp);

                    assert exp != null;
                    ttlExpense +=Double.parseDouble(exp.getExpenseAmount());
                }

                adapter.notifyDataSetChanged();

                totalExp.setText("Rs "+String.valueOf(ttlExpense));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addExpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this,ExpensesAddActivity.class);
                startActivity(intent);
            }
        });

        depositBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, DepositAmount.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

        pendingMoneyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminDashboard.this, pendingAmount.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        showLogoutConfirmationDialog();
    }
    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(adminDashboard.this,loginActivity.class);
                        startActivity(intent);
                        finish(); // Close the activity or perform the logout operation
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Dismiss the dialog and do nothing
                    }
                })
                .create()
                .show();
    }
}