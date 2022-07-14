package com.example.duan_1.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan_1.ApdapterModel.YeuThichAdapter;
import com.example.duan_1.ChiTietDonHangActivity;
import com.example.duan_1.Model.SanPham;
import com.example.duan_1.Model.YeuThich;
import com.example.duan_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link YeuThichFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class YeuThichFragment extends Fragment {
    RecyclerView rcl_yeuthich;
    YeuThichAdapter yeuThichAdapter;
    ArrayList<YeuThich> mListYeuThich;
    public YeuThichFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcl_yeuthich = view.findViewById(R.id.rcl_yeuthich);
        mListYeuThich = new ArrayList<>();
        yeuThichAdapter = new YeuThichAdapter(getContext(), mListYeuThich, new YeuThichAdapter.ClickListener() {
            @Override
            public void onDeleteItemYeuThich(YeuThich yeuThich) {
                //Xóa sản phẩm yêu thích
                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.app_name))
                        .setMessage("Bạn có muốn xóa!")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("List_YeuThich");
                                myRef.child(String.valueOf(yeuThich.getMaYT())).removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                        Toast.makeText(getContext(), "Đã xóa "+yeuThich.getTenYT(), Toast.LENGTH_SHORT).show();
                                        yeuThichAdapter.notifyDataSetChanged();
                                    }
                                });
//
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rcl_yeuthich.setLayoutManager(manager);
        rcl_yeuthich.setAdapter(yeuThichAdapter);
        getListYeuThich();
    }
    private void getListYeuThich(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_YeuThich");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                YeuThich yeuThich = snapshot.getValue(YeuThich.class);
                if (yeuThich!=null){
                    mListYeuThich.add(yeuThich);
                    yeuThichAdapter.notifyDataSetChanged();
                }
            }
            @Override

            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
            @Override
            //phương thức onChildRemoved(cập nhật lại list sau khi xóa sản phẩm yêu thích đã tồn tại)
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                YeuThich yeuThich = snapshot.getValue(YeuThich.class);
                if (yeuThich==null || mListYeuThich==null || mListYeuThich.isEmpty() ){
                    return;
                }
                for (int i = 0 ; i<mListYeuThich.size() ; i++){
                    if (yeuThich.getTenYT()==mListYeuThich.get(i).getTenYT()){
                        mListYeuThich.remove(mListYeuThich.get(i));
                        break;
                    }
                }
                yeuThichAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static YeuThichFragment newInstance(String param1, String param2) {
        YeuThichFragment fragment = new YeuThichFragment();
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
        return inflater.inflate(R.layout.fragment_yeu_thich, container, false);
    }
}