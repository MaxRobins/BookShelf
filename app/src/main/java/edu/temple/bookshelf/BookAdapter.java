package edu.temple.bookshelf;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.List;

public class BookAdapter extends ArrayAdapter {

    Context context;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;

        if(convertView == null){
            textView = new TextView(context);
            textView.setTextSize(22);
            textView.setPadding(15, 20, 0, 20);
        }
        else{
            textView = (TextView) convertView;
        }

        textView.setText(((Book)(getItem(position))).getBookTitle());

        return textView;

    }
        /*TextView textView;
        TextView textView2;
        View v = convertView;

        if(v == null){
            textView = new TextView(context);
            textView.setTextSize(22);
            textView.setPadding(15, 20, 0, 20);
            textView2 = new TextView(context);
            textView2.setTextSize(22);
            textView2.setPadding(15, 20, 0, 10);
        }
        else{
            textView = (TextView) v;
            textView2 = (TextView) v;
        }

        textView.setText(((Book)(getItem(position))).getBookTitle());
        textView2.setText(((Book)(getItem(position))).getBookAuthor());

        return v;
    }*/
}