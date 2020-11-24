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
import com.vaadin.flow.component.progressbar.ProgressBar;
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
    Dialog dialog = new Dialog();
    ProgressBar progressBar = new ProgressBar();

    @Autowired
    public HomeGui(FlashcardListService fService) {
        this.fService = fService;
        add(navbar);
        setMargin(true);
        allLists();
    }


    public void allLists(){
        List<FlashcardList> flashcardLists = fService.getAllFLists();
        for (FlashcardList f: flashcardLists
        ) {
            HorizontalLayout hlayout = new HorizontalLayout();
            hlayout.setWidth("97%");
            hlayout.getStyle().set("border", "1px solid #9E9E9E");
            Label name = new Label(f.getName());
            Button view = new Button("View",buttonClickEvent -> {
                try {
                    viewWords(dataSetUp(f.getId()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            Button learn = new Button("Learn",buttonClickEvent -> {
                try {
                    learn(dataSetUp(f.getId()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            Button exercise = new Button("Exercise", buttonClickEvent -> {
                try {
                    exercise(dataSetUp(f.getId()));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            hlayout.setFlexGrow(1,name);
            hlayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
            hlayout.add(name,view,learn,exercise);
            hlayout.setPadding(true);
            hlayout.setMargin(true);
            add(hlayout);
        }

    }

    public List<Flashcard> dataSetUp(Long id) throws JsonProcessingException {
        FlashcardList flashcardList = fService.getFListById(id);
        List<Flashcard> fList = getFlashcardsFromJson(flashcardList.getFlashcardsJSON());
        return fList;
    }

    public void viewWords(List<Flashcard> fList){
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setAlignItems(Alignment.CENTER);
        dialog.open();
        for(int i=0; i<fList.size();i++){
            Label wordPair = new Label(fList.get(i).getWord()+" -- "+fList.get(i).getMeaning());
            vlayout.add(wordPair);
        }
        Button button = new Button("ok",buttonClickEvent -> {
            dialog.close();
        });
        vlayout.add(button);
        dialog.add(vlayout);
    }

    public void learn(List<Flashcard> fList) throws JsonProcessingException {
        dialog.open();
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setAlignItems(Alignment.CENTER);
        AtomicInteger i = new AtomicInteger(0);
        AtomicBoolean clicked = new AtomicBoolean(true);
        Label word = new Label(fList.get(i.get()).getWord());
        Label meaning = new Label(fList.get(i.get()).getMeaning());
        TextField repeat = new TextField();
        Button next = new Button("next",buttonClickEvent -> {
            if(clicked.get()&&(i.get()<=fList.size()+2)){
                if(fList.get(i.get()).getMeaning().equals(repeat.getValue().trim())) {
                    i.getAndIncrement();
                    clicked.set(false);
                }
             //   progressBar.setValue((double)i.get()/fList.size()+2);
            } else {
                word.setText(fList.get(i.get()).getWord());
                meaning.setText(fList.get(i.get()).getMeaning());
                repeat.clear();
                clicked.set(true);
            }
        });
        vlayout.add(word,meaning,repeat,next, progressBar);
        dialog.add(vlayout);
        next.addClickShortcut(Key.ENTER);
        next.setAutofocus(true);
    }

    public void exercise(List<Flashcard> fList) throws JsonProcessingException {
        dialog.open();
        VerticalLayout vlayout = new VerticalLayout();
        vlayout.setAlignItems(Alignment.CENTER);
        Collections.shuffle(fList);
        AtomicInteger i = new AtomicInteger(0);
        AtomicInteger ok= new AtomicInteger();
        AtomicBoolean clicked = new AtomicBoolean(true);
        Label word = new Label(fList.get(i.get()).getWord());
        TextField meaning = new TextField();
        Label score = new Label();
        Button confirm = new Button("ok",buttonClickEvent -> {
            if(clicked.get()&&(i.get()<=fList.size()+2)){
                if(fList.get(i.get()).getMeaning().equals(meaning.getValue().trim())) {
                    ok.getAndIncrement();
                }
                score.setText("Your score: "+ok+"/"+fList.size());
                i.getAndIncrement();
              //  progressBar.setValue((double) (i.get() / fList.size() + 2));
                clicked.set(false);
            } else {
                word.setText(fList.get(i.get()).getWord());
                meaning.clear();
                clicked.set(true);
            }
        });
        confirm.addClickShortcut(Key.ENTER);
        vlayout.add(word,meaning,confirm,score);
        dialog.add(vlayout);
        meaning.setAutofocus(true);
    }

    public List<Flashcard> getFlashcardsFromJson(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Flashcard> flashcards = objectMapper.readValue(json, new TypeReference<List<Flashcard>>() {
        });
        return flashcards;
    }



}
