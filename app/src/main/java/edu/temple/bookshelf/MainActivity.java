package edu.temple.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {

    ArrayList<Book> bookArrayList;
    BookDetailsFragment bookDetailsFragment;
    BookListFragment bookListFragment;

    boolean container2present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //container2present = findViewById(R.id.container_2) != null;//true

        Context context;
        bookListFragment = new BookListFragment();
        bookDetailsFragment = new BookDetailsFragment();

        bookArrayList = new ArrayList<>();

        bookArrayList.add(new Book("Frozen in Time", "Mitchell Zuckoff"));
        bookArrayList.add(new Book("River of Doubt", "Candice Millard"));
        bookArrayList.add(new Book("To Kill a Mockingbird", "Harper Lee"));
        bookArrayList.add(new Book("The Lord of the Rings", "J. R. R. Tolkien"));
        bookArrayList.add(new Book("The Hobbit", "J. R. R. Tolkien"));
        bookArrayList.add(new Book("Catch-22", "Joseph Heller"));
        bookArrayList.add(new Book("Lord of the Flies", "William Golding"));
        bookArrayList.add(new Book("The Adventures of Huckleberry Finn", "Mark Twain"));
        bookArrayList.add(new Book("Dividing the Spoils", "Robin Waterfield"));
        bookArrayList.add(new Book("Augustus", "Anthony Everitt"));



        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_1, BookListFragment.newInstance(bookArrayList))
                .commit();

        if(container2present){
            bookDetailsFragment = new BookDetailsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_2, bookDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void bookClicked(int position) {
        if(!container2present) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, BookDetailsFragment.newInstance(bookArrayList.get(position)))
                    .addToBackStack(null)
                    .commit();
        }
        else{
            bookDetailsFragment.changeBook(bookArrayList.get(position));
        }
    }
}