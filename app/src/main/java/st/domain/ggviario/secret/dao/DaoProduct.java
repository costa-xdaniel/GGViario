package st.domain.ggviario.secret.dao;

import android.content.Context;

import st.domain.support.android.model.OnProcess;
import st.domain.ggviario.secret.model.Measure;
import st.domain.ggviario.secret.model.PriceRule;
import st.domain.ggviario.secret.model.Product;
import st.domain.ggviario.secret.model.builders.ProductBuilder;
import st.domain.ggviario.secret.model.SellRule;
import st.domain.ggviario.secret.references.RData;
import st.domain.support.android.sql.SQLRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by xdata on 7/24/16.
 */
public class DaoProduct extends Dao
{


    public DaoProduct(Context context) {
        super(context);
    }

    public Double convertMeasures(final int idMetrageFromm, final int idMetreaTo, Double valuemetreage1)
    {
//        Double conversionResult, conversionFrom, conversionTo;
//        List<SQLRow> result;
//
//        //-- Se for para converter os mesmos tipo de medida entao a regra sera
//        //-- retornar o mesmo valor a converter
//        if((idMetrageFromm+"").equals(idMetreaTo+"")) return valuemetreage1;
//
////        -- Quando as medidas forem difrentes entao calcular o valor para a medida
////        -- esperada a partir da conversao definidada da mesma
////        -- Carregar a conversao
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.T_CONVERSION)
//                .where(new Condicion() {
//                    @Override
//                    public boolean accept(int wherePosition, SQLRow row) {
//                        String conv_met_id1 = row.string(RData.CONV_MET_ID1)+"";
//                        String conv_met_id2 =row.string(RData.CONV_MET_ID2)+"";
//                        return ((conv_met_id1.equals(idMetrageFromm+"")
//                                && conv_met_id2.equals(idMetreaTo+""))
//                                || (conv_met_id2.equals(idMetrageFromm+"")
//                                    && conv_met_id1.equals(idMetreaTo+"")));
//                    }
//                });
//        execute();
//        result = getSelectResult();
//        end();
//
////        -- Caso nao encontre nenhuma regra de converter essas duas medidas entao retornar o valor de null
//        if(result.size()== 0) return null;
//        if((result.get(0).string(RData.CONV_MET_ID1)).equals(idMetrageFromm+""))
//        {
//            conversionFrom = result.get(0).realDouble(RData.CONV_VALUE1);
//            conversionTo = result.get(0).realDouble(RData.CONV_VALUE2);
//        }
//        else
//        {
//            conversionFrom = result.get(0).realDouble(RData.CONV_VALUE2);
//            conversionTo = result.get(0).realDouble(RData.CONV_VALUE1);
//        }
//        conversionResult = (valuemetreage1 * conversionTo)/conversionFrom;
//        return conversionResult;
        return null;
    }

    public Product find(final int idProducto)
    {
//        SQLRow map;
//        Product produto ;
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.VER_PRODUCT_COMPLETE)
//                .where(new Condicion() {
//                    @Override
//                    public boolean accept(int wherePosition, SQLRow row) {
//                        return Objects.equals(row.value(RData.PROD_ID), idProducto);
//                    }
//                });
//        execute();
//        map = getSelectResult().get(0);
//        end();
//
//        return new ProductBuilder().buildMap(map);
        return null;
    }

    public ArrayList<Measure> loadMetreages(final int idProducto)
    {
        ArrayList<Measure> metreages = new ArrayList<>();
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.VER_METREAGE_PRODUCT)
//                .where(new Condicion() {
//                    @Override
//                    public boolean accept(int wherePosition, SQLRow row) {
//                        return Objects.equals(row.value(RData.PROD_ID), idProducto);
//                    }
//                });
//        execute();
//
//        Measure itemMetreage;
//
//        for(SQLRow map : getSelectResult())
//        {
//            itemMetreage = mountMeasure(map);
////            itemMetreage.setIdProducto(idProducto);
//            metreages.add(itemMetreage);
//        }
//        end();
        return metreages;
    }

    public static Measure mountMeasure(SQLRow row)
    {
        int idMetreage = row.integer(RData.MET_ID);
        String metreageName = row.integer(RData.MET_NAME).toString();
        String codMetreage = row.string(RData.MET_COD);
        Double precoDefault =  row.realDouble(RData.SELL_PRICE);
        if(precoDefault == null) precoDefault = 0.0;

        Measure itemMetreage = new Measure(idMetreage, codMetreage, metreageName, precoDefault);
        return itemMetreage;
    }

    public ArrayList<Product> loadProducts(OnProcess<Product> productOnProcess)
    {
        ArrayList<Product> listProdutoShell = new ArrayList<>();
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.VER_PRODUTO_SELL);
//        execute();
//        List<SQLRow> result = getSelectResult();
//        end();
//        for(SQLRow row: result)
//        {
//            Product product = new ProductBuilder().buildMap(row);
//            if(productOnProcess != null)
//                productOnProcess.process(product);
//            listProdutoShell.add(product);
//        }
        return listProdutoShell;
    }

    public List<PriceRule> loadSellRules(final Product product, final Measure idMeasureFrom)
    {
        List<PriceRule> list = new ArrayList<>();
//        begin(Operaction.SELECT);
//        select(RData.ALL)
//                .from(RData.VER_SELLROULE)
//                .where(new Condicion() {
//                    @Override
//                    public boolean accept(int wherePosition, SQLRow row)
//                    {
//                        boolean result = Objects.equals(row.value(RData.SELL_PROD_ID), product.getId())
//                                && Objects.equals(row.value(RData.SELL_MET_ID), idMeasureFrom.getId());
//
//                        return result;
//                        //ORDER BY sh.sell_quantity DESC Executed by view
//                    }});
//        execute();
//        List<SQLRow> result = getSelectResult();
//
//        SellRule last = null;
//        SellRule current;
//
//        for(SQLRow row: result)
//        {
//            current =(new SellRule( row.realDouble(RData.SELL_QUANTITY), row.realDouble(RData.SELL_PRICE), product));
//            if(last != null)
//                last.setOtherRule(current);
//            last = current;
//            list.add(current);
//        }
        return list;
    }
}
