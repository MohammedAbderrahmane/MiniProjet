package com.example.nobleevondeur.MiseAJourFragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nobleevondeur.NonActivityClasses.ItemProduit;
import com.example.nobleevondeur.NonActivityClasses.Magazin;
import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AjouterFragment extends Fragment {

    ImageButton photo;
    EditText prix,nom;
    Button ajouter;
    TextView statue_image;
    boolean imageSelected = false;
    Uri tmpImage;
    String imageUrl;
    private ActivityResultLauncher<String> getContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for getting image from phone storage
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    if (result != null){
                        statue_image.setText("Image selected");
                        imageSelected = true;
                        tmpImage = result;
                        if (nom.getText().toString().isEmpty()||
                                prix.getText().toString().isEmpty()||
                                !imageSelected){
                            ajouter.setEnabled(false);
                        }else{
                            ajouter.setEnabled(true);
                        }
                    }else {
                        statue_image.setText("Image not selected");
                        imageSelected = false;
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public AjouterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajouter,container,false);

        ajouter = view.findViewById(R.id.ajouter_ajouter_btn);
        prix = view.findViewById(R.id.ajouter_prix);
        nom = view.findViewById(R.id.ajouter_nom);
        photo = view.findViewById(R.id.ajouter_photo);
        statue_image = view.findViewById(R.id.ajouter_statue_image);

        ajouter.setEnabled(false);
        nom.addTextChangedListener(textWatcher);
        prix.addTextChangedListener(textWatcher);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : handele parmission denied
                try {
                    getContent.launch("image/*");
                }catch (Exception e){
                    Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ajouterProduitEtImage();
            }
        });
        return view;
    }

    private void ajouterProduitEtImage() {
        StorageReference newImgRef = FirebaseStorage.getInstance().getReference().child("images/" + tmpImage.getLastPathSegment());
        newImgRef.putFile(tmpImage)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        newImgRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageUrl = uri.toString();
                                        ajouterProduit(
                                                nom.getText().toString(),
                                                prix.getText().toString(),
                                                uri.toString());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                                        imageUrl = "null";
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ajouterProduit(String nom,String prix,String imageUrl) {
        Magazin.getInstance().getRef().collection("Produits")
                .add(new ItemProduit(nom,prix,imageUrl))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"produit ajoutee", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (nom.getText().toString().isEmpty()||
                    prix.getText().toString().isEmpty()||
                    !imageSelected){
                ajouter.setEnabled(false);
            }else{
                ajouter.setEnabled(true);
            }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}