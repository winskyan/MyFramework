package com.my.myframework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.my.library_base.base.MVVMBaseActivity;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_base.constants.EventBusConstants;
import com.my.library_base.model.EventBusMessageEvent;
import com.my.library_base.utils.MMKVUtils;
import com.my.library_base.utils.Utils;
import com.my.library_db.callback.UserCallback;
import com.my.library_db.db.UserDatabase;
import com.my.library_db.model.User;
import com.my.library_multimedia.image.GlideEngine;
import com.my.library_multimedia.image.ImageLoader;
import com.my.library_multimedia.image.UserViewInfo;
import com.my.library_multimedia.video.VideoPlayerIJK;
import com.my.library_multimedia.video.VideoPlayerIJKListener;
import com.my.library_multimedia.video.VideoPlayerIJKUtils;
import com.my.library_net.callback.ResponseCallback;
import com.my.library_net.exception.ThrowableHandler;
import com.my.library_net.net.LoginManage;
import com.my.library_net.response.Response;
import com.my.library_net.response.body.LoginBean;
import com.my.myframework.databinding.ActivityMainBinding;
import com.my.myframework.viewmodel.MainViewModel;
import com.previewlibrary.GPreviewBuilder;
import com.previewlibrary.ZoomMediaLoader;
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.umeng.analytics.MobclickAgent;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.apache.log4j.Logger;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

@Route(path = ARouterConstants.PATH_APP_MAIN_ACTIVITY)
public class MainActivityMVVMBaseActivity extends MVVMBaseActivity<ActivityMainBinding, MainViewModel> {

    Logger logger = Logger.getLogger(MainActivityMVVMBaseActivity.class);

    @BindView(R.id.test_glide)
    ImageView testGlide;

    @BindView(R.id.ijk_player)
    VideoPlayerIJK ijkPlayer;

    @BindView(R.id.test_gsy_vv)
    StandardGSYVideoPlayer testGsyVideoPlayer;

    @BindView(R.id.titlebar)
    CommonTitleBar titleBar;

    @Override
    public void initData() {
        ZoomMediaLoader.getInstance().init(new ImageLoader());

        VideoPlayerIJKUtils.getInstance().onInit();
    }

    @Override
    public void initViewObservable() {
        viewModel.getUserData().observe(this, new Observer<com.my.myframework.model.User>() {

            @Override
            public void onChanged(com.my.myframework.model.User user) {
                if (user == null) {
                    return;
                }
                // do something about UI
                logger.info("data bindging :" + user);
            }
        });
    }


    @Override
    public void resume() {
        Glide.with(this).load("https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/global/img/icons_441e82f.png").into(testGlide);
        viewModel.initUser();

        titleBar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT) {

            }
            // CommonTitleBar.ACTION_LEFT_TEXT;        // 左边TextView被点击
            // CommonTitleBar.ACTION_LEFT_BUTTON;      // 左边ImageBtn被点击
            // CommonTitleBar.ACTION_RIGHT_TEXT;       // 右边TextView被点击
            // CommonTitleBar.ACTION_RIGHT_BUTTON;     // 右边ImageBtn被点击
            // CommonTitleBar.ACTION_SEARCH;           // 搜索框被点击,搜索框不可输入的状态下会被触发
            // CommonTitleBar.ACTION_SEARCH_SUBMIT;    // 搜索框输入状态下,键盘提交触发，此时，extra为输入内容
            // CommonTitleBar.ACTION_SEARCH_VOICE;     // 语音按钮被点击
            // CommonTitleBar.ACTION_SEARCH_DELETE;    // 搜索删除按钮被点击
            // CommonTitleBar.ACTION_CENTER_TEXT;      // 中间文字点击
        });

    }


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @OnClick({R.id.test_app_btn, R.id.test_eventbus_btn, R.id.test_mmkv_btn, R.id.test_room_btn, R.id.test_http_btn, R.id.test_pictureselector_btn
            , R.id.test_imagepreview_btn, R.id.test_xxpermission_btn, R.id.test_privacy_policy})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_app_btn:
                Utils.aRouteGoto(ARouterConstants.PATH_MAIN_MAIN_ACTIVITY, null);
                break;
            case R.id.test_eventbus_btn:
                //友盟埋点
                MobclickAgent.onEvent(this, "test_page");
                EventBus.getDefault().post(new EventBusMessageEvent(EventBusConstants.TEST_CODE, "Hello everyone!"));
                break;
            case R.id.test_mmkv_btn:
                MMKVUtils mmkvUtils = MMKVUtils.getInstance();
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
                        Toast.makeText(MainActivityMVVMBaseActivity.this, "查询用户成功", Toast.LENGTH_SHORT).show();
                        for (User u : users) {
                            logger.info("查询用户成功" + u);
                        }
                    }

                    @Override
                    public void onAdded() {
                        Toast.makeText(MainActivityMVVMBaseActivity.this, "添加用户成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleted() {

                    }

                    @Override
                    public void onUpdated() {

                    }

                    @Override
                    public void onError(String err) {
                        Toast.makeText(MainActivityMVVMBaseActivity.this, err, Toast.LENGTH_SHORT).show();
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
            case R.id.test_xxpermission_btn:
                XXPermissions.with(this)
                        // 申请安装包权限
                        //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                        // 申请悬浮窗权限
                        //.permission(Permission.SYSTEM_ALERT_WINDOW)
                        // 申请通知栏权限
                        //.permission(Permission.NOTIFICATION_SERVICE)
                        // 申请系统设置权限
                        //.permission(Permission.WRITE_SETTINGS)
                        // 申请单个权限
                        .permission(Permission.READ_PHONE_STATE)
                        // 申请多个权限
                        .permission(Permission.WRITE_EXTERNAL_STORAGE)
                        .request(new OnPermissionCallback() {

                            @Override
                            public void onGranted(List<String> permissions, boolean all) {
                                if (all) {
                                    Toasty.success(getContext(), "获取读取手机状态和写SD卡权限成功", Toast.LENGTH_SHORT, true).show();
                                } else {
                                    Toasty.warning(getContext(), "获取部分权限成功，但部分权限未正常授予", Toast.LENGTH_SHORT, true).show();
                                }
                            }

                            @Override
                            public void onDenied(List<String> permissions, boolean never) {
                                if (never) {
                                    Toasty.error(getContext(), "被永久拒绝授权，请手动授予读取手机状态和写SD卡权限", Toast.LENGTH_SHORT, true).show();
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    XXPermissions.startPermissionActivity(MainActivityMVVMBaseActivity.this, permissions);
                                } else {
                                    Toasty.error(getContext(), "获取录音和日历权限失败", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        });
                break;
            case R.id.test_privacy_policy:
                Map<String, String> args = new HashMap<>(1);
                args.put(ARouterConstants.ARGS_URL, "https://www.baidu.com");
                Utils.aRouteGoto(ARouterConstants.PATH_UI_WEB_VIEW, args);
                break;
        }
    }

    // 在主线程展示 Toast 结果
    @Override
    public void onMessageEvent(EventBusMessageEvent event) {
        Toasty.warning(getContext(), event.getMessage(), Toast.LENGTH_SHORT, true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.READ_PHONE_STATE) &&
                    XXPermissions.isGranted(this, Permission.WRITE_EXTERNAL_STORAGE)) {
                Toasty.success(getContext(), "用户已经在权限设置页授予了获取读取手机状态和写SD卡权限", Toast.LENGTH_SHORT, true).show();
            } else {
                Toasty.error(getContext(), "用户没有在权限设置页授予权限", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerIJKUtils.getInstance().onStop();
    }

    @Override
    public void destroy() {

    }

    @OnClick(R.id.test_gsy_video_player)
    public void onClickGsy(View v) {
        ijkPlayer.setListener(new VideoPlayerIJKListener() {
            @Override
            public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

            }

            @Override
            public void onCompletion(IMediaPlayer iMediaPlayer) {

            }

            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }

            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                return false;
            }

            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {

            }

            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {

            }

            @Override
            public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

            }
        });

        ijkPlayer.setVideoPath("/sdcard/ads/videos/trailer.mp4");


        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        testGsyVideoPlayer.setUp(source1, false, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.ic_launcher);
        testGsyVideoPlayer.setThumbImageView(imageView);
        //增加title
        testGsyVideoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        testGsyVideoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //是否可以滑动调整
        testGsyVideoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        testGsyVideoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
            }
        });
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT);
        testGsyVideoPlayer.startPlayLogic();
    }
}