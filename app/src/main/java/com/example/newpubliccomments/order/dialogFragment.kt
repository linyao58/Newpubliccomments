package com.example.newpubliccomments.order

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.example.newpubliccomments.databinding.DialogFragmentBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.StringUtils
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.android.synthetic.main.dialog_fragment.*
import java.io.UnsupportedEncodingException
import java.util.*


class dialogFragment: DialogFragment() {

    private var binding: DialogFragmentBinding? = null

    var hashMap = HashMap<EncodeHintType?, Any?>()

    companion object{
        fun newInstance(pholo: String, name: String, avatar: String): dialogFragment{
            val args = Bundle()
            val fragment = dialogFragment()
            args.putString("pholo", pholo)
            args.putString("name", name)
            args.putString("avatar", avatar)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogFragmentBinding.inflate(inflater, container, false)

        val pholo = arguments?.getString("pholo")
        val name = arguments?.getString("name")
        val avatar = arguments?.getString("avatar")

        create_QR_code(pholo!!, name!!, avatar!!)

        return binding?.root
    }

     fun create_QR_code(pholo: String, name: String, avatar: String){

        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L)
        hashMap.put(EncodeHintType.CHARACTER_SET, "utf-8")
        hashMap.put(EncodeHintType.MARGIN, 2)

        var bitMatrix: BitMatrix? = null
        try {
            bitMatrix =
                MultiFormatWriter().encode("${avatar},${name},${pholo}", BarcodeFormat.QR_CODE, 500, 500, hashMap)
        }catch (e: WriterException){
            e.printStackTrace()
        }

        val width = bitMatrix?.width
        val height = bitMatrix?.height
        val pixels = IntArray(width!! * height!!)
        for (i in 0 until width){
            for (j in 0 until height){
                if (bitMatrix?.get(i, j)!!){
                    pixels[i * width + j] = Color.BLACK
                }else{
                    pixels[i * width + j] = Color.WHITE
                }
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        binding?.img?.setImageBitmap(bitmap)

    }



}