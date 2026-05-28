package com.example.mvvmarchitecture.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.model.ExpenseItemDTO
import com.example.mvvmarchitecture.model.ReceivedItemDTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout

class AddExpenseBottomSheet(
    private val availableItems: List<ExpenseItemDTO>,
    private val existingItem: ExpenseItemDTO,
    private val onSave: (ExpenseItemDTO) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var autoCompleteName: AutoCompleteTextView
    private lateinit var etQuantity: EditText
    private lateinit var etUnit: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDeleteExpense: Button
    private lateinit var btnClose: ImageView
    private lateinit var tilQuantity: TextInputLayout
    private lateinit var tvTitle: TextView

    private var selectedItem: ExpenseItemDTO? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autoCompleteName = view.findViewById(R.id.autoCompleteName)
        etQuantity = view.findViewById(R.id.etQuantity)
        etUnit = view.findViewById(R.id.etUnit)
        etDescription = view.findViewById(R.id.etDescription)
        btnSave = view.findViewById(R.id.btnSaveExpense)
        btnClose = view.findViewById(R.id.btnClose)
        tilQuantity = view.findViewById(R.id.tilQuantity)
        btnDeleteExpense = view.findViewById(R.id.btnDeleteExpense)
        tvTitle = view.findViewById(R.id.tvTitle)

        val itemNames = availableItems.mapNotNull { it.ItemName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, itemNames)
        autoCompleteName.setAdapter(adapter)

        if(existingItem.ItemName != null){
            btnDeleteExpense.setVisibility(View.VISIBLE)
            tvTitle.setText("Edit Expense")
            btnSave.setText("Update")
            autoCompleteName.setText(existingItem.ItemName)
            autoCompleteName.isEnabled = false
            etQuantity.setText(existingItem.expenseQty.toString())
            etUnit.setText(existingItem.unit)
            etDescription.setText(existingItem.description)
            selectedItem = existingItem
            tilQuantity.suffixText = "/"+selectedItem?.availableQty.toString()
        }
        autoCompleteName.setOnItemClickListener { parent, _, position, _ ->
            val name = parent.getItemAtPosition(position) as String
            selectedItem = availableItems.find { it.ItemName == name }
            etUnit.setText(selectedItem?.unit ?: "")
            tilQuantity.suffixText = "/"+selectedItem?.availableQty.toString()
        }
        btnClose.setOnClickListener {
            dismiss()
        }
        btnSave.setOnClickListener {
            val name = autoCompleteName.text.toString()
            val quantity = etQuantity.text.toString().toDoubleOrNull() ?: 0.0
            val description = etDescription.text.toString()

            if (name.isEmpty()) {
                autoCompleteName.setError("Please enter item name")
            }else if(quantity == 0.0){
                etQuantity.setError("Please enter quantity")
            }else if(quantity > selectedItem?.availableQty!!){
                etQuantity.setError("Please enter below available quantity")
            }else{
                if(btnSave.text.toString().equals("Update")){
                    selectedItem?.isUpdate = "1"
                }else{
                    selectedItem?.isUpdate = "0"
                }
                val expense = ExpenseItemDTO(
                    expenseId = selectedItem?.expenseId,
                    itemId = selectedItem?.itemId,
                    ItemName = name,
                    expenseQty = quantity,
                    availableQty = availableItems.find { it.ItemName == name }?.availableQty,
                    unit = etUnit.text.toString(),
                    description = description,
                    category = selectedItem?.category,
                    isUpdate = selectedItem?.isUpdate
                )
                onSave(expense)
                dismiss()
            }
        }
        btnDeleteExpense.setOnClickListener {
            val name = autoCompleteName.text.toString()
            val quantity = etQuantity.text.toString().toDoubleOrNull() ?: 0.0
            val description = etDescription.text.toString()

            if(quantity == 0.0){
                etQuantity.setError("Please enter quantity")
            }else if(quantity > selectedItem?.availableQty!!){
                etQuantity.setError("Please enter below available quantity")
            }else{
                val expense = ExpenseItemDTO(
                    expenseId = selectedItem?.expenseId,
                    itemId = selectedItem?.itemId,
                    ItemName = name,
                    expenseQty = quantity,
                    availableQty = selectedItem?.availableQty,
                    unit = etUnit.text.toString(),
                    description = description,
                    category = selectedItem?.category,
                    isUpdate = "2"
                )
                onSave(expense)
                dismiss()
            }
        }
    }
}
