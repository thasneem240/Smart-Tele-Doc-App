package com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.wirting_prescriptions.ListOfDrugsFirebase;
import com.example.capstoneprojectgroup4.wirting_prescriptions.select_the_drug.SelectTheDrug;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DrugsContainersAdapter extends RecyclerView.Adapter<DrugsContainersViewHolder>{
    int p;
    String selectedDrug;
    int i;
    public DrugsContainersAdapter(String selectedDrug, int p){
        this.selectedDrug = selectedDrug;
        this.p = p;
    }

    @NonNull
    @Override
    public DrugsContainersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_drugs_containers,parent,false);
        DrugsContainersViewHolder drugsContainersViewHolder = new DrugsContainersViewHolder(view);
        return drugsContainersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugsContainersViewHolder holder, int position) {
        Log.d("nnrp", "p = "+p);
        if(position == p && position != 0)
            holder.drug1.setText(selectedDrug);
        holder.addDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ListOfDrugsFirebase listOfDrugsFirebase = new ListOfDrugsFirebase();

                Single<ArrayList<String>> searchObservable = Single.fromCallable(listOfDrugsFirebase);
                searchObservable = searchObservable.subscribeOn(Schedulers.io());
                searchObservable = searchObservable.observeOn(AndroidSchedulers.mainThread());
                searchObservable.subscribe(new SingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull ArrayList<String> listOfDrugs) {
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        FragmentManager fm = activity.getSupportFragmentManager();

                        SelectTheDrug selectTheDrug = new SelectTheDrug(listOfDrugs, position);
                        fm.beginTransaction().replace(R.id.fragment_container, selectTheDrug).commit();
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
            }

        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
