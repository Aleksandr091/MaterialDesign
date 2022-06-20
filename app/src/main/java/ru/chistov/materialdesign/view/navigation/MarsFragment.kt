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
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentMarsBinding
import ru.chistov.materialdesign.viewmodel.MarsPhotos.MarsPhotosAppState
import ru.chistov.materialdesign.viewmodel.MarsPhotos.MarsPhotosViewModel


class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding: FragmentMarsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater)
        return binding.root
    }

    private val viewModel: MarsPhotosViewModel by lazy {
        ViewModelProvider(this).get(MarsPhotosViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.sendRequest()
    }
    private fun renderData(marsPhotosAppState: MarsPhotosAppState) {
        when (marsPhotosAppState) {
            is MarsPhotosAppState.Error -> {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    marsPhotosAppState.message.toString(),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            is MarsPhotosAppState.Loading -> {
                binding.appCompatImageView2.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_vector_load_error)
                }
            }
            is MarsPhotosAppState.Success -> {
                if(marsPhotosAppState.serverResponseData.photos.isEmpty()){
                    Snackbar.make(binding.root, "В этот день curiosity не сделал ни одного снимка", Snackbar.LENGTH_SHORT).show()
                }else{
                    val url = marsPhotosAppState.serverResponseData.photos.first().imgSrc
                    binding.appCompatImageView2.load(url){
                        this.placeholder(R.drawable.progress_animation)
                        crossfade(true)
                        error(R.drawable.ic_vector_load_error)
                    }
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
        fun newInstance() = MarsFragment()
    }

    


}