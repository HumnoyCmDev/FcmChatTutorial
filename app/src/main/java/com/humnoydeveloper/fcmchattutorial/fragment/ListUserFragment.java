package com.humnoydeveloper.fcmchattutorial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.humnoydeveloper.fcmchattutorial.R;
import com.humnoydeveloper.fcmchattutorial.activity.ChatActivity;
import com.humnoydeveloper.fcmchattutorial.holder.UserAccountViewHolder;
import com.humnoydeveloper.fcmchattutorial.model.Room;
import com.humnoydeveloper.fcmchattutorial.model.UserAccount;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unused")
public class ListUserFragment extends Fragment {
    private static final String TAG = "ListUserFragment";
    @BindView(R.id.list) RecyclerView mRecycler;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<UserAccount, UserAccountViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    public ListUserFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ListUserFragment newInstance() {
        ListUserFragment fragment = new ListUserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void init(Bundle savedInstanceState) {
        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]
    }

    @SuppressWarnings("UnusedParameters")
    private void initInstances(View rootView, Bundle savedInstanceState) {
        ButterKnife.bind(this,rootView);

        mRecycler.setHasFixedSize(true);
        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        //Query User
        Query query = mDatabase.child("users").limitToFirst(100)
                .orderByChild("status");
        mAdapter = new FirebaseRecyclerAdapter<UserAccount, UserAccountViewHolder>(UserAccount.class,
                R.layout.conversation_item,UserAccountViewHolder.class,query) {
            @Override
            protected void populateViewHolder(UserAccountViewHolder viewHolder, UserAccount model, int position) {
                DatabaseReference Ref = getRef(position);
                viewHolder.bindToPost(model);
                final String userKey = Ref.getKey();

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String userId = getUid();
                        mDatabase.child("user-conversation").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String keyRoom;
                                for (DataSnapshot d : dataSnapshot.getChildren()) {
                                    String interlocutor = dataSnapshot.child(d.getKey()).getValue(Room.class).getInterlocutor();
                                    if (userKey.equals(interlocutor)) {
                                        keyRoom = d.getKey();
                                        Log.i(TAG, "onDataChange: มีห้องแล้ว "+d.getKey());
                                        openChatActivity(keyRoom);
                                        return;
                                    }
                                }

                                Log.i(TAG, "writeRoomChat: create room ");
                                //Create Room Chat
                                createRoom(userKey);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e(TAG, "onCancelled: ", databaseError.toException());
                            }
                        });
                    }
                });
            }
        };

        mRecycler.setAdapter(mAdapter);

    }

    private void openChatActivity(String keyRoom){
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(ChatActivity.EXTRA_ROOM_KEY, keyRoom);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @SuppressWarnings("UnusedParameters")
    private void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore Instance State here
    }

    private void createRoom(final String mUserKey) {
        // [START single_value_read]
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        UserAccount user = dataSnapshot.getValue(UserAccount.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(getActivity(),
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeRoomChat(userId,mUserKey, user.getUsername(), new Date());
                        }
//                        Toast.makeText(getApplication(),dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        // [END single_value_read]
    }

    // [START write_fan_out]
    private void writeRoomChat(String userIdMe,String userIdTo, String username, Date dateTime) {
        String key = mDatabase.child("user-conversation").push().getKey();
        Room room = new Room();
        room.setUserId(userIdMe);
        room.setInterlocutor(userIdTo);
        room.setAuthor(username);
        room.setTimeStamp(dateTime.toString());

//        Map<String, Object> messageMeValues = room.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/user-conversation/" + userIdMe + "/" + key, room.toMap());

        room.setUserId(userIdTo);
        room.setInterlocutor(userIdMe);
        childUpdates.put("/user-conversation/" +userIdTo + "/" + key, room.toMap());

        mDatabase.updateChildren(childUpdates);
        openChatActivity(key);
        Log.i(TAG, "writeRoomChat: create room key"+key);
    }
    // [END write_fan_out]
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
