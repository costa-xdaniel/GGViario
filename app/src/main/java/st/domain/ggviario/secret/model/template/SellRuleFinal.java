package st.domain.ggviario.secret.model.template;

import st.domain.ggviario.secret.model.PriceRule;

/**
 * Created by Daniel Costa at 8/30/16.
 * Using user computer xdata
 */
public class SellRuleFinal implements PriceRule
{
    @Override
    public double calc(double quantity)
    {
        return 0;
    }

    @Override
    public void setOtherRule(PriceRule nextRule) {
    }

    @Override
    public Double getAcceptedQuantity() {
        return 0.0;
    }
}