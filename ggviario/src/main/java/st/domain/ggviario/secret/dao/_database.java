package st.domain.ggviario.secret.dao;


/**
 *
 * Created by xdata on 12/24/16.
 */

public interface _database {

    String $user = "user";
    interface user {
        String user_id = "user_id";
        String user_name = "user_name";
        String user_surname = "user_surname";
        String user_accessname = "user_accessname";
        String user_pwd = "user_pwd";
        String user_state = "user_state";
        String user_dtrge = "user_dtreg";
    }

    String $sector = "sector";
    interface sector {
        String sector_id = "sector_id";
        String sector_name = "sector_name";
    }

    String $crop = "crop";
    interface crop {
        String crop_id = "crop_id";
        String crop_sector_id = "crop_sector_id";
        String crop_user_id  = "crop_user_id";
        String crop_ovos = "crop_ovos";
        String crop_ovosdefeituosos = "crop_ovosdefeituosos";
        String crop_percasgalinhas = "crop_percasgalinhas";
        String crop_state = "crop_state";
        String crop_dtreg = "crop_dtreg";
    }

    String $ver_cropgroup = "ver_cropgroup";
    interface ver_cropgroup {
        String date = "date";
        String fdate = "fdate";
        String quantity = "quantity";
        String quantitypercas = "quantitypercas";
        String quantitypercasgalinha = "quantitypercasgalinha";
        String quantityoperactions = "quantityoperactions";
        String numbersector = "numbersector";
    }

    String $ver_cropsectordate = "ver_cropsectordate";
    interface ver_cropsectordate {
        String date = "date";
        String fdate = "fdate";
        String quantity = "quantity";
        String quantitypercas = "quantitypercas";
        String quantitypercasgalinha = "quantitypercasgalinha";
    }

    String $ver_crop_date = "ver_crop_date";
    interface ver_crop_date {
        String date = "date";
    }

    String $objectype = "objectype";
    interface objectype {
        String tobj_id = "tobj_id";
        String tobj_desc = "tobj_desc";
        String tobj_state = "tobj_state";
    }


    //Objects
    String $object = "object";
    interface object {
        String obj_id = "obj_id";
        String obj_tobj_id = "obj_tobj_id";
        String obj_obj_id = "obj_obj_id";
        String obj_user_id = "obj_user_id";
        String obj_desc = "obj_desc";
        String obj_state = "obj_state";
    }

    String $ver_objects = "ver_objects";
    interface ver_objects extends object {}

    String $client = "client";
    interface client {
        String cli_id = "cli_id";
        String cli_user_id = "cli_user_id";
        String cli_obj_residence = "cli_obj_residence";
        String cli_obj_typedocument  = "cli_obj_typedocument";
        String cli_name = "cli_name";
        String cli_surname = "cli_surname";
        String cli_contact = "cli_contact";
        String cli_gender_id = "cli_gender_id";
        String cli_mail = "cli_mail";
        String cli_document = "cli_document";
    }

    String $ver_client_status = "ver_client_status";
    interface ver_client_status extends client {
        String totalcredits = "totalcredits";
        String totalcreditspay = "totalcreditspay";
    }

    String $gender = "gender";
    interface gender {
        String gender_id = "gender_id";
        String gender_desc = "gender_desc";
    }

    String $product = "product";
    interface product {
        String prod_id = "prod_id";
        String prod_prod_id = "prod_prod_id";
        String prod_meas_id = "prod_meas_id";
        String prod_price = "prod_price";
        String prod_name = "prod_name";
        String prod_detail = "prod_detail";
        String prod_stock = "prod_stock";
        String prod_scalesuper = "prod_scalesuper";
        String prod_state = "prod_state";
        String prod_dtreg = "prod_dtreg";
    }

    String $credit = "credit";
    interface credit {
        String credi_id = "credi_id";
        String credi_user_id = "credi_user_id";
        String credi_cli_id = "credi_cli_id";
        String credi_valuetotal = "credi_valuetotal";
        String credi_valuepago = "credi_valuepago";
        String credi_dtfinalizar = "credi_dtfinalizar";
        String credi_dtfim = "credi_dtfim";

        /**
         * Credits state the status of credits.
         * <table border="1">
         *     <tr> <th>States</th> <th>Decisions</th> </tr>
         *     <tr><td> 1 </td> <td> Ativo (Sem nem um pagamento) </td> </tr>
         *     <tr><td> 2 </td> <td> Em pagamento (Ativo com alguns pagamento ja feito) </td> </tr>
         *     <tr><td> 0 </td> <td> Fechado  (Credito pago e fechado) </td> </tr>
         *     <tr><td> -1 </td> <td> Anulado  (Credito foi anulado) </td> </tr>
         * </table>
         */
        String credi_state = "credi_state";
        String credi_dtreg = "credi_dtreg";
    }

    String $creditproduct = "creditproduct";
    interface creditproduct {
        String crediprod_id = "crediprod_id";
        String crediprod_prod_id = "crediprod_prod_id";
        String crediprod_credi_id = "crediprod_credi_id";
        String crediprod_user_id = "crediprod_user_id";
        String crediprod_state = "crediprod_state";
        String crediprod_price = "crediprod_price";
        String crediprod_dtreg = "crediprod_dtreg";
        String crediprod_quantity = "crediprod_quantity";
        String crediprod_meas_id = "crediprod_meas_id";
        String crediprod_pricequantity = "crediprod_pricequantity";
    }

    String $productprice = "productprice";
    interface productprice {
        String prodprice_id = "prodprice_id";
        String prodprice_prod_id = "prodprice_prod_id";
        String prodprice_user_id = "prodprice_user_id";
        String prodprice_meas_id = "prodprice_meas_id";
        String prodprice_quantity = "prodprice_quantity";
        String prodprice_price = "prodprice_price";
        String prodprice_dtreg = "prodprice_dtreg";
        String prodprice_state = "prodprice_state";
    }

    String $measure = "measure";
    interface measure {
        String meas_id = "meas_id";
        String meas_cod = "meas_cod";
        String meas_name = "meas_name";
    }
}
