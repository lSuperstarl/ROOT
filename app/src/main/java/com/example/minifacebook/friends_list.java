package com.example.minifacebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class friends_list extends AppCompatActivity {
    private RecyclerView friendsRecyclerView;
    private FriendsListAdapter friendsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        friendsRecyclerView = findViewById(R.id.friendsRecyclerView);

        // Set layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        friendsRecyclerView.setLayoutManager(layoutManager);

        // Get friend's data from intent
        ArrayList<userClass> friends = (ArrayList<userClass>) getIntent().getSerializableExtra("friends");

        // Initialize adapter
        friendsListAdapter = new FriendsListAdapter(friends);

        // Set adapter
        friendsRecyclerView.setAdapter(friendsListAdapter);
    }

    public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder> {
        private List<userClass> friends;

        public FriendsListAdapter(List<userClass> friends) {
            this.friends = friends;
        }

        @NonNull
        @Override
        public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
            return new FriendViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
            holder.bind(friends.get(position));
        }

        @Override
        public int getItemCount() {
            return friends.size();
        }

        class FriendViewHolder extends RecyclerView.ViewHolder {
            private TextView textName;
            private TextView textLocation;
            private TextView textGender;
            private TextView textAge;
            private ImageView userPfp;

            public FriendViewHolder(@NonNull View itemView) {
                super(itemView);
                userPfp = itemView.findViewById(R.id.userPfp);
                textAge = itemView.findViewById(R.id.textAge);
                textLocation = itemView.findViewById(R.id.textLocation);
                textName = itemView.findViewById(R.id.textName);
                textGender = itemView.findViewById(R.id.textGender);
            }

            public void bind(userClass friend) {
                // Set the views here using the friend object
                // You can use the same code you were using before to set the views
            }
        }
    }
}
