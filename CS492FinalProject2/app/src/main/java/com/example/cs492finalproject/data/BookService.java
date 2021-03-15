package com.example.cs492finalproject.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("?")
    Call<BookResponse> searchBooks(@Query(searchType) queryTerm);
}
