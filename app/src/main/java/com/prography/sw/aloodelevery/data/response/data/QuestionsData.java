package com.prography.sw.aloodelevery.data.response.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prography.sw.aloodelevery.model.Question;

import java.io.Serializable;
import java.util.List;

public class QuestionsData implements Serializable {

    @SerializedName("current_page")
    @Expose
    private int currentPage;
    @SerializedName("items")
    @Expose
    private List<Question> questions = null;
    @SerializedName("per_page")
    @Expose
    private int perPage;
    @SerializedName("total")
    @Expose
    private int total;
    private final static long serialVersionUID = 2215131880746103846L;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}