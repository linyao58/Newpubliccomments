package com.example.newpubliccomments.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.baidu.mapapi.UIMsg
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.route.*
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.Homepage
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.ActivityRouteBinding
import com.example.newpubliccomments.location.overlayutil.BikingRouteOverlay
import com.example.newpubliccomments.location.overlayutil.WalkingRouteOverlay

class RouteActivty: BaseActivity() {

    private var binding:ActivityRouteBinding? = null

    private var weidu:Double? = null
    private var jindu:Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_route)

        binding?.back?.setOnClickListener {
            onBackPressed()
        }


        val Latitude = intent.getDoubleExtra("mLatitudeStr", 0.00)
        val Longitude = intent.getDoubleExtra("mLongitudeStr", 0.00)
        weidu = Latitude
        jindu = Longitude

        location()

        var mSearch = RoutePlanSearch.newInstance()



        val listener : OnGetRoutePlanResultListener = object : OnGetRoutePlanResultListener {
            override fun onGetIndoorRouteResult(p0: IndoorRouteResult?) {
                TODO("Not yet implemented")
            }

            override fun onGetTransitRouteResult(p0: TransitRouteResult?) {
                TODO("Not yet implemented")
            }

            override fun onGetDrivingRouteResult(drivingRouteResult: DrivingRouteResult?) {
                /* var overlay : DrivingRouteOverlay = DrivingRouteOverlay(jsmapViews!!.map)
                 if (drivingRouteResult != null) {
                     if (drivingRouteResult.getRouteLines().size > 0){
                         overlay.setData(drivingRouteResult.getRouteLines().get(0))
                     overlay.addToMap()
                     }

                 }*/
            }

            override fun onGetWalkingRouteResult(walkingRouteResult: WalkingRouteResult?) {
                var overlay : WalkingRouteOverlay = WalkingRouteOverlay(binding?.map!!.map)
                if (walkingRouteResult != null) {
                    if (walkingRouteResult.getRouteLines().size > 0){
                        overlay.setData(walkingRouteResult.getRouteLines().get(0))
                        overlay.addToMap()
                    }

                }
            }

            override fun onGetMassTransitRouteResult(p0: MassTransitRouteResult?) {
                TODO("Not yet implemented")
            }

            override fun onGetBikingRouteResult(BikingRouteResult : BikingRouteResult?) {
//                var overlay : BikingRouteOverlay = BikingRouteOverlay(binding?.map!!.map)
//                if (BikingRouteResult != null) {
//                    if (BikingRouteResult.getRouteLines().size > 0){
//                        overlay.setData(BikingRouteResult.getRouteLines().get(0))
//                        overlay.addToMap()
//                    }
//
//                }
            }

        }

        mSearch.setOnGetRoutePlanResultListener(listener)



        binding?.dianji?.setOnClickListener {

            startLocation()

            var stNode : PlanNode = PlanNode.withCityNameAndPlaceName("佛山", binding?.kaiEdt?.text.toString())
            var enNode : PlanNode = PlanNode.withCityNameAndPlaceName("佛山", binding?.targetEdt?.text.toString())

            mSearch.walkingSearch/*drivingSearch*/(
                // DrivingRoutePlanOption()
                WalkingRoutePlanOption()
                    .from(stNode)
                    .to(enNode)
            )


            mSearch.destroy()

            mSearch.setOnGetRoutePlanResultListener(listener)
        }


    }

    fun startLocation(){

        var mSuggestionSearch = SuggestionSearch.newInstance()

        val listener = OnGetSuggestionResultListener {

            //处理sug检索结果

            if (it == null || it.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(this, "未搜索到内容", Toast.LENGTH_SHORT).show();
            }

            if (it.error == SearchResult.ERRORNO.NO_ERROR){



                Toast.makeText(this, "已搜索到内容", Toast.LENGTH_SHORT).show()

                val suggest: MutableList<HashMap<String, String>> = ArrayList<HashMap<String, String>>()
                for (info in it.getAllSuggestions()) {
                    if (info.getKey() != null && info.getDistrict() != null && info.getCity() != null) {
                        val map: HashMap<String, String> = HashMap()
                        map["key"] = info.getKey()
                        map["city"] = info.getCity()
                        map["dis"] = info.getDistrict()
                        map["mLatitudeStr"] = info.getPt().latitude.toString()
                        map["mlongitudeStr"] = info.getPt().longitude.toString()
                        suggest.add(map)
                    }
                }

                val fruit = suggest[0]
                weidu = fruit["mLatitudeStr"]?.toDouble()
                jindu = fruit["mlongitudeStr"]?.toDouble()

                location()
            }
        }

        mSuggestionSearch.setOnGetSuggestionResultListener(listener)

        mSuggestionSearch.requestSuggestion(
            SuggestionSearchOption()
                .city("广州")
                .keyword(binding?.kaiEdt?.text.toString())
        )

        mSuggestionSearch.destroy()

        mSuggestionSearch.setOnGetSuggestionResultListener(listener)

    }

    fun location(){

        if (weidu != null && jindu != null){
            var point = LatLng(weidu!!, jindu!!)
            var mMapStatus = MapStatus.Builder()
                .target(point)
                .zoom(18F)
                .build()
            var mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus)
            binding?.map!!.map.setMapStatus(mMapStatusUpdate)
        }

    }

    fun start(context: Context, weidu: Double, jindu: Double){
        val intent = Intent(context, RouteActivty::class.java)
        intent.putExtra("mLatitudeStr", weidu)
        intent.putExtra("mLongitudeStr", jindu)
        context.startActivity(intent)

    }

}