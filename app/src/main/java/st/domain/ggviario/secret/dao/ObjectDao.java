package st.domain.ggviario.secret.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import st.domain.ggviario.secret.model.ObjectItem;
import st.domain.ggviario.secret.model.ObjectItemType;
import st.domain.support.android.sql.OnQueryResult;
import st.domain.support.android.sql.SQLRow;

/**
 *
 * Created by dchost objectItemMap 03/02/17.
 */

public class ObjectDao extends Dao {

    public static final int OBJECT_TYPE_DOCUMENTS = 1;
    private static final int OBJECT_RESIDENCE = 2;

    private Map<Integer, ObjectItem> objectItemMap;
    private Map<Integer, ObjectItemType> objectItemTypeMap;


    public ObjectDao(Context context) {
        super(context);
        this.objectItemTypeMap = new LinkedHashMap<>();
        this.objectItemMap = new LinkedHashMap<>();
        this.loadTypes();
    }

    private void loadTypes() {
        query(
                select("*")
                .from(T_OBJECTTYPE$)
        );

        this.objectItemTypeMap.clear();
        onQueryResult(
                new OnQueryResult() {
                    @Override
                    public boolean accept(SQLRow row) {
                        ObjectItemType item = mountObjectItemType( row );
                        objectItemTypeMap.put( row.integer( T_OBJECTTYPE.tobj_id ), item );
                        return true;
                    }
                }
        );
    }

    private  ObjectItemType mountObjectItemType( SQLRow row ) {
        return new ObjectItemType(
                row.integer(T_OBJECTTYPE.tobj_id),
                row.string(T_OBJECTTYPE.tobj_desc),
                row.integer(T_OBJECTTYPE.tobj_state)
        );
    }

    public ObjectItem get(Integer id) {
        if( id == null ) return  null;

        ObjectItem object = this.objectItemMap.get(id);
        if(object == null) {
            object = this.find(id);
        }
        return  object;
    }

    public ObjectItem createNewObject( ObjectItem objectItem ) {

        execute(
                insertInto(T_OBJECT$)
                .columns(
                        T_OBJECT.obj_desc,
                        T_OBJECT.obj_obj_id,
                        T_OBJECT.obj_user_id,
                        T_OBJECT.obj_tobj_id )
                .values(
                        objectItem.getDesc(),
                        (objectItem.getSuperObjectItem() == null ? null : objectItem.getSuperObjectItem().getId()),
                        UserDao.geUser(this.getContext()).getId(),
                        objectItem.getItemType().getId()
                )
        );

        Long result = getResultExecut();
        if(result != null && result != -1) {
            return this.find(objectItem.getDesc(), objectItem.getItemType().getId());
        }
        return null;
    }

    private ObjectItem find(String desc, int typeId) {

        query(
                select("*")
                .from(VER_OBJECTS$)
                .where(upper(column(VER_OBJECTS.obj_desc))).equal(upper(desc))
                    .and(VER_OBJECTS.obj_tobj_id).equal(value(typeId))
        );

        return  this.instance();
    }


    /**
     * find one object into getDatabase
     * @param id id of object find
     * @return the find
     */
    public ObjectItem find(int id) {
        query(
                select("*")
                .from(VER_OBJECTS$)
                .where(VER_OBJECTS.obj_id).equal(value(id))

        );

        return instance();
    }

    @Nullable
    private ObjectItem instance() {

        List<SQLRow> result = catchAllResult();
        if(result != null && result.size() != 0) {
            SQLRow row = result.get(0);
            ObjectItem objectItem = mount(row);
            return objectItem;
        }
        return  null;

    }

    /**
     * Load all object form data base
     * @return list of object from getDatabase
     */
    public List<ObjectItem> loadAll(){

        /*
        load all data from my entity object
         */
        final List<ObjectItem> list = new LinkedList<>();
        query(
                select("*")
                    .from(VER_OBJECTS$)
        );
        this.objectItemMap.clear();

        onQueryResult(new OnQueryResult() {
                    @Override
                    public boolean accept(SQLRow row) {
                    ObjectItem objectItem = null;
                    if(! ObjectDao.this.objectItemMap.containsKey(row.integer(VER_OBJECTS.obj_id)) ) {
                        objectItem = ObjectDao.this.mount(row);
                    }

                    list.add(objectItem);

                    return true;
                }
            }
        );
        return list;
    }

    /**
     * Mount the object from row sql row
     * @param row the result form sql
     * @return the mounted object
     */
    protected @NonNull ObjectItem mount(SQLRow row) {

        ObjectItem superObjectItem = null;
        if( row.integer(VER_OBJECTS.obj_obj_id) != null ) {
            superObjectItem = this.get(row.integer(VER_OBJECTS.obj_obj_id));
        }


        ObjectItem objectItem = new ObjectItem(
                row.integer("obj_id"),
                row.string(VER_OBJECTS.obj_desc),
                getType(row.integer(VER_OBJECTS.obj_tobj_id)),
                superObjectItem
        );

        this.objectItemMap.put(objectItem.getId(), objectItem);
        return  objectItem;
    }

    /**
     * Verify if exist the object indexed in local map or in getDatabase
     * @param id the identifier of object required
     * @return { true } if exist any object witch id
     * { false } otherwise
     */
    public boolean hasOject(int id ) {
        return  this.get( id ) != null;
    }


    private @NonNull ObjectItemType getType(Integer integer) {
        return this.objectItemTypeMap.get(integer);
    }

    public Collection<? extends ObjectItem> loadTypeDocuments() {
        return  this.loadObjectsOf(OBJECT_TYPE_DOCUMENTS);
    }

    private Collection<? extends ObjectItem> loadObjectsOf(int objectTypeDocuments) {
        final List<ObjectItem>  listOf = new LinkedList<>();

        query(
                select("*")
                .from(T_OBJECT$)
                .where( VER_OBJECTS.obj_tobj_id ).equal( value(objectTypeDocuments) )
        );

        onQueryResult(new OnQueryResult() {
            @Override
            public boolean accept(SQLRow row) {
                listOf.add(mount(row));
                return true;
            }
        });

        return listOf;
    }

    public ObjectItem findResidence(String residence) {
        query(
                select("*")
                .from(VER_OBJECTS$)
                .where(VER_OBJECTS.obj_tobj_id).equal(value(OBJECT_RESIDENCE))
                    .and(upper(column(VER_OBJECTS.obj_desc))).equal(upper(residence))
                .limit(1)
        );

        List<SQLRow> list = catchAllResult();
        if(list != null && list.size() >0 )
            return mount(list.get(0));

        return createNewObject( new ObjectItem( residence, this.objectItemTypeMap.get( OBJECT_RESIDENCE ), null ) );
    }
}
