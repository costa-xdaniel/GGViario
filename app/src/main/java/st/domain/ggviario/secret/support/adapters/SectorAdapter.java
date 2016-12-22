package st.domain.ggviario.secret.support.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import st.domain.ggviario.secret.R;


/**
 * Created by xdata on 12/21/16.
 */

public class SectorAdapter extends BaseAdapter {


    private List<String> list;
    private  Context context;

    public SectorAdapter(Context context)
    {
        this.list = new LinkedList();
        this.context = context;

        list.add("Selecione o sector");
        list.add("Galinhas Antigas");
        list.add("Galinhas Novas");
        list.add("Galinhas de Campo");

    }


    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int index) {
        return this.list.get(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.item_sector, parent, false);
        TextView tv = (TextView) view.findViewById(R.id.sector_name);
        tv.setText(this.list.get(index));
        return view;
    }
}
