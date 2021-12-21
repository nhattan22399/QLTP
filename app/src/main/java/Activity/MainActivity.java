package Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.linhkien.R;

public class MainActivity extends AppCompatActivity {
    EditText txtUser, txtPass;
    String DATABASE_NAME = "QLTP.db";
    SQLiteDatabase database;
    Button btnDangky, btnDangnhap, btnThoat;
    String ten, mk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        Controlbtn();

    }

    private void Controlbtn() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_DeviceDefault_Dialog_Alert);
                builder.setTitle("Bạn có chắc muốn thoát khỏi app?");
                builder.setMessage("Hãy lựa chọn bên dưới để xác nhận");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });



        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUser.getText().toString().equals("admin") && txtPass.getText().toString().equals("12345")){
                        Toast.makeText(MainActivity.this, "Bạn đã đăng nhập thành công!",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Bạn đã đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }



        private void Anhxa () {
            txtUser = (EditText) findViewById(R.id.editTextName);
            txtPass = (EditText) findViewById(R.id.editTextpass);
            btnDangnhap = (Button) findViewById(R.id.btnDangNhap);

            btnThoat = (Button) findViewById(R.id.btnOut);
        }
    }