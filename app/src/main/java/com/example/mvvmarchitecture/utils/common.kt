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
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.constant.EndpointConstants
import com.example.mvvmarchitecture.model.ExpenseItemDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.example.mvvmarchitecture.model.WMSCoreAuthentication
import com.example.mvvmarchitecture.model.WMSCoreMessage
import com.google.android.material.card.MaterialCardView

class common {
    public fun buildReceiveRequest(data: ReceivedItemDTO): WMSCoreMessage {
        val message = WMSCoreMessage()
        val token = WMSCoreAuthentication()

        token.authKey = "device_serial"
        token.userId = "1"
        token.loginTimeStamp = System.currentTimeMillis().toString()

/*
        val receivedItemDto = ReceivedItemDTO()
        receivedItemDto.ReceiveId = data.ReceiveId
        receivedItemDto.itemId = data.itemId
        receivedItemDto.name = data.name
        receivedItemDto.requiredQty = data.requiredQty
        receivedItemDto.availableQty = data.availableQty
        receivedItemDto.unit = data.unit
        receivedItemDto.time = data.time
        receivedItemDto.category = data.category
        receivedItemDto.priority = data.priority
        receivedItemDto.receiveQty = data.receiveQty
        receivedItemDto.totalCost = data.totalCost
        receivedItemDto.isUpdate = data.isUpdate

*/

        message.type = EndpointConstants.ItemDTO
        message.authToken = token
        message.entityObject = data

        return message
    }
    public fun buildExpenseRequest(data: ExpenseItemDTO): WMSCoreMessage {
        val message = WMSCoreMessage()
        val token = WMSCoreAuthentication()

        token.authKey = "device_serial"
        token.userId = "1"
        token.loginTimeStamp = System.currentTimeMillis().toString()

        val expenseItemDTO = ExpenseItemDTO()
        expenseItemDTO.expenseId = data.expenseId
        expenseItemDTO.itemId = data.itemId
        expenseItemDTO.ItemName = data.ItemName
        expenseItemDTO.availableQty = data.availableQty
        expenseItemDTO.unit = data.unit
        expenseItemDTO.expenseTime = data.expenseTime
        expenseItemDTO.category = data.category
        expenseItemDTO.createdBy = data.createdBy


        message.type = EndpointConstants.ExpenseDTO
        message.authToken = token
        message.entityObject = data

        return message
    }

    public fun confirmationAlertDialog(context : Context, type : String, message: String, onConfirm: () -> Unit) {
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
        val tvTitle = dialog.findViewById<TextView>(R.id.tvTitle)
        val tvMessage = dialog.findViewById<TextView>(R.id.tvMessage)
        tvMessage.text = message
        tvTitle.text = type
        btnConfirm.text = "Delete"


        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            onConfirm()
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