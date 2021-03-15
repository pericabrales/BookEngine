package com.example.cs492finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MakeupAdapter extends RecyclerView.Adapter<MakeupAdapter.MakeupItemViewHolder> {
    private ArrayList<MakeupDataItem> makeupDataItems;
    private OnMakeupItemClickListener onMakeupItemClickListener;

    interface OnMakeupItemClickListener{
        void onMakeupItemClicked(MakeupDataItem makeup);
    }

    public MakeupAdapter(OnMakeupItemClickListener onMakeupItemClickListener) {
        this.makeupDataItems = new ArrayList<>();
        this.onMakeupItemClickListener = onMakeupItemClickListener;
    }

    @NonNull
    @Override
    public MakeupItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.makeup_list_item, parent, false);
        return new MakeupItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MakeupItemViewHolder holder, int position) {
        holder.bind(this.makeupDataItems.get(position));
    }

    public void updateMakeupData(ArrayList<MakeupDataItem> makeupDataItems) {
        this.makeupDataItems = makeupDataItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.makeupDataItems.size();
    }

    class MakeupItemViewHolder extends RecyclerView.ViewHolder {

        public MakeupItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMakeupItemClickListener.onMakeupItemClicked(makeupDataItems.get(getAdapterPosition()));
                }
            });
        }

        public void bind(MakeupDataItem makeupDataItem) {
        }

    }
}
