package com.example.razzappv2;

import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class StockadminAdapter extends RecyclerView.Adapter<StockadminAdapter.MyviewHolder> {

    private ShowStockAdmins activity;
    private List<StockadminModel> mList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public StockadminAdapter(ShowStockAdmins activity,List<StockadminModel>mList){
        this.activity=activity;
        this.mList=mList;
    }

    public void deleteData(int position){
        StockadminModel item = mList.get(position);
        db.collection("Users").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(activity, "Data Deleted", Toast.LENGTH_SHORT).show();
                            notifyRemoved(position);
                        }else {
                            Toast.makeText(activity, "Error Cant delete", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item,parent,false);
        return new MyviewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        holder.desc.setText(mList.get(position).getEmail());
        holder.title.setText(mList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyviewHolder extends RecyclerView.ViewHolder{
        TextView title,desc;
        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            desc=itemView.findViewById(R.id.desc_text);
        }
    }


}