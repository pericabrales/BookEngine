package com.example.cs492finalproject.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    //call for a general search
    @GET("?")
    Call<BookResponse> searchBooksGeneral(@Query("q") String query);

    //call for a title search
    @GET("?")
    Call<BookResponse> searchBooksTitle(@Query("title") String query);

    //call for an author search
    @GET("?")
    Call<BookResponse> searchBooksAuthor(@Query("author") String query);
}
