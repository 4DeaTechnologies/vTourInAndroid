# 4dea 360 viewer demo app
This app is to demonstrate how 4Dea 360 panorama viewer SDK can be integrated in any app with absolute ease.

## Steps to load 4Dea viewer SDK in android app

### Module import process:
1. Place .aar file in your computer at some known location.
2. Open android studio and open your project in which you want to integrate this viewer. Then go to `File>>New>>New Module` and then from the dialog select `Import .JAR/.AAR Package` and then from following dialog select the .aar file from your computer storage.
3. After that go to `File>>Project Structure` or press `CTRL+ALT+SHIFT+S` for windows, `Command+;` for mac and then keep `app` selected from `Module` section in left side of the dialog and then select `dependencies` tab from top. Then click on `+` button in the right side and then select `Module dependency` then from Choose Module dialog select the one you just imported.

### Override mandatory methods

- Now soon after build process gets completed, create a new java file and name it whatever you want. I’m going to name it `MyPanoramaHelper.java`. This file will be responsible for each and every communication of your app with our panorama viewer. So after you create this file, make class `MyPanoramaHelper` subclass of `com.fourdea.viewerlibrary.Presenters.ViewerPresenters.PanoramaHelper` class.

- After you do that, android studio will show red line under your class name. After you alt+enter (for windows) to fix it, android studio will ask to implement bunch of methods, which are as follow,

   - In getTourDataPath() method, return the ShortURL (unique identifier for each tour) in above format. Now in there string `?v=1` is needed for cdn, if data in your cdn server is changed then you would want to increment `v`'s value with one. You can ignore it if you want. But make sure to add `/` before string.
       ```
       @Override
       public String getTourDataPath() {
          return "/Bella_Vista_Resort"+"?v=1";
       }

       ```

   - In `getContainer()` method, return any Layout `(LinearLayout, RelativeLayout, FrameLayout)` in which you want to add panorama viewer. It would be best if you don't use any of scrollable layout to contain panorama viewer.
       ```
       @Override
       public ViewGroup getContainer() {
          return (ViewGroup) ((Activity)context).findViewById(R.id.activity_main_gl);
       }
       ```

   - In `getImageBaseUrl()` you would like to return prefix of urls. In your case `cdn.mycdn.com`. Don’t forget to add `/` at the end of the string.
       ```
       @Override
       public String getImageBaseUrl() {
          return "https://cdn.mycdn.com/images/";
       }
       ```

   - In `getJsonBaseUrl()` you would like to return prefix of urls. In your case `cdn.mycdn.com` (Same as image base url). Don’t forget to add `/` at the end of the string.
       ```
       @Override
       public String getJsonBaseUrl() {
          return "https://cdn.mycdn.com/json/";
       }
       ```

   After you finish `overriding` this methods, you can instantiate the class `MyPanoramaHelper` and after that call `initiate()` method. `initiate()` method of `PanoramaHelper` class (`MyPanoramaHelper`’s super class) is responsible for setting up the panorama for given `ShortURL` and in given layout.


## Documentation

### PanoramaHelper.java
- public abstract class PanoramaHelper

#### Summary
  - Public Constructor
  ```
  public PanoramaHelper(Context context)
  ```

#### Public Methods
- **void initialize()**

  Needs to be called to initialize all the necessary processes to render panorama viewer inside the layout returned in `getContainer()` method.

- **void initialize(int sceneNum)**

  Needs to be called to initialize all the necessary processes to render panorama viewer from given `sceneNum`.

- **void changeScene(int sceneNum)**

  Call this method to go to the given sceneNum. This method can be called after `initialize()` method is called and after you get `onTourDataLoaded()` callback. sceneNum parameter cannot be greater than or equal to total scenes in tour. sceneNum index starts from 0.

- **void turnGyroOn()**

  Call this method to turn on gyroscope. This method can be called after `initialize()` method is called. This will only work for gyroscope equipped devices.

- **void turnGyroOff()**

  Call this method to turn off gyroscope. This method can be called after `initialize()` method is called.

- **boolean isGyroOn()**

  This method will return true if gyroscope is on and false otherwise.

- **void goToCardboardMode()**

  Call this method to go to cardboard mode. This method must be called after `onTourDataLoaded()` is called.

- **void startAutoPlay()**

  This method will start auto-playing the vTour. Note that auto-play is on by default. If you want to prevent that then call `stopAutoPlay()` after calling `initialize()` method.

- **void stopAutoPlay()**

  Call this method to stop auto-play of vTour.

- **void isAutoPlayStopped()**

  Returns true if auto-play is stopped using `stopAutoPlay()` method and false otherwise.

- **int getCurrentSceneNum()**

  This method will return currently displayed scene's index. Note that index of scenes start from 0.

- **String getSceneName(int sceneNum) throws Exception**

  This method returns name of given sceneNum(scene index). Throws exception in case of `initialize()` method isn’t called, if `getSceneName()` is called before `onTourDataLoaded()` callback, if `sceneNum` argument is greater than or equal to total scenes.

- **String getThumbnailUrl(int sceneNum) throws Exception**

  This method returns url of given `sceneNum`(scene index). Throws exception in case of `initialize()` method isn’t called, if `getThumbnailUrl()` is called before `onTourDataLoaded()` callback, if `sceneNum` argument is greater than or equal to total scenes.

- **int getTotalNumOfScenes() throws Exception**

  This method returns total number of scenes in current tour. Throws exception in case of `initialize()` method isn’t called, if `getTotalNumOfScenes()` is called before `onTourDataLoaded()` callback.

- **void setCallBackListener(PanoramaCallBackListener callbackListener)**

  Sets callback listener for callback methods from panorama viewer.

- **void hideLayers()**

  Hides both Infolayer and Arrows from scene. Must be called after calling `initialize()`.

- **void showLayers()**

  Shows both Infolayer and Arrows from scene. Must be called after calling `initialize()`.

- **boolean areLayersHidden()**

  Returns true if Infolayer and Arrows are hidden, and false otherwise. Must be called after calling `initialize()`.

- **void stopUserInteraction()**

  By default, auto-play will be running. But if user interacts with panorama then autoplay will be stopped by default. This method will prevent user interaction with panorama. So the auto-play will keep running.

- **void setSwipeMultiplier(float swipeMultiplier)**
  This method sets multiplier of panorama moving speed after user swipe. Default value is 0.8.

- **void setQuality(String quality)**
  This methods needs to be called in order to change quality of panorama. Add quality parameter,

  - `PanoramaHelper.QUALITY_MOBIQ`: Mobile friendly quality
  - `PanoramaHelper.QUALITY_MQ`: Tablet friendly quality
  - `PanoramaHelper.QUALITY_HQ`: High quality

  Pass one of these three variables as an argument. By default sdk will load MobiQ for mobile phones and MQ for tablets. Call this method only if you want to change this behavior.

  Note that this method needs to be called after initialized method call. Also note that calling this method will not change the quality of already loaded scene.


### PanoramaCallBackListener
- public interface PanoramaCallBackListener

#### Summary
This interface contains callbacks that you might want from sdk.

#### Public Methods
- **void onTourDataLoaded()**

  This method will be called when tour data gets downloaded. Most of the operations can be performed after you get this callback.

- **void onTap()**

  This method will be called when user taps on panorama.

- **void onTouchDown()**

  This method will be called when user’s finger starts touching panorama.

- **void onTouchUp()**

  This method will be called when user’s finger leaves panorama.

- **void onAutoPlayCompleted()**

  This method will be called autoplay will be completed. Note that this will not get called when autoplay is being stopped manually i.e. by calling `stopAutoPlay()`.

- **void onLowQualityPanoLoaded(int sceneNum)**

  This library loads panorama images in cache and retrieve them whenever they are needed so that we don’t have to download images every time from server.

  The library downloads images for first time and on that first time following is how loading process goes:
  - First it downloads low quality images to display panorama quickly on screen so user does not have to wait until all high quality images gets downloaded. And so when low quality images gets downloaded, we show them on screen and call this `onLowQualityPanoLoaded()` method with loaded scene number. Then same process goes for high quality images.

- **void onHighQualityPanoLoaded(int sceneNum)**

  This method will be called when high quality panorama gets rendered on screen. `sceneNum` is loaded scene’s index.

- **void updateProgress(float progress)**

  To update total loaded progress of current scene’s loading process. You can set this progress to your own progress bar. progress indicates total loaded progress for current scene.

- **void onFailedToLoadTourData()**

  This method will be called in case of failure in loading images from server. Call to this method most probably indicates that user’s device isn’t connected to the internet.

- **void onArrowClicked()**

  This method will be called when user clicks on arrow. Now note that you don’t have to perform `changeScene()` operation on call of this method. Reason behind putting this method in callbacks is that on this callback you can add progress bar of any kind and then show progress in `updateProgress()` method and remove it on `onLowQualityPanoLoaded()` or on `onHighQualityPanoLoaded()` callbacks.



## Instructions for CardBoard(VR) mode:
In order to enable your user to experience 360 photos in virtual reality mode, there are few things that we, as developers, have to keep in mind.

Before your user navigate to card board mode, you have to check for following sensors in user's device:
  - User's device has Gyro scope sensor and Magnetometer sensor

    If this is the case, then you can directly navigate your user to cardboard mode using our api.

  - User's device has Gyro scope sensor and `not` Magnetometer sensor

    If this is the case, then you have show a message which will say something like `We need to calibrate sensors in your device. Please place your device on flat surface and, when ready, click this button` and you will give a button which will call our same api for cardboard mode `onClick()`.

  - User's device doesn't contain Gyro scope nor Magnetometer

    In this case there is nothing we can do to provide virtual reality experience. So kindly show according message.

#### Methods to check for sensors:

```
SensorManager mSensorManager =
(SensorManager) context.getSystemService(Activity.SENSOR_SERVICE);

if(mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
    //no gyro scope
}

if(mSensorManager.getDefaultSensor(Sensor. TYPE_MAGNETIC_FIELD) == null){
    //no magneto meter
}

```