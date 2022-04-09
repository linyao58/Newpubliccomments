package com.example.newpubliccomments.location

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.search.core.SearchResult
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener
import com.baidu.mapapi.search.sug.SuggestionSearch
import com.baidu.mapapi.search.sug.SuggestionSearchOption
import com.example.newpubliccomments.BaseActivity
import com.example.newpubliccomments.Homepage
import com.example.newpubliccomments.R
import com.example.newpubliccomments.databinding.ActivitySearchLocationBinding
import com.example.newpubliccomments.tool.StatusBar

class SearchLocation: BaseActivity() {

    private var binding: ActivitySearchLocationBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_location)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        val content = intent.getStringExtra("content").toString()

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

                val simpleAdapter = SimpleAdapter(
                    applicationContext,
                    suggest,
                    R.layout.item_layout,
                    arrayOf("key", "city", "dis", "mLatitudeStr","mlongitudeStr"),
                    intArrayOf(R.id.sug_key, R.id.sug_city, R.id.sug_dis, R.id.sug_mLatitudeStr,R.id.sug_mlongituder)
                )
                binding?.sugList?.setAdapter(simpleAdapter)
                binding?.sugList?.setOnItemClickListener { parent, view, position, id ->
                    val fruit = suggest[position]
                    var ssweidu = fruit["mLatitudeStr"]
                    var ssjindu = fruit["mlongitudeStr"]
                    Log.d("纬度",ssweidu.toString())
                    Log.d("经度",ssjindu.toString())
                    if (ssweidu != null && ssjindu != null){
                        Homepage().start(this, 2, ssweidu.toDouble(), ssjindu.toDouble())
                    }

                }

                simpleAdapter.notifyDataSetChanged()
            }
        }

        mSuggestionSearch.setOnGetSuggestionResultListener(listener)


        if (content.isNotEmpty()){

            binding?.data = content

        }



        binding?.search?.setOnClickListener {
            var sjian : Editable? = binding?.edt?.text
            mSuggestionSearch.requestSuggestion(
                SuggestionSearchOption()
                    .city("广州")
                    .keyword(sjian.toString())
            )

            mSuggestionSearch.destroy()

            mSuggestionSearch.setOnGetSuggestionResultListener(listener)
            binding?.sugList?.visibility = View.VISIBLE

        }


        binding?.back?.setOnClickListener {
            onBackPressed()
        }


    }

    override fun onResume() {
        super.onResume()
        binding?.map?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding?.map?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.map?.onDestroy()
    }

    fun start(context: Context){
        val intent = Intent(context, SearchLocation::class.java)
        intent.putExtra("content", "广东东软学院")
        context.startActivity(intent)

    }

    fun addressStart(context: Context, content: String){
        val intent = Intent(context, SearchLocation::class.java)
        intent.putExtra("content", content)
        context.startActivity(intent)
    }

}