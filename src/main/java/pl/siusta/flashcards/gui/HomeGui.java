package pl.siusta.flashcards.gui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.siusta.flashcards.model.Flashcard;
import pl.siusta.flashcards.model.FlashcardList;
import pl.siusta.flashcards.service.FlashcardListService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Route("")
public class HomeGui extends VerticalLayout {
    FlashcardListService fService;
    NavBar navbar = new NavBar();

    @Autowired
    public HomeGui(FlashcardListService fService) {
        this.fService = fService;
        add(navbar);
        allLists();
    }


    public void allLists(){
        List<FlashcardList> flashcardLists = fService.getAllFLists();
        for (FlashcardList f: flashcardLists
        ) {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidth("97%");
            layout.getStyle().set("border", "1px solid #9E9E9E");

            Label name = new Label(f.getName());
            Button learn = new Button("Learn",buttonClickEvent -> {
                try {
                    learn(f.getId());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            Button exercise = new Button("Exercise", buttonClickEvent -> {
                try {
                    exercise(f.getId());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            layout.setFlexGrow(1,name);
            layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            layout.add(name,learn,exercise);
            layout.setPadding(true);
            layout.setMargin(true);
            add(layout);
        }

    }

    public void learn(Long id) throws JsonProcessingException {
        Dialog dialog = new Dialog();
        dialog.open();
        VerticalLayout layout = new VerticalLayout();
        FlashcardList flashcardList = fService.getFListById(id);
        List<Flashcard> fList = getFlashcardsFromJson(flashcardList.getFlashcardsJSON());

        AtomicInteger i = new AtomicInteger(0);
        AtomicBoolean clicked = new AtomicBoolean(true);
        Label word = new Label(fList.get(i.get()).getWord());
        Label meaning = new Label(fList.get(i.get()).getMeaning());
        TextField repeat = new TextField();
        Button next = new Button("next",buttonClickEvent -> {
            if(clicked.get()&&(i.get()<=fList.size()+2)){
                if(fList.get(i.get()).getMeaning().equals(repeat.getValue())) {
                    i.getAndIncrement();
                    clicked.set(false);
                }
            } else {
                word.setText(fList.get(i.get()).getWord());
                meaning.setText(fList.get(i.get()).getMeaning());
                repeat.clear();
                clicked.set(true);
            }
        });
        layout.add(word,meaning,repeat,next);
        dialog.add(layout);
        next.addClickShortcut(Key.ENTER);
        next.setAutofocus(true);
    }

    public void exercise(Long id) throws JsonProcessingException {
        Dialog dialog = new Dialog();
        dialog.open();
        VerticalLayout layout = new VerticalLayout();
        FlashcardList flashcardList = fService.getFListById(id);
        List<Flashcard> fList = getFlashcardsFromJson(flashcardList.getFlashcardsJSON());
        Collections.shuffle(fList);

        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger ok= new AtomicInteger();
        AtomicBoolean clicked = new AtomicBoolean(true);
        Label word = new Label(fList.get(i.get()).getWord());
        TextField meaning = new TextField();
        Label score = new Label();
        Button confirm = new Button("ok",buttonClickEvent -> {
            if(clicked.get()&&(i.get()<=fList.size()+2)){
                if(fList.get(i.get()).getMeaning().equals(meaning.getValue())) {
                    ok.getAndIncrement();
                }
                score.setText("Your score: "+ok+"/"+fList.size());
                i.getAndIncrement();
                clicked.set(false);
            } else {
                word.setText(fList.get(i.get()).getWord());
                meaning.clear();
                clicked.set(true);
            }
        });
        confirm.addClickShortcut(Key.ENTER);
        layout.add(word,meaning,confirm,score);
        dialog.add(layout);
        meaning.setAutofocus(true);
    }

    public List<Flashcard> getFlashcardsFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Flashcard> flashcards = objectMapper.readValue(json, new TypeReference<List<Flashcard>>() {
        });
        return flashcards;
    }



}
