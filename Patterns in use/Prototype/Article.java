package HomeWork.HomeWork8.Task2.Prototype;

import java.util.Date;

public class Article implements Cloneable {
    int id;
    String title;
    String content;
    Date postDate;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Article temp = new Article();
        temp.content = content;
        temp.id = id;
        temp.title = title;
        temp.postDate = postDate;
        return temp;
    }
}
