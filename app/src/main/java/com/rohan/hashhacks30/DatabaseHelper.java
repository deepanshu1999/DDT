package com.rohan.hashhacks30;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    public Map<String,String> getuserinfo(String email){
        DocumentReference docRef = db.collection("Users").document(email);
        final Map<String,String> userdata=new HashMap<>();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        userdata.put("NAME",document.get("NAME").toString());
                        userdata.put("INTEREST",document.get("INTEREST").toString());
                        userdata.put("EMI",document.get("EMI").toString());
                        userdata.put("TOTALMONEY",document.get("EMI").toString());

                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());

                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
        return userdata;
    }

    public void putuserinfo(int Emi, int Totalpay,String name,int interest,String email)
    {


        Map<String, Object> user = new HashMap<>();
        user.put("NAME", name);
        user.put("INTEREST", interest);
        user.put("EMI",Emi );
        user.put("TOTALMONEY", Totalpay);

        db.collection("users")
                .add(email)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }
}
