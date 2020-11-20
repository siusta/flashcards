package pl.siusta.flashcards.gui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;

public class NavBar extends AppLayout implements BeforeEnterObserver {
    private Map<Class<? extends Component>, Tab> navigationTargetToTab = new HashMap<>();
    private Tabs tabs = new Tabs();

    public NavBar() {
        addNavTab("Home", HomeGui.class);
        addNavTab("Add new word list", AddGui.class);
        addToNavbar(tabs);


    }

    private void addNavTab(String label, Class<? extends Component> target) {
        Tab tab = new Tab(new RouterLink(label, target));
        navigationTargetToTab.put(target, tab);
        tabs.add(tab);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        tabs.setSelectedTab(navigationTargetToTab.get(event.getNavigationTarget()));
    }
}
