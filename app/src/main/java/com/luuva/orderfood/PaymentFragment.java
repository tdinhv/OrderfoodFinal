package com.luuva.orderfood;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.luuva.adapter.PaymentAdapter;
import com.luuva.model.Payment;

import java.util.ArrayList;

public class PaymentFragment extends AppCompatActivity {
    ListView lvItem_pay;
    ArrayList<Payment> dsPayment;
    PaymentAdapter payAdapter;
    TextView ttB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_payment);

        addControl();
    }
    private void addControl() {
        lvItem_pay = findViewById(R.id.lvItem_pay);
        dsPayment = new ArrayList<>();

        dsPayment.add(new Payment("Ví điện tử AirPay", R.drawable.pay1));
        dsPayment.add(new Payment("PayNow Credits", R.drawable.pay2));
        dsPayment.add(new Payment("Credit VISA/MASTER/JCB", R.drawable.pay31));
        dsPayment.add(new Payment("Tiền mặt", R.drawable.pay41));
        dsPayment.add(new Payment("Internet Banking", R.drawable.icon_5));

        payAdapter = new PaymentAdapter(PaymentFragment.this, R.layout.payment_item,dsPayment);
        lvItem_pay.setAdapter(payAdapter);
        ttB = findViewById(R.id.Back);
        ttB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}