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
gsed -i 's/.isBetaBuild : false/.isBetaBuild : true/i' ownCloudAppShared/Tools/VendorServices.swift
grep .isBetaBuild ownCloudAppShared/Tools/VendorServices.swift
gsed -i 's/value: OCLicenseQAProvider.isQAUnlockEnabled,/value: true,/i' ownCloud/Settings/DisplaySettingsSection.swift
grep .isQAUnlockEnabled ownCloud/Settings/DisplaySettingsSection.swift
gsed -i '170,200d' ownCloud/Release\ Notes/ReleaseNotesHostViewController.swift
grep -C 2 shouldShowReleaseNotes ownCloud/Release\ Notes/ReleaseNotesHostViewController.swift
gsed -i '144i OCConnectionAllowedAuthenticationMethodIDs      : @[ OCAuthenticationMethodIdentifierBasicAuth ],' ios-sdk/ownCloudSDK/Connection/OCConnection.m
gsed -i 's|self?\.expirationDate = \.now\.addingTimeInterval(24 \* 3600 \* 7)|self?.expirationDate = Calendar.current.date(byAdding: .day, value: 1, to: Calendar.current.startOfDay(for: .now))?.addingTimeInterval(-1)|' ownCloudAppShared/Client/Sharing/ShareViewController.swift
grep Calendar.current.startOfDay ownCloudAppShared/Client/Sharing/ShareViewController.swift
gsed -i 's|self?.expirationDate = expirationDate.addingTimeInterval(7 \* 24 \* 60 \* 60)|self?.expirationDate = Calendar.current.date(byAdding: .day, value: 1, to: Calendar.current.startOfDay(for: .now))?.addingTimeInterval(-1)|' ownCloudAppShared/Client/Sharing/ShareViewController.swift

xcrun xcodebuild clean -scheme "ownCloud"

xcrun xcodebuild \
  -workspace ownCloud.xcworkspace \
  -scheme ownCloud \
  -configuration Debug \
  -destination "platform=iOS Simulator,name=iPhone 17e,OS=26.4"
  OTHER_SWIFT_FLAGS="-no-explicit-module-build"

cp -r $APP_PATH/ownCloud.app $APP_TARGET
rm -rf $APP_PATH
