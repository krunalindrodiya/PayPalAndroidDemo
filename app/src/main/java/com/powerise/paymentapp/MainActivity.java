package com.powerise.paymentapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.payment.paypal.PayPal;
import com.payment.paypal.PayPalConfig;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    private Button submit;
    private EditText edtAmount;

    private PayPal payPal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit = (Button) findViewById(R.id.submit);
        edtAmount = (EditText) findViewById(R.id.input_amount);

        payPal = new PayPal(this);

        payPal.startPayPalService();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    if (payPal != null) {
                        String strAmount = edtAmount.getText().toString();
                        BigDecimal amount = new BigDecimal(strAmount);
                        payPal.makePayment(amount, "USD", "Recharge-" + strAmount);
                    }
                }
            }
        });

    }


    private boolean validation() {
        boolean flag = true;
        String amount = edtAmount.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            flag = false;
        } else {
            Snackbar.make(edtAmount, "Enter amount.", Snackbar.LENGTH_LONG).show();
        }
        return flag;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (payPal != null) {
            payPal.stopPayPalService();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PayPalConfig.PAYPAL_REQUEST_CODE) {
            payPal.onActivityResult(resultCode, data);
        }
    }

}
