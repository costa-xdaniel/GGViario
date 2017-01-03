package st.domain.ggviario.secret.model.builders;

import st.domain.ggviario.secret.model.Measure;
import st.domain.ggviario.secret.references.RData;
import st.domain.support.android.sql.SQLRow;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class MeasureBuilder extends Builder<Measure>
{
    private double defaultPrice;
    private int id;
    private String name;
    private String key;

    public MeasureBuilder() {
        super(Measure.class);
    }

    public MeasureBuilder defaultPrice(double defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public MeasureBuilder id(int id) {
        this.id = id;
        return this;
    }

    public MeasureBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MeasureBuilder key(String key) {
        this.key = key;
        return this;
    }

    @Override
    public Measure build() {
        return new Measure(id, key, name, defaultPrice);
    }

    public Measure buildMap(SQLRow row)
    {
        return id(row.integer(RData.MET_ID))
                .key(row.string(RData.MET_COD))
                .name(row.string(RData.MET_NAME))
                .build();
    }
}
