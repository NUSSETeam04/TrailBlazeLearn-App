# TrailBlazeLearnFT04



## How the app works:

### Pre-requisite to run the app:
  The TrailBlazeLearn App uses open authentication method via means of Google sign in and Facebook.
  In developer mode you will meed to register your SHA1 key to the author.
  The app also requires permission to use camera access, location and storage access of your mobile device.
  
###	Sign in Method: 
User can login using Google Sign and Facebook authentication methods into the app and these modes of authentication 
methods are enabled in firebase console for our app. 

###	Different Modes:
As of now the app supports two different i.e. Trainer and Participant.

##	Trainer Mode: 
Trainer can add, edit, delete the learning trail, trail stations and participate in discussion threads, posts and upload any items
in contributed items. Below we will see how each functionality will work.
•	Adding a learning trail: Trainer can create learning trails by providing learning trail name and learning trail date 
(by default current date will be displayed), by clicking on SAVE button the learning trail gets created and will be stored in firebase.
Here the trail id gets generated with format YYYYMMDD-<Trail Code>. 
Trail Id is the key feature for the participant to start his activities.

####	1. Editing a learning trail: 
Trainer can change the learning trail name and learning trail date using this feature. 
Participant should be able to see the updating learning trail name.

####	2. Deleting a learning trail: 
Trainer can delete a learning trail and stations present in that learning trail also get deleted from app and firebase. 
Participant will no longer have access to the deleted learning trail as it gets deleted from the participant mode too.

####	3. 	Adding a trail station: 
For a trail station to be created learning trail is the pre- requisite. 
While creating trail station trainer need to provide trail station name, provision to pick the actual location using google maps and
instructions can be provided for the participant to be carried out. Once station is created trainer also have a feature to upload 
items such as PDF/Audio blog/Document/Picture and now trainer can participate in discussion thread and posts forum.

####	4. 	Editing a trail station:
Using edit a trail station feature trainer can update the station name, location and provide any extra instructions if required. 
Similar the stations information in participant screen also gets updated. This feature is not accessible for participant mode.

####	5. 	Deleting a trail station: 
Trainer can delete a trail station if necessary and once its deleted all the updates, discussion threads, posts will also get
deleted from the app and firebase as well. Further participant won’t be able to see the station which is deleted.

##	Participant Mode: 
A participant needs to input the learning trail ID to access the learning trail created by the trainer.
Once the participant has input a valid learning trail ID, a learning trail list screen will be displayed. 
Participant can’t edit/delete a learning trail or trail station.

###	 Station Detail: 
User can carry out the instructions mentioned by the trainer and participate in discussion threads, posts and upload items 
(e.g. PDF/Image/Document/Audio blog).

####	i. 	Station Info: 
User can check the activities being carried out in a station like location, instructions and uploaded items.

####	ii. 	Discussion Thread and Posts: 
Discussion thread is opinion exchange between trainer and two or more participants who are part
of that trail station. It allows new or old participant to follow the discussion thread from the beginning to latest opinion.

####	iii. 	Updates: 
Trainer and participant can view the live updates who are part of the trail station. It allows to view items
(PDF/doc or images) or postings that are contributed by other participants. 






