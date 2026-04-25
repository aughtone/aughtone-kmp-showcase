#!/bin/sh
# Simple script to build and run iOS app on the first booted simulator

DERIVED_DATA_PATH="$(pwd)/app/ios/build"

# 1. Build the project
xcodebuild -project app/ios/iosApp.xcodeproj \
           -scheme iosApp \
           -configuration Debug \
           -sdk iphonesimulator \
           -destination 'generic/platform=iOS Simulator' \
           -derivedDataPath "$DERIVED_DATA_PATH" \
           build

# 2. Get the first booted simulator ID
SIM_ID=$(xcrun simctl list devices | grep "Booted" | head -1 | sed -E 's/.*\(([-A-Z0-9]+)\).*/\1/')

if [ -z "$SIM_ID" ]; then
    echo "No booted simulator found. Opening Simulator app..."
    open -a Simulator
    echo "Please boot a simulator and run this script again."
    exit 1
fi

# 3. Install and Launch
echo "Extracting build settings..."
SETTINGS=$(xcodebuild -project app/ios/iosApp.xcodeproj \
                      -scheme iosApp \
                      -configuration Debug \
                      -sdk iphonesimulator \
                      -destination "id=$SIM_ID" \
                      -derivedDataPath "$DERIVED_DATA_PATH" \
                      ONLY_ACTIVE_ARCH=YES \
                      -showBuildSettings)

APP_PATH=$(echo "$SETTINGS" | grep -m 1 "CODESIGNING_FOLDER_PATH" | cut -d'=' -f2 | xargs)
BUNDLE_ID=$(echo "$SETTINGS" | grep -m 1 "PRODUCT_BUNDLE_IDENTIFIER" | cut -d'=' -f2 | xargs)

if [ -z "$APP_PATH" ] || [ -z "$BUNDLE_ID" ]; then
    echo "Failed to extract APP_PATH or BUNDLE_ID. Check if xcodebuild succeeded."
    exit 1
fi

echo "Installing $APP_PATH on $SIM_ID..."
xcrun simctl install "$SIM_ID" "$APP_PATH"
echo "Launching $BUNDLE_ID..."
xcrun simctl launch "$SIM_ID" "$BUNDLE_ID"
