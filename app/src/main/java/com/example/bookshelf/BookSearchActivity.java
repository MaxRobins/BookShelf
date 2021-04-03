package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookSearchActivity extends AppCompatActivity {

    EditText urlEditText;
    Button searchButton, cancelButton;
    ContentLoadingProgressBar progressBar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search2);

        urlEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        requestQueue = Volley.newRequestQueue(this);


        urlEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        cancelButton = findViewById(R.id.cancelButton);
        progressBar = findViewById(R.id.progressBar);
        requestQueue = Volley.newRequestQueue(this);

        progressBar.hide();
        searchButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.show();
                String url = "https://kamorris.com/lab/cis3515/search.php?term="+ urlEditText.getText().toString();
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //                            displayTextView.setText(response.getJSONArray(0).getJSONObject(0).getString("title"));
                        progressBar.hide();
                        Intent intent=new Intent();
                        intent.putExtra("result",response.toString());
                        setResult(RESULT_OK,intent);
                        finish();
                        //Picasso.get().load(Uri.parse(getString("id"))).into(urlEditText);
//                        Toast.makeText(BookSearchActivity.this,"SUCCESS",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookSearchActivity.this, "ERROR"+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

        /*findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://kamorris.com/lab/cis3515/search.php?term";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }
        });*/
    }
}