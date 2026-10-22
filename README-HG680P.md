# CleanYourTV on HG680P (Android 6)

Fork of https://github.com/Gathin23/cleanyourtv (MIT) adjusted to run on a
FiberHome HG680P (MiBox3-based ROM, Android 6.0.1, API 23).

- minSdk lowered to 23; core library desugaring enabled for java.time
- Compat.java wraps API 24+ calls behind version checks
- app_library and library_button enabled in config.xml
- examples/hg680p-mibox3/debloat.txt: packages disabled on this device
