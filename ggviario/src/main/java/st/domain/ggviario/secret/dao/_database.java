package st.domain.ggviario.secret.dao;


/**
 *
 * Created by xdata on 12/24/16.
 */


interface _database {

    String T_USER$ = "T_USER";
    interface T_USER {
        String user_id = "user_id";
        String user_name = "user_name";
        String user_surname = "user_surname";
        String user_accessname = "user_accessname";
        String user_pwd = "user_pwd";
        String user_state = "user_state";
        String user_state_l = "user_state";
    }

    String T_SECTOR$ = "T_SECTOR";
    interface T_SECTOR {
        String sector_id = "sector_id";
        String sector_name = "sector_name";
    }

    String T_CROP$ = "T_CROP";
    interface T_CROP {
        String crop_id = "crop_id";
        String crop_sector_id = "crop_sector_id";
        String crop_totalovos = "crop_totalovos";
        String crop_user_id  = "crop_user_id";
        String crop_percasovos = "crop_percasovos";
        String crop_percasgalinhas = "crop_percasgalinhas";
        String crop_state = "crop_state";
        String crop_dtreg = "crop_dtreg";
    }

    String VER_CROPGROUP$ = "VER_CROPGROUP";
    interface VER_CROPGROUP {
        String date = "date";
        String fdate = "fdate";
        String quantity = "quantity";
        String quantitypercas = "quantitypercas";
        String quantitypercasgalinha = "quantitypercasgalinha";
        String quantityoperactions = "quantityoperactions";
        String numbersector = "numbersector";
    }

    String VER_CROPSECTORDATE$ = "VER_CROPSECTORDATE";
    interface VER_CROPSECTORDATE {
        String date = "date";
        String fdate = "fdate";
        String quantity = "quantity";
        String quantitypercas = "quantitypercas";
        String quantitypercasgalinha = "quantitypercasgalinha";

    }

    String VER_CROP_DATE$ = "VER_CROP_DATE";
    interface VER_CROP_DATE {
        String date = "date";
    }

    String T_OBJECTTYPE$ = "T_OBJECTTYPE";
    interface T_OBJECTTYPE {
        String tobj_id = "tobj_id";
        String tobj_desc = "tobj_desc";
        String tobj_state = "tobj_state";
    }


    //Objects
    String T_OBJECT$ = "T_OBJECT";
    interface T_OBJECT {
        String obj_id = "obj_id";
        String obj_tobj_id = "obj_tobj_id";
        String obj_obj_id = "obj_obj_id";
        String obj_user_id = "obj_user_id";
        String obj_desc = "obj_desc";
        String obj_state = "obj_state";
    }

    String VER_OBJECTS$ = "VER_OBJECTS";
    interface VER_OBJECTS extends T_OBJECT {}

    String T_CLIENT$ = "T_CLIENT";
    interface  T_CLIENT {
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

    String $VER_STATUS_CLIENT = "VER_STATUS_CLIENT";
    interface  VER_STATUS_CLIENT extends  T_CLIENT {
        String totalcredits = "totalcredits";
        String totalcreditspay = "totalcreditspay";
    }

    String T_GENDER$ = "T_GENDER";
    interface T_GENDER {
        String gender_id = "gender_id";
        String gender_desc = "gender_desc";
    }

    String $T_PRODUCT = "T_PRODUCT";
    interface T_PRODUCT {
        String prod_id = "prod_id";
        String prod_prod_id = "prod_prod_id";
        String prod_met_id = "prod_met_id";
        String prod_price = "prod_price";
        String prod_name = "prod_name";
        String prod_detais = "prod_detais";
        String prod_stock = "prod_stock";
        String prod_scalesuper = "prod_scalesuper";
        String prod_state = "prod_state";
        String prod_dtreg = "prod_dtreg";
    }

    String $CREDIT = "T_CREDIT";
    interface T_CREDIT {
        String credi_id = "credi_id";
        String credi_user_id = "credi_user_id";
        String credi_cli_id = "credi_cli_id";
        String credi_valuetotal = "credi_valuetotal";
        String credi_valuepago = "credi_valuepago";
        String credi_dtfinalizar = "credi_dtfinalizar";
        String credi_dtfim = "credi_dtfim";
        String credi_state = "credi_state";
        String credi_dtreg = "credi_dtreg";
    }

    String $T_CREDITPRODUCT = "T_CREDITPRODUCT";
    interface  T_CREDITPRODUCT {
        String crediprod_id = "crediprod_id";
        String crediprod_prod_id = "crediprod_prod_id";
        String crediprod_credi_id = "crediprod_credi_id";
        String crediprod_user_id = "crediprod_user_id";
        String crediprod_state = "crediprod_state";
        String crediprod_value = "crediprod_value";
        String crediprod_dtreg = "crediprod_dtreg";
        String crediprod_quantity = "crediprod_quantity";
    }

    String $T_PRODUCTPRICE = "T_PRODUCTPRICE";
    interface T_PRODUCTPRICE {
        String prodprice_id = "prodprice_id";
        String prodprice_prod_id = "prodprice_prod_id";
        String prodprice_user_id = "prodprice_user_id";
        String prodprice_price = "prodprice_price";
        String prodprice_dtreg = "prodprice_dtreg";
        String prodprice_state = "prodprice_state";
    }

}
