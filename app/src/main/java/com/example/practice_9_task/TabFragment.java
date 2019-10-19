package com.example.practice_9_task;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.practice_9_task.Model.Model;
import com.example.practice_9_task.Repository.Repositroy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {
    public static final int REQUEST_CODE_TABFRAGMETN = 2;
    public static final String TAG_FOR_SHOW_INSERTUPADATE = "insertupadate";
    public static final String KEY_NAMETAB = "nametab";
    public static final String KEYNAME = "key";
    public static final String KEYPASSWORD = "KEYPASSWORD";
    private RecyclerView mRecyclerView;

    private FloatingActionButton mFloatingActionButton;
    View mView1;
    RecycleAdapter recycleAdapter;

    public static TabFragment newInstance(String tabname, String name, String passwrod) {
        Bundle args = new Bundle();
        args.putString(KEY_NAMETAB, tabname);
        args.putString(KEYNAME, name);
        args.putString(KEYPASSWORD, passwrod);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView1 = inflater.inflate(R.layout.fragment_tab, container, false);
        init();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleAdapter = new RecycleAdapter(Repositroy.newInstance(getContext()).getArrayList(getArguments().getString(KEYNAME),
                getArguments().getString((KEYPASSWORD)), getArguments().getString(KEY_NAMETAB)), getContext()
                , (getArguments().getString(KEY_NAMETAB)) + getArguments().getString(KEYNAME));
        mRecyclerView.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InsertUpdateFragment insertUpdateFragment = InsertUpdateFragment.newInstance(getArguments().getString(KEY_NAMETAB), getArguments()
                        .getString(KEYNAME), getArguments().getString(KEYPASSWORD));
                insertUpdateFragment.setTargetFragment(TabFragment.this, REQUEST_CODE_TABFRAGMETN);
                insertUpdateFragment.show(getFragmentManager(), TAG_FOR_SHOW_INSERTUPADATE);
            }
        });
        return mView1;
    }

    public void init() {
        mRecyclerView = mView1.findViewById(R.id.recyclerview);
        mFloatingActionButton = mView1.findViewById(R.id.floatingActionButton_fragment_add);


    }

    public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecycleVeiwHolder> implements Filterable {
        public static final String EDIT_FRAGMENT = "Edit Fragment";
        public static final String TAG = "TAG";
        private List<Model> filterlist = new ArrayList<>();
        public void setArrayList(ArrayList<Model> arrayList) {
            mArrayList = arrayList;
            filterlist=arrayList;
        }

        private ArrayList<Model> mArrayList = new ArrayList<Model>();
        Context mContext;
        View mView;
        Model model = new Model();
        String mKey;


        public RecycleAdapter(ArrayList<Model> arrayList, Context context, String key) {
            mArrayList = arrayList;
            mContext = context;
            mKey = key;
           filterlist=new ArrayList<>(arrayList);
        }


        @NonNull
        @Override
        public RecycleVeiwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            mView = layoutInflater.inflate(R.layout.fragment_veiw, null, false);
            return new RecycleVeiwHolder(mView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecycleVeiwHolder holder, int position) {
            model = mArrayList.get(position);
            holder.mDate.setText(model.getDate() + "   " + model.getTime());
            holder.mTask.setText(model.getDescription());
            if (model.getDescription() != null)
                holder.mFloatingActionButton.setText(model.getDescription().substring(0, 1));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager manager = ((AppCompatActivity) mContext).getSupportFragmentManager();
                    EditFragment editFragment = EditFragment.newInstance(model,
                            getArguments().getString(KEYNAME), getArguments().getString(KEYPASSWORD));
                    editFragment.setTargetFragment(TabFragment.this, 1);
                    editFragment.show(manager, EDIT_FRAGMENT);

                }
            });

        }

        public RecycleAdapter ret() {
            return recycleAdapter;
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        @Override
        public Filter getFilter() {
            return filterlist1;
        }

        private Filter filterlist1 = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Model> filteredlist = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredlist.addAll(filterlist);

                } else {
                    String filterpatern = constraint.toString().toLowerCase().trim();
                    for (Model item : filterlist)
                        if (item.getTitle().toLowerCase().contains(filterpatern))
                            filteredlist.add(item);
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredlist;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mArrayList.clear();
                mArrayList.addAll((List) results.values);
                notifyDataSetChanged();

            }
        };

        class RecycleVeiwHolder extends RecyclerView.ViewHolder {
            private TextView mTask;
            private TextView mFloatingActionButton;
            private TextView mDate;

            public RecycleVeiwHolder(@NonNull View itemView) {
                super(itemView);
                mDate = itemView.findViewById(R.id.text_view_date);
                mTask = itemView.findViewById(R.id.text_view_task);
                mFloatingActionButton = itemView.findViewById(R.id.floatingActionButton_layoutview);


            }

            public TextView getTask() {
                return mTask;
            }

            public void setTask(TextView task) {
                mTask = task;
            }

            public TextView getDate() {
                return mDate;
            }

            public void setDate(TextView date) {
                mDate = date;
            }


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recycleAdapter.setArrayList(Repositroy.newInstance(getActivity()).getArrayList(getArguments()
                .getString(KEYNAME), getArguments().getString(KEYPASSWORD), getArguments().getString(KEY_NAMETAB))
        );
        recycleAdapter.notifyDataSetChanged();


    }


}

