package ru.chistov.materialdesign.view.navigation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.chistov.materialdesign.databinding.FragmentSystemBinding


class SystemFragment : Fragment() {
    var flag = false
    val duration = 1000L
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
        binding.fab.setOnClickListener {
            flag = !flag
            if (flag) {
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 0f, 405f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -50f, -250f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -20f, -150f)
                    .setDuration(duration).start()

                binding.optionOneContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = true
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionTwoContainer.isClickable = true
                        }
                    })

                binding.transparentBackground.animate()
                    .alpha(0.7f)
                    .setDuration(duration)
            } else {
                ObjectAnimator.ofFloat(binding.fab, View.ROTATION, 405f, 0f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionOneContainer, View.TRANSLATION_Y, -250f, -50f)
                    .setDuration(duration).start()
                ObjectAnimator.ofFloat(binding.optionTwoContainer, View.TRANSLATION_Y, -150f, -20f)
                    .setDuration(duration).start()

                binding.optionOneContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionOneContainer.isClickable = false
                        }
                    })
                binding.optionTwoContainer.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            binding.optionTwoContainer.isClickable = false
                        }
                    })
                binding.transparentBackground.animate()
                    .alpha(0f)
                    .setDuration(duration)

            }
        }
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