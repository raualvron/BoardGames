package com.example.boardgames.Model;

public class Top100 {
    private String id, author, subject, postdate;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthor() {
        return author;
    }

    public String getPostdate() {
        return postdate;
    }

    public String getId() {
        return id;
    }
}
