package st.domain.ggviario.secret;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import st.domain.ggviario.secret.support.FaturaAdapter;


/**
 * Created by xdata on 12/21/16.
 */

public class Despesas extends AppCompatActivity
{
    private RecyclerView listFaturaRecycler;
    private FaturaAdapter adapter;
    private FloatingActionButton floatAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.despesa);

        this.floatAdd = (FloatingActionButton) findViewById(R.id.bt_new_despesa);
        this.floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.add();
            }
        });

        this.listFaturaRecycler = (RecyclerView) findViewById(R.id.rv_fatura_list);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        this.listFaturaRecycler.setLayoutManager(llm);

        this.adapter = new FaturaAdapter(this);
        this.listFaturaRecycler.setAdapter(this.adapter);
        adapter.add();

    }
}
