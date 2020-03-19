Feature: Public Share

  As an user, i want to create a direct link to the content in my account
  so that the content is accessible for whom i send the link

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      |  Documents         |
      |  San Francisco.jpg |

  Scenario Outline: Create a public link with name
    When user selects <item> to create link with name <name>
    Then public link is created on <item> with the name <name>

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |
      |  San Francisco.jpg |  link2   |