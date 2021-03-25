package edu.temple.bookshelf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class BookListFragment extends Fragment {


    private static final String ARG_BOOKLIST = "param1";

    private ArrayList<Book> bookArrayList;

    public BookListFragment() {}

    public static BookListFragment newInstance(ArrayList<Book> bookArrayList) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BOOKLIST, bookArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookArrayList = getArguments().getParcelableArrayList(ARG_BOOKLIST);
        }
        else{
            bookArrayList = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

        listView.setAdapter(new BookAdapter(getActivity(), android.R.layout.simple_list_item_1, bookArrayList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((BookListFragmentInterface) getActivity()).bookClicked(position);
            }
        });

        return listView;
    }

    interface BookListFragmentInterface{
        public void bookClicked(int position);
    }
}