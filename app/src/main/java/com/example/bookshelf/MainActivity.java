package com.example.bookshelf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {


    private static final int SEARCH_BOOK_REQUEST_CODE = 21;
    private static final String KEY_BOOK_LIST = "bookList";
    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    Book selectedBook;
    String booksList="";
    ArrayList<Book> bookListData=new ArrayList<>();
    private final String KEY_SELECTED_BOOK = "selectedBook";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonSecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivityForResult(intent,SEARCH_BOOK_REQUEST_CODE);
            }
        });

        //Fetch selected book if there was one
        if (savedInstanceState != null){
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);
            booksList=savedInstanceState.getString(KEY_BOOK_LIST);
            Gson gson=new Gson();
            Type type=new TypeToken<ArrayList<Book>>(){}.getType();
            bookListData=gson.fromJson(booksList,type);
        }

        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();

        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.container1);


        // At this point, I only want to have BookListFragment be displayed in container_1
        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookListFragment))
            fm.beginTransaction()
                    .add(R.id.container1, BookListFragment.newInstance(bookListData))
                    .commit();

        /*
        If we have two containers available, load a single instance
        of BookDetailsFragment to display all selected books
         */
        bookDetailsFragment = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        if (twoPane) {
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        } else if (selectedBook != null) {
            /*
            If a book was selected, and we now have a single container, replace
            BookListFragment with BookDetailsFragment, making the transaction reversible
             */
            fm.beginTransaction()
                    .replace(R.id.container1, bookDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }

    }

    /*
    Generate an arbitrary list of "books" for testing
     */
    private BookList getTestBooks() {
        BookList books = new BookList();
        Book book;

        for (int i = 1; i <= 10; i++) {
//            books.add(book = new Book("Book" + i, "Author" + i));
        }
        return books;
    };

    @Override
    public void bookSelected(int index) {
        //Store the selected book to use later if activity restarts
        selectedBook = bookListData.get(index);

        if (twoPane)
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.displayBook(selectedBook);
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment.newInstance(selectedBook))
                    // Transaction is reversible
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_BOOK, selectedBook);
        outState.putString(KEY_BOOK_LIST, booksList);

    }

    @Override
    public void onBackPressed() {
        // If the user hits the back button, clear the selected book
        selectedBook = null;
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SEARCH_BOOK_REQUEST_CODE){
            if (resultCode==RESULT_OK){
                assert data != null;
                booksList=data.getStringExtra("result");
                //                    JSONArray jsonArray=new JSONArray(booksList);
                Gson gson=new Gson();
                Type type=new TypeToken<ArrayList<Book>>(){}.getType();
                bookListData=gson.fromJson(booksList,type);
                fm = getSupportFragmentManager();

                fm.beginTransaction()
                        .replace(R.id.container1, BookListFragment.newInstance(bookListData))
                        .commit();
//                  BookList bookList=  gson.fromJson(booksList,BookList.class);

//                Toast.makeText(MainActivity.this,bookListData.get(0).getTitle(),Toast.LENGTH_LONG).show();

            }
        }
    }
}