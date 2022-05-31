package ru.chistov.materialdesign.view.pictures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentPicturesOfTheDayBinding
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDayAppState
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDayViewModel


class PicturesOfTheDayFragment : Fragment() {

    private var _binding:FragmentPicturesOfTheDayBinding?=null
    private val binding:FragmentPicturesOfTheDayBinding
    get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPicturesOfTheDayBinding.inflate(inflater,container,false)
        return binding.root
    }
    private val viewModel:PicturesOfTheDayViewModel by lazy{
        ViewModelProvider(this).get(PicturesOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest()
    }

    private fun renderData(picturesOfTheDayAppState: PicturesOfTheDayAppState){
        when(picturesOfTheDayAppState){
            is PicturesOfTheDayAppState.Error -> {}
            is PicturesOfTheDayAppState.Loading -> {}
            is PicturesOfTheDayAppState.Success -> {
                binding.imageView.load(picturesOfTheDayAppState.pictureOfTheDayResponseData.url)
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = PicturesOfTheDayFragment()
    }
}