@cut
Feature: Cut/Paste item

  As a user
  I want to cut items to be pasted in other location in my account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user Alice is logged in
    And the following items have been created in the account
      | folder | DocumentsPaste  |

    @smoke
    Scenario: Cut an existent folder to another location using the Actions menu
      Given the following items have been created in the account
        | folder   | cut1  |
      When Alice selects to cut the folder cut1 using the Actions menu
      And Alice browses into folder DocumentsPaste
      And Alice selects to paste into the folder
      Then Alice should not see cut1 in the filelist anymore
      And Alice should see cut1 inside the folder DocumentsPaste

    Scenario: Cut an existent folder to another location using the Contextual menu
      Given the following items have been created in the account
        | file   | cut2.txt  |
      When Alice selects to cut the file cut2.txt using the Contextual menu
      And Alice browses into folder DocumentsPaste
      And Alice selects to paste into the folder
      Then Alice should not see cut2.txt in the filelist anymore
      And Alice should see cut2.txt inside the folder DocumentsPaste