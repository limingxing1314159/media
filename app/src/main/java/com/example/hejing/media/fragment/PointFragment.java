package com.example.hejing.media.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hejing.media.I;
import com.example.hejing.media.R;
import com.example.hejing.media.activity.HomeActivity;
import com.example.hejing.media.adapter.PointAdapter;
import com.example.hejing.media.bean.PointBean;
import com.example.hejing.media.okhttputils.CommonUtil;
import com.example.hejing.media.okhttputils.ConvertUtils;
import com.example.hejing.media.okhttputils.L;
import com.example.hejing.media.okhttputils.NetDao;
import com.example.hejing.media.okhttputils.OkHttpUtils;
import com.example.hejing.media.view.SpaceItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by limx on 2018/3/23.
 */

public class PointFragment extends BaseFragment {
    private static final String TAG = PointFragment.class.getSimpleName();
    @Bind(R.id.tv_refresh)
    TextView tvRefresh;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    HomeActivity mContext;
    PointAdapter mPointAdapter;
    ArrayList<PointBean> mList;
    int pageId = 1;
    GridLayoutManager glm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_point, container, false);
        ButterKnife.bind(this, layout);
        mContext = (HomeActivity) getContext();
        mList = new ArrayList<>();
        mPointAdapter = new PointAdapter(mContext,mList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        srl.setColorSchemeColors(
                getResources().getColor(R.color.google_blue),
                getResources().getColor(R.color.google_green),
                getResources().getColor(R.color.google_red),
                getResources().getColor(R.color.google_yellow)
        );
        glm = new GridLayoutManager(mContext, I.COLUM_NUM);
        rv.setLayoutManager(glm);
        rv.setHasFixedSize(true);
        rv.setAdapter(mPointAdapter);
        rv.addItemDecoration(new SpaceItemDecoration(15));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        setPullUpListener();
        setPullDownListener();

    }
    /**
     * 上拉加载
     **/
    private void setPullUpListener() {
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = glm.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastPosition == mPointAdapter.getItemCount()-1
                        && mPointAdapter.isMore()){
                    pageId++;
                    downloadPoint(I.ACTION_PULL_UP);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstPosition = glm.findFirstVisibleItemPosition();
                srl.setEnabled(firstPosition == 0);
            }
        });

    }
    /**
     * 下拉刷新
     * */
    private void setPullDownListener() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                tvRefresh.setVisibility(View.VISIBLE);
                pageId = 1;
                downloadPoint(I.ACTION_DOWNLOAD);
            }
        });

    }

    private void downloadPoint(final int action) {
        NetDao.downloadPoint(mContext, I.User.TOKEN, pageId, new OkHttpUtils.OnCompleteListener<PointBean[]>() {
            @Override
            public void onSuccess(PointBean[] result) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mPointAdapter.setMore(true);
                L.e("result"+result);
                if (result != null && result.length > 0) {
                    ArrayList<PointBean> list = ConvertUtils.array2List(result);
                    if (action==I.ACTION_DOWNLOAD || action == I.ACTION_PULL_DOWN){
                        mPointAdapter.initData(list);
                    }else {
                        mPointAdapter.addData(list);
                    }
                    if (list.size() < I.PAGE_SIZE_DEFAULT) {
                        mPointAdapter.setMore(false);
                    }
                } else {
                    mPointAdapter.setMore(false);
                }
            }

            @Override
            public void onError(String error) {
                srl.setRefreshing(false);
                tvRefresh.setVisibility(View.GONE);
                mPointAdapter.setMore(false);
                CommonUtil.showShortToast(error);
                L.e("error" + error);
            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
