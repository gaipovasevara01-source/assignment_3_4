# Music Streaming Platform

This project is a console-based Java application that simulates a simplified music streaming platform.  
It demonstrates object-oriented design, layered architecture, JDBC database interaction, validation, and structured exception handling.

The application manages users, artists, media content (songs and podcast episodes), and playlists stored in a PostgreSQL database.

Architecture Overview
The project follows a layered architecture:
- Controller layer – application entry point and demonstration logic (`Main`)
- Service layer – business rules and validation
- Repository layer – JDBC CRUD operations
- Model layer – domain entities and inheritance hierarchy
- Interfaces – validation and search contracts
- Utils – database connection, sorting, and reflection utilities

This separation ensures clean responsibilities and maintainable code.


Controller Responsibilities (Main.java)
The `Main` class acts as a controller that demonstrates how the system behaves in real use.

Entry point
`main()` runs the application and executes a sequence of example operations.

Service delegation
All actions are executed through services (`UserService`, `ArtistService`, `MediaService`, `PlaylistService`).  
The controller never accesses repositories directly. This guarantees that validation and business logic are always applied.

CRUD demonstration
The controller demonstrates standard CRUD workflows:
- Create users, artists, media content, and playlists
- Read and print media records
- Update an existing media item
- Attempt deletion of a missing resource to trigger an exception

Polymorphism
Media is handled through the abstract base type `MediaContent`.  
Concrete implementations include:
- `Song`
- `PodcastEpisode`

Methods such as `play()` and `displayInfo()` are invoked through the abstract reference but execute subclass-specific behavior.

Exception handling
The controller intentionally triggers and catches custom exceptions:
- `InvalidInputException` – invalid data (e.g., duration ≤ 0)
- `DuplicateResourceException` – duplicate insertion
- `ResourceNotFoundException` – missing database resource

This demonstrates safe error handling without crashing the application.

Console output
All results are printed to the command line to simulate request/response behavior and make system behavior visible.


Database
The database schema is defined in `schema.sql` and automatically initialized at startup.

Tables include:
- users
- artists
- media
- playlists
- playlist_items

The schema uses foreign keys, constraints, and unique rules to maintain consistency.  
Sample data is pre-inserted to allow immediate testing.


Advanced Features
The project includes several advanced Java features:

- Generics via a generic CRUD repository interface
- Interfaces with default and static methods
- Lambda expressions for sorting media
- Reflection utilities to inspect class metadata
- Dependency inversion through repository interfaces

