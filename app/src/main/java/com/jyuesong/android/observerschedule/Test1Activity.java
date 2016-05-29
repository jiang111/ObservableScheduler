package com.jyuesong.android.observerschedule;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.jiang.android.recyclerview.BaseAdapter;
import com.jiang.android.recyclerview.holder.BaseViewHolder;
import com.jyuesong.android.schedule.JObservable;
import com.jyuesong.android.schedule.Subscribe;
import com.jyuesong.android.schedule.SubscribeManager;
import com.jyuesong.android.schedule.Subscription;
import com.jyuesong.android.schedule.schedule.Schedules;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Test1Activity extends AppCompatActivity {

    private HashMap<String, List<String>> mGruopMap = new HashMap<String, List<String>>();

    private static final String TAG = "Test1Activity";
    private Subscription mSubscription;
    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("请稍等...");
        mDialog.setCanceledOnTouchOutside(false);

        mSubscription = JObservable.create(new JObservable.OnSubscribe<List<Bitmap>>() {
            @Override
            public void call(SubscribeManager<List<Bitmap>> mSubscriber) {
                try {
                    getImage();
                    List<String> strings = subGroupOfImage(mGruopMap);
                    List<String> string2 = new ArrayList<>();
                    string2.addAll(strings);
                    string2.addAll(strings);
                    string2.addAll(strings);
                    string2.addAll(strings);
                    string2.addAll(strings);
                    string2.addAll(strings);
                    List<Bitmap> bitmaps = new ArrayList<>();
                    for (int i = 0; i < strings.size(); i++) {
                        bitmaps.add(Utils.getSmallBitmap(strings.get(i)));
                    }
                    mSubscriber.notifyData(bitmaps);
                } catch (Exception e) {
                    mSubscriber.error(e);
                }

            }
        }).workedOn(Schedules.background())
                .subscribeOn(Schedules.mainThread())
                .subscribe(new Subscribe<List<Bitmap>>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        mDialog.show();
                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
                        mDialog.dismiss();
                    }

                    @Override
                    public void notifyData(List<Bitmap> strings) {

                        showToast("数据获取成功");
                        initRecyclerView(strings);
                    }

                    @Override
                    public void error(Throwable t) {

                        showToast(t.toString());
                    }
                });

        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mSubscription.unsubscribe();
                showToast("已取消任务");

            }
        });
    }


    private void showToast(String s) {
        Toast.makeText(Test1Activity.this, s, Toast.LENGTH_SHORT).show();
    }


    private void initRecyclerView(final List<Bitmap> bitmaps) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.test1_recyclerviews);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new BaseAdapter() {
            @Override
            protected void onBindView(BaseViewHolder holder, int position) {
                ImageView iv = holder.getView(R.id.images);
                iv.setImageBitmap(bitmaps.get(position));
            }

            @Override
            protected int getLayoutID(int position) {
                return R.layout.item_test1;
            }

            @Override
            public int getItemCount() {
                return bitmaps.size();
            }
        });

    }


    public void getImage() {
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = Test1Activity.this.getContentResolver();

        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri, null,
                MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);

        if (mCursor == null) {
            return;
        }

        while (mCursor.moveToNext()) {
            //获取图片的路径
            String path = mCursor.getString(mCursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));

            //获取该图片的父路径名
            String parentName = new File(path).getParentFile().getName();


            //根据父路径名将图片放入到mGruopMap中
            if (!mGruopMap.containsKey(parentName)) {
                List<String> chileList = new ArrayList<String>();
                chileList.add(path);
                mGruopMap.put(parentName, chileList);
            } else {
                mGruopMap.get(parentName).add(path);
            }
        }

        //通知Handler扫描图片完成
        mCursor.close();
    }

    private List<String> subGroupOfImage(HashMap<String, List<String>> mGruopMap) {
        List<String> strings = new ArrayList<>();
        if (mGruopMap.size() == 0) {
            return null;
        }


        Iterator<Map.Entry<String, List<String>>> it = mGruopMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, List<String>> entry = it.next();
            List<String> value = entry.getValue();

            for (int i = 0; i < value.size(); i++) {
                strings.add(value.get(i));
            }
        }

        return strings;

    }


}
