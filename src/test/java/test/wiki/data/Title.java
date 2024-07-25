package test.wiki.data;

public enum Title {
     Форум ("Форум"),
     Справка ("Справка");
    public final String description;

    Title(String description) {
        this.description = description;
    }
}
