package org.zk;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

/**
 * Created by zhangkang on 2018/4/3.
 */
public class User {
    private int id;
//    @JsonProperty("usernamex")
    private String username;

    @JacksonXmlElementWrapper(localName = "moviesLists", useWrapping = true)
    @JacksonXmlProperty(localName = "m")
    private List<String> movies;

    public List<String> getMovies() {
        return movies;
    }

    public void setMovies(List<String> movies) {
        this.movies = movies;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
