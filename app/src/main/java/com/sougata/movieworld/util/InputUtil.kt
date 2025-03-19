package com.sougata.movieworld.util

import androidx.lifecycle.MutableLiveData
import com.github.javafaker.Faker
import com.sougata.movieworld.models.Genre
import com.sougata.movieworld.models.MyMovie
import com.sougata.movieworld.models.PopularList
import java.text.DecimalFormat
import kotlin.random.Random

object InputUtil {

    fun getAllGenresList() =
        listOf(
            Genre("Action"),
            Genre("Adult"),
            Genre("Adventure"),
            Genre("Animation"),
            Genre("Biography"),
            Genre("Comedy"),
            Genre("Crime"),
            Genre("Documentary"),
            Genre("Drama"),
            Genre("Family"),
            Genre("Fantasy"),
            Genre("Film-Noir"),
            Genre("Game-Show"),
            Genre("History"),
            Genre("Horror"),
            Genre("Music"),
            Genre("Musical"),
            Genre("Mystery"),
            Genre("News"),
            Genre("Reality-TV"),
            Genre("Romance"),
            Genre("Sci-Fi"),
            Genre("Short"),
            Genre("Sport"),
            Genre("Talk-Show"),
            Genre("Thriller"),
            Genre("War"),
            Genre("Western")
        )


    fun getAllListNames() =
        listOf(
            PopularList(
                "top_boxoffice_200", "Most Popular Movies", MutableLiveData<List<MyMovie>>()
            ),
            PopularList(
                "most_pop_series", "Most Popular Series", MutableLiveData<List<MyMovie>>()
            ),
            PopularList(
                "top_boxoffice_last_weekend_10",
                "Top Box Office Last Week",
                MutableLiveData<List<MyMovie>>()
            ),
            PopularList(
                "top_rated_english_250",
                "Top Rated English Movies",
                MutableLiveData<List<MyMovie>>()
            ),
            PopularList(
                "top_rated_lowest_100", "Top Rated Lowest 100", MutableLiveData<List<MyMovie>>()
            ),
            PopularList(
                "top_rated_series_250", "Top Rated Series", MutableLiveData<List<MyMovie>>()
            )
        )

    fun ratingCountFormatter(count: Int): String {
        val df = DecimalFormat("#.#")
        return when {
            count >= 1_000_000_000 -> df.format(count / 1_000_000_000.0) + "B"
            count >= 1_000_000 -> df.format(count / 1_000_000.0) + "M"
            count >= 1_000 -> df.format(count / 1_000.0) + "K"
            else -> count.toString()
        }
    }

    fun getRandomImageUrl(width: Int = 300, height: Int = 300): String {
        val num = Random.nextInt(1, 1001)

        return "https://picsum.photos/id/$num/$width/$height"
    }

    fun getFakeMovieList(): List<MyMovie> {

        val faker = Faker()
        val fakeName = faker.name().name()

        return listOf(
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid()),
            MyMovie(fakeName, 2069, fakeName, fakeName, getRandomImageUrl(), faker.idNumber().valid())
        )
    }
}
