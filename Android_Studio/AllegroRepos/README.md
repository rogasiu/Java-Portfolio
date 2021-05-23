# AllegroRepos
Zadanie kwalifikacyjne

### Uruchomienie

- Aby uruchomić plik apk najpierw należy go pobrać z repozytorium, a następnie przenieść na urządzenie z systemem Android i zainstalować. Należy pamiętać o tym, aby pozwolić na instalowanie aplikacji z nieznanego źródła. Plik ten znajduje się w folderze Plik APK.
- Aby uruchomić projekt w symulatorze w Android Studio należy pobrać repozytorium, uruchomić środowisko Android Studio, otworzyć istniejący projekt, wczytać projekt AllegroRepos, wybrać jedno z zainstalowanych wirtualnych urządzeń (lub jeżeli nie jest zainstalowane, to zrobić to) i uruchomić aplikację.

### Uwagi/Ważne informacje

Do poprawnego działania aplikacji potrzebne jest połączenie internetowe. Jeżeli go nie będzie, aplikacja załaduje się i poinformuje użytkownika o braku wymaganego połączenia internetowego. Jeżeli w trakcie użytkowania aplikacji połączenie zostanie zerwane, to przy próbie wyświetlenia szczegółów repozytorium pojawi się odpowiedni komunikat, a użytkownik pozostanie na ekranie listy wszystkich repozytoriów.

GitHub Api pozwala nieuwierzytelnionym klientom na wysłanie 60 żądań na godzinę. Jeżeli liczba ta zostanie przekroczona, to zwrócony zostanie kod 403 informujący o odmowie spełnienia żądania. W celu testowania aplikacji został użyty GitHub Token pozwalający na wysłanie 1000 żądań na godzinę. Jest to token prywatny, dlatego nie został on udostępniony w ukończonej aplikacji. Użytkownik zostanie powiadomiony o odmowie dostępu, jeżeli kod odpowiedzi HTTP będzie inny niż kod prawidłowego wykonania żądania, czyli 200.

W dokumentacji GitHub Api wspomniane jest, że domyślnie na stronie wyświetlane jest do 30 elementów. Można to zmienić za pomocą parametru per_page, jednak dalej istnieje ograniczenie do 100 elementów. Na profilu Allegro znajduje się 85 repozytoriów, więc mieszczą się one w granicy 100. W przyszłości aplikację można rozwinąć i w prosty sposób za pomocą parametrów page i per_page pobierać wszystkie potrzebne repozytoria, których liczba jest większa od 100.  

### Praca nad projektem
Wykonanie tego zadania było dla mnie bardzo przyjemne. Najważniejsze było stworzenie planu pracy, czyli zebrania potrzebnych informacji, w jaki sposób korzystać z GitiHub Api, jak połączyć się z konkretnym profilem oraz w jaki sposób pobrać i przetworzyć dane, a następnie w przejrzysty sposób wyświetlić je. Kilka z tych rzeczy było dla mnie zupełnie nowych, dlatego też w trakcie pisania kodu wynikło parę problemów, z którymi udało mi się poradzić. 

Aplikacja została napisana w taki sposób, aby w przyszłości bardzo łatwo można było ją rozwinąć. W przyszłości projekt można rozszerzyć o wyszukiwanie różnych profilów, dodać informacje o tych profilach, rozszerzyć szczegółowość informacji o repozytoriach oraz podawać łączną ilość gwiazdek wszystkich repozytoriów.  

