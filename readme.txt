The Plagiarists

Project Name: DonApp : The Donate App

CSE Id's of team members:
aakritanshuman - 193050064
vikasv - 193059003
rasesh - 193050015
deepaksingh - 193050074

Github link: https://github.com/vikasatverma/DonApp

Contributions:
Deepak - GUI, Database Handling, Search Bar
Vikas - Login Page, List of Items, Marking Items(Dibs)
Aakrit - Get Item Description, Documentation
Rasesh - Insert Item, Back-End update

Motivation:
We can find numerous examples of donation for natural calamities and major incidents such as for floods, earthquakes,
prime minister relief fund, etc. But nothing such is available on an individual level. Our vision is to provide a platform for
donation of everyday use objects which could serve a better purpose in new hands.

How to Host:
For setting up the database:

1. Download and install Xampp from the following link https://www.apachefriends.org/download.html
2. For Windows: Open Xampp Control Panel from the directory where Xamppwas installed.

(a) In the Xampp control panel, start the modules for 'Apache' and 'MySQL'

(b) After both the modules have started, click on 'Admin' button for 'Apache'module.

(c) A webpage will open up in the browser. Click on the tab 'phpMyAdmin'(usually on the top right).

3. For Linux: Go to the directory /opt/lampp and run the command: sudo./xampp start4 
	 In a browser open localhost/phpmyadmin
4. On the left click on option 'new' to create a new database. Name the databaseas 'firstDB'.
5. Click on the icon for newly created Database 'firstDB' and go to the 'SQL'tab.
6. Run the following command in terminal / command prompt for enabling httpserver to respond to image requests

ruby -run -ehttpd . -p8000 [2019-11-27 08:44:40] INFO WEBrick 1.4.2[2019-11-27 08:44:40]

INFO ruby 2.5.1 (2018-03-29) [x8664-linux-gnu][2019-11-27 08:44:40]

INFO WEBrick::HTTPServer::start: pid=5175 port=8000

7. Type the following commands and click on Go:

CREATE TABLE 'itemlist' ('itemid' int(11) NOT NULL, 'itemname'varchar(30) NOT NULL, 'place' text NOT NULL DEFAULT 'IITB','imagepath' text NOT NULL, 'description' text NOT NULL DEFAULT'No further info', 'contact' varchar(20) NOT NULL DEFAULT '0', 'cat-egory' enum('Education','Sports','Food','Clothing','Footwear', 'Station-ary','Others') NOT NULL DEFAULT 'Others', 'donorid' text NOT NULLDEFAULT '0, 'doneeid' text NOT NULL DEFAULT '0', 'status' enum('added','requested','approved',") NOT NULL DEFAULT 'added')

CREATE TABLE 'users' ( 'id' int(20) NOT NULL, 'username' var-char(70) NOT NULL, 'password' varchar(40) NOT NULL, 'email' var-char(50) NOT NULL, 'dibscount' int(11) NOT NULL DEFAULT 0, 'cre-atedat' datetime NOT NULL, 'updatedat' datetime DEFAULT NULL)

For the source code:

8. Find out the IP address of the system where the Xampp is being run. Usethe commands 'ipconfig' in Windows Command Prompt Shell or 'ifconfig' inLinux to obtain the IP addresses.
9. Copy the IP address to the java file in the path: ~\DonApp-master\DonApp\src\main\java\com\journaldev\DonApp\ipaddress.java in the static String variable'ipadd'.
10. We use Android Studio which can be downloaded from https://developer.android.com/studio
11. After installing Android Studio, download SDK for Android 8.0 (api 24)
12. The entire folder 'Donapp-master' should be used as a project for AndroidStudio.
13. Build and Run the Project. The apk file can be made from here and installedin any smartphone for usage.
