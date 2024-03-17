package com.protos.memolink

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.protos.memolink.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private var _wordHashMap = mapOf(
        "start" to "begin",
        "happy" to "feeling of joy and contentment",
        "learn" to "to gain knowledge or skill",
        "memory" to "the faculty of remembering things",
        "world" to "the earth and all its inhabitants",
        "explore" to "to travel and discover new places",
        "connect" to "to form a link or relationship between things",
        "confident" to "having a strong belief in yourself and your abilities",
        "achieve" to "to succeed in doing something",
        "expand" to "to make something larger or wider",
        "opportunity" to "a chance to do something",
        "unlock" to "to open something that is locked",
        "horizon" to "the line where the sky and the earth seem to meet",
        "fluent" to "able to express oneself easily and smoothly",
        "master" to "to have a complete understanding or control of something"
    )

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val selectedWords = HashMap<String, String>() // Create a new HashMap

            // Select 5 word-definition pairs (modify as needed)
            selectedWords["start"] = _wordHashMap["start"]!!
            selectedWords["happy"] = _wordHashMap["happy"]!!
            selectedWords["learn"] = _wordHashMap["learn"]!!
            selectedWords["memory"] = _wordHashMap["memory"]!!
            selectedWords["world"] = _wordHashMap["world"]!!

            bindDataForFragment(R.id.action_FirstFragment_to_SecondFragment, selectedWords)
        }

        binding.buttonSecond.setOnClickListener {
            val selectedWords = HashMap<String, String>() // Create a new HashMap

            // Select 5 word-definition pairs (modify as needed)
            selectedWords["explore"] = _wordHashMap["explore"]!!
            selectedWords["connect"] = _wordHashMap["connect"]!!
            selectedWords["confident"] = _wordHashMap["confident"]!!
            selectedWords["achieve"] = _wordHashMap["achieve"]!!
            selectedWords["expand"] = _wordHashMap["expand"]!!

            bindDataForFragment(R.id.action_FirstFragment_to_SecondFragment, selectedWords)
        }

        binding.buttonThird.setOnClickListener {
            val selectedWords = HashMap<String, String>() // Create a new HashMap

            // Select 5 word-definition pairs (modify as needed)
            selectedWords["opportunity"] = _wordHashMap["opportunity"]!!
            selectedWords["unlock"] = _wordHashMap["unlock"]!!
            selectedWords["horizon"] = _wordHashMap["horizon"]!!
            selectedWords["fluent"] = _wordHashMap["fluent"]!!
            selectedWords["master"] = _wordHashMap["master"]!!

            bindDataForFragment(R.id.action_FirstFragment_to_SecondFragment, selectedWords)
        }
    }

    private fun bindDataForFragment(screenId : Int, hashMap: HashMap<String, String>) {
        val args = Bundle()
        args.putSerializable("selectedWords", hashMap) // Put the HashMap in the Bundle

        findNavController().navigate(screenId, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}