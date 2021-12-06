package rudenia.fit.bstu.projectbd.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ProjectDb.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Airport ( "
                + "ID_AIRPORT       INTEGER  NOT NULL , "
                + "Name_Airport         TEXT    NOT NULL, "
                + "Work_time            TEXT    NOT NULL, "
                + "AirPlane TEXT PRIMARY KEY    NOT NULL);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS AirPlane ("
                + "ID_Plane INTEGER NOT NULL, "
                + "NAME_PLANE TEXT PRIMARY KEY   NOT NULL, "
                + "Count_Pilots  INTEGER NOT NULL, "
                + "Count_state    INTEGER    NOT NULL, "
                + "YearOfRelease    INTEGER     NOT NULL, "
                + "FOREIGN KEY(NAME_PLANE) REFERENCES Airport(AirPlane) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS Scoreboard ("
                + "AirPlane   TEXT NOT NULL, "
                + "Where_From TEXT  NOT NULL, "
                + "ARRIVAL_DEPARTURE  TEXT NOT NULL, "
                + "Date DATE    NOT NULL, "
                + "PEOPLEONBOARD INTEGER    NOT NULL, "
                + "FOREIGN KEY(AirPlane) REFERENCES AirPlane(NAME_PLANE) "
                + "ON DELETE CASCADE ON UPDATE CASCADE);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE Airport;");
        db.execSQL("DROP TABLE AirPlane");
        db.execSQL("DROP TABLE Scoreboard;");
        onCreate(db);
    }


    public void addAirport(SQLiteDatabase db, int ID_AIRPORT, String Name_Airport, String Work_time, String AirPlane ) {
        ContentValues cv = new ContentValues();
        cv.put("ID_AIRPORT", ID_AIRPORT);
        cv.put("Name_Airport", Name_Airport);
        cv.put("Work_time", Work_time);
        cv.put("AirPlane", AirPlane);
        db.insert("Airport", null, cv);
        cv.clear();
    }

    public void addAirPlane(SQLiteDatabase db, int ID_Plane, String NAME_PLANE, int Count_Pilots, int Count_state, int YearOfRelease) {
        ContentValues cv = new ContentValues();
        cv.put("ID_Plane", ID_Plane);
        cv.put("NAME_PLANE", NAME_PLANE);
        cv.put("Count_Pilots", Count_Pilots);
        cv.put("Count_state", Count_state);
        cv.put("YearOfRelease", YearOfRelease);
        db.insert("AirPlane", null, cv);
        cv.clear();
    }

    public void addScoreboard(SQLiteDatabase db, String AirPlane, String Where_From, String ARRIVAL_DEPARTURE,String Date, int PEOPLEONBOARD) {
        ContentValues cv = new ContentValues();
        cv.put("AirPlane", AirPlane);
        cv.put("Where_From", Where_From);
        cv.put("ARRIVAL_DEPARTURE", ARRIVAL_DEPARTURE);
        cv.put("Date", Date);
        cv.put("PEOPLEONBOARD", PEOPLEONBOARD);
        db.insert("Scoreboard", null, cv);
        cv.clear();
    }


    public void initDatabase(SQLiteDatabase db) {
        addAirport(db, 1,"Минск-1", "Круглосуточно","boeing-777");
        addAirport(db, 2,"Минск-2", "Круглосуточно","boeing-333");
        addAirport(db, 3,"Минск-3", "Круглосуточно","boeing-555");
        addAirPlane(db, 1,"boeing-777", 7, 77, 2007);
        addAirPlane(db, 2,"boeing-333", 3, 33, 2003);
        addAirPlane(db, 3,"boeing-555", 5, 55, 2005);
        addScoreboard(db, "boeing-777", "Moscow", "вылетает","2015-11-11", 711);
        addScoreboard(db, "boeing-777", "Warsaw", "прилетает","2016-11-11", 465);
        addScoreboard(db, "boeing-333", "Istanbul", "вылетает","2017-11-11", 212);
        addScoreboard(db, "boeing-333", "Kiev", "прилетает","2018-11-11", 156);
        addScoreboard(db, "boeing-555", "Beijing", "вылетает","2019-11-11", 545);
        addScoreboard(db, "boeing-555", "Vilnius", "прилетает","2020-11-11", 444);
        addScoreboard(db, "boeing-777", "Mogilew", "вылетает","2015-12-11", 711);
        addScoreboard(db, "boeing-777", "Riga", "прилетает","2016-12-11", 465);
        addScoreboard(db, "boeing-333", "Madrid", "вылетает","2017-12-11", 212);
        addScoreboard(db, "boeing-333", "Paris", "прилетает","2018-12-11", 156);
        addScoreboard(db, "boeing-555", "Barcelona", "вылетает","2019-12-11", 545);
        addScoreboard(db, "boeing-555", "Milan", "прилетает","2020-12-11", 444);
    }
///ARRIVAL_DEPARTURE
    public void createViews(SQLiteDatabase db) {
        db.execSQL("drop view if exists Scoreboard_ARRIVAL; ");
        db.execSQL("create view if not exists Scoreboard_ARRIVAL as " +
                "select AirPlane, Where_From, ARRIVAL_DEPARTURE,Date, PEOPLEONBOARD from Scoreboard ;"
                //"where ARRIVAL_DEPARTURE = 'прилетает'; "
        );

        db.execSQL("drop view if exists Scoreboard_DEPARTURE; ");
        db.execSQL("create view if not exists Scoreboard_DEPARTURE as " +
                "select AirPlane, Where_From, ARRIVAL_DEPARTURE,Date, PEOPLEONBOARD from Scoreboard " +
                "where ARRIVAL_DEPARTURE = 'вылетает'; "
        );
    }



}
