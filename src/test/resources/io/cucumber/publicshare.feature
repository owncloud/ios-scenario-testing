Feature: Public Share

  Background: User is logged in
    Given I am logged

  @SmokeTest
  Scenario Outline: Create a public link with name
    When i select <item> to create link with name <name>
    Then public link is created with the name <name>

    Examples:
      |    item     |   name    |
      |  Documents  |   link1   |