package com.example.newpubliccomments

import android.annotation.SuppressLint
import android.content.Intent
import android.database.DatabaseUtils
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.QueryListener
import cn.bmob.v3.listener.SaveListener
import com.bumptech.glide.Glide
import com.example.newpubliccomments.NewMessage.Messages
import com.example.newpubliccomments.business.Business
import com.example.newpubliccomments.databinding.ActivitySynopsisBinding
import com.example.newpubliccomments.location.SearchLocation
import com.example.newpubliccomments.share.Evaluates
import com.example.newpubliccomments.signregister.PublicMyUser
import com.example.newpubliccomments.tool.StatusBar
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

        binding?.DelBacksyno?.setOnClickListener {
            onBackPressed()
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