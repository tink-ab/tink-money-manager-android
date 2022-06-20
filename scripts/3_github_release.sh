set -e

newVersion=$1
oldVersion=$2
jiraTicketNumber=$3
isDryRun=$4

git fetch

releaseBranch=release-"$newVersion"
if [[ $isDryRun = 'y' ]]
then
  masterBranchPublicRepo=master-public-dry-run
  masterBranchPrivateRepo=master-dry-run
  git switch -c $masterBranchPublicRepo --track public/master
  git push --set-upstream public $masterBranchPublicRepo
else
  masterBranchPublicRepo=master
  masterBranchPrivateRepo=master
fi

#Public repo: create a new release branch from `[public]/master` ,
git switch -c public-master --track public/$masterBranchPublicRepo
git switch -c "$releaseBranch"

#Public repo: update the money manager dependency version in `dependencies.gradle` and `README`
# shellcheck disable=SC2006
echo "--------> Update the money manager dependency version in dependencies.gradle and README"
read -p "--------> Press enter when done"
printf "\n"
echo "--------> Create a commit for the above changes"
read -p "--------> Press enter when done"
printf "\n"

#Public repo: override the 'string-customization-guide.md' with the same file from the private repo
echo "--------> override the 'string-customization-guide.md' with the same file from the private repo"
read -p "--------> Press enter when done"
printf "\n"
echo "--------> Create a commit for the above change"
read -p "--------> Press enter when done"
printf "\n"

echo "--------> If needed update wirth new/updated parameter for launching FinanceOverviewFragment"
read -p "--------> Press enter when done"
printf "\n"
echo "--------> Create a commit for the above change"
read -p "--------> Press enter when done"
printf "\n"

#Public repo: validate that things still work by running the moneymanager sample app
echo "--------> validate that things still work by running the moneymanager sample app"
read -p "--------> Press enter when done"
printf "\n"

#Public repo: create a PR from your newly created release branch and merge that into `[public]/master`
echo "--------> create a PR from your newly created release branch and merge that into [public]/master"
read -p "--------> Press enter when done"
printf "\n"

# Add tag to the private repo
echo "--------> Add tag: $newVersion-private  the private repo & push it"
read -p "--------> Press enter when done"
printf "\n"

# Add tag to the public repo
echo "--------> Add tag $newVersion to the public repo & push it"
read -p "--------> Press enter when done"
printf "\n"

#Public repo: create a new release on GitHub public repo with tag version (For example, 0.1.1) and set the Target to `master`. Add the `moneymanager-ui-<version>.zip` file as a binary attachment to the release.
echo "--------> create a new release on GitHub public repo with the tag $newVersion."
echo "--------> Add the moneymanager-ui-$newVersion file as a binary attachment to the release"
echo "--------> Add the release notes"
read -p "--------> Press enter when done"
printf "\n"

#Publish the release notes in 'Tink Docs changelog page' using Contentful
echo "--------> Publish the release notes in 'Tink Docs changelog page'"
read -p "--------> Press enter when done"
printf "\n"

#Inform the stakeholders by sending a message into the sdk-mobile channel in Slack, linking the release notes created at the step above
echo "--------> Inform the stakeholders by sending a message into the sdk-mobile channel in Slack,"
read -p "--------> Press enter when done"
printf "\n"

#Mark release as done in Jira
echo "--------> Mark release as done in Jira"
read -p "--------> Press enter when done"
echo "--------> DONE!"