package ru.chistov.materialdesign.view.pictures

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
        //initBottomSheetBehavior()
        //initMenu()
        //fabListener()
        tabListener(date)
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

    /*private fun fabListener() {
        binding.fab.setOnClickListener {
            if (isMain) {
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_about)
            } else {
                binding.bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_hamburger_menu_bottom_bar
                )
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
            isMain = !isMain
        }
    }*/

    /*private fun initBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.lifeHack.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }*/

    private fun setClickListenerTextInputLayout() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    /*private fun initMenu() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }*/


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
            }
        }
    }


    private fun setData(data: PicturesOfTheDayAppState.Success) {
        val url = data.pictureOfTheDayResponseData.hdurl
        if (url.isNullOrEmpty()) {
            val videoUrl = data.pictureOfTheDayResponseData.url
            videoUrl?.let { showAVideoUrl(it) }
        } else {
            binding.imageView.load(data.pictureOfTheDayResponseData.hdurl) {
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