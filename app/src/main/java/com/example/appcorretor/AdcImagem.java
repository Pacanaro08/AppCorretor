package com.example.appcorretor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import javax.xml.transform.URIResolver;

public class AdcImagem extends AppCompatActivity {

    private ImageSwitcher imageIs;
    private Button previousBtn;
    private Button nextBtn;
    private Button pickImagesBtn;
    private Button saveBtn;
    private ImageButton deleteBtn;
    private ArrayList<Uri> imagesUris;
    private static final int PICK_IMAGES_CODE = 0;
    private Context context;
    private final ImgData imgData = new ImgData(this);
    private int idDono;
    private Integer idImagem;
    private String imagem;
    private ArrayList<Integer> idImagens;
    private ArrayList<String> imagens;

    int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adc_imagem);

        imageIs = (ImageSwitcher)findViewById(R.id.imageIs);
        previousBtn = (Button) findViewById(R.id.previousBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        pickImagesBtn = (Button) findViewById(R.id.pickImagesBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        deleteBtn = (ImageButton) findViewById(R.id.deleteBtn);
        imagesUris = new ArrayList<>();

        nextBtn.setEnabled(false);
        previousBtn.setEnabled(false);

        ArrayList<Integer> idImagens = new ArrayList<Integer>();
        ArrayList<String> imagens = new ArrayList<String>();

        if(getIntent().getExtras() != null) {
            int codigo = getIntent().getExtras().getInt("adcImagem");
            imgData.carregaDados(codigo);
            idDono = imgData.getIdDono();
            idImagens = imgData.getIdImagens();
            imagens = imgData.getImagens();
//            if (imagens == null && idImagens == null) {
//                System.out.println("VEIO PRO IF MALANDRO");
//            } else {
////                for (int i = 0; i < imagens.size(); i++) {
////                    Uri teste = Uri.parse(imagens.get(i));
//                    File file = new File("/document/image:6945");
////                    System.out.println("MORRA" + file);
//
//                    if (file.exists()) {
//                        imageIs.setImageURI(Uri.fromFile(file));
//                    } else {
//                        System.out.println("MORRRAAAAAA --- " + "n exite o arquivo mlkt");
//                    }
//                }
//                Uri imagemUri = Uri.parse(imagem).normalizeScheme();
                imagesUris.add(Uri.parse(imagens.get(0)));
                for (int i = 0; i < imagens.size(); i++) {
//                    Uri teste = Uri.parse(imagens.get(i));
//                    System.out.println("MORRAAAAAAAA - " + teste);
                    Uri nibba = Uri.parse(imagens.get(i));
                    imageIs.setImageURI(nibba);
//                }
                }
                    System.out.println("CU VIRGEM == " + "idDono = " + idDono + " idImagem = " + idImagens + " imagem " + imagens);
//            }
                }


        imageIs.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                return imageView;
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0){
                    position--;
                    imageIs.setImageURI(imagesUris.get(position));
                }else{
                    Toast.makeText(AdcImagem.this, "Primeira Imagem!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < imagesUris.size() - 1){
                    position++;
                    imageIs.setImageURI(imagesUris.get(position));
                }
                else{
                    Toast.makeText(AdcImagem.this, "Última Imagem!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pickImagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesIntent();
            }
        });


        imageIs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pra brincar um pouco

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgData.excluir();
                finish();
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i <= imagesUris.size() - 1; i++) {
                    imgData.setIdDono(idDono);
                    imgData.setImagem(String.valueOf(imagesUris.get(i)));
                    imgData.salvar();
                    System.out.println("PENIS DE GORILA == " + "idDono = " + idDono + " URI = " + imagesUris.get(i));
                }
                finish();
            }
        });
    }

    private void pickImagesIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGES_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGES_CODE && resultCode == Activity.RESULT_OK){
            if(data.getClipData() != null){
                //pegou várias imagens
                int qtd = data.getClipData().getItemCount();
                for(int i = 0; i < qtd; i++ ){
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    imagesUris.add(imageUri);
                }
                //deixa 1 imagem selecionada como primeira imagem
                imageIs.setImageURI(imagesUris.get(0));
                position = 0;

            }else {
                //pegou só uma imagem
                Uri imageUri = data.getData();
                imagesUris.add(imageUri);
            }
                imageIs.setImageURI(imagesUris.get(0));
                position = 0;
        }
        if (imagesUris.size() > 1){
            nextBtn.setEnabled(true);
            previousBtn.setEnabled(true);
        }
    }
}