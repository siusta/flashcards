# Aplikacja do nauki słówek
<p>Aplikacja umożliwia zapisanie do bazy danych listy słówek. 
Następnie listę można przeglądać (wyświetlenie całej listy słówek), 
uczyć się jej (aplikacja wyświetla po kolei każdą parę słówek, żeby przejść 
dalej należy wpisać prawidłowo drugie słowo z pary) i ćwiczyć znajomość słówek 
(wyświetlane jest pierwsze słowo z pary, należy wpisać drugie). </p>
<p>Listy wyświetlane są alfabetycznie, wyszukiwanie domyslnie według nazwy, z opcją wyszukiwania według autora.</p>
<p>Aplikacja wykonana jest w Spring Boot, front-end w Vaadinie.</p>

# Funkcjonalność
<p>Strona główna: </p>
![Home Page](/screenshots/home.PNG)<br><br>
<p>Widok listy:</p>
![View](/screenshots/view.PNG)<br><br>
<p>Tryb uczenia:</p>
![Learn](/screenshots/learn.PNG)<br><br>
<p>Tryb ćwiczeń:</p>
![Exercise](/screenshots/exercise.PNG)<br><br>
<p>Widok błędnych odpowiedzi:</p>
![Wrong](/screenshots/exercise wrong.PNG)<br><br>
<p>Wyszukiwanie wg nazwy:</p>
![Search name](/screenshots/search name.PNG)<br><br>
<p>Wyszukiwanie wg autora:</p>
![Search author](/screenshots/search author.PNG)<br><br>
<p>Widok tworzenia listy:</p>
![Add](/screenshots/add.PNG)<br><br>
<p>Potwierdzenie:</p>
![Added](/screenshots/added.PNG)<br><br>

# Zawartość repozytorium
<h3>Model</h3>
<h5>Flashcard</h5>
<p>Model pary słowek, w kodzie występuje jako <strong>fiszka</strong>. Ma dwie wartości: word i meaning.</p>
<h5>FlashcardList</h5>
<p>Model listy fiszek. Ma trzy wartości: name, author i flashcardsJSON (lista fiszek zapisana jako JSON).</p>

<h3>Repo</h3>
<h5>FlashcardListRepo</h5>
<p>Połączenie z repozytorium.</p>

<h3>Service</h3>
<h5>FlashcardListService i FlashcardListServiceImpl</h5>
<p>Klasy serwisowe obsługujące repozytorium.</p>
<p>Metody:</p>
<ul>
<li>getAllFLists() - pobiera wszystkie listy z repo</li>
<li>getFListById(Long id) - pobiera listę o danym id</li>
<li>getFListByName(String name) - pobiera wszystkie listy o danej nazwie</li>
<li>getFListByAuthor(String author) - pobiera wszystkie listy danego autora</li>
<li>addFList(FlashcardList flashcardList) - dodaje listę</li>
<li>deleteFList(Long id) - usuwa listę</li>
</ul>

<h3>Gui</h3>
<h5>About</h5>
<p>Krótki opis aplikacji.</p>

<h5>AddGui</h5>
<p>Widok tworzenia listy</p>
<p>Metody:</p>
<ul>
<li>fieldsLayout() - tworzy pola tekstowe (name, author, word, meaning)</li>
<li>makeList() - tworzy listę i zapisuje ją do bazy danych</li>
<li>confirmSave(String message) - tworzy widok potwierdzenia zpisu listy</li>
<li>convertFlashcardsToString(List<Flashcard> flashcards) - zapisuje fiszki w liście jako string o formie JSONa</li>
</ul>

<h5>HomeGui</h5>
<p>Widok strony głównej</p>
<p>Metody:</p>
<ul>
<li>search(List<FlashcardList>all) - tworzy pola wyszukiwania</li>
<li>byName(List<FlashcardList> all, String name) - wyszukuje wg nazwy</li>
<li>byAuthor(List<FlashcardList> all,String author) - wyszukuje wg autora</li>
<li>updateList(List<HorizontalLayout> allLists) - odświeża wyświetlane listy</li>
<li>oneList(FlashcardList f) - tworzy layout jednej listy</li>
<li>allLists(List<FlashcardList> flashcardLists) - pobiera i sortuje wszystkie listy</li>
<li>dataSetUp(Long id) - przygotowuje fiszki z listy</li>
<li>viewWords(String titleStr, List<Flashcard> fList) - tworzy widok wyświetlania wszystkich fiszek</li>
<li>learn(List<Flashcard> fList) - widok i funkcjonalność trybu uczenia się</li>
<li>exercise(List<Flashcard> fList) - widok i funkcjonalność trybu ćwiczeń</li>
<li>getFlashcardsFromJson(String json) - konwertuje JSONa na listę fiszek</li>
</ul>

<h5>NavBar</h5>
<p>Górne menu</p>
<p>Metody:</p>
<ul>
<li>addNavTab(String label, Class<? extends Component> target) - dodanie zakładki do menu</li>
<li>beforeEnter(BeforeEnterEvent event) - podkreślenie zakładki aktualnej strony</li>
</ul>

<h3>QuickApi</h3>
<p>Api do kontrolowania zawartości bazy danych przez Postmana.</p>
<p>Metody:</p>
<ul>
<li>addFList(@RequestBody FlashcardList flashcardList) - dodaje listy przez body</li>
<li>getAll() - pobiera wszystkie listy</li>
<li>delete(@PathVariable Long id) - usuwa listę o danym id</li>
</ul>

# Diagram klas
![Uml}(/screenshots/uml.PNG)





















