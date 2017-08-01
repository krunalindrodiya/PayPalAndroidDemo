package com.talkupon.payment.paypal;

import com.paypal.android.sdk.payments.PayPalConfiguration;

/**
 * Created by Krunal on 01-08-2017.
 */

public class PayPalConfig {
    public static final String PAYPAL_CLIENT_ID = "Past your application client id.";


    //Paypal intent request code to track onActivityResult method
    public static final int PAYPAL_REQUEST_CODE = 101;

    //Paypal Configuration Object
    public static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PayPalConfig.PAYPAL_CLIENT_ID);

}
