package rudenia.fit.bstu.projectbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;


import rudenia.fit.bstu.projectbd.database.DbHelper;
import rudenia.fit.bstu.projectbd.view.MainActivityAPI;

public class MainActivity extends AppCompatActivity {
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();
      // dbHelper.createViews(db);
       //dbHelper.initDatabase(db);



    }
    public void Weather(View view){
        Intent intent = new Intent(MainActivity.this, MainActivityAPI.class);
        startActivity(intent);
    }
    public void Scoreboard(View view){
        Intent intent = new Intent(MainActivity.this, Scoreboard.class);
        startActivity(intent);
    }
    public void Scoreboard2(View view){
        Intent intent = new Intent(MainActivity.this, Scoreboard2.class);
        startActivity(intent);
    }
}