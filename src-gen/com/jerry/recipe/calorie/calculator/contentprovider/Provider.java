package com.jerry.recipe.calorie.calculator.contentprovider;

import com.jerry.recipe.calorie.calculator.database.Database;

import com.jerry.recipe.calorie.calculator.database.tables.*;

import android.provider.BaseColumns;
import android.text.TextUtils;
import android.content.ContentUris;
import android.database.sqlite.SQLiteQueryBuilder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Content provider implementation
 * The authority of the content provider is: content://com.jerry.recipe.calorie.calculator.provider.recipecalculator
 * 
 * More information about content providers:
 * @see <a href="http://developer.android.com/reference/android/content/ContentProvider.html">Reference</a>
 * @see <a href="http://developer.android.com/guide/topics/providers/content-providers.html">Tutorial</a>
 * @see <a href="http://developer.android.com/guide/topics/testing/contentprovider_testing.html">Content Provider Testing</a>
 *
 * Generated Class. Do not modify!
 * 
 * @author MDSDACP Team - goetzfred@fh-bingen.de 
 * @date 2013.02.06
 */
public class Provider extends ContentProvider {
	private static final String TAG = "com.jerry.recipe.calorie.calculator.contentprovider.Provider";

	public static final String AUTHORITY = "com.jerry.recipe.calorie.calculator.provider.recipecalculator";
	public static final Uri AUTHORITY_URI = Uri.parse("content://" + AUTHORITY);

	public static final Uri FOODSEARCHITEM_CONTENT_URI = Uri.withAppendedPath(
			Provider.AUTHORITY_URI, FoodSearchItemContent.CONTENT_PATH);

	private static final UriMatcher URI_MATCHER;

	private Database db;

	private static final int FOODSEARCHITEM_DIR = 0;
	private static final int FOODSEARCHITEM_ID = 1;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITY, FoodSearchItemContent.CONTENT_PATH,
				FOODSEARCHITEM_DIR);
		URI_MATCHER.addURI(AUTHORITY,
				FoodSearchItemContent.CONTENT_PATH + "/#", FOODSEARCHITEM_ID);
	}

	/**
	 * Provides the content information of the FoodSearchItemTable.
	 * 
	 * CONTENT_PATH: foodsearchitem (String)
	 * CONTENT_TYPE: vnd.android.cursor.dir/vnd.mdsdacp.foodsearchitem (String)
	 * CONTENT_ITEM_TYPE: vnd.android.cursor.item/vnd.mdsdacp.foodsearchitem (String)
	 * ALL_COLUMNS: Provides the same information as FoodSearchItemTable.ALL_COLUMNS (String[])
	 */
	public static final class FoodSearchItemContent implements BaseColumns {
		/**
		 * Specifies the content path of the FoodSearchItemTable for the required uri
		 * Exact URI: content://com.jerry.recipe.calorie.calculator.provider.recipecalculator/foodsearchitem
		 */
		public static final String CONTENT_PATH = "foodsearchitem";

		/**
		 * Specifies the type for the folder and the single item of the FoodSearchItemTable  
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.mdsdacp.foodsearchitem";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.mdsdacp.foodsearchitem";

		/**
		 * Contains all columns of the FoodSearchItemTable
		 */
		public static final String[] ALL_COLUMNS = FoodSearchItemTable.ALL_COLUMNS;
	}

	/**
	 * Instantiate the database, when the content provider is created
	 */
	@Override
	public final boolean onCreate() {
		db = new Database(getContext());
		return true;
	}

	/**
	 * Providing information whether uri returns an item or an directory.
	 * 
	 * @param uri from type Uri
	 * 
	 * @return content_type from type Content.CONTENT_TYPE or Content.CONTENT_ITEM_TYPE
	 *
	 */
	@Override
	public final String getType(final Uri uri) {
		switch (URI_MATCHER.match(uri)) {
			case FOODSEARCHITEM_DIR :
				return FoodSearchItemContent.CONTENT_TYPE;
			case FOODSEARCHITEM_ID :
				return FoodSearchItemContent.CONTENT_ITEM_TYPE;
			default :
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	/**
	 * Insert given values to given uri. Uri has to be from type directory (see switch-cases).
	 * Returns uri of inserted element.
	 *
	 * @param uri from type Uri
	 * @param values from type ContentValues
	 *
	 * @return uri of inserted element from type Uri
	 */
	@Override
	public final Uri insert(final Uri uri, final ContentValues values) {
		final SQLiteDatabase dbConnection = db.getWritableDatabase();

		try {
			dbConnection.beginTransaction();

			switch (URI_MATCHER.match(uri)) {
				case FOODSEARCHITEM_DIR :
				case FOODSEARCHITEM_ID :
					final long foodsearchitemid = dbConnection.insertOrThrow(
							FoodSearchItemTable.TABLE_NAME, null, values);
					final Uri newFoodSearchItem = ContentUris.withAppendedId(
							FOODSEARCHITEM_CONTENT_URI, foodsearchitemid);
					getContext().getContentResolver().notifyChange(
							newFoodSearchItem, null);
					dbConnection.setTransactionSuccessful();
					return newFoodSearchItem;
				default :
					throw new IllegalArgumentException("Unsupported URI:" + uri);
			}
		} catch (Exception e) {
			Log.e(TAG, "Insert Exception", e);
		} finally {
			dbConnection.endTransaction();
		}

		return null;
	}

	/**
	 * Updates given values of given uri, returning number of affected rows.
	 *
	 * @param uri from type Uri
	 * @param values from type ContentValues
	 * @param selection from type String
	 * @param selectionArgs from type String[]
	 *
	 * @return number of affected rows from type int
	 */
	@Override
	public final int update(final Uri uri, final ContentValues values,
			final String selection, final String[] selectionArgs) {

		final SQLiteDatabase dbConnection = db.getWritableDatabase();
		int updateCount = 0;

		try {
			dbConnection.beginTransaction();

			switch (URI_MATCHER.match(uri)) {

				case FOODSEARCHITEM_DIR :
					updateCount = dbConnection.update(
							FoodSearchItemTable.TABLE_NAME, values, selection,
							selectionArgs);
					dbConnection.setTransactionSuccessful();
					break;
				case FOODSEARCHITEM_ID :
					final Long foodsearchitemId = ContentUris.parseId(uri);
					updateCount = dbConnection.update(
							FoodSearchItemTable.TABLE_NAME, values,
							FoodSearchItemTable.ID
									+ "="
									+ foodsearchitemId
									+ (TextUtils.isEmpty(selection)
											? ""
											: " AND (" + selection + ")"),
							selectionArgs);
					dbConnection.setTransactionSuccessful();
					break;
				default :
					throw new IllegalArgumentException("Unsupported URI:" + uri);
			}
		} finally {
			dbConnection.endTransaction();
		}

		if (updateCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return updateCount;

	}

	/**
	 * Deletes given elements by their uri (items or directories) and returns number of deleted rows.
	 *
	 * @param uri from type Uri
	 * @param selection from type String
	 * @param selectionArgs from type String[]
	 *
	 * @return number of deleted rows from type int
	 */
	@Override
	public final int delete(final Uri uri, final String selection,
			final String[] selectionArgs) {

		final SQLiteDatabase dbConnection = db.getWritableDatabase();
		int deleteCount = 0;

		try {
			dbConnection.beginTransaction();

			switch (URI_MATCHER.match(uri)) {
				case FOODSEARCHITEM_DIR :
					deleteCount = dbConnection.delete(
							FoodSearchItemTable.TABLE_NAME, selection,
							selectionArgs);
					dbConnection.setTransactionSuccessful();
					break;
				case FOODSEARCHITEM_ID :
					deleteCount = dbConnection.delete(
							FoodSearchItemTable.TABLE_NAME,
							FoodSearchItemTable.WHERE_ID_EQUALS,
							new String[]{uri.getPathSegments().get(1)});
					dbConnection.setTransactionSuccessful();
					break;

				default :
					throw new IllegalArgumentException("Unsupported URI:" + uri);
			}
		} finally {
			dbConnection.endTransaction();
		}

		if (deleteCount > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}

		return deleteCount;

	}

	/**
	 * Executes a query on a given uri and returns a Cursor with results.
	 *
	 * @param uri from type Uri
	 * @param projection from type String[]
	 * @param selection from type String 
	 * @param selectionArgs from type String[]
	 * @param sortOrder from type String
	 *
	 * @return cursor with results from type Cursor
	 */
	@Override
	public final Cursor query(final Uri uri, final String[] projection,
			final String selection, final String[] selectionArgs,
			final String sortOrder) {

		final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		final SQLiteDatabase dbConnection = db.getReadableDatabase();

		switch (URI_MATCHER.match(uri)) {
			case FOODSEARCHITEM_ID :
				queryBuilder.appendWhere(FoodSearchItemTable.ID + "="
						+ uri.getPathSegments().get(1));
			case FOODSEARCHITEM_DIR :
				queryBuilder.setTables(FoodSearchItemTable.TABLE_NAME);
				break;
			default :
				throw new IllegalArgumentException("Unsupported URI:" + uri);
		}

		Cursor cursor = queryBuilder.query(dbConnection, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;

	}

}
