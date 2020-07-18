# Shiori Native Android App

[Shiori](https://github.com/go-shiori) is a "simple bookmarks manager written in Go language ... intended as a simple clone of [Pocket](getpocket.com)". It was exactly what I needed on my laptop where I could plumb it into Chrome and variant browsers, but I couldn't completely replace Pocket until I had some mobile features. Since I don't use mobile Firefox, the key part is the ability to bookmark webpages using Android's sharing feature.

## Current state

You can enter the settings to your server:

<img src="https://raw.githubusercontent.com/pietersartain/shiori-android-app/master/docs/settings.jpg" width="200px">

You can see the content of your Shiori:

<img src="https://raw.githubusercontent.com/pietersartain/shiori-android-app/master/docs/article-list.jpg" width="200px">

Clicking on an article renders the archive view from your server:

<img src="https://raw.githubusercontent.com/pietersartain/shiori-android-app/master/docs/article-detail.jpg" width="200px">

And sharing from the Android share menu brings up Shiori as an option:

<img src="https://raw.githubusercontent.com/pietersartain/shiori-android-app/master/docs/android-share.jpg" width="200px">

Once you've shared to Shiori, you'll see a brief note that your bookmark has been saved, and will be returned to the page you left. Not quite the same as the Pocket implementation, but more appropriate for my usecase.

Currently almost no caching of articles or lists is done on the app, and each time you open the app you are logged in a fresh, so this implementation requires a connection.

## Project todos

1. Fix the code. All of it. I've never written an Android app or Kotlin before, and my focus was on making this work not on making it right or pretty. As such the program flow is harder to read than I feel like it should be. It feels a bit like I've written some shonky Javascript with lots of bizarre eventing that make it hard to track flow.

My excuse is that this was a pretty quick and dirty affair: the commits will give the precise timeline away, but from a standing start this was a month's work there or there abouts. For the effort and outcome, I'm pretty damn proud of the thing, warts and all!

2. Verify the repo's buildability. I don't actually know if I've committed the rights things for this to be buildable from scratch. If you try it and it works, let me know!

3. Produce some release binaries or get them in the Google Play Store. I don't really know what the implications of doing that are right now. Let me know if that's something you'd value, you can get in touch via the email on my profile.

## Feature todos

From a feature perspective, there's a couple of things that would be nice but aren't currently impacting my use:

1. React to Pocket email links. I still receive some really interesting emails from Pocket and it'd be nice if Shiori showed up when I clicked the link.

2. Add some local caching stuff to mimic the read-it-later behaviour of Pocket. This probably requires changes to the Shiori API itself.

3. Add a search box to search tags, or titles, or text.

## Building

I built and deployed to my phone from [Android Studio 3.6.3](https://developer.android.com/studio/), and compiled the project against Android API 29.

It uses the Volley library to do HTTP calls. It's referenced as a submodule in this project.

## License

The original Go app Shiori is licensed under the MIT license.
The Volley HTTP library is licensed under the Apache v2 license.

This app is licensed under GPLv3.
