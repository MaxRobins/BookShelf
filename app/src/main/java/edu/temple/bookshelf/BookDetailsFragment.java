package edu.temple.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK= "param1";

    TextView textView;
    TextView textView2;

    private Book book;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        textView = v.findViewById(R.id.textView);
        textView2 = v.findViewById(R.id.textView2);

        changeBook(book);

        return v;
    }
    public void changeBook(Book book){
        textView.setText(book.getBookTitle());
        textView2.setText(book.getBookAuthor());
    }

}
