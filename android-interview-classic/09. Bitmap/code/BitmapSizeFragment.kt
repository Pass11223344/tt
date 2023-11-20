package com.yxd.knowledge.bitmap.code

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import com.yxd.R
import com.yxd.devlib.base.TestFragment

/**
 * 测试同一张图片在不同的mipmap文件夹下的大小
 *
 * 结论：对应的屏幕密度越低，所占用的内存大小就越大。
 */
class BitmapSizeFragment : TestFragment(){
    override fun init(view: View, savedInstanceState: Bundle?) {
        val bimapH = BitmapFactory.decodeResource(resources, R.mipmap.lvmaochong_h)
        val bimapXH = BitmapFactory.decodeResource(resources, R.mipmap.lvmaochong_xh)
        val bimapXXH = BitmapFactory.decodeResource(resources, R.mipmap.lvmaochong_xxh)
        
        /*
        BitmapSizeFragment: bimapH的大小是28224
        BitmapSizeFragment: bimapXH的大小是15876
        BitmapSizeFragment: bimapXXH的大小是7056
         */
        logt("bimapH的大小是${bimapH.allocationByteCount}")
        logt("bimapXH的大小是${bimapXH.allocationByteCount}")
        logt("bimapXXH的大小是${bimapXXH.allocationByteCount}")

    }
}