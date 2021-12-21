package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linhkien.R;

import java.util.ArrayList;


import MenuItem.Thucpham;

public class ListHSActivity extends AppCompatActivity {
    String DATABASE_NAME = "QLTP.db";
    SQLiteDatabase database;
    ListView lstDSTP;
    Button btnthem;
    ArrayList<Thucpham> list;
    AdapterThucpham adapterThucpham;
    Toolbar toolbar;

    ImageButton btnTim;
    DrawerLayout drawerlayout;

    ListView listView;


    EditText txtnoidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        lstDSTP = (ListView)findViewById(R.id.ListViewTP);
        AnhXa();
        addEvent();
        ActionBar();

        list = new ArrayList<>();
        adapterThucpham = new AdapterThucpham(ListHSActivity.this, list);
        lstDSTP.setAdapter(adapterThucpham);
        database = Database.initDatabase(ListHSActivity.this,DATABASE_NAME);

        Cursor cursor = database.rawQuery("select * from Ca",null);
        for (int i = 0; i <cursor.getCount();i++){
            cursor.moveToPosition(i);
            int ma = cursor.getInt(0);
            String ten = cursor.getString(1);
            int gia = cursor.getInt(2);
            byte[] anh = cursor.getBlob(3);
            list.add(new Thucpham(ma,ten,gia,anh));
        }
        adapterThucpham.notifyDataSetChanged();
    }

    private void addEvent() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListHSActivity.this, InsertHSActivity.class);
                startActivity(intent);
            }
        });
        btnTim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = txtnoidung.getText().toString().trim();
                if(TextUtils.isEmpty(noidung)){
                    Toast.makeText(ListHSActivity.this,"Vui long nhap",Toast.LENGTH_SHORT).show();
                    return;
                }
                database = Database.initDatabase(ListHSActivity.this,DATABASE_NAME);
                Cursor cursor= database.rawQuery("select * from Ca where Ten Like'%"+noidung+"%'",null);
                list.clear();
                for (int i=0; i<cursor.getCount();i++){
                    cursor.moveToPosition(i);
                    int ma = cursor.getInt(0);
                    String ten = cursor.getString(1);
                    int gia = cursor.getInt(2);
                    byte[] anh = cursor.getBlob(3);
                    list.add(new Thucpham(ma,ten,gia,anh));
                }
                adapterThucpham.notifyDataSetChanged();
                adapterThucpham = new AdapterThucpham(ListHSActivity.this,list);
                lstDSTP.setAdapter(adapterThucpham);
            }
        });
    }





    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        txtnoidung = (EditText)findViewById(R.id.edittextNoidung);
        btnTim = (ImageButton) findViewById(R.id.btnSearch);
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);


        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerlayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));


        btnthem = (Button)findViewById(R.id.buttonThem);
}}
