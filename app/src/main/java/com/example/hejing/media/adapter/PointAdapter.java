package com.example.hejing.media.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hejing.media.I;
import com.example.hejing.media.R;
import com.example.hejing.media.bean.PointBean;
import com.example.hejing.media.view.FooterViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by limx on 2018/3/23.
 */

public class PointAdapter extends Adapter {
    Context mContext;
    List<PointBean> mList;
    boolean isMore;
    int soryBy = I.SORT_BY_ADDTIME_DESC;

    public PointAdapter(Context Context, List<PointBean> list) {
        mContext = Context;
        mList = new ArrayList<>();
        mList.addAll(list);
    }

    public void setSoryBy(int soryBy) {
        this.soryBy = soryBy;
//        sortBy();
        notifyDataSetChanged();
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean more) {
        isMore = more;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == I.TYPE_FOOTER) {
            holder = new FooterViewHolder(View.inflate(mContext, R.layout.item_footer, null));
        } else {
            holder = new PointViewHolder(View.inflate(mContext, R.layout.item_point, null));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == I.TYPE_FOOTER) {
            FooterViewHolder vh = (FooterViewHolder) holder;
            vh.tvFooter.setText("getFootString="+getFootString());
        } else {
            PointViewHolder vh = (PointViewHolder) holder;
            PointBean pointBean = mList.get(position);
            vh.pointId.setText(pointBean.getData());
            vh.pointAddress.setText(pointBean.getData());
            vh.itemPointLinear.setTag(pointBean.getData());
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() + 1 : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return I.TYPE_FOOTER;
        }
        return I.TYPE_ITEM;
    }

    public void initData(ArrayList<PointBean> list) {
        if (mList != null) {
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public int getFootString() {
        return isMore ? R.string.load_more : R.string.no_more;
    }

    public void addData(ArrayList<PointBean> list){
        mList.addAll(list);
        notifyDataSetChanged();
    }

    class PointViewHolder extends ViewHolder {
        @Bind(R.id.point_id)
        TextView pointId;
        @Bind(R.id.point_address)
        TextView pointAddress;
        @Bind(R.id.item_point_linear)
        LinearLayout itemPointLinear;

        @OnClick(R.id.item_point_linear)
        public void onViewClicked() {

        }

        PointViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
