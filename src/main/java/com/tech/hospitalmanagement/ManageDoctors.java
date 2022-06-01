package com.tech.hospitalmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.hospitalmanagement.Models.DoctorDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageDoctors extends AppCompatActivity {

    Button button;
    ListView listView;
    private List<DoctorDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        // bundle obj passed into oncreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctors);    // method to render our layout

        button = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageDoctors.this, AddDoctor.class);      //to add form
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Doctors");           //db ref

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot taskDatasnap : dataSnapshot.getChildren()){      //get all items from doc db

                    DoctorDetails doctorDetails = taskDatasnap.getValue(DoctorDetails.class);
                    user.add(doctorDetails);
                }

                MyAdapter adapter = new MyAdapter(ManageDoctors.this, R.layout.custom_doctor_list, (ArrayList<DoctorDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        //TextView COL4;
        Button button1;
        Button button2;

    }

    class MyAdapter extends ArrayAdapter<DoctorDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;
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
                view = inflater.inflate(R.layout.custom_doctor_list, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.special);
                holder.COL3 = (TextView) view.findViewById(R.id.time);
                //holder.COL4 = (TextView) view.findViewById(R.id.description);
                holder.button1=(Button)view.findViewById(R.id.delete);
                holder.button2=(Button)view.findViewById(R.id.edit);

                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getSpecialist());
            holder.COL3.setText(user.get(position).getTime());

            System.out.println(holder);

            final String idd = user.get(position).getId();

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    String userid = user.get(position).getId();

                                    FirebaseDatabase.getInstance().getReference("Doctors").child(idd).removeValue();
                                    Toast.makeText(myContext, "Deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_doctor,null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText)view1.findViewById(R.id.updatedocid);
                    final EditText editText2 = (EditText)view1.findViewById(R.id.updatedocName);
                    final EditText editText3 = (EditText)view1.findViewById(R.id.updatedocAge);
                    final EditText editText4 = (EditText)view1.findViewById(R.id.updatedocCoatcat);
                    final EditText editText5 = (EditText)view1.findViewById(R.id.updatedocAddress);
                    final EditText editText6 = (EditText)view1.findViewById(R.id.updatedocSpecialist);
                    final EditText editText7 = (EditText)view1.findViewById(R.id.updatedocTime);
                    final EditText editText8 = (EditText)view1.findViewById(R.id.updatedocprice);
                    final Button button = (Button)view1.findViewById(R.id.update);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String age = (String) snapshot.child("age").getValue();
                            String contact = (String) snapshot.child("contact").getValue();
                            String address = (String) snapshot.child("address").getValue();
                            String specialist = (String) snapshot.child("specialist").getValue();
                            String time = (String) snapshot.child("time").getValue();
                            String price = (String) snapshot.child("price").getValue();

                            editText1.setText(id);
                            editText2.setText(name);
                            editText3.setText(age);
                            editText4.setText(contact);
                            editText5.setText(address);
                            editText6.setText(specialist);
                            editText7.setText(time);
                            editText8.setText(price);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
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
                                editText7.setError("Time Type is required");
                            }
                            else if (price.isEmpty()) {
                                editText8.setError("Appointment Charge is required");
                            }else {
//
                                HashMap map = new HashMap();
                                map.put("id", id);
                                map.put("name",name);
                                map.put("age",age);
                                map.put("contact",contact);
                                map.put("address",address);
                                map.put("specialist",special);
                                map.put("time",time);
                                map.put("price",price);
                                reference.updateChildren(map);

                                Toast.makeText(ManageDoctors.this, "Doctor updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;
        }

    }
}