@upload
Feature: Upload new content

  As a user
  I want to upload files to my account
  so that those files will be synced from now on

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario Outline: Upload from Photo Gallery
    When Alice selects the option upload from photo gallery
    And Alice selects <amount> photo
    Then Alice should see <amount> photo in the filelist

    Examples:
      | amount |
      | 1      |