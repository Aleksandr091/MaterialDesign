package ru.chistov.materialdesign.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.chistov.materialdesign.databinding.FragmentSystemBinding


class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private val binding: FragmentSystemBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = SystemFragment()
    }




}