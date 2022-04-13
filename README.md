# ArtCollectionApp

This application uses the Metropolitan Museum of Art API
This application let the user browse the collection of art either by search criteria or by department and then diplays the results of either in a list. In the displayed list the user can click on a single piece of art for more information on the particular piece of art and possibly more photos if available.

This application uses the MVVM architecture to utilize the viewModel to retain data between fragments as well as being able to maintain the data through configuration changes. The UI is updated from the viewModel using liveData which is updated when data is requested from the user's actions. The viewModel contains all the business logic for the applcation along with necessary data calls that are directed to the repository.

Libraries used:
Retrofit2 - for the framework to make network calls.
I used Retrofit due to its ease of setup and the use of annotations for network calls.

OkHttp3 - as the HTTP client to be used by Retrofit.
I used OkHttp since it is the industry standard for network calls and for its use with interceptors.

Moshi - for parsing JSON responses from API calls to POJOs (Data Classes)
I used Moshi due to the speed of the parsing of JSON objects, support for Kotlin language features, lightweight, and simple API.

Navigation - Used to simplify user flow through the application and also the use of the bottom navigation bar for user interaction.

lifecycle - viewModel - for use with the viewModel which is one of the key aspects of the MVVM architecture.

coroutines - used for asynchronous tasks (like API calls) in a different thread than the main Thread to prevent ANR.

Hilt - for dependency injection which allows the creation of objects in a single location and injected anywhere in the code that it is needed.
I used Hilt because it is easier to implement than Dagger (even though it is built on top of Dagger) and can still be used by Java code. More used than Koin since it can only be used with Kotlin.

Glide - for retrieving images from a URL and inserting them into a particular view.
I used Glide for its fast and efficient loading of images along with its speed of decoding images.

MockK - I used mockK for unit testing.
I chose mockK since it is used exclusively for Kotlin and takes into account all of Kotlin's power and ease of use.


State Of The Project:
It is fully operational in all aspects except configuration changes. On the display results fragment when rotated it maintains the position of the scroll but that becomes the top of the page instead of the Nth item. The applications ViewModel and Repository have been as tested as possible (private functions were unable to be tested).

Future of the Application:
Due to the API it is impossible to speed up the loading speeds (unless the API changes in the future) which would be ideal. When someone clicks on a piece of art in the results fragment and are navigated to the additional details fragment I would like to make each picture clickable. Once the picture is clicked I want it to navigate to another fragment where the image takes up the entire screen showcasing the art which is the centerpiece of the application. I would like to add a database to the application where the user can save their favorite art and have another navigation point in the bottom navigation to show the users favorite art.




