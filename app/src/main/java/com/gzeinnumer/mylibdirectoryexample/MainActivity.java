package com.gzeinnumer.mylibdirectoryexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gzeinnumer.gzndirectory.helper.FunctionGlobalDir;
import com.gzeinnumer.gzndirectory.helper.FunctionGlobalFile;
import com.gzeinnumer.gzndirectory.helper.FunctionGlobalZip;
import com.gzeinnumer.gzndirectory.helper.imagePicker.FileCompressor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gunakan function ini cukup satu kali saja pada awal activity
        String externalFolderName = getApplication().getString(R.string.app_name); //MyLibsTesting
        FunctionGlobalDir.initExternalDirectoryName(externalFolderName);

        if (checkPermissions()) {
            Toast.makeText(this, "Izin sudah diberikan", Toast.LENGTH_SHORT).show();
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Berikan izin untuk membuat folder terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
    }

    int MULTIPLE_PERMISSIONS = 1;

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // tambahkan ini
                onSuccessCheckPermitions();

            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }

    private void onSuccessCheckPermitions() {
        //   /storage/emulated/0/MyLibsTesting/folder1
        //   /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        //   /storage/emulated/0/MyLibsTesting/folder2
        String[] folders = new String[]{"/folder1", "/folder1/folder1_1", "/folder2"};
        if (FunctionGlobalDir.initFolder(folders)) {
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();

            onFunctionGlobalFileActionCreateFile();
            onFunctionGlobalZipActionDecodeBase64();
            onFunctionGlobalFileActionCamera();
            onFunctionGlobalFileActionGalery();
            onFunctionGlobalFileActionDownloadGambar();
        } else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    private void onFunctionGlobalFileActionCreateFile() {
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating", "File Created"};

        String fileName="/MyFile.txt";
        String saveTo="/";
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        if (FunctionGlobalFile.initFile(fileName,saveTo, data)) {
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();

            onFunctionGlobalFileActionReadFile();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }

    private void onFunctionGlobalFileActionReadFile() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        List<String> list = FunctionGlobalFile.readFile(path);
        String value_0 = list.get(0);
        Toast.makeText(this, "Jumlah baris : " + list.size() + " , " + value_0, Toast.LENGTH_SHORT).show();

        onFunctionGlobalFileActionAppentText();
    }

    private void onFunctionGlobalFileActionAppentText() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        if (FunctionGlobalDir.isFileExists(path)) {
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1", "Pesan ini akan ditambahkan ke file di line baru 2"};
            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if (FunctionGlobalFile.appentText(path, messages)) {
                Toast.makeText(this, "Line baru ditambah ke file", Toast.LENGTH_SHORT).show();

                List<String> list = FunctionGlobalFile.readFile(path);

                Toast.makeText(this, "Jumlah baris setelah ditambahkan: " + list.size(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ada error ketika add pesan", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    private void onFunctionGlobalZipActionDecodeBase64() {
        //   /storage/emulated/0/MyLibsTesting/ExternalBase64Md5ToZip.zip
        String fileName = "/ExternalBase64Md5ToZip.zip";

        //dari file zip diubah jadi base64
        //https://base64.guru/converter/encode/file
        String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

        //dari file zip diubah jadi md5
        //https://emn178.github.io/online-tools/md5_checksum.html
        String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";

        //   /storage/emulated/0/MyLibsTesting/zipLocation
        String zipLocation = "/zipLocation";
        //atau
        //   /storage/emulated/0/MyLibsTesting/
        //String zipLocation = "/"; // jika tidak mau diletakan dalam folder

        //decode string menjadi file dan extrack ke tujuan zipLocation
        //   /storage/emulated/0/MyLibsTesting/zipLocation
        if (FunctionGlobalZip.initFileFromStringToZipToFile(fileName, zipLocation, base64EncodeFromFile, md5EncodeFromFile, true)) {
            Toast.makeText(this, "Success load data", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal load data", Toast.LENGTH_SHORT).show();
        }
    }

    //1
    static final int REQUEST_TAKE_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    private void onFunctionGlobalFileActionCamera() {
        //2
        btnCamera = findViewById(R.id.btn_camera);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    //3
    //jalankan intent untuk membuka kamera
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                photoFile = FunctionGlobalFile.createImageFile(getApplicationContext(), fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", photoFile);

                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    //setelah foto diambil, dan tampil di preview maka akan lansung disimpan ke folder yang di sudah diset sebelumnya
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
                Toast.makeText(this, "Image Path : " + mPhotoFile.toString(), Toast.LENGTH_SHORT).show();
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(FunctionGlobalFile.getRealPathFromUri(getApplicationContext(), selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Glide.with(MainActivity.this).load(mPhotoFile).into(imageView);
            }
        }
    }

    static final int REQUEST_GALLERY_PHOTO = 3;
    Button btnGalery;

    private void onFunctionGlobalFileActionGalery() {
        btnGalery = findViewById(R.id.btn_galery);

        imageView = findViewById(R.id.img);

        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    private void onFunctionGlobalFileActionDownloadGambar() {
        imageView = findViewById(R.id.img);

        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        String saveTo = "/Foto_Download"; //   /storage/emulated/0/MyLibsTesting/Foto_Download
        //String saveTo = "/"; //   /storage/emulated/0/MyLibsTesting/     //Jika tidak mau diletakan dalam folder
        String fileName = "file name.jpg";

        // jika file name ada di akhir link seperti dibawah, maka kamu bsa gunakan cara seperti ini
        // imgUrl = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg";
        // String fileName = imgUrl.substring(url.lastIndexOf('/') + 1, url.length());

        //pilih 1 atau 2
        //1. jika isNew true maka file lama akan dihapus dan diganti dengan yang baru.
        FunctionGlobalFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, true);
        //2. jika isNew false maka akan otomatis load file dan disimpan, tapi jika file belum ada, maka akan tetap didownload.
        FunctionGlobalFile.initFileImageFromInternet(imgUrl, saveTo, fileName, imageView, false);
    }
}