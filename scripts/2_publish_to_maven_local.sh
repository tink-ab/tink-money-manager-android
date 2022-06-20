set -e

newVersion=$1
oldVersion=$2
jiraTicketNumber=$3
isDryRun=$4

if [[ $isDryRun = 'y' ]]
then
  masterBranch=master-dry-run
else
  masterBranch=master
fi

echo "-------> Checking out master"
git checkout $masterBranch
git fetch
git pull

pathToMavenLocal=/Users/"$USER"/.m2/repository/com/tink
if [ -e "$pathToMavenLocal" ] ; then
  rm -r "$pathToMavenLocal"
fi

echo "-------> clean & assemble"
./gradlew clean
./gradlew assemble
echo "-------> publishing to Maven local"
./gradlew :commons:publishToMavenLocal
./gradlew :data-commons:publishToMavenLocal
./gradlew :moneymanager-ui:publishToMavenLocal

printf "\n\n"
echo "-------> DONE: Publishing to Maven local completed!"
echo "-------> NEXT: Check if every file has a signed file with the same filename, \
including extension, and an additional .asc file extension"

read -p "-------> Press enter to review the files..."
ls -R /Users/"$USER"/.m2/repository/com/tink
#open /Users/"$USER"/.m2/repository/com/tink

read -p "-------> Press enter if everything looks good..."
echo "-------> Building the release zip file"

cd "/Users/$USER/.m2/repository" || exit
zip "/Users/$USER/Desktop/moneymanager-ui-$newVersion".zip -r ./com

echo "-------> DONE: Project built. A zip file with the files have been put on your desktop."
echo "-------> Launching the script #3 to create a Github release on public and private repos"
./scripts/3_github_release.sh "$newVersion" "$oldVersion" "$jiraTicketNumber"