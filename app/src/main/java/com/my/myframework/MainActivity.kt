package com.my.myframework

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.my.library_base.base.BaseActivity
import com.my.library_base.constants.EventBusConstants
import com.my.library_base.init.ARouterPath
import com.my.library_base.model.EventBusMessageEvent
import com.my.library_base.utils.MMKVUtils
import com.my.library_base.utils.Utils
import com.my.library_db.callback.UserCallback
import com.my.library_db.db.UserDatabase
import com.my.library_db.model.User
import com.my.library_multimedia.image.GlideEngine
import com.my.library_multimedia.image.ImageLoader
import com.my.library_multimedia.image.UserViewInfo
import com.my.library_multimedia.video.VideoPlayerIJK
import com.my.library_multimedia.video.VideoPlayerIJKListener
import com.my.library_multimedia.video.VideoPlayerIJKUtils
import com.my.library_net.callback.ResponseCallback
import com.my.library_net.exception.ThrowableHandler
import com.my.library_net.net.LoginManage
import com.my.library_net.response.Response
import com.my.library_net.response.body.LoginBean
import com.my.myframework.databinding.ActivityMainBinding
import com.my.myframework.viewmodel.MainViewModel
import com.previewlibrary.GPreviewBuilder
import com.previewlibrary.ZoomMediaLoader
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import tv.danmaku.ijk.media.player.IMediaPlayer
import java.util.*

@Route(path = ARouterPath.APP_MAIN_ACTIVITY)
class MainActivity : BaseActivity<ActivityMainBinding?, MainViewModel?>() {

    @JvmField
    @BindView(R.id.test_glide)
    var testGlide: ImageView? = null

    @JvmField
    @BindView(R.id.ijk_player)
    var ijkPlayer: VideoPlayerIJK? = null
    override fun initData() {
        ButterKnife.bind(this)
        EventBus.getDefault().register(this)
        ZoomMediaLoader.getInstance().init(ImageLoader())
        VideoPlayerIJKUtils.getInstance().onInit()
    }

    override fun initViewObservable() {
        viewModel!!.userData.observe(this, Observer { user ->
            if (user == null) {
                return@Observer
            }
            // do something about UI
            logger.info("data bindging :$user")
        })
    }

    override fun resume() {
        Glide.with(this).load("https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/global/img/icons_441e82f.png").into(testGlide!!)
        viewModel!!.initUser()
        ijkPlayer!!.setListener(object : VideoPlayerIJKListener() {
            override fun onBufferingUpdate(iMediaPlayer: IMediaPlayer, i: Int) {}
            override fun onCompletion(iMediaPlayer: IMediaPlayer) {}
            override fun onError(iMediaPlayer: IMediaPlayer, i: Int, i1: Int): Boolean {
                return false
            }

            override fun onInfo(iMediaPlayer: IMediaPlayer, i: Int, i1: Int): Boolean {
                return false
            }

            override fun onPrepared(iMediaPlayer: IMediaPlayer) {}
            override fun onSeekComplete(iMediaPlayer: IMediaPlayer) {}
            override fun onVideoSizeChanged(iMediaPlayer: IMediaPlayer, i: Int, i1: Int, i2: Int, i3: Int) {}
        })
        ijkPlayer?.setVideoPath("/sdcard/ads/videos/trailer.mp4")
    }


    override fun initVariableId(): Int {
         return BR.mainViewModel
    }

    @OnClick(R.id.test_app_btn, R.id.test_eventbus_btn, R.id.test_mmkv_btn, R.id.test_room_btn, R.id.test_http_btn, R.id.test_pictureselector_btn, R.id.test_imagepreview_btn, R.id.test_xxpermission_btn)
    fun onClick(v: View) {
        when (v.id) {
            R.id.test_app_btn -> Utils.aRouteGoto(ARouterPath.MAIN_MAIN_ACTIVITY, null)
            R.id.test_eventbus_btn -> EventBus.getDefault().post(EventBusMessageEvent(EventBusConstants.TEST_CODE, "Hello everyone!"))
            R.id.test_mmkv_btn -> {
                val mmkvUtils = MMKVUtils.getInstance("app")
                mmkvUtils?.setValue("test1", 1)
                mmkvUtils?.setValue("test2", "2")
                mmkvUtils?.setValue("test3", false)
                mmkvUtils?.remove("test2")
                logger.info(mmkvUtils?.getBooleanValue("test3"))
                logger.info(Arrays.toString(mmkvUtils?.allKey))
            }
            R.id.test_room_btn -> {
                val user = com.my.library_db.model.User()
                user.uid = 1
                user.firstName = "first name"
                user.lastName = "last name"
                val userCallback: UserCallback = object : UserCallback {
                    override fun onLoadUsers(users: List<User?>?) {
                        Toast.makeText(this@MainActivity, "查询用户成功", Toast.LENGTH_SHORT).show()
                        if (users != null) {
                            for (u in users) {
                                logger.info("查询用户成功$u")
                            }
                        }
                    }


                    override fun onAdded() {
                        Toast.makeText(this@MainActivity, "添加用户成功", Toast.LENGTH_SHORT).show()
                    }

                    override fun onDeleted() {}
                    override fun onUpdated() {}
                    override fun onError(err: String?) {
                        Toast.makeText(this@MainActivity, err, Toast.LENGTH_SHORT).show()
                    }
                }
                UserDatabase.userDatabase!!.addUser(userCallback, user)
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                UserDatabase.userDatabase!!.getAllUser(userCallback)
            }
            R.id.test_http_btn -> {
                val callback: ResponseCallback<LoginBean?> = object : ResponseCallback<LoginBean?> {
                    override fun accept(response: Response<LoginBean?>?) {
                        logger.info(response.toString())
                    }

                    override fun onError(throwable: Throwable?) {
                        logger.info(throwable?.let { ThrowableHandler.handleThrowable(it) })
                    }
                }
                LoginManage.instance!!.login(callback, "user")
            }
            R.id.test_pictureselector_btn -> PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofAll())
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: List<LocalMedia?>) {
                            // onResult Callback
                        }

                        override fun onCancel() {
                            // onCancel Callback
                        }
                    })
            R.id.test_imagepreview_btn -> {
                val stringList: MutableList<UserViewInfo> = ArrayList()
                val viewInfo = UserViewInfo("https://dss1.bdstatic.com/5eN1bjq8AAUYm2zgoY3K/r/www/cache/static/protocol/https/global/img/icons_441e82f.png")
                stringList.add(viewInfo)
                GPreviewBuilder.from(this)
                        .setData(stringList)
                        .setCurrentIndex(0)
                        .setSingleFling(true) //是否在黑屏区域点击返回
                        .setDrag(true) //是否禁用图片拖拽返回
                        .setType(GPreviewBuilder.IndicatorType.Number) //指示器类型
                        .start() //启动
            }
            R.id.test_xxpermission_btn -> XXPermissions.with(this) // 申请安装包权限
                    //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                    // 申请悬浮窗权限
                    //.permission(Permission.SYSTEM_ALERT_WINDOW)
                    // 申请通知栏权限
                    //.permission(Permission.NOTIFICATION_SERVICE)
                    // 申请系统设置权限
                    //.permission(Permission.WRITE_SETTINGS)
                    // 申请单个权限
                    .permission(Permission.READ_PHONE_STATE) // 申请多个权限
                    .permission(Permission.WRITE_EXTERNAL_STORAGE)
                    .request(object : OnPermissionCallback {
                        override fun onGranted(permissions: List<String>, all: Boolean) {
                            if (all) {
                                toast("获取读取手机状态和写SD卡权限成功")
                            } else {
                                toast("获取部分权限成功，但部分权限未正常授予")
                            }
                        }

                        override fun onDenied(permissions: List<String>, never: Boolean) {
                            if (never) {
                                toast("被永久拒绝授权，请手动授予读取手机状态和写SD卡权限")
                                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                XXPermissions.startPermissionActivity(this@MainActivity, permissions)
                            } else {
                                toast("获取录音和日历权限失败")
                            }
                        }
                    })
        }
    }

    // 在主线程展示 Toast 结果
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: EventBusMessageEvent) {
        toast(event.message)
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == XXPermissions.REQUEST_CODE) {
            if (XXPermissions.isGranted(this, Permission.READ_PHONE_STATE) &&
                    XXPermissions.isGranted(this, Permission.WRITE_EXTERNAL_STORAGE)) {
                toast("用户已经在权限设置页授予了获取读取手机状态和写SD卡权限")
            } else {
                toast("用户没有在权限设置页授予权限")
            }
        }
    }

    override fun onStop() {
        super.onStop()
        VideoPlayerIJKUtils.getInstance().onStop()
    }

    override fun destroy() {
        EventBus.getDefault().unregister(this)
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

}