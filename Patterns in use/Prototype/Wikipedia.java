package HomeWork.HomeWork8.Task2.Prototype;

import java.util.HashMap;
import java.util.Map;

public class Wikipedia {
    private Map<Integer, Article> articleList = new HashMap<>();
    private Map<Integer, Article> articlesCache = new HashMap<>();

    public void changeArticleById(int id, String newContent) throws CloneNotSupportedException {
        Article article = findArticleById(id);
        if (article == null) {
            System.out.println("No such article");
            return;
        }

        articlesCache.put(id, (Article) article.clone());
        article.content = newContent;
    }

    private void restoreArticleById(int id) {
        if (!articlesCache.containsKey(id)) return;
        articleList.put(id, articlesCache.remove(id));
    }

    private Article findArticleById(int id) {
        return articleList.get(id);
    }
}
