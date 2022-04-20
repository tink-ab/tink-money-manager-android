# Should be given a version on the format x.y.z as argument
echo "New version: "
# shellcheck disable=SC2162
read newVersion

if [[ $newVersion =~ ^([0-9]{1,2}\.){2}[0-9]{1,10}$ ]]; then
echo "Starting release with version: $newVersion"
else
  echo "$newVersion is not in the right format."
  exit
fi

git checkout development
git checkout -b release-"$newVersion"

major=$(echo "$newVersion" | cut -d. -f1)
minor=$(echo "$newVersion" | cut -d. -f2)
patch=$(echo "$newVersion" | cut -d. -f3)

cd ..
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

git add ./buildSrc/src/main/java/TinkMoneyManagerVersion.kt
git commit -m "Version bump to $newVersion"
git push --set-upstream origin release-"$newVersion"

echo "Version bumped t0 $newVersion!"
