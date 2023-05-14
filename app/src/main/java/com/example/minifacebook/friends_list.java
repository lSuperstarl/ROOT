package com.example.minifacebook;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
            private Handler handler = new Handler(Looper.getMainLooper());

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
                // Obtain the user's date of birth as a string

                String edad = friend.getDob();
                textAge.setText(String.valueOf(edad));
                textLocation.setText(friend.getCountry());
                textName.setText(friend.getName());
                textGender.setText(friend.getGender());
                // You need to load the user's profile picture into userPfp.
                // This depends on what type of data userPfp is and how you're loading images.
                String imageURL = "http://192.168.48.129:1337/cpen410/images/regularusers/" + friend.getProfilePicture();

                // Start a new Thread for image downloading
                new Thread(() -> {
                    final Drawable actualImage = LoadImageFromWebOperations(imageURL);
                    // Use the Handler to post back to main thread
                    handler.post(() -> {
                        ImageView userImage = findViewById(R.id.userPfp);
                        if (actualImage != null) {
                            userImage.setImageDrawable(actualImage);
                        } else {
                            Log.e("LoadImageFromWeb", "Failed to load image from " + imageURL);
                        }
                    });
                }).start();
            }

            public Drawable LoadImageFromWebOperations(String url) {
                try {
                    InputStream is = (InputStream) new URL(url).getContent();
                    Drawable d = Drawable.createFromStream(is, "src name");
                    return d;
                } catch (Exception e) {
                    Log.e("LoadImageFromWeb", "Failed to load image from " + url, e);
                    return null;
                }
            }
        }
    }
}
