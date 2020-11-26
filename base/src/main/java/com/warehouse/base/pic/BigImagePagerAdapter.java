package com.warehouse.base.pic;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.warehouse.base.R;
import com.warehouse.base.activity.FinishactivityInterface;
import com.warehouse.base.pic.photoview.EasePhotoView;
import com.warehouse.base.pic.photoview.PhotoViewAttacher;
import com.warehouse.base.utils.ImagesDoUtils;
import com.warehouse.base.utils.PhotoRotateUtil;

import java.util.ArrayList;

/**
 * Created by liuhangf on 2017/9/21.
 */

public class BigImagePagerAdapter extends PagerAdapter {

    private ArrayList<String> datas;
    private Context context;
    private FinishactivityInterface finishactivityInterface;

    public BigImagePagerAdapter(Context context) {
        this.context = context;
        finishactivityInterface = (FinishactivityInterface) context;
    }

    public void setDatas(ArrayList<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewGroup) container).removeView((View)object);
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
       super.getItemPosition(object);
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_big_viewpager,container,false);
        EasePhotoView imageView = (EasePhotoView) view.findViewById(R.id.item_big_viewpager_img);
//        Bitmap bitmap = ImagesDoUtils.ratio(datas.get(position),1080,720);
        String strImage = datas.get(position);
        if (!TextUtils.isEmpty(strImage)&&strImage.startsWith("http")){
            Glide.with(context).load(strImage).into(imageView);
        }else{
            int degree = PhotoRotateUtil.getBitmapDegree(datas.get(position));
            Bitmap bitmap = PhotoRotateUtil.rotateBitmapByDegree(ImagesDoUtils.ratio( datas.get(position), 1080, 720 ),degree);
            imageView.setImageBitmap(bitmap);
       }

//        ((AddPhotosAdapter.ViewHolder)holder).addPhoto.setImageBitmap(bitmap);

        /**
         * 旋转
         */
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//
//        if (width < height) {
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getWidth());
//        } else {
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getHeight(), bitmap.getHeight(), matrix, true);
//        }
//        imageView.setImageBitmap(bitmap);
        container.addView(view);
        imageView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                finishactivityInterface.setFinishactivityInterface();
            }
        });
        return view;
    }
}
