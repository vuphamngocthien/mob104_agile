package com.example.duan_1.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_1.ApdapterModel.LoaiSanPhamAdapter;
import com.example.duan_1.ApdapterModel.SanPhamAdapter;
import com.example.duan_1.ChiTietSanPhamActivity;
import com.example.duan_1.Model.GioHang;
import com.example.duan_1.Model.LoaiSanPham;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemSanPhamFragment extends Fragment {
    FloatingActionButton btnThemSP;
    RecyclerView rcl_SP;
    ImageView im_LoadIM;
    StorageReference storageRef;
    SanPham sanPham;
    ArrayList<SanPham> mListPS;
    SanPhamAdapter mSanPhamAdapter;
    private String link;
    Uri contentUri;
    View view;
    LoaiSanPhamAdapter mLoaiSPAdapter;
    EditText ed_tenSP;
    EditText ed_giaSP;
    EditText ed_mota;
    public ThemSanPhamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnThemSP = view.findViewById(R.id.btnThemSP);
        rcl_SP = view.findViewById(R.id.rcl_SanPham);

        GridLayoutManager manager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        rcl_SP.setLayoutManager(manager);
        mListPS = new ArrayList<>();
        mSanPhamAdapter = new SanPhamAdapter(getContext(), mListPS, new SanPhamAdapter.ClickListener() {
            //Cập nhật sản phẩm
            @Override
            public void onUpdateItem(SanPham sanPham) {
                OpenDialogUpdate(sanPham);
            }
            //Xóa sản phẩm
            @Override
            public void onDeleteItem(SanPham sanPham) {
                OpenDialogDelete(sanPham);
            }
            //Truyền dữ liệu của một item sang màn hình chi tiết sản phẩm
            @Override
            public void onItemClick(SanPham sanPham) {
                Intent intent = new Intent(getContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham",sanPham);
                startActivity(intent);
            }
        });
        rcl_SP.setAdapter(mSanPhamAdapter);
        getListSanPham();
        storageRef = FirebaseStorage.getInstance().getReference();

        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 OpenDialog();
            }
        });
    }
    //Hàm thêm sản phẩm mới
    private void addSanPham(SanPham sanPham) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_SanPham");

        String path = String.valueOf(sanPham.getMaSP());
        myRef.child(path).setValue(sanPham, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "Thêm thành công" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    //Mở hộp thoại Thêm sản phẩm
    private void OpenDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_dialog_them_sanpham,null);
        ed_tenSP = view.findViewById(R.id.ed_tenSP);
        ed_giaSP = view.findViewById(R.id.ed_giaSP);
        ed_mota = view.findViewById(R.id.ed_mota);
        Spinner spin_Loai = view.findViewById(R.id.spinner_Loai);
        mLoaiSPAdapter = new LoaiSanPhamAdapter(getActivity(),LoaiSanPhamFragment.mListLoaiSP);
        spin_Loai.setAdapter(mLoaiSPAdapter);
        im_LoadIM = view.findViewById(R.id.im_loadSP);
        //Lấy ảnh từ thư viện
        im_LoadIM.setOnClickListener(view1 -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Intent lib = new Intent(Intent.ACTION_GET_CONTENT);
            lib.setType("image/*");
            Intent chua = Intent.createChooser(camera,"Chọn");
            chua.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{lib});
            startActivityForResult(chua,9999);
        });
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        builder.setView(view);
        AlertDialog alertDialog = builder.show();

        btnThem.setOnClickListener(view1 -> {
            try {
                if (validate() < 0){
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = getActivity().getIntent();
                    int MaSP = (int) System.currentTimeMillis();
                    String maTV = intent.getStringExtra("username");
                    String tenSP = ed_tenSP.getText().toString().trim();
                    int gia = Integer.parseInt(ed_giaSP.getText().toString().trim());
                    String loai = ((LoaiSanPham) spin_Loai.getSelectedItem()).getTenLoai();
                    String moTa = ed_mota.getText().toString().trim();
                    sanPham = new SanPham(MaSP,maTV,tenSP,gia,loai,link,moTa);
                    uploadImageToFirebase(link, contentUri);
                    alertDialog.dismiss();
                }
            }catch (Exception e){
                Toast.makeText(getActivity(), "Chưa thêm được sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
        btnHuy.setOnClickListener(view1 -> {alertDialog.dismiss();});
    }
    //Mở hộp thoại sửa sản phẩm
    private void OpenDialogUpdate(SanPham sanPham) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_them_sanpham,null);
        ed_tenSP = view.findViewById(R.id.ed_tenSP);
        ed_giaSP = view.findViewById(R.id.ed_giaSP);
        ed_mota = view.findViewById(R.id.ed_mota);
        Spinner spin_Loai = view.findViewById(R.id.spinner_Loai);
        mLoaiSPAdapter = new LoaiSanPhamAdapter(getActivity(),LoaiSanPhamFragment.mListLoaiSP);
        spin_Loai.setAdapter(mLoaiSPAdapter);
        im_LoadIM = view.findViewById(R.id.im_loadSP);
        im_LoadIM.setOnClickListener(view1 -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Intent lib = new Intent(Intent.ACTION_GET_CONTENT);
            lib.setType("image/*");

            Intent chua = Intent.createChooser(camera,"Chọn");
            chua.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{lib});
            startActivityForResult(chua,9999);
        });
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnHuy = view.findViewById(R.id.btnHuy);
        builder.setView(view);
        AlertDialog alertDialog = builder.show();
        btnThem.setText("Lưu");
        btnThem.setOnClickListener(view1 -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("List_SanPham");
            try {
                if (validate() < 0){
                    Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    String tenSP = ed_tenSP.getText().toString().trim();
                    int gia = Integer.parseInt(ed_giaSP.getText().toString().trim());
                    String moTa = ed_mota.getText().toString().trim();
                    String loai = ((LoaiSanPham) spin_Loai.getSelectedItem()).getTenLoai();
                    sanPham.setTenSP(tenSP);
                    sanPham.setGia(gia);
                    sanPham.setImage(link);
                    sanPham.setMoTa(moTa);
                    sanPham.setLoai(loai);
                    uploadImageToFirebase(link, contentUri);
                    myRef.child(String.valueOf(sanPham.getMaSP())).updateChildren(sanPham.toMap());
                    alertDialog.dismiss();
                }
            }catch (Exception e){
                Toast.makeText(getActivity(), "Cập nhật sản phẩm  thất bại", Toast.LENGTH_SHORT).show();
            }

        });
        btnHuy.setOnClickListener(view1 -> {alertDialog.dismiss();});
    }
    public int validate(){
        int check = 1;
        if (ed_tenSP.getText().length() == 0 || ed_giaSP.getText().length()==0 || ed_mota.getText().length()==0){
            Toast.makeText(getActivity(), "Vui lòng không bỏ trống mục nào", Toast.LENGTH_SHORT).show();
            check = -1;
        }else if (ed_tenSP.getText().equals("") || ed_giaSP.getText().equals("") || ed_mota.getText().equals("")){
            Toast.makeText(getActivity(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
    //Mở hộp thoại xóa sản phẩm
    private void OpenDialogDelete(SanPham sanPham) {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.app_name))
                .setMessage("Bạn có muốn xóa!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("List_SanPham");
                            myRef.child(String.valueOf(sanPham.getMaSP())).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(getContext(), "Đã xóa "+sanPham.getTenSP(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(getContext(), "Sản phẩm chưa được xóa", Toast.LENGTH_SHORT).show();
                        }

                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }
    //Lấy dữ liệu từ firebase đổ lên Recyclerview
    private void getListSanPham(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_SanPham");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            //phương thức onChildAdded(cập nhật lại list sau khi thêm sản phẩm mới )
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SanPham sanPham = snapshot.getValue(SanPham.class);
                if (sanPham!=null){
                    mListPS.add(sanPham);
                    mSanPhamAdapter.notifyDataSetChanged();
                }
            }
            @Override
            //phương thức onChildChanged(cập nhật lại list sau khi sửa sản phẩm đã tồn tại)
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                SanPham sanPham = snapshot.getValue(SanPham.class);
                if (sanPham==null || mListPS==null || mListPS.isEmpty() ){
                    return;
                }
                for (int i = 0 ; i<mListPS.size() ; i++){
                    if (sanPham.getMaSP()==mListPS.get(i).getMaSP()){
                        mListPS.set(i,sanPham);
                    }
                }
                mSanPhamAdapter.notifyDataSetChanged();
            }
            @Override
            //phương thức onChildRemoved(cập nhật lại list sau khi xóa sản phẩm đã tồn tại)
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                SanPham sanPham = snapshot.getValue(SanPham.class);
                if (sanPham==null || mListPS==null || mListPS.isEmpty() ){
                    return;
                }
                for (int i = 0 ; i<mListPS.size() ; i++){
                    if (sanPham.getMaSP()==mListPS.get(i).getMaSP()){
                        mListPS.remove(mListPS.get(i));
                        break;
                    }
                }
                mSanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //upload ảnh lên firebase dưới dạng đường dẫn uri
    private void uploadImageToFirebase(String url, Uri contentUri){
        StorageReference mountainsRef = storageRef.child("IM_SANPHAM/"+ link);
        try {
            mountainsRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            sanPham.setImage(uri.toString()+"");
                            addSanPham(sanPham);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("==> Exception", e.getMessage());
                }
            });
        }catch (Exception e){
            sanPham.setImage("");
            addSanPham(sanPham);
            Toast.makeText(getContext(), "Chưa có hình, lỗi "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Calendar calendar = Calendar.getInstance();
        if (requestCode == 9999 && resultCode == RESULT_OK) {
            if (data.getExtras() != null) {
                Bundle bundle = data.getExtras();
                Bitmap bitmap = (Bitmap) bundle.get("data");
                im_LoadIM.setImageBitmap(bitmap);
                link =  calendar.getTimeInMillis()+".png";
            } else {
                contentUri = data.getData();
                im_LoadIM.setImageURI(contentUri);
                link =  calendar.getTimeInMillis()+".png";
            }
        }
    }

    public static ThemSanPhamFragment newInstance(String param1, String param2) {
        ThemSanPhamFragment fragment = new ThemSanPhamFragment();
        return fragment;
    }

    public static Fragment newInstance() {
        return ThemSanPhamFragment.newInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_them_san_pham, container, false);
    }


}