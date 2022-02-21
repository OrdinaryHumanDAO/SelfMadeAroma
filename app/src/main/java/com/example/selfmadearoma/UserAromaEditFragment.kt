package com.example.selfmadearoma

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.selfmadearoma.databinding.FragmentUserAromaEditBinding
import com.example.selfmadearoma.dialogs.ChooseFromAromaOilDialog
import com.example.selfmadearoma.utilities.dp
import com.example.selfmadearoma.viewmodels.UserAromaEditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAromaEditFragment : Fragment(), ChooseFromAromaOilDialog.DialogListener {
    private val args: UserAromaEditFragmentArgs by navArgs()

    private lateinit var binding: FragmentUserAromaEditBinding
    private val viewModel by viewModels<UserAromaEditViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserAromaEditBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.handler = this
        viewModel.getMyAroma(args.myAromaId)

        viewModel.myAromaInfo.observe(this, {
            binding.aromaOilList.removeAllViews()
            viewModel.initialCardViewNum()
            setCardView()
            setEdText()
            if(viewModel.ifFirstFlag) {
                viewModel.setFirstAromaOilInfoSize()
            }
        })

        binding.fabEdit.setOnClickListener {
            if(viewModel.getMyAromaOilInfoSize() != 0) {
                viewModel.updateMyAroma()
                viewModel.updateMyAromaOilInfo()
            } else {
                Toast.makeText(context, "アロマオイルを選んでください", Toast.LENGTH_SHORT).show()
            }

        }

        viewModel.navigatePopBackFlag.observe(this, {
            if(it) { findNavController().popBackStack() }
        })


        return binding.root
    }

    fun setEdText() {
        binding.etMyAromaName.setText(viewModel.getMyAromaName())
    }

    fun setCardView(){
        for(i in viewModel.getMyAromaOilInfoIndices()) {
            //addCardView(i)
            addLinearLayoutIncludingCvAndIv(i)
        }
    }


    fun addLinearLayoutIncludingCvAndIv(cardViewId: Int){
        val linearLayout = LinearLayout(context)
        val wc = LinearLayout.LayoutParams.WRAP_CONTENT
        val p = LinearLayout.LayoutParams(wc,wc)
        linearLayout.layoutParams = p
        linearLayout.orientation = LinearLayout.HORIZONTAL

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
        viewModel.addMyAromaInfoDate()
        addLinearLayoutIncludingCvAndIv(viewModel.cardViewNum)
    }


    fun addNewCardView() {
        viewModel.addMyAromaInfoDate()
        addCardView(viewModel.cardViewNum)
    }

    fun addCardView(cardViewId: Int): CardView {
        viewModel.addCardViewNum()

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
        linearLayout.gravity = Gravity.CENTER
        linearLayout.orientation = LinearLayout.HORIZONTAL

        val aromaOilNameText = TextView(context)
        aromaOilNameText.text = viewModel.getMyAromaOilName(cardViewId)
        linearLayout.addView(aromaOilNameText)

        val view = View(context)
        val par = LinearLayout.LayoutParams(0.dp, 1.dp)
        par.weight = 1F
        view.layoutParams = par
        linearLayout.addView(view)

        val aromaOilAmountText = TextView(context)
        aromaOilAmountText.text = viewModel.getMyAromaOilAmount(cardViewId)
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

    override fun onDialogSelectedAromaReceive(dialog: DialogFragment,cardViewId: Int,text: String) {
        viewModel.editMyAromaOilInfoName(cardViewId, text)
    }

    override fun onDialogAromaOilAmountReceive(dialog: DialogFragment,cardViewId: Int,text: String) {
        viewModel.editMyAromaOilInfoAmount(cardViewId, text)
    }
}