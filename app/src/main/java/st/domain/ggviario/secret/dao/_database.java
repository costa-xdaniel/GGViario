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

    String T_OUTGO$ = "T_OUTGO";
    interface T_OUTGO {
        String out_id = "out_id";
        String aut_user_id = "out_user_id";
        String out_data = "out_data";
        String out_dtreg = "out_dtreg";
        String out_state = "out_state";
    }


    String T_PROVIDER$ = "T_PROVIDER";
    interface T_PROVIDER {
         String provider_id ="provider_id";
         String provider_name = "provider_name";
         String provider_location = "provider_location";
         String provider_mail = "provider_mail";
         String provider_telephone = "provider_telephone";
         String provider_site = "provider_site";
         String provider_state = "provider_state";
         String provider_dtreg = "provider_dtreg";
    }

    String VER_SIMPLEPROVIDER$ = "VER_SIMPLEPROVIDER";
    interface VER_SIMPLEPROVIDER {
        String id  = "id";
        String name = "name";
        String contact = "contact";
        String location = "location";
        String site = "site";
        String mail = "mail";
    }


    String T_OUTGOPERATION$ = "T_OUTGOPERATION";
    interface T_OUTGOPERATION {
        String oprout_id = "oprout_id";
        String oprout_out_id = "oprout_out_id";
        String oprout_provider_id = "oprout_provider_id";
        String oprout_state = "oprout_state";
        String oprout_dtreg = "oprout_dtreg";
    }


    String T_ITEMOUTGO$ = "T_ITEMOUTGO";
    interface T_ITEMOUTGO {
         String iout_id = "iout_id";
         String iout_oprout_id = "iout_oprout_id";
         String iout_price = "iout_price";
         String iout_finalprice = "iout_finalprice";
         String iout_state = "iout_state";
         String iout_dtreg = "iout_dtreg";
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

    String T_GENDER$ = "T_GENDER";
    interface T_GENDER {
        String gender_id = "gender_id";
        String gender_desc = "gender_desc";
    }



























}
