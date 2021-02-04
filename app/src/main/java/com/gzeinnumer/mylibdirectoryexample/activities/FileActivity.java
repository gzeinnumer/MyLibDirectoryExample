package com.gzeinnumer.mylibdirectoryexample.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gzeinnumer.gzndirectory.helper.FGDir;
import com.gzeinnumer.gzndirectory.helper.FGFile;
import com.gzeinnumer.mylibdirectoryexample.databinding.ActivityFileBinding;

import java.util.List;

public class FileActivity extends AppCompatActivity {
    private ActivityFileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCreateFileAction();
            }
        });
        binding.btnIsFileExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIsFileExistsAction();
            }
        });
        binding.btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteFileAction();
            }
        });
        binding.btnDeleteAllFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteAllFileAction();
            }
        });
        binding.btnReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReadFileAction();
            }
        });
        binding.btnAppenttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAppenttextAction();
            }
        });
    }

    private void btnCreateFileAction() {
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating", "File Created"};

        //buat file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if (FGFile.initFile(fileName, saveTo, data)) {
            Toast.makeText(this, "File berhasil dibuat", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File gagal dibuat", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnIsFileExistsAction() {
        boolean isExists = FGFile.isFileExists("/MyFile.txt");
    }

    private void btnDeleteFileAction() {
        boolean isDeleted = FGFile.deleteDir("/MyFile.txt");
    }

    private void btnDeleteAllFileAction() {
        FGFile.deleteAllFile("/folder1");
    }

    private void btnReadFileAction() {
        boolean isExists = FGFile.isFileExists("/MyFile.txt");
        if (isExists) {
            // /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FGFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "Jumlah baris : " + list.size(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File MyText.txt not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnAppenttextAction() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        if (FGDir.isFileExists(path)) {
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1", "Pesan ini akan ditambahkan ke file di line baru 2"};
            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if (FGFile.appentText(path, messages)) {
                Toast.makeText(this, "Added new test", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File MyText.txt not found", Toast.LENGTH_SHORT).show();
        }
    }
}