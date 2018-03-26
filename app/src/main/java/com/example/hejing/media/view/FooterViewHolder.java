package com.example.hejing.media.view;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.example.hejing.media.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by limx on 2018/3/23.
 */
public class FooterViewHolder extends ViewHolder {
    @Bind(R.id.tvFooter)
    public
    TextView tvFooter;


    public FooterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
