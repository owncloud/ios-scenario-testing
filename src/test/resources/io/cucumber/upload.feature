Feature: Upload a local file to the server

  Background: User is logged in
    Given I am logged
    And There is an item called aaa.txt in the folder Downloads of the device

    @upl
  Scenario: Upload the file to the server
    When I select the option upload
    And I select the item aaa.txt to upload
    Then I see aaa.txt in my file list