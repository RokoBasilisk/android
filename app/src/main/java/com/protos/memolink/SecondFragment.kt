package com.protos.memolink

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.protos.memolink.databinding.FragmentSecondBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    private var _wordHashMap : Map<String, String>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments

        if (args != null) {
            val selectedWords = args.getSerializable("selectedWords") as HashMap<*, *>
            // Cast the retrieved object to HashMap<String, String>
            val selectedWordsCasted = selectedWords as HashMap<String, String>

            _wordHashMap = selectedWords
        }

        for (i in 0 until binding.memoryContainer.childCount) {
            val childView = binding.memoryContainer.getChildAt(i)

            if (childView is EditText) {
                val drawable = childView.getBackground()
                drawable.setColorFilter(
                    resources.getColor(R.color.black),
                    PorterDuff.Mode.SRC_ATOP
                )
                childView.background = childView.getBackground()
            }
        }

        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            val editTexts = mutableListOf<EditText>()
            var keyWords = HashSet<String>()
            var childText : String
            var drawable: Drawable?

            var correctCount = 0

            for (i in 0 until binding.memoryContainer.childCount) {
                val childView = binding.memoryContainer.getChildAt(i)

                if (childView is EditText) {
                    childText = childView.text.toString()
//                    childView.isEnabled = false
                    drawable = childView.getBackground()

                    if (childView.tag.equals(childText)) {
                        // Success path
                        drawable.setColorFilter(
                            resources.getColor(R.color.successColor),
                            PorterDuff.Mode.SRC_ATOP
                        )
                        keyWords.add(childText)
                        childView.isEnabled = false
                        correctCount += 1
                    } else {
                        // Wrong or empty path
                        drawable.setColorFilter(
                            resources.getColor(R.color.errorColor),
                            PorterDuff.Mode.SRC_ATOP
                        )

                    }
                    childView.setBackground(drawable);

                }
            }

            if (correctCount == _wordHashMap!!.keys.size) {
                val snackAct = Snackbar.make(
                    binding.memoryContainer, // Anchor view for snackAct
                    "Congratulations, you just won the game", // Message to display
                    Snackbar.LENGTH_SHORT // Duration (can be LENGTH_LONG or LENGTH_INDEFINITE)
                )
                snackAct.show()
            }
        }

        // Iterate through the wordHashMap and create elements for each entry
        var previousEditTextId = binding.buttonSecond.id
        var maxLength : Int?
        val hintBuilder : StringBuilder = StringBuilder()
        for ((key, value) in _wordHashMap!!) {
            // Init elements
            val editText = EditText(context)
            val definitionButton = Button(context)
            val guideline = Guideline(context)
            maxLength = key.length
            for (i in key.indices) {
                hintBuilder.append('?')
            }

            // Setting properties for elements
            editText.id = View.generateViewId()
            editText.hint = hintBuilder // Set hint as the word (key)
            editText.tag = key
            val filter = InputFilter.LengthFilter(maxLength)
            editText.filters = arrayOf(filter)

            definitionButton.id = View.generateViewId()
            definitionButton.tag = String.format("%c%s", Character.toUpperCase(value[0]), value.substring(1))
            definitionButton.text = getString(R.string.hint_button_text)

            guideline.id = View.generateViewId() // Assign a unique ID

            definitionButton.setOnClickListener {
                val definition = definitionButton.tag as String // Cast tag to String

                val snackAct = Snackbar.make(
                    binding.memoryContainer, // Anchor view for snackAct
                    definition, // Message to display
                    Snackbar.LENGTH_SHORT // Duration (can be LENGTH_LONG or LENGTH_INDEFINITE)
                )
                snackAct.show()
            }

            // Append views into container
            binding.memoryContainer.addView(editText)
            binding.memoryContainer.addView(definitionButton)
            binding.memoryContainer.addView(guideline)

            // Init constraint
            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.memoryContainer) // Clone constraints from memoryContainer

            // constraints for editText
            constraintSet.connect(
                editText.id,
                ConstraintSet.TOP,
                previousEditTextId,
                ConstraintSet.BOTTOM,
                0
            )
            constraintSet.connect(
                editText.id,
                ConstraintSet.START,
                binding.memoryContainer.id,
                ConstraintSet.START,
                0
            )
            constraintSet.connect(
                editText.id,
                ConstraintSet.END,
                guideline.id,
                ConstraintSet.START,
                0
            )

            // constraints for definitionButton
            constraintSet.connect(
                definitionButton.id,
                ConstraintSet.TOP,
                previousEditTextId,
                ConstraintSet.BOTTOM,
                0
            )
            constraintSet.connect(
                definitionButton.id,
                ConstraintSet.START,
                guideline.id,
                ConstraintSet.END
            )
            constraintSet.connect(
                definitionButton.id,
                ConstraintSet.END,
                binding.memoryContainer.id,
                ConstraintSet.END,
                0
            )

            // Apply constraints to memoryContainer
            constraintSet.applyTo(binding.memoryContainer)

            // Update previousEditTextId for next iteration
            previousEditTextId = editText.id
            hintBuilder.clear()
        }

//        binding.memoryCard1.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
//            when {
//
//                //Check if it is the Enter-Key, Check if the Enter Key was pressed down
//                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {
//                    //perform an action here e.g. a send message button click
//
//
//                    //return true
//                    return@OnKeyListener true
//                }
//                else -> false
//            }
//
//        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}