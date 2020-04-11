package com.example.creativediligence.champsapp;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class Activity_Chat extends AppCompatActivity {
    static final String USER_ID_KEY = "userId";
    static final String BODY_KEY = "body";

    static final String TAG = Activity_Chat.class.getSimpleName();
    static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;
    EditText chatMessage;
    Button sendButton;
    RecyclerView rvChat;

    ArrayList<Helper_ChatMsg> mMessages;
    boolean mFirstLoad;
    Adapter_Chat mAdapter;

    static final int POLL_INTERVAL = 1000; // milliseconds
    Handler myHandler = new Handler();  // android.os.Handler
    Runnable mRefreshMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMessages();
            myHandler.postDelayed(this, POLL_INTERVAL);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatMessage = findViewById(R.id.etMessage);
        sendButton = findViewById(R.id.btSend);
        rvChat = findViewById(R.id.rvChat);

        mMessages = new ArrayList<>();
        mFirstLoad = true;
        final String userId = ParseUser.getCurrentUser().getObjectId();
        myHandler.postDelayed(mRefreshMessagesRunnable, POLL_INTERVAL);
        mAdapter = new Adapter_Chat(Activity_Chat.this, userId, mMessages);
        rvChat.setAdapter(mAdapter);

        // associate the LayoutManager with the RecylcerView
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Chat.this);
        linearLayoutManager.setReverseLayout(true);
        rvChat.setLayoutManager(linearLayoutManager);

    }

    public void SendChatMsg(View view) {
        String data = chatMessage.getText().toString();
        if (data.trim().length() > 0) {
            /*ParseObject message = ParseObject.create("Message");
            message.put(USER_ID_KEY, ParseUser.getCurrentUser().getObjectId());
            message.put(BODY_KEY, data);*/


            Helper_ChatMsg message = new Helper_ChatMsg();
            message.setBody(data);
            message.setUserId(ParseUser.getCurrentUser().getObjectId());
            message.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(Activity_Chat.this, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT).show();
                        chatMessage.setText("");
                    } else {
                        Log.e(TAG, "Failed to save message", e);
                    }
                }
            });
        }
    }

    void refreshMessages() {
        // Construct query to execute
        ParseQuery<Helper_ChatMsg> query = ParseQuery.getQuery(Helper_ChatMsg.class);
        // Configure limit and sort order
        query.setLimit(MAX_CHAT_MESSAGES_TO_SHOW);

        // get the latest 50 messages, order will show up newest to oldest of this group
        query.orderByDescending("createdAt");
        // Execute query to fetch all messages from Parse asynchronously
        // This is equivalent to a SELECT query with SQL
        query.findInBackground(new FindCallback<Helper_ChatMsg>() {
            public void done(List<Helper_ChatMsg> messages, ParseException e) {
                if (e == null) {
                    mMessages.clear();
                    mMessages.addAll(messages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (mFirstLoad) {
                        rvChat.scrollToPosition(0);
                        mFirstLoad = false;
                    }
                } else {
                    Log.e("message", "Error Loading Messages" + e);
                }
            }
        });
    }
}
