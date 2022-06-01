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
import com.tech.hospitalmanagement.Models.PharmacyItemDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pharmacy extends AppCompatActivity {

    Button button;
    ListView listView;
    private List<PharmacyItemDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);

        button = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pharmacy.this, AddPharmacyItem.class);
                startActivity(intent);
            }
        });

        ref = FirebaseDatabase.getInstance().getReference("PharmacyItems");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot taskDatasnap : dataSnapshot.getChildren()){

                    PharmacyItemDetails pharmacyItemDetails = taskDatasnap.getValue(PharmacyItemDetails.class);
                    user.add(pharmacyItemDetails);
                }

                MyAdapter adapter = new MyAdapter(Pharmacy.this, R.layout.activity_custom_pharmacy_items, (ArrayList<PharmacyItemDetails>) user);
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
        Button button1;
        Button button2;

    }

    class MyAdapter extends ArrayAdapter<PharmacyItemDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<Map<String, String>> newList;
        List<PharmacyItemDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<PharmacyItemDetails> objects) {
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
            final ManageDoctors.ViewHolder holder;
            if (view == null) {
                holder = new ManageDoctors.ViewHolder();
                view = inflater.inflate(R.layout.activity_custom_pharmacy_items, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.price);
                holder.COL3 = (TextView) view.findViewById(R.id.description);
                holder.button1=(Button)view.findViewById(R.id.delete);
                holder.button2=(Button)view.findViewById(R.id.edit);

                view.setTag(holder);
            } else {

                holder = (ManageDoctors.ViewHolder) view.getTag();
            }

            holder.COL1.setText(user.get(position).getName());
            holder.COL2.setText(user.get(position).getPrice());
            holder.COL3.setText(user.get(position).getDescription());

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

                                    FirebaseDatabase.getInstance().getReference("PharmacyItems").child(idd).removeValue();
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
                    View view1 = inflater.inflate(R.layout.custom_update_pharmacy_item,null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText)view1.findViewById(R.id.updatedrugName);
                    final EditText editText2 = (EditText)view1.findViewById(R.id.updatedrugPrice);
                    final EditText editText3 = (EditText)view1.findViewById(R.id.updatedrugDescription);
                    final Button button = (Button)view1.findViewById(R.id.udate);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("PharmacyItems").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String id = (String) snapshot.child("id").getValue();
                            String name = (String) snapshot.child("name").getValue();
                            String price = (String) snapshot.child("price").getValue();
                            String description = (String) snapshot.child("description").getValue();
                            editText1.setText(name);
                            editText2.setText(price);
                            editText3.setText(description);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = editText1.getText().toString();
                            String price = editText2.getText().toString();
                            String description =editText3.getText().toString();


                            if (name.isEmpty()) {
                                editText1.setError("Name is required");
                            } else if (price.isEmpty()) {
                                editText2.setError("Price is required");
                            }  else if (description.isEmpty()) {
                                editText3.setError("Description is required");
                            }else {
//
                                HashMap map = new HashMap();
                                map.put("name",name);
                                map.put("price",price);
                                map.put("description",description);
                                reference.updateChildren(map);

                                Toast.makeText(Pharmacy.this, "Item updated successfully", Toast.LENGTH_SHORT).show();

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