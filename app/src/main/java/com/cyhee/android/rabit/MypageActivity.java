package com.cyhee.android.rabit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MypageActivity extends AppCompatActivity {

    private ListView goalListView;
    private GoalListAdapter goalListAdp;
    private List<Goal> goalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        /* TODO: after backend is done, change it */
        String[][] tmp1 = {{"cy118@naver.com", "cy118", "108"},{"dlqmsgldi@naver.com", "dlqmsgl", "101"}};
        goalListView = findViewById(R.id.goalListView);
        goalList = new ArrayList<Goal>();
        goalList.add(new Goal("admin", "admin@gmail.com", "exercise more than 30min", "2018-01-01", "133", "0", tmp1, "It's tough day, so I failed...", "progress"));
        goalList.add(new Goal("admin", "admin@gmail.com", "read books more than 30min", "2018-01-02", "132", "1", tmp1, "Today, I read 'The Great Gatsby' 104p~170p", "progress"));
        goalList.add(new Goal("admin", "admin@gmail.com", "memorize more than 10 kangi", "2018-01-03", "131", "2", tmp1, "Memorizing is the thing that I HATE to do, but I did it.", "progress"));
        goalList.add(new Goal("admin", "admin@gmail.com", "think positively", "2018-01-04", "130", "3", tmp1, "Nothing bad happened today.", "progress"));
        goalListAdp = new GoalListAdapter(getApplicationContext(), goalList);
        goalListView.setAdapter(goalListAdp);

        goalListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);

                Intent participantIntent = new Intent (MypageActivity.this, ParticipantsActivity.class);
                MypageActivity.this.startActivity(participantIntent);

            }
        });


    }
}
