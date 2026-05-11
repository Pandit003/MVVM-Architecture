package com.example.mvvmarchitecture.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.model.ItemDTO
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial

class AddItemBottomSheet(
    private val existingItem: ItemDTO? = null,
    private val onSave: (ItemDTO) -> Unit
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottom_sheet_add, container, false)
        val units = listOf("kg", "liter", "pieces", "packets", "boxes");
        val categories = listOf(
            "Raw Materials",
            "Tools & Equipment",
            "Electrical & Plumbing",
            "Finishing Materials",
            "Consumables & Supplies",
            "Miscellaneous"
        );
        val etName = view.findViewById<AutoCompleteTextView>(R.id.etName)
        val etQty = view.findViewById<EditText>(R.id.etQuantity)
        val spinnerUnit = view.findViewById<Spinner>(R.id.spinnerUnit)
        val spinnerCategory = view.findViewById<Spinner>(R.id.spinnerCategory)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroupPriority)
        val switchReceived = view.findViewById<SwitchMaterial>(R.id.switchReceived)
        val layoutReceived = view.findViewById<View>(R.id.layoutReceived)
        val seekBar = view.findViewById<SeekBar>(R.id.seekReceived)
        val tvReceived = view.findViewById<TextView>(R.id.tvReceivedValue)
        val etCost = view.findViewById<EditText>(R.id.etCost)
        val btnSave = view.findViewById<Button>(R.id.btnSave)
        val progress = view.findViewById<ProgressBar>(R.id.progress)

        val unitadapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            units
        )

        unitadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerUnit.adapter = unitadapter

        val catadapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )

        catadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCategory.adapter = catadapter

        // ---- Prefill (Edit mode) ----
        existingItem?.let {
            etName.setText(it.name)
            etQty.setText(it.quantity.toString())
            etCost.setText(it.cost.toString())

            switchReceived.isChecked = it.receiveQty!! > 0
            layoutReceived.visibility = if (it.receiveQty > 0) View.VISIBLE else View.GONE

            seekBar.progress = it.receiveQty.toInt()
            tvReceived.text = it.receiveQty.toString()
        }

        // ---- Toggle Received Section ----
        switchReceived.setOnCheckedChangeListener { _, isChecked ->
            layoutReceived.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        // ---- SeekBar Logic ----
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progressValue: Int, fromUser: Boolean) {
                tvReceived.text = progressValue.toString()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // ---- Save Button ----
        btnSave.setOnClickListener {

            val name = etName.text.toString().trim()
            val qty = etQty.text.toString().toDoubleOrNull()
            val cost = etCost.text.toString().toDoubleOrNull() ?: 0.0
            val receivedQty = if (switchReceived.isChecked) seekBar.progress.toDouble() else 0.0

            if (name.isEmpty() || qty == null || qty <= 0) {
                etQty.error = "Enter valid quantity"
                return@setOnClickListener
            }

            progress.visibility = View.VISIBLE
            btnSave.isEnabled = false

            val selectedPriorityId = radioGroup.checkedRadioButtonId
            val selectedPriority = view.findViewById<RadioButton>(selectedPriorityId)?.text?.toString() ?: "Low"

            val data = ItemDTO(
                name = name,
                quantity = qty,
                unit = spinnerUnit.selectedItem.toString(),
                category = spinnerCategory.selectedItem.toString(),
                priority = selectedPriority,
                receiveQty = receivedQty,
                cost = cost
            )

            onSave(data)

            dismiss()
        }

        return view
    }
}