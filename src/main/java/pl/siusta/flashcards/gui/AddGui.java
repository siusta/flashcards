package pl.siusta.flashcards.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import pl.siusta.flashcards.model.Flashcard;
import pl.siusta.flashcards.model.FlashcardList;
import pl.siusta.flashcards.service.FlashcardListService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Route("add")
public class AddGui extends HorizontalLayout {
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
        setMargin(true);
        add(navbar);
        fieldsLayout();
        makeList();
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    public void fieldsLayout(){
        VerticalLayout left1 = new VerticalLayout();
        VerticalLayout left2 = new VerticalLayout();
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(confirm, save);
        left1.add(name, author);
        left2.add(word, meaning, buttons);
        add(left1, left2);
    }

    public void makeList(){
        List<Flashcard> flashcards = new ArrayList<>();
        VerticalLayout right = new VerticalLayout();
        right.setAlignItems(Alignment.CENTER);
        getElement().getStyle().set("margin-top","50px");
        confirm.addClickListener(buttonClickEvent -> {
            flashcards.add(new Flashcard(word.getValue().trim(),meaning.getValue().trim()));
            Label list = new Label(word.getValue()+" -- "+meaning.getValue());
            right.add(list);
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
            if(fService.addFList(flashcardList)){
                confirmSave("Word list saved.");
                right.removeAll();
                flashcards.clear();
            } else{
                confirmSave("Something went wrong. Try again.");
            };
        });
        setFlexGrow(1,right);
        add(right);
    }

    public void confirmSave(String message){
        Dialog dialog = new Dialog();
        dialog.open();
        VerticalLayout vlayout = new VerticalLayout();
        Label label = new Label(message);
        Button button = new Button("ok",buttonClickEvent -> dialog.close());
        vlayout.add(label, button);
        vlayout.setPadding(true);
        vlayout.setSpacing(true);
        vlayout.setAlignItems(Alignment.CENTER);
        dialog.add(vlayout);
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
