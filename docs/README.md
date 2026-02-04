Music Streaming Platform 


Explanation of Controller Responsibilities:

Acts as the entry point:
main() runs the application and demonstrates all main features.

Calls the service layer:
All actions are executed through services (e.g., UserService, ArtistService, MediaService, PlaylistService) so that validation and business rules are applied before database operations.


Demonstrates CRUD operations:
Create: create a user, artists, and media content (songs and podcast episodes)
Read: retrieve and print all media records from the database
Update: update an existing media item (example: change title and duration)
Delete: try deleting a non-existing media item to demonstrate error handling (and/or delete an existing item if needed)


Shows polymorphism:
Media is stored and processed using MediaContent references (abstract base type), while the actual objects are subclasses (Song or PodcastEpisode).
Methods like play() and displayInfo() are called through MediaContent, but execute subclass-specific behavior.


Triggers validation and custom exceptions:
The controller intentionally triggers and catches custom exceptions to prove correct error handling:
Invalid input (example: duration ≤ 0) → InvalidInputException
Duplicate resource (example: adding an existing artist or playlist item again) → DuplicateResourceException
Resource not found (example: deleting media with non-existing ID) → ResourceNotFoundException


Prints results to CLI:
The console output acts as “example requests/responses”, showing successful operations and handled errors clearly.
