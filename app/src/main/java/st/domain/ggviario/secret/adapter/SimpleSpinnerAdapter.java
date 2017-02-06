package st.domain.ggviario.secret.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import st.domain.ggviario.secret.R;


/**
 *
 * Created by xdata on 12/21/16.
 */

public class SimpleSpinnerAdapter extends BaseAdapter {


    private List<CharSequence> list;
    private  Context context;
    private OnItemCreated onItemCreated;

    public SimpleSpinnerAdapter(Context context) {
        this.list = new LinkedList();
        this.context = context;
    }

    public SpinnerAdapter add (CharSequence item) {
        this.list.add(item);
        return this;
    }

    public void setOnItemCreated( OnItemCreated onItemCreated ){
        this.onItemCreated = onItemCreated;
    }

    public void addAll (Collection<? extends CharSequence> items) {
        this.list.addAll(items);
    }

    public CharSequence get(int index ) {
        return this.list.get(index);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public CharSequence getItem(int index) {
        return this.list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView tv = (TextView) view.findViewById(R.id.spinner_item_name);
        CharSequence item;
        tv.setText( item = this.list.get(index) );

        if( this.onItemCreated != null )
            this.onItemCreated.accept(view, tv, index, item);
        return view;
    }

    public OnItemCreated getOnItemCreated() {
        return onItemCreated;
    }

    public interface OnItemCreated {
        void accept( View viewLayout, TextView view, int index, CharSequence item );
    }
}
