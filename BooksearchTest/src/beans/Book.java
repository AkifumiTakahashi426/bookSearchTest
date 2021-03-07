package beans;

import org.json.JSONArray;

public class Book {
    // title書籍名
    String title;

    // publisher出版社
    String publisher;

    // authors著者
    String authors;

    // thumbnail
    String thumbnail;

    // ISBN
    String identifier;

    // price（無い可能性ありのため無し？）

    // 商品ページURL
    String selfLink;

    // 評価
    int fun;

    // コメント
    String summary;

    // 既読未読、初期値未読
    boolean alreadyRead = false;

    // 持ってるか買ってるか
    int have;

    // description商品説明
    String description;

    // コンストラクタ
    public Book(String title, String publisher, String authors, String thumbnail, String identifier,
            String selfLink, String description,int fun, String summary, boolean alreadyRead, int have) {
        this.title = title;
        this.publisher = publisher;
        this.authors = authors;
        this.thumbnail = thumbnail;
        this.identifier = identifier;
        this.selfLink = selfLink;
        this.fun = 3;
        this.summary = summary;
        this.alreadyRead = false;
        this.have = have;
        this.description = description;
    }

    //public String getAuthors() {
    //    return authors;
    //}

  //  public void setAuthors(String authors) {
  //      this.authors = authors;
  //  }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getThumbnail() {
        return thumbnail;
    }



    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public int getFun() {
        return fun;
    }

    public void setFun(int fun) {
        this.fun = fun;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isAlreadyRead() {
        return alreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        this.alreadyRead = alreadyRead;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String booktitle) {
        this.title = booktitle;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
