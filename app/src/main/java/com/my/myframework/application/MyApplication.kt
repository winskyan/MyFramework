package com.my.myframework.application

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants

class MyApplication : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "com.my.myframework.application.MyApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false)