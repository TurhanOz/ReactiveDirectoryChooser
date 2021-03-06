# Android ReactiveDirectoryChooser
[![Build Status](https://travis-ci.org/TurhanOz/ReactiveDirectoryChooser.svg?branch=master)](https://travis-ci.org/TurhanOz/ReactiveDirectoryChooser)
[![Release](https://jitpack.io/v/TurhanOz/ReactiveDirectoryChooser.svg)](https://jitpack.io/#TurhanOz/ReactiveDirectoryChooser)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ReactiveDirectoryChooser-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1699)
[![Stories in Ready](https://badge.waffle.io/TurhanOz/ReactiveDirectoryChooser.png?label=ready&title=Ready)](https://waffle.io/TurhanOz/ReactiveDirectoryChooser)

A simple android library that lets user select a directory, either on primary external SD Card, or Secondary one (if you have multiple external storage).

This library has been developed using RxJava. It also integrates relevant unit tests.

<table>
<tr>
<th>Usual Fragment<br><img src="media/RDC-FullFragment.png" width="38%"></th>
<th>Floating DialogFragment<br><img src="media/RDC-FloatingFragment.png" width="38%"></th>
</tr>
</table>

## Usage

### From JitPack

Library releases are available on JitPack; you can add dependencies as follow :

**Gradle**

```groovy
allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
dependencies {
        compile 'com.github.turhanoz:reactivedirectorychooser:{latest version}'
    }
```

### Supported Android SDK

You can use this library for apps starting from android 4.0 (icecream /API 14) to android 8 (oreo / API 26)

```
minSdkVersion 14
targetSdkVersion 26
```

##Important behaviour
Starting API LEVEL 21, please use ACTION_OPEN_DOCUMENT_TREE intent instead of opening raw directories
(here is a sample to guide you : https://github.com/googlesamples/android-DirectorySelection)

### Manifest

You need to add the `android.permission.WRITE_EXTERNAL_STORAGE` permission.

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

### Fragment
This library integrates a Fragment called DirectoryChooserFragment;
You can use it as floating dialog fragment or regular fragment. You can also pass a default root directory file which will be displayed first.
To be notified which directory the user has chosen, you can implement the OnDirectoryChooserFragmentInteraction interface on your host activity (the fragment will automatically register the host activity's interface for you).

```java
public class MainActivity extends ActionBarActivity implements OnDirectoryChooserFragmentInteraction {
    File currentRootDirectory = Environment.getExternalStorageDirectory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    void addDirectoryChooserFragment() {
        DialogFragment directoryChooserFragment = DirectoryChooserFragment.newInstance(currentRootDirectory);
        getSupportFragmentManager()
			.beginTransaction()
			.addToBackStack("RDC")
			.add(R.id.fragment_host, directoryChooserFragment, "RDC")
			.commit();
    }

    void addDirectoryChooserAsFloatingFragment() {
            DialogFragment directoryChooserFragment = DirectoryChooserFragment.newInstance(currentRootDirectory);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            directoryChooserFragment.show(transaction, "RDC");
    }

    @Override
    public void onEvent(OnDirectoryChosenEvent event) {
        File directoryChosenByUser = event.getFile();
    }

    @Override
    public void onEvent(OnDirectoryCancelEvent event) {
    }
}

```


## Article
Follow the detailed process of this open source project in this [blog post](http://turhanoz.com/reactive-directory-chooser-an-open-source-journey/)

## License

**Fun one**
```text
Copyright 2015 Turhan Oz

"THE VIRGIN-MOJITO-WARE LICENSE" (Revision 2048): As long as you retain this notice you can do whatever you want with this stuff. If we meet some day, and you think this stuff is worth it, you can buy me a virgin mojito in return. Turhan
```

**Real one**
```text
    Copyright 2015 Turhan OZ

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```