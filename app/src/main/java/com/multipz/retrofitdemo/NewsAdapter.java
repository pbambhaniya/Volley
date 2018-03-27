package com.multipz.retrofitdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Admin on 10-01-2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context context;
    private List<StatusModel> list;
    private ItemClickListener clickListener;


    public NewsAdapter(List<StatusModel> expertsList, Context context) {
        this.list = expertsList;
        this.context = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_status, txt_id;


        public MyViewHolder(View view) {
            super(view);
            txt_status = (TextView) view.findViewById(R.id.txt_status);
            txt_id = (TextView) view.findViewById(R.id.txt_id);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_status_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StatusModel model = list.get(position);
        holder.txt_status.setText(model.getText_status());
        holder.txt_id.setText(model.getTbl_status_id());

//        double time = Double.parseDouble(model.getTbl_status_id().toString());
//
//        new CountDownTimer((long) (time * 60000), 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                NumberFormat f = new DecimalFormat("00");
//                long hour = (millisUntilFinished / 3600000) % 24;
//                long min = (millisUntilFinished / 60000) % 60;
//                long sec = (millisUntilFinished / 1000) % 60;
////                holder.txt_time.setText(""+(millisUntilFinished / 1000));
//                holder.txt_id.setText(f.format(hour) + ":" + f.format(min) + ":" + f.format(sec) + "");
//
//            }
//
//            public void onFinish() {
//                holder.txt_id.setText("Done");
//            }
//        }.start();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
