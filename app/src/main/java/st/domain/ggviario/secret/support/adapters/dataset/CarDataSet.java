package st.domain.ggviario.secret.support.adapters.dataset;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.model.Car;
import st.domain.ggviario.secret.model.ItemSell;
import st.domain.ggviario.secret.model.action.CarAction;
import st.domain.ggviario.secret.util.FormatterFactory;

import java.text.NumberFormat;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CarDataSet implements BaseRecyclerAdapter.ItemDataSet
{
    private final NumberFormat formatter;
    private Car car;
    private CarAction lastAction;
    private ItemSell lastItemSell;

    public CarDataSet(Car car) {
        FormatterFactory formatterFactory = new FormatterFactory();
        this.formatter = formatterFactory.instanceFormatterMoney();
        this.car = car;
    }

    public void setLastAction(CarAction lastAction, ItemSell lastItemSell) {
        this.lastAction = lastAction;
        this.lastItemSell = lastItemSell;
    }

    public CarAction getLastAction() {
        return lastAction;
    }

    public ItemSell getLastItemSell() {
        return lastItemSell;
    }

    @Override
    public int getTypeView() {
        return R.layout.item_group_car;
    }


    public Car getCar() {return this.car;}

    public String getAmount() {
        return formatter.format(this.car.getAmountFinal());
    }
}
