package com.example.quickyes_project1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link displayallreport#newInstance} factory method to
 * create an instance of this fragment.
 */
public class displayallreport extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public displayallreport() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment displayallreport.
     */
    // TODO: Rename and change types and number of parameters
    public static displayallreport newInstance(String param1, String param2) {
        displayallreport fragment = new displayallreport();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    RecyclerView rv;
    displayallreportsadapter myadpter;
    SearchView search;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_displayallreport, container, false);
        rv=view.findViewById(R.id.displayallReportrecycleview);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        search=view.findViewById(R.id.searchplzallreport);
        FirestoreRecyclerOptions<allreportsretiveclass> options = new FirestoreRecyclerOptions.Builder<allreportsretiveclass>()
                .setQuery(FirebaseFirestore.getInstance().collection("Report"), allreportsretiveclass.class)
                .build();
        myadpter=new displayallreportsadapter(options);
        rv.setAdapter(myadpter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startsearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                startsearch(newText);
                return false;
            }
        });


        return view;
    }

    private void startsearch(String newText) {
        FirestoreRecyclerOptions<allreportsretiveclass> options = new FirestoreRecyclerOptions.Builder<allreportsretiveclass>()
                .setQuery(FirebaseFirestore.getInstance().collection("Report").orderBy("Report_Name").startAt(newText).endAt(newText+"\uf8ff"), allreportsretiveclass.class)
                .build();
        myadpter=new displayallreportsadapter(options);
        myadpter.startListening();
        rv.setAdapter(myadpter);

    }
    @Override
    public void onStart() {
        super.onStart();
        myadpter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        myadpter.stopListening();
    }
}