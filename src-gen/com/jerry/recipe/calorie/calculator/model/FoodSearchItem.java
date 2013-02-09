package com.jerry.recipe.calorie.calculator.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.jerry.recipe.calorie.calculator.database.tables.FoodSearchItemTable;

/**
 * Generated model class for usage in your application, defined by classifiers in ecore diagram
 * 		
 * Generated Class. Do not modify!
 * 
 * @author MDSDACP Team - goetzfred@fh-bingen.de 
 * @date 2013.02.06	 
 */
public class FoodSearchItem {

	private Long id;
	private java.lang.String serverid;
	private java.lang.String name;
	private java.lang.String type;
	private java.lang.String brand;
	private java.lang.String url;
	private java.lang.String description;

	private final ContentValues values = new ContentValues();

	public FoodSearchItem() {
	}

	public FoodSearchItem(final Cursor cursor) {
		setId(cursor.getLong(cursor.getColumnIndex(FoodSearchItemTable.ID)));
		setServerId(cursor.getString(cursor
				.getColumnIndex(FoodSearchItemTable.SERVERID)));
		setName(cursor.getString(cursor
				.getColumnIndex(FoodSearchItemTable.NAME)));
		setType(cursor.getString(cursor
				.getColumnIndex(FoodSearchItemTable.TYPE)));
		setBrand(cursor.getString(cursor
				.getColumnIndex(FoodSearchItemTable.BRAND)));
		setUrl(cursor.getString(cursor.getColumnIndex(FoodSearchItemTable.URL)));
		setDescription(cursor.getString(cursor
				.getColumnIndex(FoodSearchItemTable.DESCRIPTION)));

	}

	/**
	 * Set id
	 *
	 * @param id from type java.lang.Long
	 */
	public void setId(final Long id) {
		this.id = id;
		this.values.put(FoodSearchItemTable.ID, id);
	}

	/**
	 * Get id
	 *
	 * @return id from type java.lang.Long				
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Set serverid and set content value
	 *
	 * @param serverid from type java.lang.String
	 */
	public void setServerId(final java.lang.String serverid) {
		this.serverid = serverid;
		this.values.put(FoodSearchItemTable.SERVERID, serverid);
	}

	/**
	 * Get serverid
	 *
	 * @return serverid from type java.lang.String				
	 */
	public java.lang.String getServerId() {
		return this.serverid;
	}

	/**
	 * Set name and set content value
	 *
	 * @param name from type java.lang.String
	 */
	public void setName(final java.lang.String name) {
		this.name = name;
		this.values.put(FoodSearchItemTable.NAME, name);
	}

	/**
	 * Get name
	 *
	 * @return name from type java.lang.String				
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * Set type and set content value
	 *
	 * @param type from type java.lang.String
	 */
	public void setType(final java.lang.String type) {
		this.type = type;
		this.values.put(FoodSearchItemTable.TYPE, type);
	}

	/**
	 * Get type
	 *
	 * @return type from type java.lang.String				
	 */
	public java.lang.String getType() {
		return this.type;
	}

	/**
	 * Set brand and set content value
	 *
	 * @param brand from type java.lang.String
	 */
	public void setBrand(final java.lang.String brand) {
		this.brand = brand;
		this.values.put(FoodSearchItemTable.BRAND, brand);
	}

	/**
	 * Get brand
	 *
	 * @return brand from type java.lang.String				
	 */
	public java.lang.String getBrand() {
		return this.brand;
	}

	/**
	 * Set url and set content value
	 *
	 * @param url from type java.lang.String
	 */
	public void setUrl(final java.lang.String url) {
		this.url = url;
		this.values.put(FoodSearchItemTable.URL, url);
	}

	/**
	 * Get url
	 *
	 * @return url from type java.lang.String				
	 */
	public java.lang.String getUrl() {
		return this.url;
	}

	/**
	 * Set description and set content value
	 *
	 * @param description from type java.lang.String
	 */
	public void setDescription(final java.lang.String description) {
		this.description = description;
		this.values.put(FoodSearchItemTable.DESCRIPTION, description);
	}

	/**
	 * Get description
	 *
	 * @return description from type java.lang.String				
	 */
	public java.lang.String getDescription() {
		return this.description;
	}

	/**
	 * Get ContentValues
	 *
	 * @return id from type android.content.ContentValues with the values of this object				
	 */
	public ContentValues getContentValues() {
		return this.values;
	}
}
