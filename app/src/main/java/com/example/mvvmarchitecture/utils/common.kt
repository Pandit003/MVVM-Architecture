package com.example.mvvmarchitecture.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.ItemDTO
import com.example.mvvmarchitecture.model.LoginUserDTO
import com.example.mvvmarchitecture.model.WMSCoreAuthentication
import com.example.mvvmarchitecture.model.WMSCoreMessage
import com.google.android.material.card.MaterialCardView

class common {
    public fun buildLoginRequest(data: ItemDTO): WMSCoreMessage {
        val message = WMSCoreMessage()
        val token = WMSCoreAuthentication()

        token.authKey = "device_serial"
        token.userId = "1"
        token.loginTimeStamp = System.currentTimeMillis().toString()

        val itemDto = ItemDTO()
        itemDto.id = data.id
        itemDto.name = data.name
        itemDto.requiredQty = data.requiredQty
        itemDto.availableQty = data.availableQty
        itemDto.unit = data.unit
        itemDto.time = data.time
        itemDto.category = data.category
        itemDto.priority = data.priority
        itemDto.receiveQty = data.receiveQty
        itemDto.totalCost = data.totalCost
        itemDto.isUpdate = data.isUpdate


        message.type = EndpointConstants.ItemDTO
        message.authToken = token
        message.entityObject = itemDto

        return message
    }

    public fun confirmationAlertDialog(context : Context,type : String, message: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.alert_dialog)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnClose = dialog.findViewById<View>(R.id.btnClose)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {

            // delete action

            dialog.dismiss()
        }

        dialog.show()
    }
    public fun alertDialog(context : Context,type : String, message: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.alert_dialog)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val btnClose = dialog.findViewById<View>(R.id.btnClose)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm)
        val ivIcon = dialog.findViewById<ImageView>(R.id.ivIcon)
        val cv_iconBackground = dialog.findViewById<MaterialCardView>(R.id.cv_iconBackground)
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        tvTitle.text = type
        tvMessage.text = message
        when (type) {
            "Error" -> {
                ivIcon.setImageResource(R.drawable.cross_circle)
                cv_iconBackground.setCardBackgroundColor(Color.parseColor("#FEE2E2"))  // Light red
            }
            "Success" -> {
                ivIcon.setImageResource(R.drawable.success)
                cv_iconBackground.setCardBackgroundColor(Color.parseColor("#DBEAFE"))  // Light green
            }
            else -> {
                ivIcon.setImageResource(R.drawable.warning_img)
                cv_iconBackground.setCardBackgroundColor(Color.parseColor("#EEF2FF"))  // Light blue (default)
            }
        }
        btnConfirm.text = "OK"
        btnCancel.visibility = View.INVISIBLE
        btnClose.visibility = View.INVISIBLE

        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}