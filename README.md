# System Tests

Scenarios contained in feature files written in Gherkin language. Available scenarios can be found [here](android-scenario-testing/src/test/resources/io/cucumber). 

Defined for the [ownCloud Android app](https://github.com/owncloud/android)


## Global architecture

- Scenarios are defined with [Gherkin Syntax](https://cucumber.io/docs/gherkin/).

- Steps are interpreted by [Cucumber](https://cucumber.io/). 

- Step implementation language: [Java](https://docs.oracle.com/javase/7/docs/)

- Device interaction with [Appium](http://appium.io/)

![](architecture.png)

## Get the code

- With git: 

`git clone https://github.com/owncloud/android-scenario-testing.git`

- Download a [zip file](https://github.com/owncloud/android-scenario-testing/archive/master.zip)

## Requirements

Different requirements:

* `Appium` instance running and reachable

* At least, one device attached and reachable via adb. Check command `adb devices` to ensure `Appium` will get the device reference to interact with it


* A `local.properties` file should be included in the project with the following parameters:

  * Remote set up:

      * `userName1`: Username of existing user in all servers to test (to simplify). By default `user1`
      * `passw1`: Password for the users defined in `userName1`. By default `a`
      * `userToShare`: Existing user to search and share with. By default `user2`
      * `userToSharePwd`: Password of `userToShare`. By default `a`
      * `appiumURL`: URL of default running Appium server. By default `http\://127.0.0.1\:4723/wd/hub`

  * App parameters:

      * `apkName`: Name of the apk
      * `appPackage`: Package name of the app to test
      * `userAgent`: User Agent of the client

The environment variable `$ANDROID_HOME` needs to be correctly set up, pointing to the Android SDK folder

## How to test

The script `executeTests` will launch the tests. The script needs some parameters.


	-s (mandatory) URL of ownCloud server to test against
	-a (optional) Appium server URL. if Appium Server is not specified, will be used "localhost:4723/wd/hub"
	-t (optional) Filter. F. ex: @createfolder will send only tests tagged with such label. Tags are allowed to concatenate, sepparated by \",\". It is also allowed to use a classpath to execute all the test in such class"
	-d (optional) In case of several devices attached, tests will be sent against the UID set in this option. This is the id returned by `adb devices` command.

Executing the script with the option `-h` or without parameters, will display the help.

The execution will display step by step how the scenario is being executed.

## Test filtering

The way to filter which tests are executed is by tag. Tests can be tagged as well as feature files

Using the `-t` option with the `executeTests` script is the way to do:

With a tag just above the scenario definition, it is posible to select which scenarios will be tested:

```
  @oauth2
  Scenario: A valid login with OAuth2
    When server with OAuth2 is available
    And user logins as user1 with password a as OAuth2 credentials
    Then user can see the main page
```

Then...

````
./executeTests -s ... -t @oauth2
````

will trigger only the tests inside that scenario.

The tag set on the top of the feature file will involve all the tests inside

```
@delete
Feature: Delete item

  As an user, i want to be able to delete content from my list
  so that i can get rid of the content i do not need anymore

   Background: User is logged in
  ...
```

Then...

````
./executeTests -s ... -t @delete
````

will trigger all the tests inside the mentioned feature file

More than one tag is allowed (separated with `,`)

````
./executeTests -s ... -t @oauth2,@delete
````

The meaning is OR. All tests that are tagged with, at least, one of the tags will be executed

More info in [Cucumber reference](https://cucumber.io/docs/cucumber/api/)

**Note**: This repository was forked from [Cucumber-java skeleton](https://github.com/cucumber/cucumber-java-skeleton) repository, which contains the base skeleton to start working.
