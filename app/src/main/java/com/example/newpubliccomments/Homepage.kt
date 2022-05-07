package com.example.newpubliccomments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.collection.CollectionActivity
import com.example.newpubliccomments.databinding.ActivitySettingBinding
import com.example.newpubliccomments.databinding.FragmentHomeBinding
import com.example.newpubliccomments.location.LocationFragment
import com.example.newpubliccomments.message.ConverFragment
import com.example.newpubliccomments.share.Evaluates
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.GlideEngine
import com.example.newpubliccomments.tool.StatusBar
import com.huantansheng.easyphotos.EasyPhotos
import com.huantansheng.easyphotos.callback.SelectCallback
import com.huantansheng.easyphotos.models.album.entity.Photo
import com.squareup.picasso.Picasso
import com.tbruyelle.rxpermissions2.RxPermissions
import de.hdodenhof.circleimageview.CircleImageView
import io.rong.imkit.RongIM
import io.rong.imlib.RongIMClient
import io.rong.imlib.model.Conversation
import io.rong.imlib.model.UserInfo
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//import cn.bmob.v3.exception.BmobException
//import cn.bmob.v3.listener.SaveListener

//      name:评价信息 avatar:头像 aid:当前id spholo：展示的图片
class Fruits(val name:String, val avatar: String, val aid: String,val spholo: String, val phone: String)

class FruitAdapters(val fruitList : List<Fruits>) :
    RecyclerView.Adapter<FruitAdapters.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val avatar : CircleImageView = view.findViewById(R.id.avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapters.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_threr_item,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            val fruitid = fruit.aid
            val fruitpholo = fruit.avatar

            var bundle = Bundle()
            bundle.putString("gopholo",fruitpholo)
            bundle.putString("data_id",fruitid)
            bundle.putString("phone", fruit.phone)
            bundle.putString("name", fruit.name)

            val intent = Intent(parent.context, SynopsisActivity::class.java)

            intent.putExtras(bundle)

            parent.context.startActivity(intent)
        }

        return viewHolder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: FruitAdapters.ViewHolder, position: Int) {
        val fruit = fruitList[position]

//        glide自带实现图片圆角方法
//        val roundedCorners = RoundedCorners(10)
//        val options = RequestOptions.bitmapTransform(roundedCorners)

        Glide.with(holder.itemView.context).load(fruit.spholo).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        Glide.with(holder.itemView.context).load(fruit.avatar).into(holder.avatar)
    }

    override fun getItemCount() = fruitList.size


}

private var bid: String? = null
private var bname: String? = null

private var settingBinding: ActivitySettingBinding? = null

class FruitSetting(val name:String, val image: String, val profile: String,val aid : String,var pholo : String, var rating: Float)

class FruitAdapterSetting(val fruitList: List<FruitSetting>) :
    RecyclerView.Adapter<FruitAdapterSetting.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val fruitImage : ImageView = view.findViewById(R.id.fruitImage)
        val fruitName : TextView = view.findViewById(R.id.fruitName)
        val fruitProfile : TextView = view.findViewById(R.id.fruitProfile)
        val fruitRating: RatingBar = view.findViewById(R.id.rating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdapterSetting.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_towitem,parent,false)

        val viewHolder = ViewHolder(view)

//            商家详情
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

             bid = fruit.aid
             bname = fruit.name

            settingBinding?.name?.text = bname
            settingBinding?.recyclerView?.visibility = View.GONE
            notifyDataSetChanged()

        }

//            商家详情
        viewHolder.fruitImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

            bid = fruit.aid
            bname = fruit.name

            settingBinding?.name?.text = bname
            settingBinding?.recyclerView?.visibility = View.GONE
            notifyDataSetChanged()

        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: FruitAdapterSetting.ViewHolder, position: Int) {
        val fruit = fruitList[position]
        Glide.with(holder.itemView.context).load(fruit.image).into(holder.fruitImage)
        holder.fruitName.text = fruit.name
        holder.fruitProfile.text = fruit.profile
        holder.fruitRating.rating = fruit.rating
    }

    override fun getItemCount() = fruitList.size

}


class FruitsImg(val uri: String)

class FruitAdaptersImg(val fruitList : List<FruitsImg>) :
    RecyclerView.Adapter<FruitAdaptersImg.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val image : ImageView = view.findViewById(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdaptersImg.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_img,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]



        }

        return viewHolder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: FruitAdaptersImg.ViewHolder, position: Int) {
        val fruit = fruitList[position]

//        glide自带实现图片圆角方法
//        val roundedCorners = RoundedCorners(10)
//        val options = RequestOptions.bitmapTransform(roundedCorners)

        holder.image.setImageURI(Uri.parse(fruit.uri))

}

    override fun getItemCount() = fruitList.size


}


class home(intent: Intent) : Fragment(){

    private var binding: FragmentHomeBinding? = null

    var gopholo = intent.getStringExtra("gopholo").toString()

    var mLocationClient : LocationClient? = null

    private val fruitLists = ArrayList<Fruits>()
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

        checkVersion()

        var recyclerViews = binding?.recyclerView

        initFruits()

//        val layoutManager = GridLayoutManager(context,2)
//        //线性布局
//        recyclerViews?.layoutManager = layoutManager
//        //完成适配器配置
//        val adapter = FruitAdapters(fruitLists)
//        recyclerViews?.adapter = adapter


        var meishi = binding?.LineMeishi
        meishi?.setOnClickListener {
//            val intent = Intent("com.example.newpubliccomment_Delicious.ACTION_START")
//            intent.putExtra("delpholo",gopholo)
//            startActivity(intent)

            DeliciousfoodActivity().start(it.context, gopholo)

        }



        return binding!!.root
    }

    override fun onResume() {
        super.onResume()

        GlobalScope.launch(Dispatchers.Main){
            delay(500)
            binding?.data = city
        }

    }

    private fun initFruits() {

        val bmobQuery = BmobQuery<Evaluates>()
        bmobQuery.findObjects(object : FindListener<Evaluates>(){
            override fun done(p0: MutableList<Evaluates>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
//                        fruitLists.add(Fruits(p0[0].Messages, p0[0].avatar, p0[0].objectId.toInt(), p0[0].photo))
                        p0.forEach {
                            var message = it.message
                            var avatar = it.avatar
                            var id = it.objectId
                            var photo = it.photo

                            Log.e("TAG", "done123456789: $message, $avatar, $id, $photo")
                            fruitLists.add(Fruits(message, avatar, id, photo, gopholo))
                        }

                        val layoutManager = GridLayoutManager(context,2)
                        //线性布局
                        binding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdapters(fruitLists)
                        binding?.recyclerView?.adapter = adapter

                    }
                }else{
                    Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

    }

    @SuppressLint("CheckResult")
    private fun checkVersion(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            var rxPermissions =  RxPermissions(requireActivity())
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
                        Toast.makeText(requireContext(), "权限未开启", Toast.LENGTH_SHORT).show()
                    }
                }
        }else {
            initLocation();// 定位初始化
        }
    }

    private fun initLocation() {
        //开启定位图层

        binding?.map?.map?.isMyLocationEnabled = true

        mLocationClient = LocationClient(this.requireContext())
        var myLocationListener : MyLocationListener = MyLocationListener()
        mLocationClient!!.registerLocationListener(myLocationListener)
        var option : LocationClientOption = LocationClientOption()

        option.setIsNeedAddress(true)
        option.addrType = "all"
        option.setOpenGps(true)
        option.setCoorType("bd09ll")
        option.setScanSpan(1000)

        mLocationClient!!.locOption = option

        mLocationClient!!.start()
    }

}

class car : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_car,container,false)
    }
}

class setting(intent: Intent) : Fragment() {
    var gopholo = intent.getStringExtra("gopholo").toString()

    private val fruitList = ArrayList<FruitSetting>()

    private val fruitListsImg = ArrayList<FruitsImg>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.activity_setting,container,false)

        settingBinding = ActivitySettingBinding.inflate(inflater, container, false)

//        var quxiao = view.findViewById(R.id.setting_quxiao) as TextView
//        var edt_synopsis = view.findViewById(R.id.edt_synopsis) as EditText
//        var Text_fabu = view.findViewById(R.id.Text_fabu) as TextView
//        var vedioPhoto = view.findViewById<LinearLayout>(R.id.vediophoto)
//
//        var relation = view.findViewById<Button>(R.id.relation)
//        var name = view.findViewById<TextView>(R.id.name)
//        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        settingBinding?.settingQuxiao?.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_Homepage.ACTION_START")
            intent.putExtra("gopholo",gopholo)
            startActivity(intent)
        }

        settingBinding?.relation?.setOnClickListener {

            settingBinding?.recyclerView?.visibility = View.VISIBLE

            initFruits()



        }

        settingBinding?.TextFabu?.setOnClickListener {

            var E_synopsis = edt_synopsis.text.toString()
            Log.e("显示",E_synopsis)

            if (settingBinding?.name?.text?.isEmpty() == true){
                Toast.makeText(requireContext(), "请关联商家", Toast.LENGTH_SHORT).show()
            }else{
                val eva = Evaluates()
                val bmobQuery = BmobQuery<PublicMyUser>()
                bmobQuery.findObjects(object : FindListener<PublicMyUser>(){
                    override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                        if (p1 == null){
                            if (p0 != null){
                                p0.forEach {
                                    if (it.phone == gopholo){
                                        eva.avatar = it.avatar
                                        eva.message = E_synopsis
                                        eva.phone = gopholo
                                        eva.businessid = bid
                                        eva.save(object : SaveListener<String>(){
                                            override fun done(p0: String?, p1: BmobException?) {
                                                if (p1 == null){
                                                    Toast.makeText(requireContext(), "发布成功", Toast.LENGTH_SHORT).show()
                                                }else{
                                                    Toast.makeText(requireContext(), "发布失败", Toast.LENGTH_SHORT).show()
                                                }
                                            }

                                        })
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }


//            Log.e("TAG", "onCreateView1245: ${settingBinding?.name?.text}")

        }

        settingBinding?.vediophoto?.setOnClickListener {

            EasyPhotos.createAlbum(this, true, true, GlideEngine.getInstance())
                .setFileProviderAuthority("com.example.newpubliccomments.fileprovider")
                .setCount(9)
                .setVideo(true)
                .setOriginalMenu(true, true, null)
                .start( object : SelectCallback(){
                    override fun onResult(
                        photos: java.util.ArrayList<Photo>?,
                        isOriginal: Boolean
                    ) {

                        if (photos != null){
                            photos.forEach {
                                fruitListsImg.add(FruitsImg(it.uri.toString()))
                            }
                        }

                        val layoutManager = GridLayoutManager(context,3)
                        //线性布局
                        img_recy?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdaptersImg(fruitListsImg)
                        img_recy?.adapter = adapter

                        img_recy.visibility = View.VISIBLE

                        Log.e("TAG", "onResult: ${photos?.get(0)?.uri}")
                    }

                    override fun onCancel() {
                        Toast.makeText(it.context, "Cancel", Toast.LENGTH_SHORT).show()
                    }

                })

        }

//        return view
        return settingBinding?.root
    }


    private fun initFruits(){

        val bmobQuery = BmobQuery<Business>()
        bmobQuery.findObjects(object : FindListener<Business>(){
            override fun done(p0: MutableList<Business>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){

                        if (fruitList.isNotEmpty()){
                            fruitList.clear()
                        }

                        p0.forEach {
                            var title = it.title
                            var bavatar = it.bavatar
                            var introduce = it.introduce
                            var id = it.objectId
                            var grade = it.grade
                            fruitList.add(FruitSetting(title, bavatar, introduce, id, gopholo, grade.toFloat()))
                        }

                        val layoutManager = LinearLayoutManager(requireContext())
                        //线性布局
                        settingBinding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdapterSetting(fruitList)
                        settingBinding?.recyclerView?.adapter = adapter

                    }

//                    Toast.makeText(this@DeliciousfoodActivity, "查询成功${p0?.get(0)?.bavatar}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "done123456789: ${p1?.message}, ${p1?.errorCode}")
                }
            }

        })

    }

}

class news : Fragment(){
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_news,container,false)



        return view
    }

}

class accont(intent: Intent) : Fragment(){
    var gopholo = intent.getStringExtra("gopholo").toString()
    @SuppressLint("WrongViewCast")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_accont,container,false)
        var os = view.findViewById(R.id.Overall_situation) as LinearLayout
        var setting = view.findViewById(R.id.setting) as ImageView
        var dingdanshu = view.findViewById(R.id.dingdanxianshi) as LinearLayout

        var customerService = view.findViewById<LinearLayout>(R.id.customer_service_layout)

        var collection = view.findViewById<LinearLayout>(R.id.collection)

        setting.setOnClickListener {
            val intent = Intent("com.example.newpubliccomment_set.ACTION_START")
            intent.putExtra("setpholo",gopholo)
            startActivity(intent)
        }

        dingdanshu.setOnClickListener {

//            XianshiOrderMainActivity().start(it.context, gopholo)
            if (gopholo == "" || gopholo.isEmpty()){
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }else{
                XianshiOrderMainActivity().start(it.context, gopholo)
            }

        }

        if (gopholo == "" || gopholo.isEmpty()){
            var click : TextView = view.findViewById(R.id.but_click) as TextView
            var zushi : TextView = view.findViewById(R.id.zushi) as TextView
            var accont_tou : de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.accont_touxiang) as de.hdodenhof.circleimageview.CircleImageView
            click.text = "点击登录"
            zushi.text = "登录更精彩"
            accont_tou.setImageResource(R.drawable.touxiang)
            os.setOnClickListener {
                val intent = Intent("com.example.newpubliccomment_LoginActivity.ACTION_START")
                startActivity(intent)
            }
        }else{



            var click : TextView = view.findViewById(R.id.but_click) as TextView
            var zushi : TextView = view.findViewById(R.id.zushi) as TextView
            var geren : TextView = view.findViewById(R.id.geren) as TextView
            var accont_tou : de.hdodenhof.circleimageview.CircleImageView = view.findViewById(R.id.accont_touxiang) as de.hdodenhof.circleimageview.CircleImageView
            click.text = gopholo
            zushi.text = "粉丝|关注"
            geren.text = "个人主页>"
//            accont_tou.setImageResource(R.drawable.jiutou)

//            获取头像并显示
            val user = BmobQuery<PublicMyUser>()
            user.findObjects(object : FindListener<PublicMyUser>(){
                override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                    if (p1 == null){

                        if (p0 != null) {
                            for (user: PublicMyUser in p0){
                                if (user.phone == gopholo){
                                    Glide.with(this@accont).load(user.avatar).placeholder(R.drawable.jiutou).into(accont_tou)
                                }
                            }
                        }
                    }else{
                        Toast.makeText(requireContext(), "查询失败", Toast.LENGTH_SHORT).show()
                    }
                }

            })

            os.setOnClickListener {
//                val intent = Intent("com.example.newpubliccomment_Peplo.ACTION_START")
                val intent = Intent(requireContext(), PeploActivity::class.java)
                intent.putExtra("phone", gopholo)
                startActivity(intent)
            }
        }

        customerService.setOnClickListener {

            RongIM.getInstance().startConversation(requireContext(), Conversation.ConversationType.PRIVATE, "100", "官方客服")

        }

        collection.setOnClickListener {

            if (gopholo == "" || gopholo.isEmpty()){
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
            }else{
                CollectionActivity().start(it.context, gopholo)
            }

        }

        return view
    }
}

var city : String? = null

class Homepage : BaseActivity() {

    //读写权限
    val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    //请求状态码
    val REQUEST_PERMISSION_CODE = 1

    var firstTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)



        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
        }

//        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)


        var gopholo = intent.getStringExtra("gopholo").toString()
        Log.e("xianshi",gopholo)

        val icon1: RelativeLayout =findViewById(R.id.icon1)
        val icon2: RelativeLayout =findViewById(R.id.icon2)
        val icon3: RelativeLayout =findViewById(R.id.icon3)
        val icon4: RelativeLayout =findViewById(R.id.icon4)
        val icon5: RelativeLayout =findViewById(R.id.icon5)
        val image1: ImageView =findViewById(R.id.iconimage1)
        val image2: ImageView =findViewById(R.id.iconimage2)
        val image3: ImageView =findViewById(R.id.iconimage3)
        val image4: ImageView =findViewById(R.id.iconimage4)
        val image5: ImageView =findViewById(R.id.iconimage5)
        val text1: TextView =findViewById(R.id.icontext1)
        val text2: TextView =findViewById(R.id.icontext2)
        val text3: TextView =findViewById(R.id.icontext3)
        val text4: TextView =findViewById(R.id.icontext4)
        val text5: TextView =findViewById(R.id.icontext5)

        /*val frag1 = fragHome()
        val frag2 = fragChess()
        val frag3 = fragUser()*/

        image1.setImageResource(R.drawable.home)
        image1.setColorFilter(Color.parseColor("#d81e06"))
        text1.setTextColor(Color.parseColor("#d81e06"))
        text2.setTextColor(Color.parseColor("#8E8E8E"))
        text3.setTextColor(Color.parseColor("#8E8E8E"))
        text4.setTextColor(Color.parseColor("#8E8E8E"))
        text5.setTextColor(Color.parseColor("#8E8E8E"))
        fraghome(home(intent))
        icon1.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#d81e06"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#d81e06"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            fraghome(home(intent))


        }

        icon2.setOnClickListener{
            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#d81e06"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#d81e06"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

//            fragChess(car())

            fragChess(LocationFragment.newInstance(gopholo))

        }

        icon3.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#d81e06"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#d81e06"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            if (gopholo != ""){
                fragUser(setting(intent))
            }else{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }

        }

        icon4.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#d81e06"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#d81e06"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            if (gopholo.isNotEmpty()){
//                fragnews(news())
//              进入消息页面

                RongIM.setConnectionStatusListener(object : RongIMClient.ConnectionStatusListener{
                    override fun onChanged(p0: RongIMClient.ConnectionStatusListener.ConnectionStatus?) {

                        Log.e("TAG", "onChanged123456: ${p0?.value}")

                    }

                })

                val token = "XQRY5vJO51IV+4CxOH4qngWoIfn2uZNJ@xj69.cn.rongnav.com;xj69.cn.rongcfg.com"

                RongIM.connect(token, object : RongIMClient.ConnectCallback(){
                    override fun onSuccess(p0: String?) {
                        Log.e("TAG", "onSuccess123456: ${p0}" )
                    }

                    override fun onDatabaseOpened(p0: RongIMClient.DatabaseOpenStatus?) {

                    }

                    override fun onError(p0: RongIMClient.ConnectionErrorCode?) {

                    }

                })

                RongIM.setUserInfoProvider(object : RongIM.UserInfoProvider{
                    override fun getUserInfo(p0: String?): UserInfo {
                        val userInfo = UserInfo(
                            "100",
                            "官方客服",
                            Uri.parse("https://lianshangke.oss-cn-zhangjiakou.aliyuncs.com/merchant/1637734692663d343522df3a751079748b21c60380d0c")
                        )
                        return userInfo
                    }

                }, true)

                val userInfo = UserInfo(
                    "100",
                    "官方客服",
                    Uri.parse("https://lianshangke.oss-cn-zhangjiakou.aliyuncs.com/merchant/1637734692663d343522df3a751079748b21c60380d0c")
                )
                RongIM.getInstance().refreshUserInfoCache(userInfo)


                fragnews(ConverFragment.newInstance(gopholo))
                
            }else{
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }

        }

        icon5.setOnClickListener{

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#8E8E8E"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#d81e06"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#8E8E8E"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#d81e06"))

            fragaccont(accont(intent))

        }

    }

    override fun onResume() {
        super.onResume()

//        var action = intent.getBooleanExtra("Action", false)
        var type = intent.getIntExtra("type", 0)
        var weidu  = intent.getDoubleExtra("weidu", 0.00)
        var jindu = intent.getDoubleExtra("jindu", 0.00)
        if (type == 2 || type == 3){

            val image1: ImageView =findViewById(R.id.iconimage1)
            val image2: ImageView =findViewById(R.id.iconimage2)
            val image3: ImageView =findViewById(R.id.iconimage3)
            val image4: ImageView =findViewById(R.id.iconimage4)
            val image5: ImageView =findViewById(R.id.iconimage5)
            val text1: TextView =findViewById(R.id.icontext1)
            val text2: TextView =findViewById(R.id.icontext2)
            val text3: TextView =findViewById(R.id.icontext3)
            val text4: TextView =findViewById(R.id.icontext4)
            val text5: TextView =findViewById(R.id.icontext5)

            image1.setImageResource(R.drawable.home)
            image2.setImageResource(R.drawable.location)
            image3.setImageResource(R.drawable.newadd)
            image4.setImageResource(R.drawable.news)
            image5.setImageResource(R.drawable.account)

            image1.setColorFilter(Color.parseColor("#8E8E8E"))
            image2.setColorFilter(Color.parseColor("#d81e06"))
            image3.setColorFilter(Color.parseColor("#8E8E8E"))
            image4.setColorFilter(Color.parseColor("#8E8E8E"))
            image5.setColorFilter(Color.parseColor("#8E8E8E"))

            text1.setTextColor(Color.parseColor("#8E8E8E"))
            text2.setTextColor(Color.parseColor("#d81e06"))
            text3.setTextColor(Color.parseColor("#8E8E8E"))
            text4.setTextColor(Color.parseColor("#8E8E8E"))
            text5.setTextColor(Color.parseColor("#8E8E8E"))

            fragChess(LocationFragment.newSecondInstance(type, weidu, jindu))
        }
//
//        action = false

    }

//    退出程序
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {

        when (keyCode){

            KeyEvent.KEYCODE_BACK -> {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime > 2000){
                    Toast.makeText(this, "再按一次退出美食点评", Toast.LENGTH_SHORT).show()
                    firstTime = secondTime
                    return true
                }else{
                    finishAffinity()
                    System.exit(0)
                }
            }

        }

        return super.onKeyUp(keyCode, event)
    }

    private fun fragaccont(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fragnews(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fragUser(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    public fun fragChess(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    private fun fraghome(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frag,fragment)
        transaction.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (i in 0 until permissions.size) {
                Log.i(
                    "MainActivity",
                    "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]
                )
            }
        }
    }



    fun start(context: Context, type: Int, weidu: Double, jindu: Double, phone: String){
        val intent = Intent(context, Homepage::class.java)
            intent.putExtra("type", type)
            intent.putExtra("weidu", weidu)
            intent.putExtra("jindu", jindu)
            intent.putExtra("gopholo", phone)
        context.startActivity(intent)
    }

}

class MyLocationListener : BDAbstractLocationListener() {
    //private val mapviews: MapView? = null
    var isFirstLoc = true
    override fun onReceiveLocation(location: BDLocation) {

        // MapView 销毁后不在处理新接收的位置
        if (location == null) {
            return
        }

        city = location.city
        Log.e("TAG", "onReceiveLocation: ${location.city}")

    }
}