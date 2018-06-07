package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.android.popularmovies.Data.MovieData;
import com.example.android.popularmovies.Data.MovieDetails;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String MOVIE_EXTRA_POSITION = "movie_extra_position";
    private static final int MOVIE_NOT_FOUND_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d("MovieDetailActivity", "Intent not available");
            return;
        }

        int position = intent.getIntExtra(MOVIE_EXTRA_POSITION, MOVIE_NOT_FOUND_POSITION);

        if (position == MOVIE_NOT_FOUND_POSITION) {
            Log.d("MovieDetailActivity", "Movie data not available");
            finish();
            return;
        }

        MovieDetails movieDetail = MovieData.getInstance().movieDetailsArray.get(position);
        if (movieDetail == null) {
            Log.d("MovieDetailActivity", "Movie data could not be extracted");
            finish();
            return;
        }

        ImageView movieBannerImage = (ImageView) findViewById(R.id.movie_banner_iv);
        Picasso.with(this)
                .load(movieDetail.getBackdropPath())
                .into(movieBannerImage);
        setTitle(movieDetail.getMovieTitle());

        TextView movieOrgTitle = (TextView) findViewById(R.id.org_title_tv);
        movieOrgTitle.setText(movieDetail.getOriginalTitle());

        TextView movieAvgRating = (TextView) findViewById(R.id.average_rating_tv);
        String voteAvgText = String.valueOf(movieDetail.getVoteAverage()) +
                getString(R.string.avg_vote_out_of);
        movieAvgRating.setText(voteAvgText);

        RatingBar movieRatingBar = (RatingBar) findViewById(R.id.average_rating_rb);
        movieRatingBar.setRating((float) movieDetail.getVoteAverage()/2);

        TextView movieReleaseDate = (TextView) findViewById(R.id.release_date_tv);
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        movieReleaseDate.setText(dateFormat.format(movieDetail.getReleaseDate()));

        TextView movieSynopsis = (TextView) findViewById(R.id.synopsis_tv);
        movieSynopsis.setText(movieDetail.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
