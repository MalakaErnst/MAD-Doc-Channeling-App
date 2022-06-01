package com.tech.hospitalmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech.hospitalmanagement.Models.DoctorDetails;

public class AddDoctor extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;
    Button button;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        editText1=(EditText)findViewById(R.id.docid);
        editText2=(EditText)findViewById(R.id.docName);
        editText3=(EditText)findViewById(R.id.docAge);
        editText4=(EditText)findViewById(R.id.docCoatcat);
        editText5=(EditText)findViewById(R.id.docAddress);
        editText6=(EditText)findViewById(R.id.docSpecialist);
        editText7=(EditText)findViewById(R.id.docTime);
        editText8=(EditText)findViewById(R.id.docappointmentPrice);
        button=(Button)findViewById(R.id.docadd);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference = FirebaseDatabase.getInstance().getReference("Doctors");

                String id = editText1.getText().toString();
                String name = editText2.getText().toString();
                String age =editText3.getText().toString();
                String contact = editText4.getText().toString();
                String address = editText5.getText().toString();
                String special = editText6.getText().toString();
                String time = editText7.getText().toString();
                String price = editText8.getText().toString();


                if (id.isEmpty()) {
                    editText1.setError("ID is required");
                } else if (name.isEmpty()) {
                    editText2.setError("Name is required");
                }  else if (age.isEmpty()) {
                    editText3.setError("Age is required");
                } else if (contact.isEmpty()) {
                    editText4.setError("Contact is required");
                } else if (address.isEmpty()) {
                    editText5.setError("Address is required");
                } else if (special.isEmpty()) {
                    editText6.setError("Specialist is required");
                }else if (time.isEmpty()) {
                    editText7.setError("Time is required");
                }
                else if (price.isEmpty()) {
                    editText7.setError("Price is required");
                }else {

                    DoctorDetails doctorDetails = new DoctorDetails(id,name,age,contact,address,special,time,price);
                    reference.child(id).setValue(doctorDetails);

                    Toast.makeText(AddDoctor.this, "Doctor added successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}