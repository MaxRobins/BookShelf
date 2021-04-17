package com.example.bookshelf;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

public class ControlFragment extends Fragment implements View.OnClickListener
{

    private TextView bookTitle;
    private AppCompatSeekBar playProgress;
    private static final String BOOK_KEY = "book";
    private Book book;

    ControlFragment.PlayingBookInterface parentActivity;

    public ControlFragment() {}

    public static ControlFragment newInstance(Book book) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();

        /*
         A Book implements the Parcelable interface
         therefore we can place a Book inside a bundle
         by using that put() method.
         */
        args.putParcelable(BOOK_KEY, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
         This fragment needs to communicate with its parent activity
         so we verify that the activity implemented our defined interface
         */
        if (context instanceof ControlFragment.PlayingBookInterface) {
            parentActivity = (ControlFragment.PlayingBookInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(BOOK_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_control, container, false);

        bookTitle = view.findViewById(R.id.titleTextView);
        playProgress = view.findViewById(R.id.playProgress);

        playProgress.setEnabled(false);

        view.findViewById(R.id.playButton).setOnClickListener(this);
        view.findViewById(R.id.pauseButton).setOnClickListener(this);
        view.findViewById(R.id.stopButton).setOnClickListener(this);

        playProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser)
            {
                if (fromUser) parentActivity.seekTo(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });

        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.playButton:
                if (book != null)
                    parentActivity.play(book.getId());
                break;
            case R.id.pauseButton:
                if (book != null)
                    parentActivity.pause();
                break;
            case R.id.stopButton:
                if (book != null)
                    parentActivity.stop();
        }
    }

    /*
    Interface for communicating with attached activity
     */
    interface PlayingBookInterface {
        void play(int id);
        void pause();
        void stop();
        void seekTo(int position);
    }

    /*
    This method is used to update seekBar progress
     */
    public void updateProgress(int progress) {
        playProgress.setProgress(progress);
    }

    /*
    This method is used both internally and externally (from the activity)
    to update selected book
     */
    public void selectedBook(Book book) {
        this.book = book;
        updateUI(0,false);
    }

    /*
    update the UI
     */
    public void updateUI(int currentProgress, boolean isPlaying) {
        if (isPlaying) {
            playProgress.setEnabled(true);
            bookTitle.setText(getString(R.string.book_playing_title,book.getTitle()));
        }else {
            playProgress.setEnabled(false);
            bookTitle.setText("");
        }
        updateProgress(currentProgress);
    }
}
