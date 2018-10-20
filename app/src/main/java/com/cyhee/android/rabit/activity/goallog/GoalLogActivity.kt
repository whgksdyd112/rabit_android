package com.cyhee.android.rabit.activity.goallog

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cyhee.android.rabit.R
import com.cyhee.android.rabit.activity.comment.CommentViewAdapter
import com.cyhee.android.rabit.listener.IntentListener
import com.cyhee.android.rabit.model.*
import com.cyhee.android.rabit.useful.Fun
import kotlinx.android.synthetic.main.item_complete_fullgoallog.*
import kotlinx.android.synthetic.main.item_complete_list.*
import kotlinx.android.synthetic.main.item_complete_prevtopbar.*
import kotlinx.android.synthetic.main.item_part_goalwriter.*
import kotlinx.android.synthetic.main.item_part_reaction.*
import kotlinx.android.synthetic.main.item_part_text.*


class GoalLogActivity: AppCompatActivity(), GoalLogContract.View {
    override var presenter : GoalLogContract.Presenter = GoalLogPresenter(this)
    private var commentAdapter: CommentViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goallog)

        if (intent.hasExtra("goalLogId")) {
            val goalLogId = intent.getLongExtra("goalLogId", -1)
            presenter.goalLogInfo(goalLogId)

            likeNumberText.setOnClickListener(IntentListener.toGoalLogLikeListListener(goalLogId))

        } else {
            Toast.makeText(this, "전달된 goalLog 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        prevBtn.setOnClickListener {
            Log.d("preBtn","clicked")
            finish()
        }
    }

    override fun showGoalLogInfo(goalLogInfo : GoalLogInfo) {
        nameText.text = goalLogInfo.goal.author.username
        val goalTitle = goalLogInfo.goal.content + Fun.dateDistance(goalLogInfo)
        titleText.text = goalTitle

        var companion = when {
            goalLogInfo.goal.parent != null -> "${goalLogInfo.goal.parent!!.author.username} 님 외 ${goalLogInfo.companionNum}명이 함께하는 중"
            goalLogInfo.companionNum != 0 -> "${goalLogInfo.companionNum}명이 함께하는 중"
            else -> "함께 해보세요!"
        }

        companionText.text = companion
        likeNumberText.text = goalLogInfo.likeNum.toString()
        commentNumberText.text = goalLogInfo.commentNum.toString()

        text.text = goalLogInfo.content

        when {
            goalLogInfo.goal.parent != null -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.parent!!.id))
            else -> companionText.setOnClickListener(IntentListener.toGoalListener(goalLogInfo.goal.id))
        }

        likeButton.setOnClickListener {
            presenter.postLikeForGoalLog(goalLogInfo.id)
        }

        commentWriteLayout.findViewById<Button>(R.id.postBtn).setOnClickListener {
            val content = commentWriteLayout.findViewById<EditText>(R.id.commentText).text.toString()
            // TODO: 내용이 없을 경우 포스트 안되도록
            val postedComment = CommentFactory.Post(content)

            presenter.postCommentForGoalLog(goalLogInfo.id, postedComment)
        }
    }

    override fun showComments(comments: MutableList<Comment>) {
        if (commentAdapter == null) {
            commentAdapter = CommentViewAdapter(comments)
            listView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
            listView.adapter = commentAdapter
        } else {
            commentAdapter!!.appendComments(comments)
        }
    }
}