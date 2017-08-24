package com.don.bilibili.fragment.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.don.bilibili.Json.Json;
import com.don.bilibili.R;
import com.don.bilibili.adapter.LiveRankLoveAdapter;
import com.don.bilibili.annotation.Id;
import com.don.bilibili.fragment.base.BindFragment;
import com.don.bilibili.http.HttpManager;
import com.don.bilibili.model.LiveRankLove;
import com.don.bilibili.service.SignService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LiveRankLoveFragment extends BindFragment {

    @Id(id = R.id.live_rank_child_lv_display)
    private RecyclerView mLvDisplay;

    private int mId;

    private List<LiveRankLove> mLoves = new ArrayList<LiveRankLove>();
    private LiveRankLoveAdapter mAdapter;

    public LiveRankLoveFragment(int id) {
        super();
        mId = id;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_live_rank_child;
    }

    @Override
    protected void bindListener() {
        setOnGetSignCallBack(new OnGetSignCallBack() {
            @Override
            public void callback(String method, String sign) {
                if ("AppRoom/opTop".equals(method)){
                    getLiveRankLove(sign);
                }
            }
        });
    }

    @Override
    protected void init() {
        initRecyclerView();
        getSign();
    }

    private void initRecyclerView() {
        mLvDisplay.setNestedScrollingEnabled(false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setSmoothScrollbarEnabled(true);
        mLvDisplay.setLayoutManager(layoutManager);
    }

    private void getSign() {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(mContext, SignService.class);
        intent.putExtra("method", "AppRoom/opTop");
        bundle.putString("_device", "android");
        bundle.putString("_hwid", "9edc79b18c3cf6f9");
        bundle.putString("access_key", "bda109fc53f39041fab6cbe2bd043ec1");
        bundle.putString("appkey", "1d8b6e7d45233436");
        bundle.putString("build", "508000");
        bundle.putString("mobi_app", "android");
        bundle.putString("platform", "android");
        bundle.putString("room_id", mId + "");
        bundle.putString("scale", "xxhdpi");
        bundle.putString("type", "youaishe");
        bundle.putString("src", "bili");
        bundle.putString("trace_id", "20170707090700027");
        bundle.putString("ts", "1499389647");
        bundle.putString("version", "5.8.0.508000");
        intent.putExtra("param", bundle);
        mContext.startService(intent);
    }

    private void getLiveRankLove(String sign) {
        Call<JSONObject> call = HttpManager.getInstance().getApiSevice().getUrl("http://api.live.bilibili.com/AppRoom/opTop?_device=android&_hwid=9edc79b18c3cf6f9&access_key=bda109fc53f39041fab6cbe2bd043ec1&appkey=1d8b6e7d45233436&build=508000&mobi_app=android&platform=android&room_id=" + mId + "&scale=xxhdpi&src=bili&trace_id=20170707090700027&ts=1499389647&type=youaishe&version=5.8.0.508000&sign=" + sign);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.body().optInt("code", -1) == 0) {
                    JSONObject object = response.body().optJSONObject("data");
                    if (object != null) {
                        JSONArray array = object.optJSONArray("list");
                        mLoves = Json.parseJsonArray(
                                LiveRankLove.class, array);
                        mAdapter = new LiveRankLoveAdapter(mContext,
                                mLoves);
                        mLvDisplay.setAdapter(mAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {

            }
        });
    }
}
