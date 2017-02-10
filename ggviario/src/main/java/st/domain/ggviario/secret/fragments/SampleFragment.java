package st.domain.ggviario.secret.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.ggviario.secret.R;

/**
 *
 * Created by dchost on 09/02/17.
 */

public class SampleFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate( R.layout.simle_fragemnt_text, container, false );


        return rootView;
    }
}
