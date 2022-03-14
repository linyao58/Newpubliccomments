package com.example.newpubliccomments.location

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.CoordType
import com.baidu.mapapi.SDKInitializer
import com.baidu.mapapi.map.*
import com.example.newpubliccomments.databinding.ActivityCarBinding
import com.example.newpubliccomments.message.ConversationListFragment
import com.tbruyelle.rxpermissions2.RxPermissions

var mapviews : MapView? = null
class LocationFragment: Fragment() {

    private var binding: ActivityCarBinding? = null
    var mLocationClient : LocationClient? = null

    companion object {
        fun newInstance(): LocationFragment {
            val args = Bundle()

            val fragment = LocationFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ActivityCarBinding.inflate(inflater, container, false)

        mapviews = binding?.bmapView

        checkVersion()


        return binding?.root

    }

    @SuppressLint("CheckResult")
    private fun checkVersion(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            var rxPermissions =  RxPermissions(this.requireActivity())
            rxPermissions.request(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted: Boolean ->
                    if (granted) { //申请成功
                        //发起连续定位请求
                        initLocation() // 定位初始化
                    } else { //申请失败
                        Toast.makeText(this.requireContext(), "权限未开启", Toast.LENGTH_SHORT).show()
                    }
                }
        }else {
            initLocation();// 定位初始化
        }
    }

    private fun initLocation() {
        //开启定位图层
        binding?.bmapView!!.map.isMyLocationEnabled = true

        mLocationClient = LocationClient(this?.requireContext().applicationContext)
        var myLocationListener : MyLocationListener = MyLocationListener()
        mLocationClient!!.registerLocationListener(myLocationListener)
        var option : LocationClientOption = LocationClientOption()

        //option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy

        option.setOpenGps(true)
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)
        //option.setLocationNotify(true)

        //option.setNeedNewVersionRgc(true)
        mLocationClient!!.locOption = option

        mLocationClient!!.start()
    }

    override fun onResume() {
        super.onResume()

        binding?.bmapView?.onResume()

    }

    override fun onPause() {
        super.onPause()

        binding?.bmapView?.onPause()

    }

    override fun onDestroy() {
        super.onDestroy()

        binding?.bmapView?.onDestroy()
        mLocationClient?.stop()
        mapviews!!.map.setMyLocationEnabled(false)
        mapviews?.onDestroy()
        mapviews = null

    }

}

class MyLocationListener : BDAbstractLocationListener() {
    //private val mapviews: MapView? = null
    var isFirstLoc = true
    override fun onReceiveLocation(location: BDLocation) {

        // MapView 销毁后不在处理新接收的位置
        if (location == null || mapviews == null) {
            return
        }
        val locData = MyLocationData.Builder()
            .accuracy(location.radius) // 设置定位数据的精度信息，单位：米
            .direction(location.direction) // 此处设置开发者获取到的方向信息，顺时针0-360
            .latitude(location.latitude)
            .longitude(location.longitude)
            .build()
        // 设置定位数据, 只有先允许定位图层后设置数据才会生效
        mapviews!!.map.setMyLocationData(locData)
    }
}