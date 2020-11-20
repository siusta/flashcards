package pl.siusta.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.siusta.flashcards.model.FlashcardList;
import pl.siusta.flashcards.service.FlashcardListService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuickApi{
    FlashcardListService fService;

    @Autowired
    public QuickApi(FlashcardListService fService) {
        this.fService = fService;
    }

    @PostMapping("/add")
    public void addFList(@RequestBody FlashcardList flashcardList){
        fService.addFList(flashcardList);
    }

    @GetMapping("/all")
    public List<FlashcardList> getAll(){
        List<FlashcardList> fList = fService.getAllFLists();
        return fList;
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        fService.deleteFList(id);
    }

}
