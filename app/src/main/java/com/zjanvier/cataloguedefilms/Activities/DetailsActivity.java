package com.zjanvier.cataloguedefilms.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.zjanvier.cataloguedefilms.Model.Movie;
import com.zjanvier.cataloguedefilms.R;
import com.zjanvier.cataloguedefilms.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {
    private Movie movie;
    private TextView movieTitle;
    private ImageView movieImage;
    private TextView movieYear;
    private TextView director;
    private TextView actors;
    private TextView category;
    private TextView rating;
    private TextView writers;
    private TextView plot;
    private TextView boxOffice;
    private TextView runtime;
    private RequestQueue queue;
    private String movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        queue = Volley.newRequestQueue(this);

        movie = (Movie) getIntent().getSerializableExtra("movie"); // récupérer tous les éléments
        movieId = movie.getImdbId();

        setUpUI();
        getMovieDetails(movieId);

    }

    private void setUpUI() {

        movieTitle = findViewById(R.id.movieTitleIDDets);
        movieImage =findViewById(R.id.movieImageIDDets);
        movieYear = findViewById(R.id.movieReleaseIDDets);
        director = findViewById(R.id.directedByDet);
        category = findViewById(R.id.movieCatIDDet);
        rating = findViewById(R.id.movieRatingIDDet);
        writers = findViewById(R.id.writersDet);
        plot = findViewById(R.id.plotDet);
        boxOffice = findViewById(R.id.boxOfficeDet);
        runtime = findViewById(R.id.runtimeDet);
        actors = findViewById(R.id.actorsDet);
    }

    private void getMovieDetails(String id) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL + id + Constants.API_KEY,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    if (response.has("Ratings")) { //n'existe pas pour certains cas
                        JSONArray ratings = response.getJSONArray("Ratings");

                        String source = null;
                        String value = null;
                        if (ratings.length() > 0) {

                            JSONObject mRatings = ratings.getJSONObject(ratings.length() - 1);
                            source = mRatings.getString("Source");
                            value = mRatings.getString("Value");
                            rating.setText(source + " : " + value);

                        }else {
                            rating.setText("Ratings: N/A");
                        }

                        movieTitle.setText(response.getString("Title"));
                        movieYear.setText("Released: " + response.getString("Released"));
                        director.setText("Director: " + response.getString("Director"));
                        writers.setText("Writers: " + response.getString("Writer"));
                        plot.setText("Plot: " + response.getString("Plot"));
                        runtime.setText("Runtime: " + response.getString("Runtime"));
                        actors.setText("Actors: " + response.getString("Actors"));

                        Picasso.get()
                                .load(response.getString("Poster"))
                                .fit()
                                .into(movieImage);
                        boxOffice.setText("Box Office: " + response.getString("BoxOffice"));
                    }

                }catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error:", error.getMessage());

            }
        });
        queue.add(jsonObjectRequest);

    }

}
