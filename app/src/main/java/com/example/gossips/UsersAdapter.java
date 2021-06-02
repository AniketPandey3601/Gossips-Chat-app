package com.example.gossips;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gossips.databinding.RowConversationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.userviewholder> {
    Context context;
    ArrayList<User> users;

    public UsersAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;

    }

    @NonNull
    @Override
    public userviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_conversation, parent, false);
        return new userviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userviewholder holder, int position) {
        User user = users.get(position);

        String senderid = FirebaseAuth.getInstance().getUid();
        String senderRoom = senderid + user.getUid();

        FirebaseDatabase.getInstance().getReference()
                .child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String lastmsg = snapshot.child("lastMsg").getValue(String.class);
                            long time = snapshot.child("lastmsgtime").getValue(Long.class);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                            holder.binding.lastMsg.setText(lastmsg);

                        } else {
                            holder.binding.lastMsg.setText("tap to chat");
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });

        holder.binding.username.setText(user.getName());
        Glide.with(context).load(user.getProfileimage())
                .placeholder(R.drawable.avatar)
                .into(holder.binding.profile);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,chat.class);

                i.putExtra("name",user.getName());
                i.putExtra("uid",user.getUid());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class userviewholder extends RecyclerView.ViewHolder {

        RowConversationBinding binding;
        public userviewholder(@NonNull View itemView) {
            super(itemView);
            binding=RowConversationBinding.bind(itemView);
        }
    }
}
