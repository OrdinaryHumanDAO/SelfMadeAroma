package com.example.selfmadearoma

import android.os.Bundle
import android.util.Log
import android.view.Gravity.CENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.selfmadearoma.databinding.FragmentAromaCreateBinding
import com.example.selfmadearoma.dialogs.ChooseFromAromaOilDialog
import com.example.selfmadearoma.utilities.dp
import com.example.selfmadearoma.viewmodels.AromaCreateViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AromaCreateFragment : Fragment(), ChooseFromAromaOilDialog.DialogListener {

    private lateinit var binding: FragmentAromaCreateBinding
    private val viewModel by viewModels<AromaCreateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAromaCreateBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.handler = this

        viewModel.aromaOilAmounts.observe(this, {
            binding.aromaOilList.removeAllViews()
            updateCardView()
        })
        redrawCardView()

        binding.fabCreate.setOnClickListener{
            viewModel.putMyAromaName(binding.etMyAromaName.text.toString())
            viewModel.insertMyAroma()
            viewModel.insertMyAromaOilInfo()

            binding.etMyAromaName.setText("")
        }

        return binding.root
    }

    private fun updateCardView() {
        for(i in 0..viewModel.cardViewNum){
            //addCardView(i)
            addLinearLayoutIncludingCvAndIv(i)
        }
    }

    private fun redrawCardView() {
        Log.d("aiueo", "${viewModel.cardViewNum}")
        if(viewModel.cardViewNum > 0) {
            for(i in 1..viewModel.cardViewNum){
                //addCardView(i)
                addLinearLayoutIncludingCvAndIv(i)
            }
        }
    }

    fun addLinearLayoutIncludingCvAndIv(cardViewId: Int){
        val linearLayout = LinearLayout(context)
        val wc = LinearLayout.LayoutParams.WRAP_CONTENT
        val p = LinearLayout.LayoutParams(wc,wc)
        linearLayout.layoutParams = p
        linearLayout.orientation = HORIZONTAL

        linearLayout.addView(addCardView(cardViewId))

        val imageButton = ImageButton(context)
        imageButton.id = cardViewId
        val params = ViewGroup.LayoutParams(20.dp, 20.dp)
        imageButton.layoutParams = params
        val mlp = ViewGroup.MarginLayoutParams(imageButton.layoutParams)
        mlp.setMargins(15.dp,45.dp,0,0)
        imageButton.layoutParams = mlp
        imageButton.setBackgroundResource(R.drawable.style_circle_button)
        imageButton.setImageResource(R.drawable.horizontal_rule)
        imageButton.setOnClickListener {
            viewModel.deleteCardView(it.id)
        }

        linearLayout.addView(imageButton)

        binding.aromaOilList.addView(linearLayout)
    }

    fun addNewLinearLayoutIncludingCvAndIv() {
        viewModel.addCardViewNum()
        addLinearLayoutIncludingCvAndIv(viewModel.cardViewNum)
    }

    fun addCardView(cardViewId: Int): CardView {

        val cardView = CardView(context!!)

        cardView.id = cardViewId

        cardView.cardElevation = 8F.dp
        cardView.radius = 10F.dp

        val params = ViewGroup.LayoutParams(300.dp, 100.dp)
        cardView.layoutParams = params

        val MLP = ViewGroup.MarginLayoutParams(cardView.layoutParams)
        MLP.setMargins(0,5.dp,0,5.dp)
        cardView.layoutParams = MLP

        cardView.addView(addLinearLayoutInCardView(cardView.id))

        cardView.setOnClickListener {
            showDialog(cardView.id)
        }

        return cardView
    }

    private fun addLinearLayoutInCardView(cardViewId: Int): LinearLayout {
        val linearLayout = LinearLayout(context)
        val mp = LinearLayout.LayoutParams.MATCH_PARENT
        val p = LinearLayout.LayoutParams(mp,mp)
        linearLayout.layoutParams = p
        linearLayout.gravity = CENTER
        linearLayout.orientation = HORIZONTAL

        val aromaOilNameText = TextView(context)
        aromaOilNameText.text = viewModel.aromaOilNames.value!![cardViewId]
        linearLayout.addView(aromaOilNameText)

        val view = View(context)
        val par = LinearLayout.LayoutParams(0.dp, 1.dp)
        par.weight = 1F
        view.layoutParams = par
        linearLayout.addView(view)

        val aromaOilAmountText = TextView(context)
        aromaOilAmountText.text = viewModel.aromaOilAmounts.value!![cardViewId]
        linearLayout.addView(aromaOilAmountText)

        return linearLayout
    }


    fun showDialog(cardViewId: Int) {
        val dialog = ChooseFromAromaOilDialog.Builder(this)
        dialog
            .setId(cardViewId)
            .build()
            .show(childFragmentManager, ChooseFromAromaOilDialog::class.simpleName)
    }

    override fun onDialogSelectedAromaReceive(dialog: DialogFragment,cardViewId: Int, text: String) {
        viewModel.editAromaOilNames(cardViewId, text)
        Log.d("aiueo", "${viewModel.aromaOilNames.value}")

    }
    override fun onDialogAromaOilAmountReceive(dialog: DialogFragment,cardViewId: Int, text: String) {
        viewModel.editAromaOilAmounts(cardViewId, "$text ml")
        Log.d("aiueo", "${viewModel.aromaOilAmounts}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("aiuoe", "破棄")
    }
}