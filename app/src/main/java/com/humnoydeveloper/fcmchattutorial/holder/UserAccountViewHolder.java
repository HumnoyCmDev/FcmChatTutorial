package com.humnoydeveloper.fcmchattutorial.holder;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.humnoydeveloper.fcmchattutorial.R;
import com.humnoydeveloper.fcmchattutorial.model.UserAccount;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * สร้างสรรค์ผลงานโดย humnoyDeveloper ลงวันที่ 19/6/59.11:29น.
 *
 * @FcmChatTutorial
 */
public class UserAccountViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.last_message)
    TextView description;
    @BindView(R.id.time)
    TextView status;
    public UserAccountViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindToPost(UserAccount account){
        title.setText(account.getUsername());
        description.setText(account.getEmail());
        if (account.getStatus().contains("offline"))
            status.setTextColor(Color.RED);
        else
            status.setTextColor(Color.GREEN);
        status.setText(account.getStatus());
    }
}
