@upload
Feature: Upload new content

  As a user
  I want to upload files to my account
  so that those files will be synced from now on

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Upload a single file
    When Alice selects the option upload from photo gallery
    And Alice selects a photo
    Then Alice should see the photo in the filelist