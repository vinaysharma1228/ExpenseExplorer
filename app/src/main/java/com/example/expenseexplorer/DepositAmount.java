package com.example.expenseexplorer;

import android.annotation.SuppressLint;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DepositAmount extends AppCompatActivity {

    TextView ttl_deposit,dptBtn,status;
    String selectedMemberName;
    Spinner memberNameSpinner;
    private List<user> itemList;
    private DatabaseReference databaseReference,databaseReference2,databaseReference3;
    double totalDeposit;
    EditText currdepositAmount;
    ProgressDialog progressDialog;
    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit_amount);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);

        memberNameSpinner=findViewById(R.id.memberNameDeposit);
        ttl_deposit=findViewById(R.id.totalDepositAmount);
        currdepositAmount=findViewById(R.id.depositAmount);
        dptBtn=findViewById(R.id.depositAmountBtn);
        status=findViewById(R.id.successDeposit);
        backArrow = findViewById(R.id.backBtn);

        itemList=new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference("users");


        HashMap<String,String> map =new HashMap<>();

        map.put("KARMA", "mW5Dvrht0eOBTj0D1xj863lJA6s2");
        map.put("VINAY KUMAR SHARMA", "TDxQSuAAhObqZvjVGtpfaJAJt1m2");
        map.put("VISHAL SHARMA", "7ql866Hey7d2zxOqA0RhBDogGXJ3");
        map.put("PAPPU SAH", "dq4ouMkTnxO1CDtMLBSCg5LUvEE3");
        map.put("PREM SHAH", "QJRPwFXHyJUfKO6eRDAoqoYIqQf1");
        map.put("MH MISHUK", "3KPiHEjbGCb0cFdKSmkGDULRC5y2");
        map.put("SHREEMA", "2SVcAb7N2QVJBvhLKZKeZxdRxl52");
        map.put("KINLEY", "JVEiC3sYfsZFCDWwGdwdUOthdPv2");
        map.put("ANURADHA", "pmdqRb11Q7Y16Vo6LFoOKZOJgs82");
        map.put("PEMA", "sgZQUx9aNXSIdPtz43QqP5aYQWY2");
        map.put("UGYEN", "jLEXJwxceENyUqVJRMyWc4xIYlA2");
        map.put("LHADEN", "Ioxs0IRRKihHzzPQjKiyPpitWEg1");




        // for member name selection

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.memberName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberNameSpinner.setAdapter(adapter);


        memberNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMemberName= (String) adapterView.getItemAtPosition(i);
                Toast.makeText(DepositAmount.this, map.get(selectedMemberName), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(DepositAmount.this, "select expense category", Toast.LENGTH_SHORT).show();
            }
        });


        // for calculating total deposit Amount


        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                totalDeposit=0;
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    user exp= snapshot1.getValue(user.class);
                    itemList.add(exp);

                    assert exp != null;
                    totalDeposit+=Double.parseDouble(exp.getDepositAmt());

                }
                ttl_deposit.setText("Total Deposit Amt : Rs "+ totalDeposit);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DepositAmount.this, "Total Deposit can't calculated", Toast.LENGTH_SHORT).show();
            }
        });

        // end of calculating total deposit amount

        dptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                if(currdepositAmount.getText().toString().isEmpty() || selectedMemberName.isEmpty()){
                    Toast.makeText(DepositAmount.this, "Enter amount and select member name ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {
                    String memberUid=map.get(selectedMemberName);
                    assert memberUid != null;
                    databaseReference2= FirebaseDatabase.getInstance().getReference("users").child(memberUid);

                    databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            user u1=snapshot.getValue(user.class);

                            if(u1!=null){

                                double total_expense =Double.parseDouble(u1.getTotalExp());
                                double deposit_amount=Double.parseDouble(u1.getDepositAmt());
                                double current_deposit_amount = Double.parseDouble(currdepositAmount.getText().toString().trim());

                                deposit_amount+=current_deposit_amount;

                                double remaing_amount = deposit_amount - total_expense;

                                String deposit_amount_str=String.format("%.2f",deposit_amount);
                                String remaing_amount_str=String.format("%.2f",remaing_amount);


                                HashMap<String,Object> obj=new HashMap<>();

                                obj.put("depositAmt",deposit_amount_str);
                                obj.put("remainAmt",remaing_amount_str);

                                databaseReference3= FirebaseDatabase.getInstance().getReference("users").child(memberUid);
                                databaseReference3.updateChildren(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(DepositAmount.this, "Amount Deposit Successfully", Toast.LENGTH_SHORT).show();
                                            status.setText("Deposit successfully");
                                            progressDialog.dismiss();

                                        }
                                        else {
                                            progressDialog.dismiss();
                                            Toast.makeText(DepositAmount.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });



                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(DepositAmount.this, "Error in loading data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            progressDialog.dismiss();
                        }
                    });
//                    Toast.makeText(DepositAmount.this, memberUid, Toast.LENGTH_SHORT).show();
//                    status.setText("Deposit successfully");
                }
            }
        });


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepositAmount.this, adminDashboard.class);
                startActivity(intent);
            }
        });


    }
}