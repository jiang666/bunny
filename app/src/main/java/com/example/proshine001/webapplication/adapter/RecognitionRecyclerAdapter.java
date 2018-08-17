package com.example.proshine001.webapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proshine001.webapplication.R;
import com.example.proshine001.webapplication.bean.ChargeRecordsInfo;

import java.util.List;

/**
 * Created by jianglei on 2017/2/24.
 */

public class RecognitionRecyclerAdapter extends RecyclerView.Adapter<RecognitionRecyclerAdapter.RecognitionViewHolder> {
    private List<ChargeRecordsInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public RecognitionRecyclerAdapter(Context context, List<ChargeRecordsInfo> datas){
        this. mContext=context;
        this. mDatas=datas;
        inflater= LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(final RecognitionViewHolder holder, final int position) {
        holder.tv_state.setText( mDatas.get(position).beaginLevel);
        /*holder.tv_time.setText(mDatas.get(position).getData());
        if(mDatas.get(position).isResult()){
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.white));
        }else {
            holder.tv_state.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.tv_time.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        holder.iv_recognition.setImageBitmap(mDatas.get(position).getBitmap());*/

    }
    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public RecognitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_recognition,parent, false);

        RecognitionViewHolder holder= new RecognitionViewHolder(view);
        return holder;
    }

    class RecognitionViewHolder extends RecyclerView.ViewHolder {

        TextView tv_state,tv_time;

        public RecognitionViewHolder(View view) {
            super(view);
            tv_state=(TextView) view.findViewById(R.id.tv_state);
            tv_time=(TextView) view.findViewById(R.id.tv_time);
        }

    }
    public void addData(int position) {
        //mDatas.add(position, "Insert One");
        //notifyItemInserted(position);
    }

}
