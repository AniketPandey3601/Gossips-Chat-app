package com.example.gossips;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gossips.databinding.ReceiveBinding;
import com.example.gossips.databinding.SendBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class messages_adapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Message> messages;

    final int Item_sent = 1;
    final int Item_receive = 2;

 public messages_adapter(Context context, ArrayList<Message> messages)
 {

     this.context=context;
     this.messages = messages;
 }    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     if(viewType==Item_sent){
         View view = LayoutInflater.from(context).inflate(R.layout.send,parent,false);
         return new sendViewHolder(view);
     }
     else{

         View view = LayoutInflater.from(context).inflate(R.layout.receive,parent,false);
         return new RecieverViewHolder(view);
     }

    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderid())) {
            return Item_sent;
        }
        else{
            return Item_receive;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ;
        Message message = messages.get(position);
        if (holder.getClass() == sendViewHolder.class) {
            sendViewHolder viewHolder = (sendViewHolder)holder;
            viewHolder.binding.sendChat.setText(message.getMessage());
        }
        else {
            RecieverViewHolder viewHolder = (RecieverViewHolder)holder;
            viewHolder.binding.receive.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class sendViewHolder extends RecyclerView.ViewHolder{

        SendBinding binding;


        public sendViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SendBinding.bind(itemView);
        }
    }
     public class RecieverViewHolder extends RecyclerView.ViewHolder{

        ReceiveBinding binding;

         public RecieverViewHolder(@NonNull View itemView) {
             super(itemView);
             binding = ReceiveBinding.bind(itemView);
         }
     }

}
