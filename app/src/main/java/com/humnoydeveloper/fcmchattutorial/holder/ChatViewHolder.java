package com.humnoydeveloper.fcmchattutorial.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.humnoydeveloper.fcmchattutorial.R;
import com.humnoydeveloper.fcmchattutorial.model.Message;
import com.humnoydeveloper.fcmchattutorial.model.Room;
import com.humnoydeveloper.fcmchattutorial.util.Utils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 19/6/59.13:26น.
 *
 * @FcmChatTutorial
 */
public class ChatViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.title)
    TextView user;
    @BindView(R.id.last_message)
    TextView message;
    @BindView(R.id.time)
    TextView time;

    public ChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindToPost(Context mContext, Message message){
           user.setText(message.getAuthor());
           this.message.setText(message.getText());
            time.setVisibility(View.GONE);
    }

    public void bindToPost(Context mContext, Room message){
        user.setText(message.getAuthor());
        this.message.setText("[test]");
        time.setVisibility(View.GONE);
    }

}
