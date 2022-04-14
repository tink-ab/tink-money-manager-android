# Should be given a version on the format x.y.z as argument

major=$(echo "$1" | cut -d. -f1)
minor=$(echo "$1" | cut -d. -f2)
patch=$(echo "$1" | cut -d. -f3)

rm ./buildSrc/src/main/java/TinkMoneyManagerVersion.kt

echo "object TinkMoneyManagerVersion {

    private const val major = $major
    private const val minor = $minor
    private const val patch = $patch

    const val name = \"\$major.\$minor.\$patch\"

    private const val minorOffset = 100 //make space for 100 patches per minor version
    private const val majorOffset = 100 * minorOffset //make space for 100 minor versions per major version

    //Will generate a readable int representation of the version
    //For example 4.12.3 will be 412003
    const val code = major * majorOffset + minor * minorOffset + patch
}" >> ./buildSrc/src/main/java/TinkMoneyManagerVersion.kt
