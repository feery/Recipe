package com.fycedwin.chicken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class SqlQry {

    public static final String TABLE                ="CHICKN_TABLE";
    public static final String _ID                  ="_ID";
    public static final String name                 ="name";
    public static final String creator              ="creator";
    public static final String review               ="review";
    public static final String rating               ="rating";
    public static final String image                ="image";
    public static final String ingredients          ="ingredients";



    public static final String CREATE_TABLE =
            " CREATE TABLE "+TABLE+" ("
                    +_ID+" INTEGER PRIMARY KEY, "
                    +name+" TEXT, "
                    +creator+" TEXT, "
                    +review+" TEXT, "
                    +rating+" TEXT, "
                    +image+" TEXT, "
                    +ingredients+" TEXT); ";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS "+TABLE;

    private final Context context;
    private SqlHel databaseHelper;
    private SQLiteDatabase sqLiteDatabase;


    public SqlQry(Context context) {
        this.context = context;
    }

    private SqlQry open() throws SQLException {
        databaseHelper = new SqlHel(context, "CHICKEN", 1);
        return this;
    }

    private void close() {
        if (sqLiteDatabase != null && sqLiteDatabase.isOpen())
            sqLiteDatabase.close();
    }




    public MetaData save(MetaData model) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues v = new ContentValues();
        v.put(name, model.getName());
        v.put(creator, model.getCreator());
        v.put(review, model.getReview());
        v.put(rating, model.getRating());
        v.put(image, model.getImage());
        v.put(ingredients, model.getIngredients());

        if (model.getId() == null) {
            Long id = sqLiteDatabase.insert(TABLE, null, v);
            model.setId(id.intValue());
        } else {
            sqLiteDatabase.update(TABLE, v, _ID + "=?", new String[]{String.valueOf(model.getId())});
        }

        close();
        return model;
    }

//    public UserMetaData save(Long mobileId, Long branchId, String branchType, String branchName,
//                             Long userId, String username, String name, String roleName,
//                             Long roleGroupId, String appName, Long LoginTime, String imei, Long recapId, Integer v,String email) {
//        UserMetaData model = selectBy(username);
//        if (model == null) {
//            model = new UserMetaData();
//        }
//        model.setMobileId(mobileId);
//        model.setBranchId(branchId);
//        model.setBranchType(branchType);
//        model.setBranchName(branchName);
//        model.setUserId(userId);
//        model.setUsername(username);
//        model.setName(name);
//        model.setRoleName(roleName.toUpperCase());
//        model.setRoleGroupId(roleGroupId);
//        model.setAppName(appName);
//        model.setImei(imei);
//        model.setRecapId(recapId);
//        model.setLoginTime(LoginTime);
//        model.setV(v);
//        model.setEmail(email);
//        return save(model);
//    }



    public void delete(Integer id) {
        open();
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, _ID + "=?", new String[]{String.valueOf(id)});
        close();
    }




    public List<MetaData> selectList() {
        String q = "SELECT * FROM "+TABLE+" WHERE 1=1";
        List<MetaData> list = new ArrayList<>();

        open();
        sqLiteDatabase = databaseHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery(q, null);

        MetaData model;
        if (c.moveToFirst()) {
            do {
                model = new MetaData();
                model.setId(c.getInt(c.getColumnIndex(_ID)));
                model.setName(c.getString(c.getColumnIndex(name)));
                model.setCreator(c.getString(c.getColumnIndex(creator)));
                model.setImage(c.getString(c.getColumnIndex(image)));
                model.setIngredients(c.getString(c.getColumnIndex(ingredients)));
                model.setRating(c.getString(c.getColumnIndex(rating)));
                model.setReview(c.getString(c.getColumnIndex(review)));
                list.add(model);
            } while (c.moveToNext());
        }

        close();
        return list;
    }




}
