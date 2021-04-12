package com.my.library_ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_ui.R;
import com.my.library_ui.R2;
import com.my.library_ui.TitleBar;
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX;

import org.apache.log4j.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = ARouterConstants.PATH_UI_WEB_VIEW)
public class WebViewActivity extends AppCompatActivity {

    Logger logger = Logger.getLogger(WebViewActivity.class);

    @BindView(R2.id.ui_webview)
    LinearLayout mLayout;

    @BindView(R2.id.ui_titlebar)
    TitleBar mTitleBar;

    @Autowired(name = ARouterConstants.ARGS_URL)
    String url;

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_activity_webview);

        ButterKnife.bind(this);

        ARouter.getInstance().inject(this);

        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        //获取网页的标题
        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(title)) {
                    mTitleBar.setTitle(title);
                }
                super.onReceivedTitle(view, title);
            }
        });
        initStatusBar();
        initTitleBar();

    }

    protected void initStatusBar() {
        UltimateBarX.with(this)
                .color(Color.RED)
                .fitWindow(true)
                .light(false)
                .applyStatusBar();
    }

    private void initTitleBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTitleBar.setOnBackClick(() -> finish());
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
