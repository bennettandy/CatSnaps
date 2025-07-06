# CatSnaps
CatSnaps is a simple Android app that allows users to browse cat breeds and view images of their selected breed,
powered by [The Cat API](https://thecatapi.com). The app features a paginated grid of cat images, built using Jetpack Compose and MVI architecture.

## AI
Utilised grok AI to generate place holder images and come up with some quirky error messages and demo data for Previews.

## Features
- **Breed Selection**: Search and select from a list of cat breeds using a filterable search bar.
- **Paginated Image Display**: View images of the selected breed in a lazy-loaded grid, with pagination support.
- **In-Memory Caching**: Cache breed data for faster searches (image caching handled by Coil).
- **Animations**: Smooth fade-in/out transitions for the splash screen and list item animations.

## Tech Stack
### Libraries
- **Retrofit + Gson**: For API calls to The Cat API.
- **Coil**: For efficient image loading and caching in Jetpack Compose.
- **Hilt**: For dependency injection.
- **Paging 3**: For paginated loading of cat images in a `LazyVerticalGrid`.
- **Kotlin Coroutines + Flow**: For asynchronous operations and reactive data streams.
- **Orbit MVI**: For managing state and side effects in a unidirectional data flow.
- **Jetpack Compose**: For building the modern, declarative UI.
- **Lottie**: For adding engaging animations (e.g., splash screen or error screen).

### Architecture
- **Clean Architecture**: Separates concerns into presentation, domain, and data layers.
- **MVI (Model-View-Intent)**: Uses Orbit MVI for predictable state management and side effects.
- **Domain Layer**:
    - `CatBreed`: Represents a cat breed with `id`, `name`, `temperament`, and `description`.
    - `CatImage`: Represents an image with `id`, `url`, and optional `page` for pagination.
- **Data Layer**:
    - `CatApiService`: Retrofit interface for The Cat API endpoints (`/v1/breeds`, `/v1/images/search`).
    - `BreedDto` and `ImageDto`: Data transfer objects generated using a JSON-to-Kotlin tool.
    - `CatImagePagingSource`: Paging 3 source for loading images by breed.
    - `RequestInterceptor`: Adds the API key to all API requests via `BuildConfig`.
- **Presentation Layer**:
    - `SplashScreen`: Displays the app logo with fade-in/out animations.
    - `BreedSearch`: Filterable search bar and `LazyColumn` for breed selection.
    - `CatImageList`: `LazyVerticalGrid` for paginated cat images.
    - `CatSnapsViewModel`: Manages state (`CatSnapsUiState`) and side effects (`CatSnapsSideEffect`) with Orbit MVI.

## Getting Started
### Prerequisites
- Android Studio (latest stable version)
- Kotlin 1.9.x or higher
- JDK 17
- An API key from [The Cat API](https://thecatapi.com)

### Setup
1. **Unzip the Repository**:

2. **Add API Key**:
    - Sign up for an API key at [The Cat API](https://thecatapi.com).
    - Add the key to `local.properties` in the project root:
      ```properties
      catApiKey=YOUR_API_KEY
      ```

3. **Build the Project**:
    - Open the project in Android Studio.
    - Sync the project with Gradle (File > Sync Project with Gradle Files).
    - Build and run on an emulator or device (API 21+).


### Usage
1. Launch the app to see the splash screen with the "CatSnaps" title and logo.
2. Navigate to the breed search screen, where you can filter breeds by typing in the search bar.
3. Select a breed to view a paginated grid of cat images.

### Acknowledgments
- [The Cat API](https://thecatapi.com) for providing cat breed and image data.
- Jetpack Compose and Orbit MVI for enabling a modern, reactive UI.


![My Image](images/cat_snap_logo.jpg)