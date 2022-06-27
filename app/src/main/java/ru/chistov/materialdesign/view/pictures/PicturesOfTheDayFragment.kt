package ru.chistov.materialdesign.view.pictures

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentPicturesOfTheDayBinding


import ru.chistov.materialdesign.view.settings.SettingsFragment
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDay.PicturesOfTheDayAppState
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDay.PicturesOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*


class PicturesOfTheDayFragment : Fragment() {

    var isMain = true
    var isOpen = false
    var isClick = false
    private val dateFormatted = SimpleDateFormat("yyyy-MM-dd")


    private var _binding: FragmentPicturesOfTheDayBinding? = null
    private val binding: FragmentPicturesOfTheDayBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPicturesOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: PicturesOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PicturesOfTheDayViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> {}
            R.id.app_bar_settings -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance()).addToBackStack("")
                    .commit()
            }
            android.R.id.home -> {
                BottomNavigationDrawerFragment.newInstance()
                    .show(requireActivity().supportFragmentManager, "")
            }

        }
        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        val date = dateFormatted.format(Calendar.getInstance().time)
        viewModel.sendRequest(date)
        setClickListenerTextInputLayout()

        tabListener(date)
        wListener()
    }

    private fun wListener() {
        binding.imageW.setOnClickListener {

            val constraintSet = ConstraintSet()
            constraintSet.clone(binding.constraintLayout)
            val transitionFade = Fade().apply { duration = 1500 }
            val transitionChangeBounds = ChangeBounds().apply { duration = 1500 }
            val transitionSet = TransitionSet()
            transitionSet.addTransition(transitionFade)
            transitionSet.addTransition(transitionChangeBounds)
            transitionChangeBounds.interpolator = AnticipateOvershootInterpolator(2f)
            TransitionManager.beginDelayedTransition(binding.constraintLayout, transitionSet)
            //TransitionManager.beginDelayedTransition(binding.nestedScrollView, transitionChangeBounds)//не работает

            isClick = !isClick
            if (isClick) {
                constraintSet.connect(
                    R.id.tabLayout,
                    ConstraintSet.TOP,
                    R.id.inputLayout,
                    ConstraintSet.BOTTOM
                )
                //binding.nestedScrollView.translationY = binding.appBar.height.toFloat() + binding.inputLayout.height.toFloat() - binding.imageW.height.toFloat() //не работает
                ObjectAnimator.ofFloat(binding.nestedScrollView, View.TRANSLATION_Y, binding.appBar.height.toFloat() + binding.inputLayout.height-binding.imageW.height).apply {
                    duration = 1500
                    interpolator = AnticipateOvershootInterpolator(2f)
                }.start()
            }
            constraintSet.applyTo(binding.constraintLayout)


            binding.inputLayout.visibility =
                if (isClick) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            binding.imageW.visibility =
                if (isClick) {
                    View.GONE
                } else {
                    View.VISIBLE
                }


        }
    }

    private fun tabListener(date: String) {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val dateYT =
                    dateFormatted.format(System.currentTimeMillis() - 86400000)// сутки=86400000 м.с
                val dateTDBY = dateFormatted.format(System.currentTimeMillis() - 86400000 * 2)
                when (tab?.position) {

                    0 -> {
                        binding.imageView.visibility = View.VISIBLE
                        binding.videoOfTheDay.visibility = View.GONE
                        viewModel.sendRequest(date)
                    }
                    1 -> {
                        binding.imageView.visibility = View.VISIBLE
                        binding.videoOfTheDay.visibility = View.GONE
                        viewModel.sendRequest(dateYT)
                    }
                    2 -> {
                        binding.imageView.visibility = View.VISIBLE
                        binding.videoOfTheDay.visibility = View.GONE
                        viewModel.sendRequest(dateTDBY)
                    }

                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }



    private fun setClickListenerTextInputLayout() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }



    private fun renderData(picturesOfTheDayAppState: PicturesOfTheDayAppState) {
        when (picturesOfTheDayAppState) {
            is PicturesOfTheDayAppState.Error -> {
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    picturesOfTheDayAppState.message,
                    Snackbar.LENGTH_LONG
                ).show()

            }
            is PicturesOfTheDayAppState.Loading -> {
                binding.imageView.load(R.drawable.progress_animation) {
                    error(R.drawable.ic_vector_load_error)
                }
            }
            is PicturesOfTheDayAppState.Success -> {
                setData(picturesOfTheDayAppState)
                imageScaleTypeListener()
            }
        }
    }

    private fun imageScaleTypeListener() {
        binding.imageView.setOnClickListener {
            isOpen = !isOpen
            val transitionCB = ChangeBounds()
            val transitionImage = ChangeImageTransform()
            val transitionSet = TransitionSet().apply {
                addTransition(transitionCB)
                addTransition(transitionImage)
            }
            TransitionManager.beginDelayedTransition(binding.root, transitionSet)
            binding.imageView.scaleType =
                if (isOpen) {
                    ImageView.ScaleType.CENTER_CROP
                } else {
                    ImageView.ScaleType.CENTER_INSIDE
                }

            val params = (binding.imageView.layoutParams as FrameLayout.LayoutParams)
            params.height = if (isOpen) {
                FrameLayout.LayoutParams.MATCH_PARENT
            } else {
                FrameLayout.LayoutParams.WRAP_CONTENT
            }
            binding.imageView.layoutParams = params
        }
    }


    private fun setData(data: PicturesOfTheDayAppState.Success) {
        val url = data.pictureOfTheDayResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.pictureOfTheDayResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            binding.imageView.load(data.pictureOfTheDayResponseData.url) {
                this.placeholder(R.drawable.progress_animation)
                crossfade(true)
                error(R.drawable.ic_vector_load_error)
            }
            binding.title.text =
                data.pictureOfTheDayResponseData.title
            binding.explanation.text =
                data.pictureOfTheDayResponseData.explanation
        }
    }

    private fun showAVideoUrl(videoUrl: String) = with(binding) {
        imageView.visibility = View.GONE
        videoOfTheDay.visibility = View.VISIBLE
        videoOfTheDay.text = "Сегодня у нас без картинки дня, но есть  видео дня! " +
                "${videoUrl.toString()} \n кликни >ЗДЕСЬ< чтобы открыть в новом окне"
        videoOfTheDay.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(videoUrl)
            }
            startActivity(i)
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