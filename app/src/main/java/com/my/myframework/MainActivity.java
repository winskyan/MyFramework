package com.my.myframework;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.my.library_base.constants.EventBusConstants;
import com.my.library_base.init.ARouterPath;
import com.my.library_base.model.EventBusMessageEvent;
import com.my.library_base.picture.GlideEngine;
import com.my.library_base.picture.ImageLoader;
import com.my.library_base.picture.UserViewInfo;
import com.my.library_base.utils.MMKVUtils;
import com.my.library_base.utils.Utils;
import com.my.library_db.callback.UserCallback;
import com.my.library_db.db.UserDatabase;
import com.my.library_db.model.User;
import com.my.library_net.callback.ResponseCallback;
import com.my.library_net.exception.ThrowableHandler;
import com.my.library_net.net.LoginManage;
import com.my.library_net.response.Response;
import com.my.library_net.response.body.LoginBean;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPath.APP_MAIN_ACTIVITY)
public class MainActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger(MainActivity.class);

    @BindView(R.id.test_glide)
    ImageView testGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        ZoomMediaLoader.getInstance().init(new ImageLoader());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).load("https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/global/img/icons_441e82f.png").into(testGlide);
    }

    @OnClick({R.id.test_app_btn, R.id.test_eventbus_btn, R.id.test_mmkv_btn, R.id.test_room_btn, R.id.test_http_btn, R.id.test_pictureselector_btn
            , R.id.test_imagepreview_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_app_btn:
                Utils.aRouteGoto(ARouterPath.MAIN_MAIN_ACTIVITY, null);
                break;
            case R.id.test_eventbus_btn:
                EventBus.getDefault().post(new EventBusMessageEvent(EventBusConstants.TEST_CODE, "Hello everyone!"));
                break;
            case R.id.test_mmkv_btn:
                MMKVUtils mmkvUtils = MMKVUtils.getInstance("app");
                mmkvUtils.setValue("test1", 1);
                mmkvUtils.setValue("test2", "2");
                mmkvUtils.setValue("test3", false);
                mmkvUtils.remove("test2");
                logger.info(mmkvUtils.getBooleanValue("test3"));
                logger.info(Arrays.toString(mmkvUtils.getAllKey()));
                break;
            case R.id.test_room_btn:
                User user = new User();
                user.setUid(1);
                user.setFirstName("first name");
                user.setLastName("last name");
                UserCallback userCallback = new UserCallback() {
                    @Override
                    public void onLoadUsers(List<User> users) {
                        Toast.makeText(MainActivity.this, "查询用户成功", Toast.LENGTH_SHORT).show();
                        for (User u : users) {
                            logger.info(u);
                        }
                    }

                    @Override
                    public void onAdded() {
                        Toast.makeText(MainActivity.this, "添加用户成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleted() {

                    }

                    @Override
                    public void onUpdated() {

                    }

                    @Override
                    public void onError(String err) {
                        Toast.makeText(MainActivity.this, err, Toast.LENGTH_SHORT).show();
                    }
                };
                UserDatabase.getUserDatabase().addUser(userCallback, user);

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                UserDatabase.getUserDatabase().getAllUser(userCallback);
                break;
            case R.id.test_http_btn:
                ResponseCallback<LoginBean> callback = new ResponseCallback<LoginBean>() {
                    @Override
                    public void accept(Response<LoginBean> response) {
                        logger.info(response.toString());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        logger.info(ThrowableHandler.handleThrowable(throwable));
                    }
                };
                LoginManage.getInstance().login(callback, "user");
                break;
            case R.id.test_pictureselector_btn:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofAll())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .forResult(new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                // onResult Callback
                            }

                            @Override
                            public void onCancel() {
                                // onCancel Callback
                            }
                        });
                break;
            case R.id.test_imagepreview_btn:
                List<UserViewInfo> stringList = new ArrayList<>();
                UserViewInfo viewInfo = new UserViewInfo("https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/global/img/icons_441e82f.png");
                stringList.add(viewInfo);

                GPreviewBuilder.from(this)
                        .setData(stringList)
                        .setCurrentIndex(0)
                        .setSingleFling(true)//是否在黑屏区域点击返回
                        .setDrag(true)//是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number)//指示器类型
                        .start();//启动
                break;
        }
    }

    // 在主线程展示 Toast 结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMessageEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}