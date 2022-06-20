package ru.chistov.materialdesign.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.snackbar.Snackbar
import ru.chistov.materialdesign.BuildConfig
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentEarthBinding
import ru.chistov.materialdesign.viewmodel.EarthEpic.EarthEpicAppState
import ru.chistov.materialdesign.viewmodel.EarthEpic.EarthEpicViewModel



class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding: FragmentEarthBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthBinding.inflate(inflater)
        return binding.root
    }

    private val viewModel: EarthEpicViewModel by lazy {
        ViewModelProvider(this).get(EarthEpicViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest()
    }

    private fun renderData(earthEpicAppState: EarthEpicAppState) {
        when (earthEpicAppState) {
            is EarthEpicAppState.Error -> {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    earthEpicAppState.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is EarthEpicAppState.Loading -> {
                binding.appCompatImageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_vector_load_error)
                }
            }
            is EarthEpicAppState.Success -> {
                val strDate = earthEpicAppState.serverResponseData.last().date.split(" ").first()
                val image =earthEpicAppState.serverResponseData.last().image
                val url = "https://api.nasa.gov/EPIC/archive/natural/" +
                        strDate.replace("-","/",true) +
                        "/png/" +
                        "$image" +
                        ".png?api_key=${BuildConfig.NASA_API_KEY}"
                binding.appCompatImageView.load(url) {
                    this.placeholder(R.drawable.progress_animation)
                    crossfade(true)
                    error(R.drawable.ic_vector_load_error)
                }

            }
        }
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {

        @JvmStatic
        fun newInstance() = EarthFragment()
    }


}