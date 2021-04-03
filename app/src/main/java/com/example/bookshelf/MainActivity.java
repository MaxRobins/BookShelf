package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookSelectedInterface {

    FragmentManager fm;

    boolean twoPane;
    BookDetailsFragment bookDetailsFragment;
    Book selectedBook;
    private final String KEY_SELECTED_BOOK = "selectedBook";

    public static final String EXTRA_MESSAGE = "com.example.bookshelf.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(this, BookSearchActivity.class);
        EditText editText = (EditText) findViewById(R.id.searchEditText);
        Button button = (Button) findViewById(R.id.searchButton);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/

        findViewById(R.id.buttonSecondActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivity(intent);
            }
        });
        //Fetch selected book if there was one

        if (savedInstanceState != null)
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);

        twoPane = findViewById(R.id.container2) != null;

        fm = getSupportFragmentManager();

        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.container1);


        // At this point, I only want to have BookListFragment be displayed in container_1
        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookListFragment))
            fm.beginTransaction()
                    .add(R.id.container1, BookListFragment.newInstance(getTestBooks()))
            .commit();


        //If we have two containers available, load a single instance
        //of BookDetailsFragment to display all selected books

        bookDetailsFragment = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        if (twoPane) {
            fm.beginTransaction()
                    .replace(R.id.container2, bookDetailsFragment)
                    .commit();
        } else if (selectedBook != null) {

            //If a book was selected, and we now have a single container, replace
            //BookListFragment with BookDetailsFragment, making the transaction reversible

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

            //books.add(book = new Book("Book" + i, "Author" + i));
            /*books.add(book = new Book("Frozen in Time", "Mitchel Zuckoff"));
            books.add(book = new Book("River of Doubt", "Candice Millard"));
            books.add(book = new Book("To Kill a Mockingbird", "Harper Lee"));
            books.add(book = new Book("The Lord of the Rings", "J. R. R. Tolkien"));
            books.add(book = new Book("The Hobbit", "J. R. R. Tolkien"));
            books.add(book = new Book("Catch-22", "Joseph Heller"));
            books.add(book = new Book("Lord of the Flies", "William Golding"));
            books.add(book = new Book("The Adventures of Huckleberry Finn", "Mark Twain"));
            books.add(book = new Book("Dividing the Spoils", "Robin Waterfield"));
            books.add(book = new Book("Augustus", "Anthony Everitt"));*/

        return books;
    };

    @Override
    public void bookSelected(int index) {
        //Store the selected book to use later if activity restarts
        selectedBook = getTestBooks().get(index);

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
    }

    @Override
    public void onBackPressed() {
        // If the user hits the back button, clear the selected book
        selectedBook = null;
        super.onBackPressed();
    }
}
