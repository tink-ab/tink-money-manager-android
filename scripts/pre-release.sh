cd ..

# shellcheck disable=SC2162
read -p "New version number: " version

rm -r /Users/"$USER"/.m2/repository/com/tink

./gradlew clean
./gradlew assemble
./gradlew publishToMavenLocal

echo Maven Local Files
echo Check if your artifacts exist in com/tink/moneymanager. \
 You should see 3 folders - moneymanager-ui, commons and data-commons
ls -R /Users/"$USER"/.m2/repository/com/tink
open /Users/"$USER"/.m2/repository/com

# TODO: add working zip
#zip /Users/"$USER"/Desktop/moneymanager-ui-"$version".zip -r /Users/"$USER"/.m2/repository/com