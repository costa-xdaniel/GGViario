package st.domain.ggviario.secret.model.action;

import android.widget.TextView;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.ggviario.secret.model.Car;
import st.domain.ggviario.secret.model.ItemSell;
import st.domain.ggviario.secret.util.FormatterFactory;
import st.domain.ggviario.secret.support.adapters.dataset.ItemSellDataSet;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/2/16.
 * Using user computer xdata
 */
public class CarActionAdd extends CarAction
{

    private ItemSell item;
    private Car car;
    private TextView tvAmount;
    private BaseRecyclerAdapter adapter;

    @Override
    public CarAction item(ItemSell itemSell) {
        this.item = itemSell;
        return this;
    }

    @Override
    public CarAction car(Car car) {
        this.car = car;
        return this;
    }

    @Override
    public CarAction amountView(TextView tvAmountPay) {
        this.tvAmount = tvAmountPay;
        return this;
    }

    @Override
    public CarAction adapter(BaseRecyclerAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    @Override
    public void execute() {
        this.populateAdapter(this.adapter, this.car);
        NumberFormat format = new FormatterFactory().instanceFormatterMoney();
        this.car.add(this.item);
        this.adapter.addDataSet(new ItemSellDataSet(this.item));
        this.tvAmount.setText(format.format(this.car.getAmountFinal()));
        this.adapter.notifyItemInserted(this.adapter.getLastIndex());
    }

}
