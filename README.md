
## Project Name
#### MaylBorrow <br/> 
*Don't went, when you can rent*

## Description of MaylBorrow
MayIBorrow is a mobile application that will provide its user with a place to rent out and as well as borrow items for a definite period of time. Unlike conventional rental stores and Facebook marketplace, it is designed to allow users to only rent instead of buying items, where it will aim to provide users with a wide variety of items. Additionally, users will be able to advertise their available items for rent in just a few clicks and will make them available to borrowers instantly.The customers would be able to use these items on a rental basis and return them on the decided date. In addition, it will allow them to first rent and test the brand and items before buying as well.

## Team members
[1] Dharmay Dineshchandra Sureja
[2] Aman Singh Bhandari
[3] Anas Malvat
[4] Chirag Jayeshkumar Pancholi
[5] Khushboo Patel
[6] Sukaran Golani 

## Stable branch
main

## Tools Required for MaylBorrow
- Android Studio Latest Version
- Min SDK Version 29
- Emulator in Andriod Studio <br/>
#### Good to test using: 
- Android Phone
- USB Cable for connecting phone

## Installation Steps:
You can install this application and test on your android phone or on emulator. To install the application go to the terminal and write:
- git clone 
- Open the cloned project in android studio
- Create a device from Device Manager having API higher than 29 but for better use keep it higher than or equal to 30
- Install all the dependency
- Make the project using CTRL+F9 in Android Studio and let the project build
- Run the project from the Android Studio

## Tools used
- Firebase Database
- Firebase Authentication
- Android Studio IDE
- Android version 4.0 or later
- Android SDK 29

## Architecture: 
- Model View ViewModel because our application is smaller application since we have not integrated it with multilple API like Stripe API or Google Map API. In MVVM we can pass the data from ViewModel. It becomes easy to binding of view to field using arbitary object [51], event callback and properties.    

## Dependencies
- core-ktx:1.7.0
- appcompat:1.5.1
- com.google.android.material:material:1.7.0
- constraintlayout:2.1.4
- lifecycle-livedata-ktx:2.5.1
- lifecycle-viewmodel-ktx:2.5.1
- navigation-fragment-ktx:2.5.3
- navigation-ui-ktx:2.5.3
- firebase-firestore:24.0.2
- recyclerview:1.2.0
- cardview:1.0.0
- kotlinx-coroutines-android:1.2.1
- kotlinx-coroutines-play-services:1.2.1
- firebase-database:19.2.1
- firebase-auth:21.1.0
- firebase-common-ktx:20.2.0
- firebase-storage:20.1.0
- firebase-auth-ktx:21.1.0
- junit:4.13.2

## Collaborate with the team
- [ ] [Invite team members and collaborators] We had conducted regular meeting frequently through teams and meeting in person for discussing the progress of our application
- [ ] [Create a new merge request] We had used version control git to keep the track of everyone's work. We had created Dev branch from the main branch and then all the team members have created their own branch from the Dev branch where everyone kept the track of the Dev branch.
- [ ] [Automatically close issues from merge requests] Conflicts were resolved at the time.
- [ ] [Enable merge request approvals] We were merging our code with Dev branch in a timely manner to avoid the conflicts at later stage and get the changes of all the team member.
- [ ] [Manually merge] Merge after resolving all the conflict manually to Dev branch 
- [ ] [Merge into Main branch ] Every team member was continually performing white box testing for their own feature. Integrated the frontend with backend. After testing all the feature by providing different input and confirming the application was working successfully we then merged all our code into main branch. 

## Usage for application
- User first need to registered if not and then they can login as a current user using his email id and password you set while registering. We are not storing password since we are using token based authentication.
- After register you can create post, update post, and see all your listings.
- You can also filter the application from Name of the product or sort by price from low to high. You can search for specific product from the name like Dosa, Sofa as per your requirement
- You can edit the profile
- You can request the contact detail from the seller where seller can contact you from email.
- You can provide the rating for each product that you have used.     

## Support
If you face any issue for installation please check the link properly as mentioned in Installation Steps or you can contact one of the team member through email as mentioned above.

## Roadmap
- In future we would work on Delete the post careted by owner. We would also work on creating the recommendation for other user based on their search and instead of contactig through email we would work on integrating payment for borrowing item considering item inventory management. We would also be considering proving geolocation so the user can check in map

## Contributing
Every person in the team has equally contributed and had deveoted their time. Every team member has put their input in designing the application flow and the layout. Every team member has worked on backend as well as beautificaiton of frontend.

## Authors and acknowledgment
We appreciate Aman Singh Bhaandari, Chirag Pancholi, Khushboo Patel, Sukaran Golani, Dharmay Sureja and Anas Malavat for designing and developing the Application MaylBorrow. 

## Project status
We have completed all that feature that we had plan to complete. We had completed extra feature that was Rating and Wishlist.
 
 [1] Minimum Functionality was:
 - [x] Register (Completed)
 - [x] Login (Completed)
 - [x] Edit Profile (Completed)
 - [x] Create Post (Completed)
 - [x] Edit Post (Completed)
 - [x] Delete Post (Completed)
 - [x] View Post (Completed)

 [2] Expected functionality was:
 - [x] Product Catalogue (Completed)
 - [x] Filter (Completed)
 - [x] Search (Completed)
 - [x] Request/Contact Detail (Completed)
 - [x] Forgot/Reset Password (Completed)

 [3] Bonus Functionality was:
 - [x] Wishlist (Completed)
 - [x] Rating (Completed)
 - [ ] Recommendation
 - [ ] Chat
 - [ ] Show by Geolocation

## Reference

[1]	Danish Amjad. 2018. Connect android device with wifi within android studio. AndroidPub. Retrieved November 27, 2022 from https://medium.com/android-news/connect-android-device-with-wifi-within-android-studio-3b1bc00c1e17
 	 
[2]	Patrick Martin. Firebase and Tasks, how to deal with asynchronous logic in Unity. The Firebase Blog. Retrieved November 27, 2022 from https://firebase.blog/posts/2019/07/firebase-and-tasks-how-to-deal-with
 	 
[3]	2019. Android EditText in kotlin. GeeksforGeeks. Retrieved November 27, 2022 from https://www.geeksforgeeks.org/android-edittext-in-kotlin/
 	 
[4]	2019. Dynamic ImageView in kotlin. GeeksforGeeks. Retrieved November 27, 2022 from https://www.geeksforgeeks.org/dynamic-imageview-in-kotlin/
 	 
[5]	2020. How to set Elevation for Card in Android Compose? TutorialKart. Retrieved November 27, 2022 from https://www.tutorialkart.com/android-jetpack-compose/card-elevation/
 	 
[6]	Create dynamic lists with RecyclerView. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/layout/recyclerview
 	 
[7]	Build a responsive UI with ConstraintLayout. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/layout/constraint-layout
 	 
[8]	Create a card-based layout. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/layout/cardview
 	 
[9]	Linear layout. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/layout/linear
 	 
[10]	BottomNavigationView. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView
 	 
[11]	Add a floating action button. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/components/floating-action-button
 	 
[12]	Buttons. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/components/button
 	 
[13]	Menus. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/components/menus
 	 
[14]	Dialogs. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/develop/ui/views/components/dialogs
 	 
[15]	AlertDialog.Builder. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/reference/android/app/AlertDialog.Builder
 	 
[16]	Fragments. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/guide/fragments
 	 
[17]	Context. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/reference/android/content/Context
 	 
[18]	Get started with the Navigation component. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/guide/navigation/navigation-getting-started
 	 
[19]	Intents and intent filters. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/guide/components/intents-filters
 	 
[20]	Create app icons with Image Asset Studio. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/studio/write/image-asset-studio
 	 
[21]	Firebase authentication. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/auth/?hl=be
 	 
[22]	Add Firebase to your Android project. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/android/setup
 	 
[23]	Store data in ViewModel. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/codelabs/basic-android-kotlin-training-viewmodel
 	 
[24]	Store data in ViewModel. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/codelabs/basic-android-kotlin-training-viewmodel
 	 
[25]	Store data in ViewModel. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/codelabs/basic-android-kotlin-training-viewmodel
 	 
[26]	Connect your app to the Authentication Emulator. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/emulator-suite/connect_auth
 	 
[27]	Using async-await for database queries. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/63165599/using-async-await-for-database-queries
 	 
[28]	Techvidvan.com. Retrieved November 27, 2022 from https://techvidvan.com/tutorials/send-email-through-android/#:~:text=1%20Step%201%3A%20Launch%20your%20Android%20Studio.%202,paste%20the%20below%20code%20in%20your%20MainActivity.kt%20file.
 	 
[29]	Firestore whereEqualTo, orderBy and limit(1) not working. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/50305328/firestore-whereequalto-orderby-and-limit1-not-working
 	 
[30]	Firebase Cloud Firestore Query whereEqualTo for reference. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/51054114/firebase-cloud-firestore-query-whereequalto-for-reference
 	 
[31]	Firestore query with multiple where clauses based on parameters. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/51814343/firestore-query-with-multiple-where-clauses-based-on-parameters
 	 
[32]	How to change the border size(thickness) of checkbox in android kotlin? Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/58178874/how-to-change-the-border-sizethickness-of-checkbox-in-android-kotlin
 	 
[33]	How can I get .addOnCompleteListener() methods of Firebase inside a loop? Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/55038434/how-can-i-get-addoncompletelistener-methods-of-firebase-inside-a-loop
 	 
[34]	Add data to cloud firestore. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/firestore/manage-data/add-data
 	 
[35]	Delete data from cloud firestore. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/firestore/manage-data/delete-data
 	 
[36]	Updating the data on firebase android. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/41296416/updating-the-data-on-firebase-android
 	 
[37]	Cloud firestore data model. Firebase. Retrieved November 27, 2022 from https://firebase.google.com/docs/firestore/data-model
 	 
[38]	Android Kotlin - Firestore image upload and display. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/50471245/android-kotlin-firestore-image-upload-and-display
 	 
[39]	Two CardViews side by side. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/38686590/two-cardviews-side-by-side
 	 
[40]	OnSuccess. Kotlin. Retrieved November 27, 2022 from https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/on-success.html
 	 
[41]	Coroutines. Kotlin Help. Retrieved November 27, 2022 from https://kotlinlang.org/docs/coroutines-overview.html
 	 
[42]	Sign in ·. GitLab. Retrieved November 27, 2022 from https://git.cs.dal.ca/courses/2022-fall/csci4176_5708/groupproject/macs-10
 	 
[43]	How to show an empty view with a RecyclerView? Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/28217436/how-to-show-an-empty-view-with-a-recyclerview
 	 
[44]	How to use “getActivity()” in fragment using kotlin. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/63789381/how-to-use-getactivity-in-fragment-using-kotlin
 	 
[45]	Using .addOnSuccessListener to return a value for a private method. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/68757724/using-addonsuccesslistener-to-return-a-value-for-a-private-method
 	 
[46]	How to make a textview invisible. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/20159969/how-to-make-a-textview-invisible
 	 
[47]	When is adapter null in RecyclerView. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/36302764/when-is-adapter-null-in-recyclerview
 	 
[48]	Load Details view from Kotlin RecyclerView(cardview) onclick. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/60407716/load-details-view-from-kotlin-recyclerviewcardview-onclick
 	 
[49]	Activities and intents. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/codelabs/basic-android-kotlin-training-activities-intents
 	 
[50]	Remember and authenticate users. Android Developers. Retrieved November 27, 2022 from https://developer.android.com/training/id-auth

[51]    Why to use MVVM instead of MVC in android. Stack Overflow. Retrieved November 27, 2022 from https://stackoverflow.com/questions/56770241/why-to-use-mvvm-instead-of-mvc-in-android
