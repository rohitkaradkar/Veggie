# Warning  
I'm no longer maintaining this project. I haven't update Firebase project & backend so most of the things should not work as expected. 
Feel free to reach out to me for any help.


# Veggie

It is a simple fruits and vegetable shopping app, built to support my friend's startup. It is also an experiment to make use of `Model View Presenter` architecture.

## Features
* Select products you want to purchase. You can search and sort them by category. Product volume can be managed while checkout.

<p align="center" target>	
	<img src="https://github.com/karadkar/Veggie/blob/master/screenshots/1.browse.png?raw=true" width="300">
	<img src="https://github.com/karadkar/Veggie/blob/master/screenshots/2.cart.png?raw=true" width="300">
</p>

* You will be notified when your order is shipped and you can check your order history. 
	
<p align="center">
	<img src="https://github.com/karadkar/Veggie/blob/master/screenshots/3.notification.png?raw=true" width="300">
	<img src="https://github.com/karadkar/Veggie/blob/master/screenshots/4.history.png?raw=true" width="300">
</p>

* Yes, we provide shipping to Mars.

<p align="center">
	<img src="https://github.com/karadkar/Veggie/blob/master/screenshots/5.profile.png?raw=true" width="300">
</p>

## Structure 
This application is built using `Model View Presenter` architecture which is divided in three modules. Code separation allows to reuse the code. Makes it easy to maintain and modify. It provides abstraction while creating Android views. I have listed all of the modules with their usage.

#### [App module][link_module_app]
Android application module which acts as a `View`. It depends on [core][link_module_core] module which delivers all necessary data. This allows to focus on user interface and simplifies the code.  

#### [Common Module][link_module_common]
All common data models between [app][link_module_app] and [core][link_module_core] module are available here. 
 
#### [Core Module][link_module_core]
This module works as a `Presenter`. All application logic is provided here. I've described `package` name with their use.

* [presenter][link_pkg_core_presenter] - Handles all application logic. Android activity / fragment uses associated presenter to handle data delivery, network operation, error cases and database operations.
* [remote][link_pkg_core_remote] - Handles remote connection with backend. Usually accessed by `Presenter` and `Service` classes.
* [service][link_pkg_core_service] - Background services to download product and user information. These classes helps to cache user data in advance so they doesn't need to wait.  
* [storage][link_pkg_core_storage] - ContentProvider implementation along with [helper][link_pkg_core_helper] classes to access database easily. 


## Setup
* Download project source and import in Android studio .
* Create new project in [Firebase console.](https://console.firebase.google.com/)
* In Firebase console, add Android app with package name  `com.greentopli.app.user`.
* Download `google-services.json` file from Firebase console and add to `app/src/user/` directory of project .



## Reference
* [Dribble client](https://github.com/hitherejoe/Bourbon) by [joe birch.](https://joebirch.co)
* Product icons are created by [Devender Malhotra.](mailto:devesh.m93@gmail.com)
* Basket icon is created by [Freepik](http://www.freepik.com/) from [www.flaticon.com,](www.flaticon.com) and it is licensed under [CC 3.0 BY.](http://creativecommons.org/licenses/by/3.0/)
* Launcher icon is created using [Android asset studio.](https://romannurik.github.io/AndroidAssetStudio/index.html) 

[link_pkg_core_presenter]:https://github.com/karadkar/Green-Basket/tree/master/core/src/main/java/com/greentopli/core/presenter
[link_pkg_core_remote]:https://github.com/karadkar/Green-Basket/tree/master/core/src/main/java/com/greentopli/core/remote
[link_pkg_core_service]:https://github.com/karadkar/Green-Basket/tree/master/core/src/main/java/com/greentopli/core/service
[link_pkg_core_storage]:https://github.com/karadkar/Green-Basket/tree/master/core/src/main/java/com/greentopli/core/storage
[link_pkg_core_helper]:https://github.com/karadkar/Green-Basket/tree/master/core/src/main/java/com/greentopli/core/storage/helper
[link_module_common]:https://github.com/karadkar/Green-Basket/tree/master/common
[link_module_core]:https://github.com/karadkar/Green-Basket/tree/master/core
[link_module_app]:https://github.com/karadkar/Green-Basket/tree/master/app
