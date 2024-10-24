package com.example.expenseexplorer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    ImageView backArrow;
    TextView ttl_exp,dep_amt,rem_amt,name;
    CircleImageView profile;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    RecyclerView ppRecyclerView;
    private profileAdapter adapter;
    private List<ExpenseAddModel> itemList;
    private DatabaseReference databaseReference;
    double ttlExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();


        backArrow=findViewById(R.id.backBtn);
        ttl_exp=findViewById(R.id.totalExpenseProfile);
        dep_amt=findViewById(R.id.depositAmount);
        rem_amt=findViewById(R.id.remainAmount);
        name=findViewById(R.id.usernameProfile);

        profile=findViewById(R.id.profile_picture);
        ppRecyclerView=findViewById(R.id.recyclerViewProfile);

        String id = auth.getUid();


        ppRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList=new ArrayList<>();
        adapter=new profileAdapter(itemList);
        ppRecyclerView.setAdapter(adapter);


        databaseReference= FirebaseDatabase.getInstance().getReference("AllExp");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale", "NotifyDataSetChanged"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                itemList.clear();
                 ttlExpense=0;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    ExpenseAddModel exp= snapshot1.getValue(ExpenseAddModel.class);
                    itemList.add(exp);

                    assert exp != null;
                    ttlExpense+=Double.parseDouble(exp.getExpenseAmount());

                }

                ttlExpense=ttlExpense/12;
                String ttlStr=String.valueOf(ttlExpense);
                HashMap<String,Object> obj=new HashMap<>();

                obj.put("totalExp",ttlStr);

                database.getReference().child("users").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d("profileActivity", "Total Expense updated successfully.");
                                } else {
                                    Log.e("profileActivity", "Failed to update Total Expense", task.getException());
                                }
                            }
                        });


                adapter.notifyDataSetChanged();
                // i am getting correct answer but it is override by database
               // ttl_exp.setText("Individual Total Exp : Rs "+String.format("%.2f",ttlExpense));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("users").child(Objects.requireNonNull(auth.getUid())).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                user u = snapshot.getValue(user.class);


                if(u==null){

                }
                else {
                    Picasso.get()
                            .load(u.getProfileImage())
                            .placeholder(R.drawable.profile_image)
                            .into(profile);

                    String totalExpAmt=u.getTotalExp();
                    String to_deposit=u.getDepositAmt();
                    double ttdouble=Double.parseDouble(totalExpAmt);
                    double todep=Double.parseDouble(to_deposit);
                    double rem_amt1 = todep - ttdouble;


                    ttl_exp.setText("Individual Total Exp : Rs "+String.format("%.2f",ttdouble));
                    dep_amt.setText("Deposit Amt: Rs "+u.getDepositAmt());
                    rem_amt.setText("remains Amt: Rs "+String.format("%.2f",rem_amt1));
                    name.setText(u.name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(profileActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

        Toast.makeText(this, String.valueOf(ttlExpense), Toast.LENGTH_SHORT).show();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profileActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });
    }
}