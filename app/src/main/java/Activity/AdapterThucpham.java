package Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.linhkien.R;

import java.util.ArrayList;

import MenuItem.Thucpham;


public class AdapterThucpham extends BaseAdapter {
    Context context;
    ArrayList<Thucpham> list;
    String DATABASE_NAME = "QLTP.db";
    SQLiteDatabase database;
    Button btnxoa,btnsua;
    ImageView imgAnh;
    TextView txtma,txtten,txtgia;



    public AdapterThucpham(Context context, ArrayList<Thucpham> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row_thucpham,null);

        TextView txtma = (TextView) row.findViewById(R.id.textViewMa);
        TextView txtten = (TextView) row.findViewById(R.id.textViewTen);
        TextView txtgia = (TextView) row.findViewById(R.id.textViewgia);
        ImageView imgAnh = (ImageView) row.findViewById(R.id.imageViewAnh);
        Button btnsua = (Button) row.findViewById(R.id.buttonSua);
        Button btnxoa = (Button) row.findViewById(R.id.buttonXoa);



        final Thucpham thucpham = list.get(i);
        txtma.setText("Mã: "+thucpham.ma+"");
        txtten.setText(thucpham.ten);
        txtgia.setText("Giá: "+thucpham.gia);
        Bitmap bitmap = BitmapFactory.decodeByteArray(thucpham.anh, 0, thucpham.anh.length);
        imgAnh.setImageBitmap(bitmap);

        btnsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, UpdateHaisan.class);
               intent.putExtra("id",thucpham.ma);
               context.startActivities(new Intent[]{intent});
            }
        });
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xac nhan");
                builder.setMessage("Ban co muon xoa hay khong");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(thucpham.ma);
                    }

                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.create().show();
            }
        });
        return row;
    }
    private void delete(int mans){
        database = Database.initDatabase((Activity) context, DATABASE_NAME);
        database.delete("Ca", "Ma = ?",new String[] {txtma+""});

        Cursor cursor = database.rawQuery("Select * from  Ca",null);
        list.clear();
        while(cursor.moveToNext()){
            int Ma = cursor.getInt(0);
            String Ten = cursor.getString(1);
            int Gia = cursor.getInt(2);
            byte[] Anh = cursor.getBlob(3);
            list.add( new Thucpham(Ma,Ten,Gia,Anh));

        }
        notifyDataSetChanged();
    }
}
