package com.humnoydeveloper.fcmchattutorial.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.humnoydeveloper.fcmchattutorial.R;
import com.humnoydeveloper.fcmchattutorial.adapter.ChatAdapter;
import com.humnoydeveloper.fcmchattutorial.model.Message;
import com.humnoydeveloper.fcmchattutorial.model.UserAccount;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 19/6/59.13:02น.
 *
 * @FcmChatTutorial
 */
public class ChatActivity extends BaseActivity {
    private static final String TAG = "ChatActivity";
    public static final String EXTRA_ROOM_KEY = "room_key";

    private DatabaseReference mDatabase;
    private DatabaseReference mUserRoomMessageReference;
    private DatabaseReference mMessageReference;
    private ValueEventListener mPostListener;
    private String mRoomKey;
    private ChatAdapter mAdapter;

    @BindView(R.id.message_input)
    EditText mMessageInput;

    @BindView(R.id.list_message)
    RecyclerView mListChat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_chat);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get post key from intent
        mRoomKey = getIntent().getStringExtra(EXTRA_ROOM_KEY);
        if (mRoomKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_ROOM_KEY");
        }


//        // Initialize Database
//        mUserRoomMessageReference = FirebaseDatabase.getInstance().getReference()
//                .child("posts").child(mRoomKey);
//
        mMessageReference = FirebaseDatabase.getInstance().getReference()
                .child("user-message").child(mRoomKey);

        LinearLayoutManager mManager = new LinearLayoutManager(this);
        mManager.setStackFromEnd(true);
        mListChat.setLayoutManager(mManager);
        mAdapter = new ChatAdapter(this,mMessageReference);
        mListChat.setAdapter(mAdapter);
    }

    @OnClick(R.id.button_send)
    public void onClickSendMessage(){
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user information
                        UserAccount user = dataSnapshot.getValue(UserAccount.class);
                        String authorName = user.getUsername();

                        // Create new comment object
                        String messageText = mMessageInput.getText().toString();
                        Message message = new Message();
                        message.setUid(uid);
                        message.setAuthor(authorName);
                        message.setText(messageText);
//                        message.setTimeStamp(new Date());
                        // Push the comment, it will appear in the list
                        mMessageReference.push().setValue(message);

                        // Clear the field
                        mMessageInput.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "onCancelled: ", databaseError.toException());
                    }
                });
    }
}
