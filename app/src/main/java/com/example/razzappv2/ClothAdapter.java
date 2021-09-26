package com.example.razzappv2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ClothAdapter extends FirebaseRecyclerAdapter<ClothModel,ClothAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ClothAdapter(@NonNull FirebaseRecyclerOptions<ClothModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull ClothModel model) {
        holder.name.setText(model.getName());
        holder.des.setText(model.getDes());
        holder.qty.setText(model.getQty());
        holder.mat.setText(model.getMat());

        if (model.getEurl().isEmpty()) {
            holder.img.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
        } else{
            Picasso.get().load(model.getEurl()).into(holder.img);
        }


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus =DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.cloth_update_popup))
                        .setExpanded(true,1400)
                        .create();

                View view = dialogPlus.getHolderView();
                EditText name =view.findViewById(R.id.txtName);
                EditText des = view.findViewById(R.id.txtDes);
                EditText qty = view.findViewById(R.id.txtQty);
                EditText eurl = view.findViewById(R.id.txtImageUrl);
                EditText mat= view.findViewById(R.id.txtMat);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);
                name.setText(model.getName());
                des.setText(model.getDes());
                qty.setText(model.getQty());
                eurl.setText(model.getEurl());
                mat.setText(model.getMat());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name",name.getText().toString());
                        map.put("des",des.getText().toString());
                        map.put("qty",qty.getText().toString());
                        map.put("eurl",eurl.getText().toString());
                        map.put("mat",mat.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("clothproduct")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.name.getContext(), "Error Updating...", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you Sure");
                builder.setMessage("Data permenently Deleted");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("clothproduct")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cloth_main_layout,parent,false);

        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView img;

        TextView name,des,qty,mat;

        Button btnEdit,btnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img1);
            name = (TextView) itemView.findViewById(R.id.nametext);
            des = (TextView) itemView.findViewById(R.id.destext);
            qty = (TextView) itemView.findViewById(R.id.qtytext);
            mat =(TextView)itemView.findViewById(R.id.mattext);

            btnEdit=(Button) itemView.findViewById(R.id.btnEdit);
            btnDelete=(Button) itemView.findViewById(R.id.btnDelete);
        }
    }

}
