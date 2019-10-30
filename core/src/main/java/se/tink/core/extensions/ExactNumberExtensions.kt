package se.tink.core.extensions

import se.tink.core.models.misc.ExactNumber


operator fun ExactNumber.plus(other: ExactNumber): ExactNumber {
    return add(other)
}