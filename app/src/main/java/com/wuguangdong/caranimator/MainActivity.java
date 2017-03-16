package com.wuguangdong.caranimator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private CartAnimLayout cartAnimLayout;
    private Button button;
    private ImageView cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cartAnimLayout = (CartAnimLayout) findViewById(R.id.cartAnimLayout);
        button = (Button) findViewById(R.id.add);
        cart = (ImageView) findViewById(R.id.cart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartAnimLayout.startCartAnim(button,cart,R.layout.move_view);
            }
        });
    }
}
