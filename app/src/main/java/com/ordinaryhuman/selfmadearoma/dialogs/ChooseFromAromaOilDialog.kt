package com.ordinaryhuman.selfmadearoma.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color.RED
import android.graphics.Color.WHITE
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ordinaryhuman.selfmadearoma.R
import com.ordinaryhuman.selfmadearoma.data.AppDatabase
import com.ordinaryhuman.selfmadearoma.data.aromaOil.AromaOilRepository
import com.ordinaryhuman.selfmadearoma.viewmodels.AromaCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class ChooseFromAromaOilDialog: DialogFragment() {

    private var listener: DialogListener? = null
    interface DialogListener{
        fun onDialogSelectedAromaReceive(dialog: DialogFragment, cardViewId: Int, text: String)
        //Activity側へStringを渡します。
        fun onDialogAromaOilAmountReceive(dialog: DialogFragment, cardViewId: Int, text: String)
    }


    companion object {
        private const val ID_KEY = "IdKey"
    }

    class Builder(private val fragment: Fragment) {
        private val bundle = Bundle()

        fun setId(id: Int): Builder {
            return this.apply {
                bundle.putInt(ID_KEY, id)
            }
        }

        fun build(): ChooseFromAromaOilDialog {
            return ChooseFromAromaOilDialog().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var cardViewId = 0
        arguments?.let {
            cardViewId = it.getInt(ID_KEY, 0)

        }

        val builder = AlertDialog.Builder(requireContext())

        val dao = AppDatabase.getInstance(context!!).aromaOilDao()
        val repository = AromaOilRepository(dao)

        val dialogView = requireActivity()
            .layoutInflater
            .inflate(R.layout.dialog_choose_from_aroma_oil, null)!!
        val listView: ListView = dialogView.findViewById(R.id.listView)
        val searchView: SearchView = dialogView.findViewById(R.id.searchView)
        val editText: EditText = dialogView.findViewById(R.id.etAromaOilAmount)

        //リストアダプターにDBのデータを入れてる
        lifecycleScope.launch(Dispatchers.IO){
            val aromaOilNames = repository.getAromaOils().map { it.aromaOilName }

            Handler(Looper.getMainLooper()).post {
                listView.adapter = ArrayAdapter(
                    context!!, android.R.layout.simple_list_item_1, aromaOilNames
                )
            }
        }

        //サーチビューに変更があった時の処理
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch(Dispatchers.IO) {
                    val searchResults = repository.findByAromaOilNameLike(newText!!).map { it.aromaOilName }

                    Handler(Looper.getMainLooper()).post {
                        listView.adapter = ArrayAdapter(
                            context!!, android.R.layout.simple_list_item_1, searchResults
                        )
                    }
                }

                return true
            }
        })

        var selectedAroma = ""
        var selectedView: View? = null
        listView.setOnItemClickListener { parent, view, position,_ ->
            selectedView?.setBackgroundColor(WHITE)
            selectedAroma = parent.getItemAtPosition(position).toString()
            view.setBackgroundColor(resources.getColor(R.color.lime_500))
            selectedView = view
        }

        builder
            .setView(dialogView)
            .setTitle("アロマオイルを選択")
            .setPositiveButton("OK"){ _,_ ->
                listener?.onDialogSelectedAromaReceive(this, cardViewId, selectedAroma)
                listener?.onDialogAromaOilAmountReceive(this, cardViewId, "${editText.text}")
                dismiss()
            }
            .setNegativeButton("CANCEL"){ _,_ ->
                dismiss()
            }

        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            when {
                context is DialogListener -> listener = context
                parentFragment is DialogListener -> listener = parentFragment as DialogListener
            }
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement DialogListener"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
    }

}