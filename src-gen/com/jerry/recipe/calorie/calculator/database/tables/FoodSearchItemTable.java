package com.jerry.recipe.calorie.calculator.database.tables;

/**
 * This interface represents the columns and SQLite statements for the FoodSearchItemTable.
 * This table is represented in the sqlite database as FoodSearchItem column.
 * 				  
 * Generated Class. Do not modify!
 * 
 * @author MDSDACP Team - goetzfred@fh-bingen.de 
 * @date 2013.02.06
 */
public interface FoodSearchItemTable {
	String TABLE_NAME = "foodsearchitem";

	String ID = "_id";
	String SERVERID = "serverid";
	String NAME = "name";
	String TYPE = "type";
	String BRAND = "brand";
	String URL = "url";
	String DESCRIPTION = "description";

	String[] ALL_COLUMNS = new String[]{ID, SERVERID, NAME, TYPE, BRAND, URL,
			DESCRIPTION};

	String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + SERVERID + " TEXT"
			+ "," + NAME + " TEXT" + "," + TYPE + " TEXT" + "," + BRAND
			+ " TEXT" + "," + URL + " TEXT" + "," + DESCRIPTION + " TEXT"
			+ " )";

	String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + SERVERID + ","
			+ NAME + "," + TYPE + "," + BRAND + "," + URL + "," + DESCRIPTION
			+ ") VALUES ( ?, ?, ?, ?, ?, ? )";

	String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	String WHERE_ID_EQUALS = ID + "=?";
}
