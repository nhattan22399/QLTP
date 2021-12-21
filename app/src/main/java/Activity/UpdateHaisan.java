package Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.linhkien.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateHaisan extends AppCompatActivity {
    EditText txtTen,  txtgia;
    Button btnChonhinh, btnChuphinh, btnLuu,btnHuy;
    ImageView imgAnhsua;
    String DATABASE_NAME = "QLTP.db";
    SQLiteDatabase database;
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;
    int ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_haisan);
        addControl();
        loadData();
        addEvent();
    }

    private void addEvent() {
        btnChuphinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
        btnChonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhoto();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void update() {
        String ten =  txtTen.getText().toString();
        String gia =  txtgia.getText().toString();
        byte[] anhsua = getByteArrayFromImageView(imgAnhsua);

        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten",ten);
        contentValues.put("Gia",gia);
        contentValues.put("Anh",anhsua);
        database = Database.initDatabase(UpdateHaisan.this, DATABASE_NAME);
        database.update("Ca", contentValues, "Ma = ?",new String[] {ma+""});
        Intent intent = new Intent(UpdateHaisan.this, ListHSActivity.class);
        startActivity(intent);

    }
    public byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void loadData() {
        Intent intent = getIntent();
        ma = intent.getIntExtra("id",-1);

        if(ma != -1){
            database = Database.initDatabase(UpdateHaisan.this,DATABASE_NAME);

            Cursor cursor = database.rawQuery("select * from Nhansu where MaNv=?",new String[] {ma+""});
            cursor.moveToFirst();
            String ten = cursor.getString(1);
            String gia = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            if(anh != null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0,anh.length);
                imgAnhsua.setImageBitmap(bitmap);

            }
            txtTen.setText(ten);
            txtgia.setText(gia);
        }
    }

    private void addControl() {
        txtTen = (EditText)findViewById(R.id.editTextTenUpdate);
        txtgia = (EditText)findViewById(R.id.editTextgiaUpdate);
        btnChonhinh = (Button)findViewById(R.id.buttonChonHinh);
        btnChuphinh = (Button)findViewById(R.id.buttonChupHinh);
        btnLuu = (Button)findViewById(R.id.buttonLuu);
        btnHuy = (Button)findViewById(R.id.buttonHuy);
        imgAnhsua = (ImageView)findViewById(R.id.imageAnhSua);
    }
    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnhsua.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnhsua.setImageBitmap(bitmap);}
         }
    }
}
