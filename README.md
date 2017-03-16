# 4dea 360 viewer demo app
This app is to demonstrate how 4Dea 360 viewer SDK can be integrated in any app with absolute ease.

## Steps to load 4Dea viewer SDK in android app

### Module import process:
1. Place .aar file in your computer at some known location.
2. Open android studio and open your project in which you want to integrate this viewer. Then go to `File>>New>New Module` and then from the dialog select `Import .JAR/.AAR Package` and then from following dialog select the .aar file from your computer storage.
3. After that go to `File>>Project Structure` or press `CTRL+ALT+SHIFT+S` for windows, `Command+;` for mac and then keep `app` selected from `Module` section in left side of the dialog and then select `dependencies` tab from top. Then click on `+` button in the right side and then select `Module dependency` then from Choose Module dialog select the one you just imported.

### Override mandatory methods

- Now soon after build process gets completed, create a new java file and name it whatever you want. I’m going to name it `MyPanoramaHelper.java`. This file will be responsible for each and every communication of your app with our panorama viewer. So after you create this file, make class `MyPanoramaHelper` subclass `com.fourdea.viewerlibrary.Presenters.ViewerPresenters.PanoramaHelper` class.

   After you do that, android studio will show red line under your class name. After you alt+enter (for windows) to fix it, android studio will ask to implement bunch of methods, which are as follow,

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