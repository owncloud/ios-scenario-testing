#!/bin/bash

# Required variables:

## PROJECT_ROOT : root folder of the iOS project
## APP_PATH: path where the .app will be stored after being built 
##           typically $HOME/Library/Developer/Xcode/DerivedData/ownCloud-*/Build/Products/Debug-iphonesimulator
## APP_TARGET: path to copy the .app after being built

rm -rf $APP_TARGET/ownCloud.app
cd $PROJECT_ROOT

gsed -i 's/.showBetaWarning : true/.showBetaWarning : false/i' ownCloudAppShared/Tools/VendorServices.swift
grep .showBetaWarning ownCloudAppShared/Tools/VendorServices.swift
gsed -i '170,200d' ownCloud/Release\ Notes/ReleaseNotesHostViewController.swift
grep -C 2 shouldShowReleaseNotes ownCloud/Release\ Notes/ReleaseNotesHostViewController.swift
gsed -i '136i OCConnectionAllowedAuthenticationMethodIDs      : @[ OCAuthenticationMethodIdentifierBasicAuth ],' ios-sdk/ownCloudSDK/Connection/OCConnection.m

xcrun xcodebuild clean -scheme "ownCloud"

xcrun xcodebuild \
  -workspace ownCloud.xcworkspace \
  -scheme ownCloud \
  -configuration Debug \
  -destination 'platform=iOS Simulator,name=iPhone 15'


cp -r $APP_PATH/ownCloud.app $APP_TARGET
rm -rf $APP_PATH

