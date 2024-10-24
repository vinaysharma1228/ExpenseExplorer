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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity {

    CircleImageView itineraryBtn;
    ImageView profileBtn,logoutBtn,teamBtn;
    FirebaseAuth auth;
    TextView totalExp;

    private RecyclerView recyclerView;
    private expenseAdapter adapter;
    private List<ExpenseAddModel> itemList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        totalExp=findViewById(R.id.totalExpenseDash);
        itineraryBtn=findViewById(R.id.itineraryBtn);
        profileBtn=findViewById(R.id.profileBtn);
        logoutBtn=findViewById(R.id.LogOutBtn);
        teamBtn=findViewById(R.id.teamBtn);
        auth=FirebaseAuth.getInstance();

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
                    ttlExpense+=Double.parseDouble(exp.getExpenseAmount());

                }

                adapter.notifyDataSetChanged();
                totalExp.setText("Rs "+String.valueOf(ttlExpense));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,profileActivity.class);
                startActivity(intent);
            }
        });

        itineraryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this,ItineraryActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLogoutConfirmationDialog();
            }
        });

        teamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, memberList.class);
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
                        auth.signOut();
                        Intent intent = new Intent(Dashboard.this,loginActivity.class);
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