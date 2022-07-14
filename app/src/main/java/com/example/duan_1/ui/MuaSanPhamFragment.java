package com.example.duan_1.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan_1.ApdapterModel.LoaiSanPhamAdapter;
import com.example.duan_1.ApdapterModel.MuaSanPhamAdapter;
import com.example.duan_1.ApdapterModel.SanPhamAdapter;
import com.example.duan_1.ChiTietSanPhamActivity;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MuaSanPhamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MuaSanPhamFragment extends Fragment {

    FloatingActionButton btnThemSP;
    RecyclerView rcl_SP;
    ImageView im_LoadIM;
    StorageReference storageRef;
    SanPham sanPham;
    ArrayList<SanPham> mListPS;
    MuaSanPhamAdapter mSanPhamAdapter;
    private String link;
    Uri contentUri;
    View view;
    LoaiSanPhamAdapter mLoaiSPAdapter;
    ArrayList<LoaiSanPham> mListLoaiSP;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btnThemSP = view.findViewById(R.id.btnThemSP);
        rcl_SP = view.findViewById(R.id.rcl_SanPham);

        GridLayoutManager manager = new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        rcl_SP.setLayoutManager(manager);
        mListPS = new ArrayList<>();
        mSanPhamAdapter = new MuaSanPhamAdapter(getContext(), mListPS, new MuaSanPhamAdapter.ClickListener() {
            @Override
            public void onUpdateItem(SanPham sanPham) {

            }

            @Override
            public void onDeleteItem(SanPham sanPham) {

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

    }

//    private void addSanPham(SanPham sanPham) {
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("List_SanPham");
//
//        String path = String.valueOf(sanPham.getMaSP());
//        myRef.child(path).setValue(sanPham, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                Toast.makeText(getContext(), "Thêm thành công" , Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    //Mở hộp thoại Thêm sản phẩm
//    private void OpenDialog() {
//        getListLoaiSP();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        view = inflater.inflate(R.layout.layout_dialog_them_sanpham,null);
//        EditText ed_tenSP = view.findViewById(R.id.ed_tenSP);
//        EditText ed_giaSP = view.findViewById(R.id.ed_giaSP);
//        EditText ed_mota = view.findViewById(R.id.ed_mota);
//        Spinner spin_Loai = view.findViewById(R.id.spinner_Loai);
//
//        mLoaiSPAdapter = new LoaiSanPhamAdapter(getActivity(),LoaiSanPhamFragment.mListLoaiSP);
//        spin_Loai.setAdapter(mLoaiSPAdapter);
//        im_LoadIM = view.findViewById(R.id.im_loadSP);
//        //Lấy ảnh từ thư viện
//        im_LoadIM.setOnClickListener(view1 -> {
//            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            Intent lib = new Intent(Intent.ACTION_GET_CONTENT);
//            lib.setType("image/*");
//            Intent chua = Intent.createChooser(camera,"Chọn");
//            chua.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{lib});
//            startActivityForResult(chua,9999);
//        });
//        Button btnThem = view.findViewById(R.id.btnThem);
//        Button btnHuy = view.findViewById(R.id.btnHuy);
//        builder.setView(view);
//        AlertDialog alertDialog = builder.show();
//
//        btnThem.setOnClickListener(view1 -> {
//            Intent intent = getActivity().getIntent();
//            int MaSP = (int) System.currentTimeMillis();
//            String maTV = intent.getStringExtra("username");
//            String tenSP = ed_tenSP.getText().toString().trim();
//            int gia = Integer.parseInt(ed_giaSP.getText().toString().trim());
//            String loai = ((LoaiSanPham) spin_Loai.getSelectedItem()).getTenLoai();
//            Log.d("====>",loai);
//            String moTa = ed_mota.getText().toString().trim();
//            sanPham = new SanPham(MaSP,maTV,tenSP,gia,loai,link,moTa);
//            uploadImageToFirebase(link, contentUri);
//            alertDialog.dismiss();
//        });
//        btnHuy.setOnClickListener(view1 -> {alertDialog.dismiss();});
//    }
    //Mở hộp thoại sửa sản phẩm
//    private void OpenDialogUpdate(SanPham sanPham) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        View view = inflater.inflate(R.layout.layout_dialog_them_sanpham,null);
//        EditText ed_tenSP = view.findViewById(R.id.ed_tenSP);
//        EditText ed_giaSP = view.findViewById(R.id.ed_giaSP);
//        EditText ed_mota = view.findViewById(R.id.ed_mota);
//        Spinner spin_Loai = view.findViewById(R.id.spinner_Loai);
//        im_LoadIM = view.findViewById(R.id.im_loadSP);
//        im_LoadIM.setOnClickListener(view1 -> {
//            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//            Intent lib = new Intent(Intent.ACTION_GET_CONTENT);
//            lib.setType("image/*");
//
//            Intent chua = Intent.createChooser(camera,"Chọn");
//            chua.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{lib});
//            startActivityForResult(chua,9999);
//        });
//        Button btnThem = view.findViewById(R.id.btnThem);
//        Button btnHuy = view.findViewById(R.id.btnHuy);
//        builder.setView(view);
//        AlertDialog alertDialog = builder.show();
//        btnThem.setText("Lưu");
//        btnThem.setOnClickListener(view1 -> {
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference myRef = database.getReference("List_SanPham");
//
//            String tenSP = ed_tenSP.getText().toString().trim();
//            int gia = Integer.parseInt(ed_giaSP.getText().toString().trim());
//            String moTa = ed_mota.getText().toString().trim();
//            sanPham.setTenSP(tenSP);
//            sanPham.setGia(gia);
//            sanPham.setImage(link);
//            sanPham.setMoTa(moTa);
//            uploadImageToFirebase(link, contentUri);
//            myRef.child(String.valueOf(sanPham.getMaSP())).updateChildren(sanPham.toMap());
//            alertDialog.dismiss();
//        });
//        btnHuy.setOnClickListener(view1 -> {alertDialog.dismiss();});
//    }
    //Mở hộp thoại xóa sản phẩm
//    private void OpenDialogDelete(SanPham sanPham) {
//        new AlertDialog.Builder(getActivity())
//                .setTitle(getString(R.string.app_name))
//                .setMessage("Bạn có muốn xóa!")
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        DatabaseReference myRef = database.getReference("List_SanPham");
//                        myRef.child(String.valueOf(sanPham.getMaSP())).removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(getContext(), "Đã xóa "+sanPham.getTenSP(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                })
//                .setNegativeButton("Cancel",null)
//                .show();
//    }
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
//    private void uploadImageToFirebase(String url, Uri contentUri){
//        StorageReference mountainsRef = storageRef.child("IM_SANPHAM/"+ link);
//        try {
//            mountainsRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            sanPham.setImage(uri.toString()+"");
//                            addSanPham(sanPham);
//                        }
//                    });
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d("==> Exception", e.getMessage());
//                }
//            });
//        }catch (Exception e){
//            sanPham.setImage("");
//            addSanPham(sanPham);
//            Toast.makeText(getContext(), "Chưa có hình, lỗi "+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

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

    public MuaSanPhamFragment() {
        // Required empty public constructor
    }


    public static MuaSanPhamFragment newInstance(String param1, String param2) {
        MuaSanPhamFragment fragment = new MuaSanPhamFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mua_san_pham, container, false);
    }
}