package st.domain.ggviario.secret.dao;

import android.content.Context;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import st.domain.ggviario.secret.model.Measure;
import st.domain.ggviario.secret.model.ProductPrice;
import st.domain.ggviario.secret.model.Product;
import st.zudamue.support.android.sql.SQLRow;

/**
 *
 * Created by dchost on 10/02/17.
 */
public class ProductDao extends Dao<Product> {

    private Map< Integer, Measure> measureMap;
    private Map< Integer, Product> productMap;
    private Map< Integer, ProductPrice > productPriceMap;

    public ProductDao(Context context) {
        super(context);
        this.measureMap = new LinkedHashMap<>();
        this.productMap = new LinkedHashMap<>();
        this.productPriceMap = new LinkedHashMap<>();
        loadMeasure();
    }

    private void loadMeasure() {
        query(
                select("*")
                .from($measure)
        ).forLoopCursor(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                Measure measure = mountMeasure( row );
                measureMap.put(measure.getId(), measure);
            }
        });
    }

    private Measure mountMeasure(SQLRow row ){
        return new Measure(
                row.integer( measure.meas_id),
                row.string( measure.meas_cod),
                row.string( measure.meas_name)
        );
    }

    public Product getProduct (Integer idProduct) {

        if ( idProduct == null ) return  null;

        if( productMap.containsKey( idProduct ) )
            return productMap.get( idProduct );

        query(
                select("*")
                .from($product)
                .where( product.prod_id ).equal( value( idProduct ) )
                .limit(1)

        );

        List<SQLRow> results = this.catchAllResult();

        if( results != null  && results.size() >0 ) {
            SQLRow row = results.get( 0 );
            return mount( row );
        }
        return null;
    }

    private Product mount(SQLRow row) {

        Measure measure = getMeasure( row.integer( product.prod_meas_id) );
        Product vProduct =  new Product(

                row.integer( product.prod_id ),
                row.string( product.prod_name ),
                row.string( product.prod_detail ),
                measure,
                row.timestamp( product.prod_dtreg ),
                row.real( product.prod_price ),
                getProduct( row.integer( product.prod_prod_id ) ),
                row.realDouble( product.prod_scalesuper ),
                row.integer( product.prod_state ),
                row.integer( product.prod_stock )

        );

        this.productMap.put( vProduct.getId(), vProduct );

        return vProduct;
    }

    private Measure getMeasure(Integer integer) {
        return this.measureMap.get( integer );
    }

    public List<Measure> getMeasures() {
        return new LinkedList<>( this.measureMap.values() );
    }

    public List<Product> loadProducts() {
        final List < Product > products = new LinkedList<>();

        query(select( "*" )
                .from( $product ));
        onQueryResult(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                products.add( mount( row ) );
            }
        });

        return products;
    }

    public List<ProductPrice> loadProductPriceOf(final Product product ) {
        final List<ProductPrice> measures = new LinkedList<>();
        query(
                select("*")
                .from($productprice)
                .where(productprice.prodprice_meas_id).equal( value( product.getId() ) )
                .and( productprice.prodprice_state ).equal( value( 1 ) ) // etado activo
        ).forLoopCursor(new OnAllQueryResults() {
            @Override
            protected void onRow(SQLRow row) {
                Integer id = row.integer( productprice.prodprice_id );
                ProductPrice productPrice ;

                if( productPriceMap.containsKey( id ) )
                    productPrice = productPriceMap.get( id );
                else productPrice = mountProductPrice ( product, row);
                measures.add( productPrice );
            }
        });
        return measures;
    }

    private ProductPrice mountProductPrice(Product product, SQLRow row) {
        ProductPrice productMeasure = new ProductPrice(
                product,
                getMeasure( row.integer( productprice.prodprice_meas_id ) ),
                row.realDouble( productprice.prodprice_quantity ),
                row.real( productprice.prodprice_price )
        );
        return productMeasure;
    }

    public Measure getMeasureById(Integer integer) {
        return this.measureMap.get( integer );
    }
}
