package com.example.noblee.NonActivityClasses.RecycleViewMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noblee.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    Context context;
    List<ItemMessage> messages;

    public MessageAdapter(Context context, List<ItemMessage> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageHolder(LayoutInflater.from(context).inflate(R.layout.layout_message,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        ItemMessage message = messages.get(position);

        holder.contenu.setText(message.getContenu());
        holder.date.setText(new SimpleDateFormat().format(message.getDate()));

        if (message.isSentByMalade()){
            holder.contenu.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }else {
            holder.contenu.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        }

        holder.contenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.date.getVisibility() == View.GONE){
                    holder.date.setVisibility(View.VISIBLE);
                }else {
                    holder.date.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
