package com.kansa.cjs.kansa;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by cjs on 2017/2/3.
 */

public class KanShaActivity  extends AppCompatActivity{
    private ImageButton btn_find;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kansha_layout);
        initview();
    }

    private void initview() {
        btn_find= (ImageButton) findViewById(R.id.Btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(KanShaActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}
