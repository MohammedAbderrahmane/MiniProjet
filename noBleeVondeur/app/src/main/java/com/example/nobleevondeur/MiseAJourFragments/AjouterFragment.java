package com.example.nobleevondeur.MiseAJourFragments;

import android.content.pm.PackageManager;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.nobleevondeur.NonActivityClasses.ItemProduit;
import com.example.nobleevondeur.NonActivityClasses.DataBase;
import com.example.nobleevondeur.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AjouterFragment extends Fragment {

    ImageButton photo;
    EditText prix,nom,categorie;
    AppCompatButton ajouter;
    TextView statue_image;
    boolean imageSelected = false;
    Uri tmpImage;
    String imageUrl;
    private ActivityResultLauncher<String> getContent;
    private ProgressBar prograsBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // for getting image from phone storage
        setUpGetContent();

    }

    private void setUpGetContent() {
        getContent = registerForActivityResult(new ActivityResultContracts.GetContent(),new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                try {
                    if (result != null){
                        statue_image.setText("Image selected");
                        imageSelected = true;
                        tmpImage = result;
                        if (
                                nom.getText().toString().isEmpty()||
                                prix.getText().toString().isEmpty()||
                                categorie.getText().toString().isEmpty()||
                                !imageSelected)
                            ajouter.setEnabled(false);
                        else
                            ajouter.setEnabled(true);

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajouter,container,false);

        ajouter = view.findViewById(R.id.ajouter_ajouter_btn);
        prix = view.findViewById(R.id.ajouter_prix);
        nom = view.findViewById(R.id.ajouter_nom);
        categorie = view.findViewById(R.id.ajouter_categorie);
        photo = view.findViewById(R.id.produit_modifier_btn);
        statue_image = view.findViewById(R.id.ajouter_statue_image);
        prograsBar = view.findViewById(R.id.ajouter_progress_bar);

        ajouter.setEnabled(false);
        nom.addTextChangedListener(textWatcher);
        prix.addTextChangedListener(textWatcher);


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                else
                    getContent.launch("image/*");
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
        prograsBar.setVisibility(View.VISIBLE);
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
                                                categorie.getText().toString(),
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

    private void ajouterProduit(String nom,String prix,String categorie,String imageUrl) {
        DataBase.getInstance(getContext()).getMagazinRef()
                .collection("Produits")
                .add(new ItemProduit(nom,prix,categorie,imageUrl))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        prograsBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(), "Le produit a ete ajouté", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        prograsBar.setVisibility(View.INVISIBLE);
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
            if (
                    nom.getText().toString().isEmpty()||
                    prix.getText().toString().isEmpty()||
                    categorie.getText().toString().isEmpty()||
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