package com.example.gossips;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.gossips.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class chat extends AppCompatActivity {

    ActivityChatBinding binding;

    messages_adapter adapter;
    UsersAdapter usersAdapter;

    ArrayList<Message> messages;

    String receiverRoom, senderRoom;
    ArrayList<User>users;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        usersAdapter= new UsersAdapter(this,users);

        messages = new ArrayList<>();
        adapter = new messages_adapter(this, messages);
        binding.cycle.setLayoutManager(new LinearLayoutManager(this));
        binding.cycle.setAdapter(adapter);
        String name = getIntent().getStringExtra("name");
        String receiverUid = getIntent().getStringExtra("uid");
        String senderUid = FirebaseAuth.getInstance().getUid();


        senderRoom =senderUid + receiverUid;
        receiverRoom =receiverUid + senderUid;


        database = FirebaseDatabase.getInstance();
        database.getReference().child("chats")
                .child(senderRoom)
                .child("messages")
               .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Message message = snapshot1.getValue(Message.class);
                            messages.add(message);
                        }
                        adapter.notifyDataSetChanged();
                    }
//
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
//
                    }
                });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msgtxt = binding.chatarea.getText().toString();

                Date date = new Date();
                Message message = new Message(msgtxt, senderUid, date.getTime());
                binding.chatarea.setText("");

                String randomkey = database.getReference().push().getKey();
                HashMap<String, Object> lastnsgobj = new HashMap<>();
                lastnsgobj.put("lastMsg",message.getMessage());
                lastnsgobj.put("lastmsgtime",date.getTime());

                database.getReference().child("chats").child(senderRoom).updateChildren(lastnsgobj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastnsgobj);

                database.getReference()
                        .child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .child(randomkey)
                        .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        database.getReference()
                                .child("chats")
                                .child(receiverRoom)
                                .child("messages")
                                .child(randomkey)
                                .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });

                    }

                });

            }
        });

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


        @Override
        public boolean onSupportNavigateUp () {
            finish();
            return super.onSupportNavigateUp();
        }
    }
