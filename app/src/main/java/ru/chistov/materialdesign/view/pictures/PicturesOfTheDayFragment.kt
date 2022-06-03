package ru.chistov.materialdesign.view.pictures

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.Placeholder
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import ru.chistov.materialdesign.R
import ru.chistov.materialdesign.databinding.FragmentPicturesOfTheDayBinding
import ru.chistov.materialdesign.view.MainActivity
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDayAppState
import ru.chistov.materialdesign.viewmodel.PicturesOfTheDayViewModel
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
            R.id.app_bar_settings -> {}
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
        initBottomSheetBehavior()
        initMenu()

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
        binding.chipGroup.setOnCheckedChangeListener { _, position ->

            val dateYT = dateFormatted.format(System.currentTimeMillis() - 86400000)// сутки=86400000 м.с
            val dateTDBY = dateFormatted.format(System.currentTimeMillis() - 86400000 * 2)

            when (position) {
                1 -> {
                    viewModel.sendRequest(date)
                }
                2 -> {
                    viewModel.sendRequest(dateYT)
                }
                3 -> {
                    viewModel.sendRequest(dateTDBY)
                }
            }
        }
    }

    private fun initBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.lifeHack.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
    }

    private fun setClickListenerTextInputLayout() {
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedia.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
    }

    private fun initMenu() {
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)
        setHasOptionsMenu(true)
    }




    private fun renderData(picturesOfTheDayAppState: PicturesOfTheDayAppState) {
        when (picturesOfTheDayAppState) {
            is PicturesOfTheDayAppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar.make(
                    requireContext(),
                    binding.root,
                    picturesOfTheDayAppState.message,
                    Snackbar.LENGTH_LONG
                ).show()

            }
            is PicturesOfTheDayAppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is PicturesOfTheDayAppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                binding.imageView.load(picturesOfTheDayAppState.pictureOfTheDayResponseData.hdurl) { this.placeholder(R.drawable.kos) }
                binding.lifeHack.title.text =
                    picturesOfTheDayAppState.pictureOfTheDayResponseData.title
                binding.lifeHack.explanation.text =
                    picturesOfTheDayAppState.pictureOfTheDayResponseData.explanation
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