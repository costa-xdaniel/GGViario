package st.domain.ggviario.secret.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.ggviario.secret.R;
import st.domain.support.android.model.ItemView;

/**
 * Created by dchost on 24/02/17.
 */
public class MeasureItemView  implements ItemView{

    private View rootView;


    @Override
    public View createView(int position, LayoutInflater inflater, View view, ViewGroup viewGroup) {
        this.rootView = inflater.inflate( R.layout.item , viewGroup, false );
        return rootView;
    }

    @Override
    public Object getObject() {
        return null;
    }
}
