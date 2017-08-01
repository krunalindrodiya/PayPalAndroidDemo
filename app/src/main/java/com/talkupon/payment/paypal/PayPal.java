package com.talkupon.payment.paypal;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.powerise.paymentapp.PaymentStatusActivity;

import org.json.JSONException;

import java.math.BigDecimal;

/**
 * Created by Krunal on 01-08-2017.
 */

public class PayPal {

    private Activity activity;
    private BigDecimal amount;

    public PayPal(Activity activity) {
        this.activity = activity;
    }

    public void onActivityResult(int resultCode, Intent data) {
        //If the result is OK i.e. user has not canceled the payment
        if (resultCode == Activity.RESULT_OK) {
            //Getting the payment confirmation
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

            //if confirmation is not null
            if (confirm != null) {
                try {
                    //Getting the payment details
                    String paymentDetails = confirm.toJSONObject().toString(4);
                    Log.i("paymentExample", paymentDetails);

                    //Starting a new activity for the payment details and also putting the payment details with intent
                    Intent intent = new Intent(activity, PaymentStatusActivity.class);
                    intent.putExtra("PaymentDetails", paymentDetails);
                    intent.putExtra("PaymentAmount", amount.toString());
                    activity.startActivity(intent);

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    public void stopPayPalService() {
        if (activity != null) {
            activity.stopService(new Intent(activity, PayPalService.class));
        }
    }

    public void makePayment(BigDecimal amount, String currency, String message) {

        if (activity != null) {
            this.amount = amount;
            //Creating a paypalpayment
            PayPalPayment payment = new PayPalPayment(amount, currency, message,
                    PayPalPayment.PAYMENT_INTENT_SALE);

            //Creating Paypal Payment activity intent
            Intent intent = new Intent(activity, PaymentActivity.class);

            //putting the paypal configuration to the intent
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfig.config);

            //Puting paypal payment to the intent
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

            //Starting the intent activity for result
            //the request code will be used on the method onActivityResult
            activity.startActivityForResult(intent, PayPalConfig.PAYPAL_REQUEST_CODE);
        }
    }

    public void startPayPalService() {
        if (activity != null) {
            Intent intent = new Intent(activity, PayPalService.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfig.config);
            activity.startService(intent);
        }
    }

}
