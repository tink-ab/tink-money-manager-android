echo "New version: "
# shellcheck disable=SC2162
read newVersion

if [[ $newVersion =~ ^([0-9]{1,2}\.){2}[0-9]{1,10}$ ]]; then
echo "Starting pre release with version: $newVersion"
else
  echo "$newVersion is not in the right format."
  exit
fi

rm -r /Users/"$USER"/.m2/repository/com/tink

cd ..
./gradlew clean
./gradlew assemble
./gradlew :commons:publishToMavenLocal
./gradlew :data-commons:publishToMavenLocal
./gradlew :moneymanager-ui:publishToMavenLocal

echo Maven Local Files
echo Check if your artifacts exist in com/tink/moneymanager. \
 You should see 3 folders - moneymanager-ui, commons and data-commons
ls -R /Users/"$USER"/.m2/repository/com/tink/moneymanager
open /Users/"$USER"/.m2/repository/com/tink/moneymanager

cd "/Users/$USER/.m2/repository/com" || exit
zip "/Users/$USER/Desktop/moneymanager-ui-$newVersion".zip -r .

echo "Project built. A zip file with the files have been put on your desktop."