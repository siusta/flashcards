package pl.siusta.flashcards.service;

import pl.siusta.flashcards.model.FlashcardList;

import java.util.List;

public interface FlashcardListService {
    List<FlashcardList> getAllFLists();
    FlashcardList getFListById(Long id);
    List<FlashcardList> getFListByName(String name);
    List<FlashcardList> getFListByAuthor(String author);
    void addFList(FlashcardList flashcardList);
    void deleteFList(Long id);
    void editFList(Long id, FlashcardList fEdit);

}
