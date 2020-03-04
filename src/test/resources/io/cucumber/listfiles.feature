Feature: List of files is correctly retrieved from server.

  As an user, i want to see in my device the list of files
  so i know which content i have in  my server

  Background: User is logged in
    Given user1 is logged

    @hello
  Scenario: Check items in the list of files
    Then the list of files matches with the server

