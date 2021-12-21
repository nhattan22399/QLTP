package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.linhkien.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView textView;
    Toolbar toolbar;

    ImageButton btnhaisan, btnthit, btnraucu, btnkhac;
    DrawerLayout drawerlayout;

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lg);
        AnhXa();
        ActionBar();
        ActionBtn();


    }

    private void ActionBtn() {
        btnhaisan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ListHSActivity.class);
                startActivity(intent);
            }
        });
//        btnthit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,ChuotActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnraucu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,ManhinhActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnkhac.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(LoginActivity.this,BanphimActivity.class);
//
//                startActivity(intent);
//            }
//        });
    }




    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void AnhXa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);

        btnhaisan = (ImageButton)findViewById(R.id.imghaisan);
        btnthit = (ImageButton)findViewById(R.id.imgthit);
        btnraucu = (ImageButton)findViewById(R.id.imgrau);
        btnkhac = (ImageButton)findViewById(R.id.imgKhac);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawerlayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimary));




    }

}