package com.tink.moneymanagerui

import androidx.fragment.app.FragmentTransaction

enum class FragmentAnimationFlags {
    FULL, EXIT_ONLY, KEEP_BEHIND, FADE, FADE_IN, FADE_IN_ONLY, SLIDE_UP, NONE
}

internal fun FragmentTransaction.setAnimation(animation: FragmentAnimationFlags): FragmentTransaction {
    return when (animation) {
        FragmentAnimationFlags.EXIT_ONLY ->
            setCustomAnimations(0, 0, R.anim.tink_push_in, R.anim.tink_right_slide_out)

        FragmentAnimationFlags.FULL ->
            setCustomAnimations(R.anim.tink_right_slide_in, R.anim.tink_push_back, R.anim.tink_push_in, R.anim.tink_right_slide_out)

        FragmentAnimationFlags.FADE ->
            setCustomAnimations(R.anim.tink_crossfade_in, R.anim.tink_crossfade_out, R.anim.tink_crossfade_out, R.anim.tink_crossfade_in)

        FragmentAnimationFlags.FADE_IN ->
            setCustomAnimations(R.anim.tink_crossfade_in, 0, R.anim.tink_crossfade_out, 0)

        FragmentAnimationFlags.FADE_IN_ONLY ->
            setCustomAnimations(R.anim.tink_crossfade_in, 0, 0, 0)

        FragmentAnimationFlags.SLIDE_UP ->
            setCustomAnimations(R.anim.tink_bottom_slide_in, R.anim.tink_stay, R.anim.tink_stay, R.anim.tink_bottom_slide_out)

        FragmentAnimationFlags.KEEP_BEHIND ->
            setCustomAnimations(R.anim.tink_right_slide_in, R.anim.tink_stay, 0, R.anim.tink_right_slide_out)

        FragmentAnimationFlags.NONE ->
            setCustomAnimations(0, 0)
    }
}
