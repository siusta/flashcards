package pl.siusta.flashcards.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import pl.siusta.flashcards.model.Flashcard;
import pl.siusta.flashcards.model.FlashcardList;
import pl.siusta.flashcards.service.FlashcardListService;

import java.util.ArrayList;
import java.util.List;

@Route("add")
public class AddGui extends VerticalLayout {
    FlashcardListService fService;
    NavBar navbar = new NavBar();

    TextField name = new TextField("name");
    TextField author = new TextField("author");
    TextField word = new TextField("word");
    TextField meaning = new TextField("meaning");
    Button confirm = new Button("ok");
    Button save = new Button("save");

    public AddGui(FlashcardListService fService) {
        this.fService = fService;
        setPadding(true);
        setMargin(true);
        add(navbar);
        fieldsLayout();
        makeList();
    }

    public void fieldsLayout(){
        HorizontalLayout horizontal1 = new HorizontalLayout();
        horizontal1.add(name, author);
        HorizontalLayout horizontal2 = new HorizontalLayout();
        horizontal2.add(word, meaning);
        HorizontalLayout horizontal3 = new HorizontalLayout();
        horizontal3.add(confirm, save);
        add(horizontal1,horizontal2,horizontal3);
    }

    public void makeList(){
        List<Flashcard> flashcards = new ArrayList<>();

        confirm.addClickListener(buttonClickEvent -> {
            flashcards.add(new Flashcard(word.getValue(),meaning.getValue()));
            Label list = new Label(word.getValue()+" -- "+meaning.getValue());
            add(list);
            word.clear();
            meaning.clear();
        });
        save.addClickListener(buttonClickEvent -> {
            String jsonFlashcards="Flashcard";
            try {
                jsonFlashcards = convertFlashcardsToString(flashcards);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            FlashcardList flashcardList = new FlashcardList(name.getValue(),author.getValue(),jsonFlashcards);
            fService.addFList(flashcardList);
        });

    }

    public String convertFlashcardsToString(List<Flashcard> flashcards) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String temp="[";
        for (Flashcard f:flashcards
        ) {
            temp = temp + objectMapper.writeValueAsString(f) + ",";
        }
        String json = temp.substring(0,temp.length()-1) + "]";
        return json;
    }

}
