package pl.siusta.flashcards.service;

import org.springframework.stereotype.Service;
import pl.siusta.flashcards.model.FlashcardList;
import pl.siusta.flashcards.repo.FlashcardListRepo;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardListServiceImpl implements FlashcardListService {
    private FlashcardListRepo fListRepo;

    public FlashcardListServiceImpl(FlashcardListRepo fListRepo) {
        this.fListRepo = fListRepo;
    }

    @Override
    public List<FlashcardList> getAllFLists() {
        return fListRepo.findAll();
    }

    @Override
    public FlashcardList getFListById(Long id) {
        Optional<FlashcardList> tempFList = fListRepo.findById(id);
        FlashcardList fList = tempFList.get();
        return fList;
    }

    @Override
    public List<FlashcardList> getFListByName(String name) {
        return fListRepo.findAllByName(name);
    }

    @Override
    public List<FlashcardList> getFListByAuthor(String author) {
        return fListRepo.findAllByAuthor(author);
    }

    @Override
    public void addFList(FlashcardList flashcardList) {
        fListRepo.save(flashcardList);

    }

    @Override
    public void deleteFList(Long id) {
        fListRepo.deleteById(id);
    }

    @Override
    public void editFList(Long id, FlashcardList fEdit) {
        FlashcardList flashcardList = getFListById(id);
        flashcardList = fEdit;
        fListRepo.save(flashcardList);
    }
}
