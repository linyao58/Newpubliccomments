package com.example.newpubliccomments

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.NewMessage.Messages
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.collection.Collection
import com.example.newpubliccomments.databinding.ActivitySynopsisBinding
import com.example.newpubliccomments.follow.Follow
import com.example.newpubliccomments.live.Live
import com.example.newpubliccomments.location.SearchLocation
import com.example.newpubliccomments.share.Evaluates
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.StatusBar
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import de.hdodenhof.circleimageview.CircleImageView
import io.rong.imkit.RongIM
import io.rong.imlib.model.Conversation
import kotlinx.android.synthetic.main.activity_synopsis.*

class FruitsMessage(val name:String, val avatar: String, val userid: String,val message: String)

class FruitAdaptersMessage(val fruitList : List<FruitsMessage>) :
    RecyclerView.Adapter<FruitAdaptersMessage.ViewHolder>(){

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val avatar : CircleImageView = view.findViewById(R.id.avatar)
        val name : TextView = view.findViewById(R.id.name)
        val message : TextView = view.findViewById(R.id.message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitAdaptersMessage.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item_message,parent,false)
        val viewHolder = ViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val fruit = fruitList[position]

        }

        return viewHolder
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: FruitAdaptersMessage.ViewHolder, position: Int) {
        val fruit = fruitList[position]

//        glide自带实现图片圆角方法
//        val roundedCorners = RoundedCorners(10)
//        val options = RequestOptions.bitmapTransform(roundedCorners)

        Glide.with(holder.itemView.context).load(fruit.avatar).into(holder.avatar)
        holder.name.text = fruit.name
        holder.message.text = fruit.message

    }

    override fun getItemCount() = fruitList.size


}

class SynopsisActivity : AppCompatActivity() {

    private var binding: ActivitySynopsisBinding? = null

    private val fruitLists = ArrayList<FruitsMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_synopsis)

        //        使状态栏变透明，使布局变成侵入式布局
        StatusBar().statusBarColor(this)
//        设置状态栏图标颜色
        StatusBar().statusBarTextColor(this, true)

        var bundle = intent.extras
        var getpholos = bundle?.getString("gopholo").toString()
        var getsynoid = bundle?.getString("data_id")
        var getPhone = bundle?.getString("phone").toString()
        var getName = bundle?.getString("name").toString()
        var getUserId = bundle?.getString("userId").toString()

        binding?.DelBacksyno?.setOnClickListener {
            onBackPressed()
        }

        val shareListener = object : UMShareListener{
            override fun onStart(p0: SHARE_MEDIA?) {
                Toast.makeText(this@SynopsisActivity, "分享成功",Toast.LENGTH_SHORT).show()
            }

            override fun onResult(p0: SHARE_MEDIA?) {

            }

            override fun onError(p0: SHARE_MEDIA?, p1: Throwable?) {
                Toast.makeText(this@SynopsisActivity, "分享失败",Toast.LENGTH_SHORT).show()
            }

            override fun onCancel(p0: SHARE_MEDIA?) {

            }

        }

        binding?.fenxiang?.setOnClickListener {
            if (getPhone.isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                ShareAction(this).withText("hello").setDisplayList(SHARE_MEDIA.SINA,
                    SHARE_MEDIA.WEIXIN,
                    SHARE_MEDIA.WEIXIN_CIRCLE,
                    SHARE_MEDIA.WEIXIN_FAVORITE,
                    SHARE_MEDIA.DINGTALK,
                    SHARE_MEDIA.ALIPAY)
                    .setCallback(shareListener).open()
            }
        }

        if (getPhone.isEmpty()){
            binding?.colleType = false
        }else{
            val collle1 = BmobQuery<Collection>()
            collle1.addWhereEqualTo("phone", getPhone)
            val collle2 = BmobQuery<Collection>()
            collle2.addWhereEqualTo("businessid", getsynoid)

            val arrayList = ArrayList<BmobQuery<Collection>>()
            arrayList.add(collle1)
            arrayList.add(collle2)

            val bombQuery = BmobQuery<Collection>()
            bombQuery.and(arrayList)
            bombQuery.findObjects(object : FindListener<Collection>(){
                override fun done(p0: MutableList<Collection>?, p1: BmobException?) {
                    if (p1 == null){
                        if (p0 != null){
                            if (p0[0].collection == "true"){
                                binding?.colleType = true
                            }else{
                                binding?.colleType = false
                            }
                        }
                    }else{
                        binding?.colleType = false
                    }
                }

            })
        }

        binding?.soucang?.setOnClickListener {
            if (getPhone.isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{

                val collle1 = BmobQuery<Collection>()
                collle1.addWhereEqualTo("phone", getPhone)
                val collle2 = BmobQuery<Collection>()
                collle2.addWhereEqualTo("businessid", getsynoid)

                val arrayList = ArrayList<BmobQuery<Collection>>()
                arrayList.add(collle1)
                arrayList.add(collle2)

                val bombQuery = BmobQuery<Collection>()
                bombQuery.and(arrayList)
                bombQuery.findObjects(object : FindListener<Collection>(){
                    override fun done(p0: MutableList<Collection>?, p1: BmobException?) {
                        if (p1 == null){
                            if (p0 == null){


                            }else{
                                val collections =
                                    Collection()
                                if (p0[0].collection == "true"){
                                    collections.collection = "false"
                                    binding?.colleType = false
                                    Toast.makeText(this@SynopsisActivity, "取消收藏", Toast.LENGTH_SHORT).show()
                                }else{
                                    collections.collection = "true"
                                    binding?.colleType = true
                                    Toast.makeText(this@SynopsisActivity, "收藏成功", Toast.LENGTH_SHORT).show()
                                }

                                collections.update(p0[0].objectId, object : UpdateListener(){
                                    override fun done(p0: BmobException?) {
                                        if (p0 == null){

                                        }else{

                                        }
                                    }

                                })

                            }
                        }else{
//                            Toast.makeText(this@SynopsisActivity, "${p1.message}", Toast.LENGTH_SHORT).show()
                            val collection =
                                Collection()
                            collection.businessid = getsynoid
                            collection.phone = getPhone
                            collection.collection = "true"

                            collection.save(object : SaveListener<String>(){
                                override fun done(p0: String?, p1: BmobException?) {
                                    if (p1 == null){
                                        Toast.makeText(this@SynopsisActivity, "收藏成功", Toast.LENGTH_SHORT).show()
                                        binding?.colleType = true
                                    }else{
                                        Toast.makeText(this@SynopsisActivity, "收藏失败", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            })
                        }
                    }

                })
            }
        }

        if (getPhone.isEmpty()){
            binding?.liveType = false
        }else{
            val collle1 = BmobQuery<Live>()
            collle1.addWhereEqualTo("phone", getPhone)
            val collle2 = BmobQuery<Live>()
            collle2.addWhereEqualTo("synopsisid", getsynoid)

            val arrayList = ArrayList<BmobQuery<Live>>()
            arrayList.add(collle1)
            arrayList.add(collle2)

            val bombQuery = BmobQuery<Live>()
            bombQuery.and(arrayList)
            bombQuery.findObjects(object : FindListener<Live>(){
                override fun done(p0: MutableList<Live>?, p1: BmobException?) {
                    if (p1 == null){
                        if (p0 != null){
                            if (p0[0].live == "true"){
                                binding?.liveType = true
                            }else{
                                binding?.liveType = false
                            }
                        }
                    }else{
                        binding?.liveType = false
                    }
                }

            })
        }

        binding?.live?.setOnClickListener {

            if (getPhone.isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{

                val collle1 = BmobQuery<Live>()
                collle1.addWhereEqualTo("phone", getPhone)
                val collle2 = BmobQuery<Live>()
                collle2.addWhereEqualTo("synopsisid", getsynoid)

                val arrayList = ArrayList<BmobQuery<Live>>()
                arrayList.add(collle1)
                arrayList.add(collle2)

                val bombQuery = BmobQuery<Live>()
                bombQuery.and(arrayList)
                bombQuery.findObjects(object : FindListener<Live>(){
                    override fun done(p0: MutableList<Live>?, p1: BmobException?) {
                        if (p1 == null){
                            if (p0 == null){


                            }else{
                                val collections =
                                    Live()
                                if (p0[0].live == "true"){
                                    collections.live = "false"
                                    binding?.liveType = false
                                    Toast.makeText(this@SynopsisActivity, "取消点赞", Toast.LENGTH_SHORT).show()
                                }else{
                                    collections.live = "true"
                                    binding?.liveType = true
                                    Toast.makeText(this@SynopsisActivity, "点赞成功", Toast.LENGTH_SHORT).show()
                                }

                                collections.update(p0[0].objectId, object : UpdateListener(){
                                    override fun done(p0: BmobException?) {
                                        if (p0 == null){

                                        }else{

                                        }
                                    }

                                })

                            }
                        }else{
//                            Toast.makeText(this@SynopsisActivity, "${p1.message}", Toast.LENGTH_SHORT).show()
                            val collection =
                                Live()
                            collection.synopsisId = getsynoid
                            collection.phone = getPhone
                            collection.live = "true"

                            collection.save(object : SaveListener<String>(){
                                override fun done(p0: String?, p1: BmobException?) {
                                    if (p1 == null){
                                        Toast.makeText(this@SynopsisActivity, "点赞成功", Toast.LENGTH_SHORT).show()
                                        binding?.liveType = true
                                    }else{
                                        Toast.makeText(this@SynopsisActivity, "点赞失败", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            })
                        }
                    }

                })
            }

        }

        var businesId = ""

        Glide.with(this@SynopsisActivity).load(getpholos).into(binding?.userAvatar!!)

        var e_phone = ""
        val eva = BmobQuery<Evaluates>()
        eva.getObject(getsynoid, object : QueryListener<Evaluates>(){
            override fun done(p0: Evaluates?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        e_phone = p0.phone
                    }
                }
            }

        })

        binding?.fruitProfile?.setOnClickListener {
            SearchLocation().addressStart(it.context, binding?.fruitProfile?.text?.toString()!!, getPhone)
        }

        val buser = BmobQuery<PublicMyUser>()
        buser.findObjects(object : FindListener<PublicMyUser>(){
            override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){

                        p0.forEach {
                            if (it.phone == e_phone){
                                binding?.userName?.text = it.name
                            }
                        }

                    }
                }else{
                    Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                }
            }

        })

        if (getPhone.isEmpty()){
            binding?.follow?.text = "关注"
        }else{
            val collle1 = BmobQuery<Follow>()
            collle1.addWhereEqualTo("phone", getPhone)
            val collle2 = BmobQuery<Follow>()
            collle2.addWhereEqualTo("synopsisid", getUserId)

            val arrayList = ArrayList<BmobQuery<Follow>>()
            arrayList.add(collle1)
            arrayList.add(collle2)

            val bombQuery = BmobQuery<Follow>()
            bombQuery.and(arrayList)
            bombQuery.findObjects(object : FindListener<Follow>(){
                override fun done(p0: MutableList<Follow>?, p1: BmobException?) {
                    if (p1 == null){
                        if (p0 != null){
                            p0.forEach {
                                if (it.follow == "true"){
                                    binding?.follow?.text = "已关注"
                                }else{
                                    binding?.follow?.text = "关注"
                                }
                            }
                        }
                    }else{
                        binding?.follow?.text = "关注"
                    }
                }

            })
        }

        binding?.follow?.setOnClickListener {

            if (getPhone.isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{

                val collle1 = BmobQuery<Follow>()
                collle1.addWhereEqualTo("phone", getPhone)
                val collle2 = BmobQuery<Follow>()
                collle2.addWhereEqualTo("synopsisid", getUserId)

                val arrayList = ArrayList<BmobQuery<Follow>>()
                arrayList.add(collle1)
                arrayList.add(collle2)

                val bombQuery = BmobQuery<Follow>()
                bombQuery.and(arrayList)
                bombQuery.findObjects(object : FindListener<Follow>(){
                    override fun done(p0: MutableList<Follow>?, p1: BmobException?) {
                        if (p1 == null){
                            if (p0 == null){


                            }else{
                                val collections =
                                    Follow()
                                if (p0[0].follow == "true"){
                                    collections.follow = "false"
//                                    binding?.colleType = false
                                    binding?.follow?.text = "关注"
                                    Toast.makeText(this@SynopsisActivity, "取消关注", Toast.LENGTH_SHORT).show()
                                }else{
                                    collections.follow = "true"
                                    binding?.follow?.text = "已关注"
//                                    binding?.colleType = true
                                    Toast.makeText(this@SynopsisActivity, "关注成功", Toast.LENGTH_SHORT).show()
                                }

                                collections.update(p0[0].objectId, object : UpdateListener(){
                                    override fun done(p0: BmobException?) {
                                        if (p0 == null){

                                        }else{

                                        }
                                    }

                                })

                            }
                        }else{
//                            Toast.makeText(this@SynopsisActivity, "${p1.message}", Toast.LENGTH_SHORT).show()
                            val collection =
                                Follow()
                            collection.synopsisId = getUserId
                            collection.phone = getPhone
                            collection.follow = "true"

                            collection.save(object : SaveListener<String>(){
                                override fun done(p0: String?, p1: BmobException?) {
                                    if (p1 == null){
                                        binding?.follow?.text = "已关注"
                                        Toast.makeText(this@SynopsisActivity, "关注成功", Toast.LENGTH_SHORT).show()
//                                        binding?.colleType = true
                                    }else{
                                        Toast.makeText(this@SynopsisActivity, "关注失败", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            })
                        }
                    }

                })

            }

        }

        binding?.recyclerView?.isNestedScrollingEnabled = false

        var userPhone = ""

        val bmobQuery = BmobQuery<Evaluates>()
        bmobQuery.getObject(getsynoid, object : QueryListener<Evaluates>(){
            override fun done(p0: Evaluates?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        Glide.with(this@SynopsisActivity).load(p0.photo).into(binding?.photo!!)
                        binding?.sytext?.text = p0.message
                        userPhone = p0.phone
                        if (p0.phone == getPhone){
                            binding?.follow?.visibility = View.GONE
                        }
                        val busi = BmobQuery<Business>()
                        busi.getObject(p0.businessid, object : QueryListener<Business>(){
                            override fun done(business: Business?, p11: BmobException?) {
                                if (p11 == null){
                                    if (business != null){

                                        Glide.with(this@SynopsisActivity).load(business.bavatar).into(binding?.fruitImage!!)
                                        binding?.fruitName?.text = business.title
                                        binding?.fruitProfile?.text = business.address
                                        binding?.rating?.rating = business.grade.toFloat()
                                        binding?.grade = business.grade
                                        businesId = business.objectId
                                    }
                                }else{
                                    Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                    }
                }else{
                    Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                }
            }

        })

        binding?.im?.setOnClickListener {

            if (getPhone.isEmpty()){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{

                val user = BmobQuery<PublicMyUser>()
                user.findObjects(object : FindListener<PublicMyUser>(){
                    override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                        if (p1 == null){

                            if (p0 != null){
                                p0.forEach {
                                    if (it.phone == userPhone){
                                        RongIM.getInstance().startConversation(this@SynopsisActivity, Conversation.ConversationType.PRIVATE, it.rongid , it.name)
                                    }
                                }
                            }

                        }else{
                            Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                        }
                    }

                })

            }

        }

        binding?.business?.setOnClickListener {
            var bundles = Bundle()
            bundles.putString("gopholo",getpholos)
            bundles.putString("data_id",businesId)

            val intent = Intent(this, BusinessMainActivity::class.java)
            intent.putExtras(bundles)
            startActivity(intent)

        }

        binding?.work?.setOnClickListener {
            if (getPhone.isEmpty()){
                val intent = Intent(this@SynopsisActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding?.work?.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

                val trim = binding?.work?.text?.toString()?.trim()
                if (event != null && event.action == KeyEvent.ACTION_DOWN){
                    if (TextUtils.isEmpty(trim)){
                        Toast.makeText(this@SynopsisActivity, "请输入评论", Toast.LENGTH_SHORT).show()

                        return false
                    }

                    val bmob= BmobQuery<PublicMyUser>()
                    bmob.findObjects(object : FindListener<PublicMyUser>(){
                        override fun done(p0: MutableList<PublicMyUser>?, p1: BmobException?) {
                            if (p1 == null){
                                if (p0 != null){
                                    p0.forEach {
                                        if (it.phone == getPhone){

                                            val message = Messages()
                                            message.avatar = it.avatar
                                            message.name = it.name
                                            message.myuserid = it.objectId
                                            message.message = trim
                                            message.evaluateid = getsynoid
                                            message.save(object : SaveListener<String>(){
                                                override fun done(p0: String?, p1: BmobException?) {
                                                    if (p1 == null){
                                                        Toast.makeText(this@SynopsisActivity, "评论成功", Toast.LENGTH_SHORT).show()
                                                    }else{
                                                        Toast.makeText(this@SynopsisActivity, "评论失败", Toast.LENGTH_SHORT).show()
                                                    }
                                                }

                                            })
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })

                    return false

                }
                return false
            }

        })

        val message = BmobQuery<Messages>()
        message.findObjects(object : FindListener<Messages>(){
            override fun done(p0: MutableList<Messages>?, p1: BmobException?) {
                if (p1 == null){
                    if (p0 != null){
                        p0.forEach {
                            if (it.evaluateid == getsynoid){
                                fruitLists.add(FruitsMessage(it.name, it.avatar, it.myuserid, it.message))

                                binding?.number = p0.size.toString()
                            }else{
                                binding?.number = "0"
                            }
                        }

                        val layoutManager = LinearLayoutManager(this@SynopsisActivity)
                        //线性布局
                        binding?.recyclerView?.layoutManager = layoutManager
                        //完成适配器配置
                        val adapter = FruitAdaptersMessage(fruitLists)
                        binding?.recyclerView?.adapter = adapter

                    }
                }else{
                    Toast.makeText(this@SynopsisActivity, "查询失败", Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}