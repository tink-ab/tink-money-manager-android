package com.tink.pfmui

import androidx.fragment.app.FragmentTransaction

internal enum class FragmentAnimationFlags {
    FULL, EXIT_ONLY, KEEP_BEHIND, FADE, FADE_IN, FADE_IN_ONLY, SLIDE_UP, NONE
}

internal fun FragmentTransaction.setAnimation(animation: FragmentAnimationFlags): FragmentTransaction {
    return when (animation) {
        FragmentAnimationFlags.EXIT_ONLY ->
            setCustomAnimations(0, 0, R.anim.push_in, R.anim.right_slide_out)

        FragmentAnimationFlags.FULL ->
            setCustomAnimations(R.anim.right_slide_in, R.anim.push_back, R.anim.push_in, R.anim.right_slide_out)

        FragmentAnimationFlags.FADE ->
            setCustomAnimations(R.anim.crossfade_in, R.anim.crossfade_out, R.anim.crossfade_out, R.anim.crossfade_in)

        FragmentAnimationFlags.FADE_IN ->
            setCustomAnimations(R.anim.crossfade_in, 0, R.anim.crossfade_out, 0)

        FragmentAnimationFlags.FADE_IN_ONLY ->
            setCustomAnimations(R.anim.crossfade_in, 0, 0, 0)

        FragmentAnimationFlags.SLIDE_UP ->
            setCustomAnimations(R.anim.bottom_slide_in, R.anim.stay, R.anim.stay, R.anim.bottom_slide_out)

        FragmentAnimationFlags.KEEP_BEHIND ->
            setCustomAnimations(R.anim.right_slide_in, R.anim.stay, 0, R.anim.right_slide_out)

        FragmentAnimationFlags.NONE ->
            setCustomAnimations(0, 0)

    }
}
