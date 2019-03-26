package com.example.bantay.bantay;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeployedRescueRequest extends Fragment {

    public TextView requestfirstname, requestcontactnumber, requestlocation, requestlandmarks,
            requestpax, requestvulnerability, requestspecification, newrequesttv;
    public FirebaseAuth firebaseAuth;
    public FirebaseDatabase firebaseDatabase;

    public DeployedRescueRequest() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deployed_rescue_request, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DeployedRescueRequest deployedRescueRequest = (DeployedRescueRequest) getChildFragmentManager().findFragmentById(R.id.deployedrescuerequest);
        loadEntries();
    }


    private void loadEntries(){

        requestfirstname = getView().findViewById(R.id.deployedrequestfirstname);
        requestcontactnumber = getView().findViewById(R.id.deployedrequestcontactnum);
        requestlocation = getView().findViewById(R.id.deployedrequestlocation);
        requestlandmarks = getView().findViewById(R.id.deployedrequestlandmarks);
        requestpax = getView().findViewById(R.id.deployedrequestpax);
        requestvulnerability = getView().findViewById(R.id.deployedrequestvulnerability);
        requestspecification = getView().findViewById(R.id.deployedrequestspecification);

        String path = "/Rescue Requests/Deployed Rescue Requests";
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);

        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    RequestEntries requestEntries = dataSnapshot.getValue(RequestEntries.class);
                    requestfirstname.setText(requestEntries.getRequestFirstName() + " " + requestEntries.getRequestLastName());
                    requestcontactnumber.setText(requestEntries.getRequestContactNumber());
                    requestlocation.setText(requestEntries.getRequestLocation());
                    requestlandmarks.setText(requestEntries.getRequestLandmarks());
                    requestpax.setText(requestEntries.getRequestPax());
                    requestspecification.setText(requestEntries.getRequestSpecific());
                }
                else{
                    setRequestFragment();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //Go to SetRequestFragment
    public void setRequestFragment(){
        SetRequestFragment setRequestFragment = new SetRequestFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_nav, setRequestFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}