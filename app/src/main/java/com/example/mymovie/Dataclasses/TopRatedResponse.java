package com.example.mymovie.Dataclasses;

import java.util.List;

public class TopRatedResponse {

        Integer page;

        public Integer getPage() {
                return page;
        }

        public void setPage(Integer page) {
                this.page = page;
        }

        Integer total_pages;

        public Integer getTotal_pages() {
                return total_pages;
        }

        public void setTotal_pages(Integer total_pages) {
                this.total_pages = total_pages;
        }

        private List<Movie> results;

        public List<Movie> getResults() {
                return results;
        }

        public void setResults(List<Movie> results) {
                this.results = results;
        }
}
