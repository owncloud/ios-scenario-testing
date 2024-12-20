@list
Feature: List of files is correctly retrieved from server.

  As a user
  I want to see in my device the correct list of files in every location
  So that i know which files and folders i have stored in my server

  Background: User is logged in
    Given user Alice is logged in

  Rule: Listing existing files

    @smoke
    Scenario Outline: Check items in the list of files of an specific folder
      Then the list of files in <path> folder should match with the server

      Examples:
        | path |
        | /    |

    @smoke
    Scenario: Check items in the list of files of an created folder
      Given the following items have been created in Alice account
        | folder | Many |
      And the folder Many contains 10 files
      Then the list of files in /Many folder should match with the server