package com.warehouse.arch_demo.activity;

import android.content.Context;

import android.net.Uri;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.utils.DensityUtil;
import com.warehouse.base.pic.ImageWatcher;
import com.warehouse.arch_demo.utils.InflateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghts on 2017/12/13.
 */

public class DemandDetailPictureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> picUrls;
    private Map<Integer, byte[]> picBytes;
    private Context mContext;
    private AppCompatActivity mActivity;
    private RecyclerView mRecyclerView;

    private ImageWatcher vImageWatcher;
    final SparseArray<ImageView> mapping = new SparseArray<>(); // 这个请自行理解，
    private List<Uri> dataList = new ArrayList<>();

    public DemandDetailPictureAdapter(Context ctx, List<String> picUrls,RecyclerView recyclerView,ImageWatcher vImageWatcher) {
        this.picUrls = picUrls;
        this.mContext = ctx;
        this.mActivity = (AppCompatActivity) ctx;
        picBytes = new HashMap<>();
        this.mRecyclerView=recyclerView;
        this.vImageWatcher=vImageWatcher;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = InflateUtils.inflate(R.layout.imageview_200, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (position == picUrls.size() - 1) {
            ((ViewHolder) holder).paddingRightView.setVisibility(View.GONE);
        } else {
            ((ViewHolder) holder).paddingRightView.setVisibility(View.VISIBLE);
        }
        if (picUrls != null && picUrls.size() == 1) {
            ((ViewHolder) holder).rlMain.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewHolder) holder).imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, 240)));
            ((ViewHolder) holder).imageView.setBackgroundResource(R.drawable.empty_demand);
        } else {
            ((ViewHolder) holder).rlMain.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewHolder) holder).imageView.setLayoutParams(new RelativeLayout.LayoutParams(
                    DensityUtil.dip2px(mContext, 240), DensityUtil.dip2px(mContext, 240)));
            ((ViewHolder) holder).imageView.setBackgroundResource(R.drawable.empty_square);
        }
        ((ViewHolder) holder).imageView.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(picUrls.get(position))
                .into(((ViewHolder) holder).imageView);

        mapping.put(position,((ViewHolder) holder).imageView);

        ((ViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mContext, BigImagePagerByteActivity.class);
//                Bundle bundle = new Bundle();
////                bundle.putSerializable("Biglist", (Serializable) picBytes);
//                Global.mPicBytes = picBytes;
//                bundle.putSerializable("picUrls", (Serializable) picUrls);
//                bundle.putInt("size", picUrls.size());
//                bundle.putInt("position", position);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);

//                ImagePagerActivity.startImagePage(mActivity,
//                        (ArrayList<String>) picUrls, position, mRecyclerView.getLayoutManager().findViewByPosition(position));

                dataList.clear();
                for(int i = 0;i<picUrls.size();i++) {  //imagePaths
                    dataList.add(Uri.parse(picUrls.get(i)));
                }
                vImageWatcher.show(((ViewHolder) holder).imageView, mapping, dataList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return picUrls == null ? 0 : picUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private View paddingRightView;
        private RelativeLayout rlMain;

        public ViewHolder(View itemView) {
            super(itemView);
            rlMain = (RelativeLayout) (itemView).findViewById(R.id.imageview_200_main);
            imageView = (ImageView) (itemView).findViewById(R.id.image_demand_detail);
            paddingRightView = (itemView).findViewById(R.id.image_demand_paddingright);
        }
    }
}
