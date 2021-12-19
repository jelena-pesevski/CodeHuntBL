package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Question;

import java.util.List;

public class QuestionAdapter extends
        RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView questionName;
        public TextView questionAnswer;

        public ViewHolder(View itemView){
            super(itemView);
            questionName = (TextView) itemView.findViewById(R.id.question_name);
            questionAnswer = (TextView) itemView.findViewById(R.id.question_answer);
        }
    }

    private List<Question> mQuestions;

    public QuestionAdapter(List<Question> questions){
        mQuestions = questions;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View questionView = inflater.inflate(R.layout.item_question, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(questionView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Question question = mQuestions.get(position);
        // Set item views based on your views and data model
        TextView questionNameTV = holder.questionName;
        questionNameTV.setText(question.getText());
        TextView questionAnswerTV = holder.questionAnswer;
        questionAnswerTV.setText(question.getAnswer());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mQuestions.size();
    }

}
