package st.domain.ggviario.secret.support.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.ggviario.secret.Crop;
import st.domain.ggviario.secret.Despesas;
import st.domain.support.android.model.ItemFragment;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.references.RMap;
import st.domain.ggviario.secret.NewClientActivity;
import st.domain.ggviario.secret.SellStepperActivity;
import st.domain.ggviario.secret.support.adapters.SupportHome;

import java.util.List;

//import ivb.com.materialstepper.stepperFragment;

/**
 * Created by xdata on 8/11/16.
 */
public class MainHome extends Fragment implements ItemFragment
{
    private View rootView;
    private RecyclerView recyclerView;
    private SupportHome supportAdapter;
    private List<SupportHome.DataOperation> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.supportAdapter = new SupportHome(this.getContext());


        SupportHome.DataOperation sell = SupportHome.newOperation()
                .color(R.color.mat_indigo_primary)
                .name("Venda")
                .image(R.drawable.ic_shopping_cart_white_48dp)
                .activity(SellStepperActivity.class);


        SupportHome.DataOperation sync = SupportHome.newOperation()
                .color(R.color.mat_green_primary)
                .name("Sincronização")
                .image(R.drawable.ic_sync_white_48dp)
                .activity(NewClientActivity.class);


        SupportHome.DataOperation harvest = SupportHome.newOperation()
                .color(R.color.mat_pink_primary)
                .name("Colheita")
                .image(R.drawable.ic_shopping_basket_white_48dp)
                .activity(Crop.class);


        SupportHome.DataOperation fatura = SupportHome.newOperation()
                .color(R.color.md_brown_500)
                .name("Despesas")
                .image(R.drawable.ic_content_paste_white_48dp)
                .activity(Despesas.class);


        this.list = supportAdapter.getCreatedSupport().getListDataSet();


        this.list.add(harvest);
        this.list.add(fatura);
//        this.list.add(sell);
//        this.list.add(sync);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout._main_home, container, false);
        this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        int columns = (this.getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                ? 3: 2;

        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(columns, LinearLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        this.recyclerView.setHasFixedSize(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletPosition = 0;
                int max = -1;
                int row [] = layoutManager.findLastCompletelyVisibleItemPositions(null);
                for (int i: row)
                    if(i>max) max = i;

//                lastCompletPosition = layoutManager.findLastCompletelyVisibleItemPosition() ;
//                lastCompletPosition = max;

                if(lastCompletPosition +1 == supportAdapter.getCreatedSupport().getItemCount())
                {

                }
            }
        });

        this.recyclerView.setAdapter(this.supportAdapter.getCreatedSupport());
        return this.rootView;
    }

    @Override
    public CharSequence getTitle()
    {
        return "Inicio";
    }

    @Override
    public Fragment getFragment()
    {
        return this;
    }
    
    
    @Override
    public CharSequence getProtocolKey() {
        return RMap.IDENTIFIER_MAIN_HOME;
    }

//    @Override
//    public boolean onNextButtonHandler() {
//        return true;
//    }
}
