package st.domain.ggviario.secret.support.adapters.vfactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import st.domain.support.android.adapter.BaseRecyclerAdapter;
import st.domain.ggviario.secret.R;
import st.domain.ggviario.secret.support.adapters.CalculatorViewHolder;

/**
 * Created by Daniel Costa at 9/1/16.
 * Using user computer xdata
 */
public class CalculatorViewHolderFactory implements ViewHolderFactory
{

    private CalculatorViewHolder.OnClickKeyboardListener onKeyboardClickListener;
    private OnCreateCalculatorViewHolder onCreateCalculator;

    @Override
    public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view) {
        CalculatorViewHolder calculatorViewHolder = new CalculatorViewHolder(view);
        calculatorViewHolder.setOnKeyboardClickListener(this.onKeyboardClickListener);
        this.onCreateCalculator.onCreateCalculatorViewHolder(calculatorViewHolder);
        return calculatorViewHolder;
    }

    public CalculatorViewHolderFactory setOnKeyboardClickListener(CalculatorViewHolder.OnClickKeyboardListener onKeyboardClickListener)
    {
        this.onKeyboardClickListener = onKeyboardClickListener;
        return this;
    }

    @Override
    public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @Override
    public int getViewType() {
        return R.layout.calculator_item;
    }

    public CalculatorViewHolderFactory setOnCreateCalculator(OnCreateCalculatorViewHolder onCreateCalculator) {
        this.onCreateCalculator = onCreateCalculator;
        return this;
    }

    public interface OnCreateCalculatorViewHolder {
        void onCreateCalculatorViewHolder(CalculatorViewHolder calculatorViewHolder);
    }
}
