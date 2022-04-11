package com.example.newpubliccomments.location

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.baidu.location.*
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.poi.*
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.ActivityCarBinding
import com.example.newpubliccomments.location.overlayutil.PoiOverlay
import com.tbruyelle.rxpermissions2.RxPermissions

var mapviews : MapView? = null
class LocationFragment: Fragment() {

    private var binding: ActivityCarBinding? = null
    var mLocationClient : LocationClient? = null

    private var locationService: LocationService? = null
    var mLatitudeStr: String? = null
    var mLongitudeStr: String? = null
    var province: String? = null

    private val mListener: BDLocationListener = object : BDLocationListener {
        var count = 0
        override fun onReceiveLocation(location: BDLocation) {
            mLatitudeStr = java.lang.Double.toString(location.latitude)
            mLongitudeStr = java.lang.Double.toString(location.longitude)

            /*var myData : MyLocationData? = MyLocationData.Builder()
                .direction(location.direction).latitude(location.latitude)
                .longitude(location.longitude).longitude(location.longitude)
                .build()

            mapviews!!.map.setMyLocationData(myData)*/

            mapviews!!.map.isMyLocationEnabled = true

            var point = LatLng(location.latitude.toString().toDouble(), location.longitude.toString().toDouble())


            var mMapStatus = MapStatus.Builder()
                .target(point)
                .zoom(18F)
                .build()
            var mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
            mapviews!!.map.setMapStatus(mMapStatusUpdate)

            //地图标志
            var pointss = LatLng(location.latitude.toString().toDouble(), location.longitude.toString().toDouble())
            var bitmap : BitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.water_drop)

            var options : OverlayOptions = MarkerOptions()
                .position(pointss)
                .icon(bitmap)

            mapviews!!.map.addOverlay(options)



            var circle : CircleOptions = CircleOptions().center(pointss).fillColor(0x80D2E9FF.toInt()).radius(100)
            mapviews!!.map.addOverlay(circle)

            Log.d("精度",mLongitudeStr.toString())
            Log.d("纬度",mLatitudeStr.toString())
            //j!!.setText(mLongitudeStr)
            //w!!.setText(mLatitudeStr)
            count++
            province = location.province
            if ("" != province && locationService != null) {
                locationService!!.stop()
            }
        }
    }

    companion object {
        fun newInstance(phone: String): LocationFragment {
            val args = Bundle()

            val fragment = LocationFragment()
            args.putString("phone",phone)
            fragment.arguments = args
            return fragment
        }

        fun newSecondInstance(type: Int, weidu: Double, jindu: Double): LocationFragment {
            val args = Bundle()

            val fragment = LocationFragment()
            args.putInt("type", type)
            args.putDouble("weidu", weidu)
            args.putDouble("jindu", jindu)
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

        var type = arguments?.getInt("type")
        if (type == 2){
            location()
        }else{
            locationService = LocationService(this.requireContext().applicationContext)
            locationService!!.registerListener(mListener)
            //注册监听
            locationService!!.setLocationOption(locationService!!.defaultLocationClientOption)
            locationService!!.start()
        }

        placeLocation()

        binding?.search?.setOnClickListener {

            var phone = arguments?.getString("phone")

            SearchLocation().start(it.context, phone!!)

//            var point = LatLng(23.143150, 113.02954)
//            var mMapStatus = MapStatus.Builder()
//                .target(point)
//                .zoom(18F)
//                .build()
//            var mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
//            binding?.bmapView!!.map.setMapStatus(mMapStatusUpdate)

        }

        binding?.luxian?.setOnClickListener {
            if (mLatitudeStr != null && mLongitudeStr != null){
                RouteActivty().start(it.context, mLatitudeStr!!.toDouble(), mLongitudeStr!!.toDouble())
            }

        }

        return binding?.root

    }

    private fun location(){
        var weidu = arguments?.getDouble("weidu")
        var jindu = arguments?.getDouble("jindu")

        if (weidu != null) {
            if (jindu != null) {
                var point = LatLng(weidu, jindu)
                var mMapStatus = MapStatus.Builder()
                    .target(point)
                    .zoom(20F)
                    .build()
                var mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
                binding?.bmapView!!.map.setMapStatus(mMapStatusUpdate)
            }
        }

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

    private fun placeLocation(){

        var mPoiSearch = PoiSearch.newInstance()

        val listener: OnGetPoiSearchResultListener = object : OnGetPoiSearchResultListener {
            override  fun onGetPoiResult(poiResult: PoiResult) {

                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    Toast.makeText(this@LocationFragment.requireContext(), "未搜索到内容", Toast.LENGTH_SHORT).show()
                }

                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    //获取POI检索结果
                    Toast.makeText(this@LocationFragment.requireContext(), "已搜索到内容", Toast.LENGTH_SHORT).show()
                    binding?.bmapView!!.map.clear()
                    val poiOverlay = PoiOverlay(binding?.bmapView!!.map)
                    poiOverlay.setData(poiResult)

                    //将poiOverlay添加至地图并缩放至合适级别
                    poiOverlay.addToMap()
                    poiOverlay.zoomToSpan()
                }
            }
            override   fun onGetPoiDetailResult(poiDetailSearchResult: PoiDetailSearchResult) {}
            override   fun onGetPoiIndoorResult(poiIndoorResult: PoiIndoorResult) {}

            //废弃
            override fun onGetPoiDetailResult(poiDetailResult: PoiDetailResult) {}
        }



        mPoiSearch.setOnGetPoiSearchResultListener(listener)
//      美食
        binding?.fool?.setOnClickListener {
            var type = arguments?.getInt("type")
            if (type == 2){

                var weidu = arguments?.getDouble("weidu")
                var jindu = arguments?.getDouble("jindu")
                if (weidu != null && jindu != null){

                    mPoiSearch.searchNearby(
                        PoiNearbySearchOption()
                            .location(LatLng(weidu, jindu))
                            .radius(1000)
//                    .city(citys.toString()) //必填
                            .keyword(binding?.fool?.text?.toString()) //必填
                            .pageNum(0)
                    )

                    mPoiSearch.destroy()

                    mPoiSearch.setOnGetPoiSearchResultListener(listener)

                }

            }else{
                mPoiSearch.searchNearby(
                    PoiNearbySearchOption()
                        .location(mLongitudeStr?.toDouble()?.let {
                            mLatitudeStr?.toDouble()?.let { it1 ->
                                LatLng(
                                    it1,
                                    it
                                )
                            }
                        })
                        .radius(1000)
//                    .city(citys.toString()) //必填
                        .keyword(binding?.fool?.text?.toString()) //必填
                        .pageNum(0)
                )

                mPoiSearch.destroy()

                mPoiSearch.setOnGetPoiSearchResultListener(listener)

            }

        }
//        医院
        binding?.hospital?.setOnClickListener {

            var type = arguments?.getInt("type")
            if (type == 2){

                var weidu = arguments?.getDouble("weidu")
                var jindu = arguments?.getDouble("jindu")
                if (weidu != null && jindu != null){

                    mPoiSearch.searchNearby(
                        PoiNearbySearchOption()
                            .location(LatLng(weidu, jindu))
                            .radius(1000)
//                    .city(citys.toString()) //必填
                            .keyword(binding?.hospital?.text?.toString()) //必填
                            .pageNum(0)
                    )

                    mPoiSearch.destroy()

                    mPoiSearch.setOnGetPoiSearchResultListener(listener)

                }

            }else{
                mPoiSearch.searchNearby(
                    PoiNearbySearchOption()
                        .location(mLongitudeStr?.toDouble()?.let {
                            mLatitudeStr?.toDouble()?.let { it1 ->
                                LatLng(
                                    it1,
                                    it
                                )
                            }
                        })
                        .radius(1000)
//                    .city(citys.toString()) //必填
                        .keyword(binding?.hospital?.text?.toString()) //必填
                        .pageNum(0)
                )

                mPoiSearch.destroy()

                mPoiSearch.setOnGetPoiSearchResultListener(listener)

            }

        }

//        景点

        binding?.jindian?.setOnClickListener {

            var type = arguments?.getInt("type")
            if (type == 2){

                var weidu = arguments?.getDouble("weidu")
                var jindu = arguments?.getDouble("jindu")
                if (weidu != null && jindu != null){

                    mPoiSearch.searchNearby(
                        PoiNearbySearchOption()
                            .location(LatLng(weidu, jindu))
                            .radius(1000)
//                    .city(citys.toString()) //必填
                            .keyword(binding?.jindian?.text?.toString()) //必填
                            .pageNum(0)
                    )

                    mPoiSearch.destroy()

                    mPoiSearch.setOnGetPoiSearchResultListener(listener)

                }

            }else{
                mPoiSearch.searchNearby(
                    PoiNearbySearchOption()
                        .location(mLongitudeStr?.toDouble()?.let {
                            mLatitudeStr?.toDouble()?.let { it1 ->
                                LatLng(
                                    it1,
                                    it
                                )
                            }
                        })
                        .radius(1000)
//                    .city(citys.toString()) //必填
                        .keyword(binding?.jindian?.text?.toString()) //必填
                        .pageNum(0)
                )

                mPoiSearch.destroy()

                mPoiSearch.setOnGetPoiSearchResultListener(listener)

            }

        }

    }

    private fun initLocation() {
        //开启定位图层
//        binding?.bmapView!!.map.isMyLocationEnabled = true
//
//        mLocationClient = LocationClient(this?.requireContext().applicationContext)
//        var myLocationListener : MyLocationListener = MyLocationListener()
//        mLocationClient!!.registerLocationListener(myLocationListener)
//        var option : LocationClientOption = LocationClientOption()
//
//        //option.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
//
//        option.setOpenGps(true)
//        option.setCoorType("bd09ll")
//        option.setScanSpan(1000)
//        //option.setLocationNotify(true)
//
//        //option.setNeedNewVersionRgc(true)
//        mLocationClient!!.locOption = option
//
//        mLocationClient!!.start()
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
//        mLocationClient?.stop()
//        mapviews!!.map.setMyLocationEnabled(false)
//        mapviews?.onDestroy()
//        mapviews = null

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