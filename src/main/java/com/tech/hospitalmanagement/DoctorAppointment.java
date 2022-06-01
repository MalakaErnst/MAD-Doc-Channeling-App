package com.tech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.hospitalmanagement.Models.AppointmentDetails;
import com.tech.hospitalmanagement.Models.DoctorDetails;

import java.util.ArrayList;
import java.util.List;

public class DoctorAppointment extends AppCompatActivity {

    Button button;
    ListView listView;
    private List<DoctorDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);

        button = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorAppointment.this, AppointmentList.class);
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Doctors");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot taskDatasnap : dataSnapshot.getChildren()){

                    DoctorDetails doctorDetails = taskDatasnap.getValue(DoctorDetails.class);
                    user.add(doctorDetails);
                }

                MyAdapter adapter = new MyAdapter(DoctorAppointment.this, R.layout.custom_appointment, (ArrayList<DoctorDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        ImageView imageView;
        TextView COL1;
        TextView COL2;
        TextView COL3;
        Button button;
    }

    class MyAdapter extends ArrayAdapter<DoctorDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<DoctorDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<DoctorDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_appointment, null);

                holder.COL1 = (TextView) view.findViewById(R.id.doc_name);
                holder.COL2 = (TextView) view.findViewById(R.id.doc_speacialist);
                holder.COL3 = (TextView) view.findViewById(R.id.cd_time);
                holder.button = (Button) view.findViewById(R.id.channel);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getSpecialist());
            holder.COL3.setText(user.get(position).getTime());
            System.out.println(holder);


            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view = inflater.inflate(R.layout.custom_add_appointment_details, null);
                    dialogBuilder.setView(view);

                    final TextView textView1 = (TextView) view.findViewById(R.id.cd_id);
                    final TextView textView2 = (TextView) view.findViewById(R.id.cd_name);
                    final TextView textView3 = (TextView) view.findViewById(R.id.cd_special);
                    final TextView textView4 = (TextView) view.findViewById(R.id.cd_time);
                    final TextView textView5 = (TextView) view.findViewById(R.id.cd_price);
                    final EditText editText1 = (EditText) view.findViewById(R.id.cuname);
                    final EditText editText2 = (EditText) view.findViewById(R.id.cunic);
                    final EditText editText3 = (EditText) view.findViewById(R.id.cucontat);
                    final EditText editText4 = (EditText) view.findViewById(R.id.cuaddress);
                    final Button buttonAdd = (Button) view.findViewById(R.id.uchannelnow);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String speacial = (String) snapshot.child("specialist").getValue();
                            String time = (String) snapshot.child("time").getValue();
                            String price = (String) snapshot.child("price").getValue();

                            textView1.setText(id);
                            textView2.setText(name);
                            textView3.setText(speacial);
                            textView4.setText(time);
                            textView5.setText(price);

                            buttonAdd.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AppointmentRequests");

                                    final String username = editText1.getText().toString();
                                    final String nic = editText2.getText().toString();
                                    final String contact = editText3.getText().toString();
                                    final String address = editText4.getText().toString();
                                    String Id = textView1.getText().toString();
                                    String Name = textView2.getText().toString();
                                    String speacial = textView3.getText().toString();
                                    String time = textView4.getText().toString();
                                    Integer price = Integer.valueOf(textView5.getText().toString());

                                    Integer tax = (price*2) / 100 ;
                                    String total = String.valueOf(price+tax);

                                    if (username.isEmpty()) {
                                        editText1.setError("Name is required");
                                    }else if (nic.isEmpty()) {
                                        editText2.setError("NIC is required");
                                    }else if (contact.isEmpty()) {
                                        editText3.setError("Contact Number is required");
                                    }else if (address.isEmpty()) {
                                        editText4.setError("Address is required");
                                    }else {

                                        AppointmentDetails appointmentDetails = new AppointmentDetails(Id,Name,speacial,time ,total,username,nic,contact,address);
                                        reference.child(Id).setValue(appointmentDetails);

                                        Toast.makeText(DoctorAppointment.this, "Successfully added", Toast.LENGTH_SHORT).show();

                                        alertDialog.dismiss();
                                    }

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });

                }

            });

            return view;

        }
    }
}