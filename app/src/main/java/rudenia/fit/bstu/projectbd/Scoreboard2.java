package rudenia.fit.bstu.projectbd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import rudenia.fit.bstu.projectbd.database.DbHelper;

public class Scoreboard2 extends AppCompatActivity implements View.OnClickListener {

    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private EditText mDateWith, mDateOn;
    private String dateWith, dateOn;
    //private Spinner mType;
    private GridView mResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard2);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        mDateWith = findViewById(R.id.date_with);
        mDateOn = findViewById(R.id.date_on);
        //mType = findViewById(R.id.type);
        mResult =  findViewById(R.id.result);

        findViewById(R.id.button_select).setOnClickListener(this);

        // initSpiner();
    }


//    protected void initSpiner() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getType());
//        mType.setAdapter(adapter);
//    }

//    protected ArrayList<String> getType() {
//        ArrayList<String> data = new ArrayList<>();
//        String query = "select Scoreboard.ARRIVAL_DEPARTURE as faculty from Scoreboard";
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            int facultyIndex = cursor.getColumnIndex("faculty");
//            do {
//                data.add(cursor.getString(facultyIndex));
//            } while (cursor.moveToNext());
//        }
//        return data;
//    }

    protected boolean formatDateIsCorrect(String date) {
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setLenient(false);
        return df.parse(date, new ParsePosition(0)) != null;
    }

    protected boolean getPeriod(){
        if(formatDateIsCorrect(mDateWith.getText().toString())) {
            dateWith = mDateWith.getText().toString();
            if(formatDateIsCorrect(mDateOn.getText().toString())) {
                dateOn = mDateOn.getText().toString();
                return true;
            } else {
                mDateOn.setError("Неверный формат даты");
                mDateOn.requestFocus();
                return false;
            }
        } else {
            mDateWith.setError("Неверный формат даты");
            mDateWith.requestFocus();
            return false;
        }
    }

    protected void Scoreboard() {
        if (getPeriod()) {
            mResult.setNumColumns(4);
            mResult.setAdapter(null);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
            adapter.add("Самолет");
            adapter.add("Откуда");
            adapter.add("тип");
            adapter.add("дата");
            //adapter.add("PEOPLEONBOARD");

            String query = "select AirPlane, Where_From, ARRIVAL_DEPARTURE, Date from Scoreboard_ARRIVAL "
                    + "where ARRIVAL_DEPARTURE = 'прилетает' "
                    + "and Date BETWEEN '" + dateWith + "' and '" + dateOn + "' "
                    + "order by Where_From desc";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int facultyIndex = cursor.getColumnIndex("AirPlane");
                int groupNameIndex = cursor.getColumnIndex("Where_From");
                int strudentNameIndex = cursor.getColumnIndex("ARRIVAL_DEPARTURE");
                int markIndex = cursor.getColumnIndex("Date");
                //int PeopleIndex = cursor.getColumnIndex("PEOPLEONBOARD");
                do {
                    adapter.add(cursor.getString(facultyIndex));
                    adapter.add(cursor.getString(groupNameIndex));
                    adapter.add(cursor.getString(strudentNameIndex));
                    adapter.add(cursor.getString(markIndex));
                    // adapter.add(cursor.getString(PeopleIndex));
                } while (cursor.moveToNext());
            }
            mResult.setAdapter(adapter);
            cursor.close();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_select:
                Scoreboard();
                break;
        }
    }
}