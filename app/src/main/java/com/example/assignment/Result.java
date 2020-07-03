package com.example.assignment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

/**
 * Created by admin on 16/6/16.
 */
public class Result {

    private Context context;
    private TableLayout tbData;

    public Result(Context context, TableLayout tbData) {
        this.context = context;
        this.tbData = tbData;
    }

    public void fillTable(Cursor cursor) throws SQLiteException {
        tbData.removeAllViews();

        tbData.addView(fillHeader(cursor));

        while (cursor.moveToNext())
            tbData.addView(fillRow(cursor));
    }

    public TableRow fillHeader(Cursor cursor) {
        TableRow tr = new TableRow(context);
        tr.setBackgroundColor(0x33B5E5FF);

        for (int i=0; i<cursor.getColumnCount(); i++) {
            int col = context.getResources().getIdentifier(
                    cursor.getColumnName(i), "string", context.getPackageName());
            tr.addView(fillCell(context.getResources().getString(col)));
        }

        return tr;
    }

    public TableRow fillRow(Cursor cursor) {
        TableRow tr = new TableRow(context);

        for (int i=0; i<cursor.getColumnCount(); i++)
            tr.addView(fillCell(cursor.getString(i)));

        return tr;
    }

    public TextView fillCell(String value) {
        TextView tv = new TextView(context);
        tv.setText(value);
        tv.setPadding(20, 20, 20, 20);
        tv.setTextSize(16);
        tv.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        tv.setTextColor(0xFF000000);

        return tv;
    }
}
