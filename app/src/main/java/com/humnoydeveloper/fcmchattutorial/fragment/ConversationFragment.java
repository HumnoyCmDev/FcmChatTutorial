package com.humnoydeveloper.fcmchattutorial.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.humnoydeveloper.fcmchattutorial.R;
import com.humnoydeveloper.fcmchattutorial.activity.ChatActivity;
import com.humnoydeveloper.fcmchattutorial.holder.ChatViewHolder;
import com.humnoydeveloper.fcmchattutorial.holder.UserAccountViewHolder;
import com.humnoydeveloper.fcmchattutorial.model.Room;
import com.humnoydeveloper.fcmchattutorial.model.UserAccount;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressWarnings("unused")
public class ConversationFragment extends Fragment {
    @BindView(R.id.list)
    RecyclerView mRecycler;
    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Room, ChatViewHolder> mAdapter;
    private LinearLayoutManager mManager;

    public ConversationFragment() {
        super();
    }

    @SuppressWarnings("unused")
    public static ConversationFragment newInstance() {
        ConversationFragment fragment = new ConversationFragment();
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Query query = mDatabase.child("user-conversation")
                .child(userId).limitToFirst(100);
        mAdapter = new FirebaseRecyclerAdapter<Room, ChatViewHolder>(Room.class,R.layout.conversation_item,
                ChatViewHolder.class,query) {
            @Override
            protected void populateViewHolder(ChatViewHolder viewHolder, Room model, int position) {
                DatabaseReference Ref = getRef(position);
                viewHolder.bindToPost(getContext(),model);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent = new Intent(getActivity(), ChatActivity.class);
//                        intent.putExtra(ChatActivity.EXTRA_ROOM_KEY, keyRoom);
//                        startActivity(intent);
                    }
                });
            }
        };

        mRecycler.setAdapter(mAdapter);

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

}
