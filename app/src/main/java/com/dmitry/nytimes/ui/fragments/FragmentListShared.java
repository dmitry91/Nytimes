package com.dmitry.nytimes.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dmitry.nytimes.R;
import com.dmitry.nytimes.models.ResultTitle;
import com.dmitry.nytimes.models.Title;
import com.dmitry.nytimes.retrofit.RetrofitService;
import com.dmitry.nytimes.ui.adapters.DataAdapter;
import net.vrgsoft.layoutmanager.RollingLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;

public class FragmentListShared extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Title> data;
    private DataAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_shared, container, false);
    }

    @Override
    public void onStart() {
        initViews();
        super.onStart();
    }

    private void initViews() {
        recyclerView = (RecyclerView)getActivity().findViewById(R.id.card_recycler_view_shared);
        recyclerView.setHasFixedSize(true);
        RollingLayoutManager rollingLayoutManager = new RollingLayoutManager(getActivity());
        recyclerView.setLayoutManager(rollingLayoutManager);
        request();
    }

    private void request(){
        Call<ResultTitle> mCall = RetrofitService.getInstance().getMostShared();
        mCall.enqueue(new Callback<ResultTitle>() {
            @Override
            public void onResponse(Call<ResultTitle> call, @NonNull Response<ResultTitle> response) {
                response.body();
                data = (ArrayList<Title>) response.body().getResult();
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResultTitle> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.conn_error, Toast.LENGTH_LONG).show();
            }
        });
    }
}