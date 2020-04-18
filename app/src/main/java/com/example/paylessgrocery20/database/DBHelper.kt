package com.example.paylessgrocery20.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.paylessgrocery20.app.Config
import com.example.paylessgrocery20.models.Product

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, Config.DATABASE_NAME, null, Config.DATABASE_VERSION) {

    companion object {
        const val TABLE_NAME = "cartItems"
        const val COLUMN_PRODUCT_NAME = "productName"
        const val COLUMN_PRODUCT_IMAGE = "productImage"
        const val COLUMN_PRODUCT_PRICE = "productPrice"
        const val COLUMN_PRODUCT_PRICE_MRP = "productPriceMRP"
        const val COLUMN_PRODUCT_QUANTITY = "productQuantity"
    }

    // all statement to create a table
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "create table $TABLE_NAME($COLUMN_PRODUCT_NAME char(200),$COLUMN_PRODUCT_QUANTITY int(20),$COLUMN_PRODUCT_PRICE double(50),$COLUMN_PRODUCT_PRICE_MRP double(50), $COLUMN_PRODUCT_IMAGE char(200))"
        db?.execSQL(createTable) // generate the SQL statement and create the database
        Log.d("testingDB", "OnCreate Called")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "drop table $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db) // first time only to create the table, next time when you modify, you ahve to change the version code
        Log.d("testingDB", "OnUpgrade Called")
    }

    fun addItemCart(product: Product) {
        if (!isItemInCart(product)) {
            var db: SQLiteDatabase =
                this.writableDatabase  // when ever you want ot modify the data on DB
            // creating the instances of contain values
            val contentValue = ContentValues()
            //contentValue.put(COLUMN_ID, employee.id)
            contentValue.put(COLUMN_PRODUCT_NAME, product.productName )
            contentValue.put(COLUMN_PRODUCT_QUANTITY, product.quantity)
            contentValue.put(COLUMN_PRODUCT_PRICE, product.price)
            contentValue.put(COLUMN_PRODUCT_PRICE_MRP, product.mrp)
            contentValue.put(COLUMN_PRODUCT_IMAGE, product.image)
            // contentvlaue contains both the name and data
            db.insert(TABLE_NAME, null, contentValue)
        } else {
            updateProductQuantity(product,product.quantity)
        }
    }

    fun updateProductQuantity(product: Product,count:Int) {
        var db: SQLiteDatabase = this.writableDatabase
        // first thing = concentValue that contains the hashmap
        val contentValue = ContentValues()
        contentValue.put(COLUMN_PRODUCT_NAME, product.productName )
        //contentValue.put(COLUMN_PRODUCT_QUANTITY, product.quantity)
        contentValue.put(COLUMN_PRODUCT_QUANTITY, count)
        // when ever you want to update, set the column name
        var whereClause = "$COLUMN_PRODUCT_NAME = ?"
        var whereArgs = arrayOf(product.productName) // ? will be replaced with id at runtime,

        db.update(TABLE_NAME, contentValue, whereClause, whereArgs)
    }
    fun getProductQuantity(productName: String): Int {
        //val productCarts = ArrayList<Product>()
        //val user = Employee()
        var quantityRead = 0
        val db = writableDatabase
        var cursor: Cursor? = null
        // SQL Statement to read
        cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_NAME + "='" + productName + "'", null)

        // Passing to Employee
        if (cursor!!.moveToFirst()) {
            quantityRead = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))
            cursor.moveToNext()
        }
        return quantityRead
    }
    fun isCartEmpty():Boolean{
        var db: SQLiteDatabase = this.writableDatabase
        var cursor = db.rawQuery("select * from " + TABLE_NAME  , null)
        var cartExists :Boolean
        if (cursor.moveToFirst())
        {
            cartExists = true;

        } else
        {
            cartExists = false;
        }
        return cartExists
    }
    fun getItemQuantity(product: Product): Int {
        var database: SQLiteDatabase = this.writableDatabase
        var quantity = 0
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_PRODUCT_NAME = ?"
        var cursor = database.rawQuery(query, arrayOf(product.productName))
        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))
            cursor.close()
            return quantity
        }
        return quantity
    }
    fun deleteAllItem() {
        var db: SQLiteDatabase = this.writableDatabase
        db.delete(TABLE_NAME,null, null)
    }
    fun isItemInCart(product: Product): Boolean {
        var database: SQLiteDatabase = this.writableDatabase
        val query = "Select * from $TABLE_NAME where $COLUMN_PRODUCT_NAME=?"
        val cursor = database.rawQuery(query, arrayOf(product.productName))
        var count = cursor.count
        return count != 0
    }
    fun readAllItemCart():ArrayList<Product>{
        val productCarts = ArrayList<Product>()
        //val user = Employee()
        val db = writableDatabase
        var cursor: Cursor? = null
        // SQL Statement to read
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME,null)
        } catch (e: SQLiteException) {
            // if table not yet present, toast an error
            Toast.makeText(mContext, "Nothing in the product database", Toast.LENGTH_LONG).show()
        }
        // Passing to prodcts
        var nameRead: String
        var quantityRead: Int
        var priceRead: Double
        var mrpPriceRead: Double
        var imageRead: String

        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nameRead = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                quantityRead = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))
                priceRead = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE))
                imageRead = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMAGE))
                mrpPriceRead = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE_MRP))
                //emailRead = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                var des = "Testerr"
                productCarts.add(Product(null,nameRead, quantityRead, priceRead,mrpPriceRead, imageRead))
                //users = Employee(userID.toInt(), nameRead, emailRead)
                cursor.moveToNext()
            }
        }
        return productCarts
    }
    fun readItemCart(productName: String): ArrayList<Product> {
        val productCarts = ArrayList<Product>()
        //val user = Employee()
        val db = writableDatabase
        var cursor: Cursor? = null
        // SQL Statement to read
        try {
            cursor = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + COLUMN_PRODUCT_NAME + "='" + productName + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, toast an error
            Toast.makeText(mContext,"Wrong email, please try again", Toast.LENGTH_LONG).show()
        }
        // Passing to Employee
        var nameRead: String
        var quantityRead: Int
        var priceRead: Double
        var mrpPriceRead:Double
        var imageRead: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                nameRead = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                quantityRead = cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY))
                priceRead = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE))
                imageRead = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_IMAGE))
                mrpPriceRead = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRODUCT_PRICE_MRP))
                //emailRead = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
                var des = "Tester"
                productCarts.add(Product(null,nameRead, quantityRead, priceRead,mrpPriceRead, imageRead))
                cursor.moveToNext()
            }
        }
        return productCarts
    }


    fun deleteProduct(product: Product) {
        var database: SQLiteDatabase = this.writableDatabase
        var whereClause = "$COLUMN_PRODUCT_NAME=?"
        var whereArgs = arrayOf(product.productName)
        database.delete(TABLE_NAME, whereClause, whereArgs)
    }
    fun updateItemQuantity(product: Product) {
        var database: SQLiteDatabase = this.writableDatabase
        var contentValues = ContentValues()
        contentValues.put(COLUMN_PRODUCT_QUANTITY, product.quantity)
        database.update(
            TABLE_NAME, contentValues, "$COLUMN_PRODUCT_NAME = ?", arrayOf(product.productName.toString())
        )
    }
    fun getCartQuantityTotal(): Int {
        var database: SQLiteDatabase = this.writableDatabase
        var count = 0
        val columns = arrayOf(COLUMN_PRODUCT_QUANTITY)
        var cursor = database.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var qty = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_QUANTITY)).toInt()
                count = count + qty
            } while (cursor.moveToNext())
        }
        cursor.close()
        return count
    }


}