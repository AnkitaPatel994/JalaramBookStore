package com.iterationtechnology.jalarambook.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iterationtechnology.jalarambook.R;
import com.iterationtechnology.jalarambook.model.Message;
import com.iterationtechnology.jalarambook.network.GetProductDataService;
import com.iterationtechnology.jalarambook.network.RetrofitInstance;
import com.iterationtechnology.jalarambook.network.SessionManager;
import com.iterationtechnology.jalarambook.payment.WebViewActivity;
import com.iterationtechnology.jalarambook.utility.AvenuesParams;
import com.iterationtechnology.jalarambook.utility.Constants;
import com.iterationtechnology.jalarambook.utility.ServiceUtility;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {

    ArrayList<String> OrderProIdArray = new ArrayList<>();
    ArrayList<String> OrderProQtyArray = new ArrayList<>();
    ArrayList<String> OrderProSizeArray = new ArrayList<>();
    ArrayList<String> OrderProPriceArray = new ArrayList<>();
    String user_id,TotalCartPrice,ShippingPrice,coupon_code,discount_rate,rs,PaymentMethod,user_email;
    TextView txtConPrice,txtConShippingPrice,txtConShippingCouponPrice,txtConTotalAmount,txtChange;
    RadioGroup rgPaymentMethod;
    RadioButton rbOnlinePayment;/*rbCashonDelivery,rbCreditCard,rbDebitCard;*/
    Button btnContinuesOrder;
    GetProductDataService productDataService;
    SessionManager session;
    int change,amount;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");

        session = new SessionManager(OrderActivity.this);

        productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
        rs = getResources().getString(R.string.RS);

        HashMap<String,String> user = session.getUserDetails();
        user_email = user.get(SessionManager.user_email);

        user_id = getIntent().getExtras().getString("user_id");
        TotalCartPrice = getIntent().getExtras().getString("TotalCartPrice");
        ShippingPrice = getIntent().getExtras().getString("ShippingPrice");
        discount_rate = getIntent().getExtras().getString("discount_rate");
        coupon_code = getIntent().getExtras().getString("coupon_code");
        OrderProIdArray = getIntent().getExtras().getStringArrayList("OrderProIdArray");
        OrderProQtyArray = getIntent().getExtras().getStringArrayList("OrderProQtyArray");
        OrderProSizeArray = getIntent().getExtras().getStringArrayList("OrderProSizeArray");
        OrderProPriceArray = getIntent().getExtras().getStringArrayList("OrderProPriceArray");

        Log.d("discount_rate",""+discount_rate);

        txtChange = (TextView)findViewById(R.id.txtChange);
        txtConPrice = (TextView)findViewById(R.id.txtConPrice);
        txtConShippingPrice = (TextView)findViewById(R.id.txtConShippingPrice);
        txtConShippingCouponPrice = (TextView)findViewById(R.id.txtConShippingCouponPrice);
        txtConTotalAmount = (TextView)findViewById(R.id.txtConTotalAmount);
        final LinearLayout llChange = (LinearLayout) findViewById(R.id.llChange);
        LinearLayout llCouponcode = (LinearLayout) findViewById(R.id.llCouponcode);
        rgPaymentMethod = (RadioGroup)findViewById(R.id.rgPaymentMethod);
        //rbCashonDelivery = (RadioButton) findViewById(R.id.rbCashonDelivery);
        rbOnlinePayment = (RadioButton) findViewById(R.id.rbOnlinePayment);
        /*rbCreditCard = (RadioButton) findViewById(R.id.rbCreditCard);
        rbDebitCard = (RadioButton) findViewById(R.id.rbDebitCard);*/
        btnContinuesOrder = (Button) findViewById(R.id.btnContinuesOrder);

        if (discount_rate.equals("0"))
        {
            llCouponcode.setVisibility(View.GONE);
        }
        else
        {
            llCouponcode.setVisibility(View.VISIBLE);
        }

        txtConPrice.setText(TotalCartPrice);
        txtConShippingPrice.setText(ShippingPrice);
        txtConShippingCouponPrice.setText(discount_rate);

        PaymentMethod = "online";
        change = 0;
        txtChange.setText("0");
        amount = ((Integer.parseInt(TotalCartPrice)+Integer.parseInt(ShippingPrice))-Integer.parseInt(discount_rate))+change;
        txtConTotalAmount.setText(rs+amount);
        rgPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    /*case R.id.rbCashonDelivery:
                        PaymentMethod = "Cash on Delivery";
                        change = 100;
                        txtChange.setText("100");
                        amount = ((Integer.parseInt(TotalCartPrice)+Integer.parseInt(ShippingPrice))-Integer.parseInt(discount_rate))+change;
                        txtConTotalAmount.setText(rs+amount);
                        llChange.setVisibility(View.VISIBLE);
                        break;*/
                    case R.id.rbOnlinePayment:
                        PaymentMethod = "online";
                        change = 0;
                        amount = ((Integer.parseInt(TotalCartPrice)+Integer.parseInt(ShippingPrice))-Integer.parseInt(discount_rate))+change;
                        txtConTotalAmount.setText(rs+amount);
                        llChange.setVisibility(View.GONE);
                        break;
                    /*case R.id.rbCreditCard:
                        PaymentMethod = "Credit Card";
                        change = 0;
                        amount = ((Integer.parseInt(TotalCartPrice)+Integer.parseInt(ShippingPrice))-Integer.parseInt(discount_rate))+change;
                        txtConTotalAmount.setText(rs+amount);
                        llChange.setVisibility(View.GONE);
                        break;
                    case R.id.rbDebitCard:
                        PaymentMethod = "Debit Card";
                        change = 0;
                        amount = ((Integer.parseInt(TotalCartPrice)+Integer.parseInt(ShippingPrice))-Integer.parseInt(discount_rate))+change;
                        txtConTotalAmount.setText(rs+amount);
                        llChange.setVisibility(View.GONE);
                        break;*/

                }
            }
        });

        //txtConTotalAmount.setText(rs+amount);

        btnContinuesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Random random = new Random();
                String randomno = String.format("%09d", random.nextInt(1000000000));
                String invoice_no = "INV-"+randomno;*/

                //String pro_id1 = OrderProIdArray;

                /*for (int i = 0;i<OrderProIdArray.size();i++)
                {*/

                final String customer_id = user_id;
                String pro_id = "";
                for (String ss : OrderProIdArray)
                {
                    if(pro_id == ""){
                        pro_id += ss;
                    }else{
                        pro_id += "," + ss;
                    }
                }

                String pro_quantity = "";
                for (String ss : OrderProQtyArray)
                {
                    if(pro_quantity == ""){
                        pro_quantity += ss;
                    }else{
                        pro_quantity += "," + ss;
                    }
                }

                String shipping_method = ShippingPrice;
                String payment_method = PaymentMethod;
                String order_size = "";
                for (String ss : OrderProSizeArray)
                {
                    if(order_size == ""){
                        order_size += ss;
                    }else{
                        order_size += "," + ss;
                    }
                }

                String order_price = "";
                for (String ss : OrderProPriceArray)
                {
                    if(order_price == ""){
                        order_price += ss;
                    }else{
                        order_price += "," + ss;
                    }
                }
                String order_total = TotalCartPrice;

                String cod_charge = String.valueOf(change);
                Log.d("order_pro",""+pro_id+"--"+order_price);
                Log.d("order_pr", "" + pro_id + "===" + pro_quantity + "===" + order_size + "===" + order_price);

                String coupon_discount = discount_rate;
                String total = String.valueOf(amount);

                if (payment_method.equals("Cash on Delivery"))
                {
                    dialog.show();
                    InsertOrder(customer_id,user_email,pro_id,pro_quantity,shipping_method,payment_method,order_size,order_price,order_total,coupon_code,coupon_discount,cod_charge,total);
                }
                else
                {
                    Intent intent = new Intent(OrderActivity.this, WebViewActivity.class);
                    intent.putExtra(AvenuesParams.ACCESS_CODE, ServiceUtility.chkNull(Constants.ACCESS_CODE).toString().trim());
                    intent.putExtra(AvenuesParams.MERCHANT_ID, ServiceUtility.chkNull(Constants.MARCHANT_ID).toString().trim());
                    Integer randomNum = ServiceUtility.randInt(0, 9999999);
                    intent.putExtra(AvenuesParams.ORDER_ID, ServiceUtility.chkNull(randomNum + "").toString().trim());
                    intent.putExtra(AvenuesParams.CURRENCY, ServiceUtility.chkNull(Constants.CURRENCEY).toString().trim());
                    intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull(total).toString().trim());
                    //intent.putExtra(AvenuesParams.AMOUNT, ServiceUtility.chkNull("1").toString().trim());
                    intent.putExtra(AvenuesParams.REDIRECT_URL, ServiceUtility.chkNull(Constants.REDIRECT_URL).toString().trim());
                    intent.putExtra(AvenuesParams.CANCEL_URL, ServiceUtility.chkNull(Constants.CANCEL_URL).toString().trim());
                    intent.putExtra(AvenuesParams.RSA_KEY_URL, ServiceUtility.chkNull(Constants.RSA_KEY_URL).toString().trim());
                    startActivityForResult(intent, 1);

                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (resultCode == 1) {
            final String customer_id = user_id;
            String pro_id = "";
            for (String ss : OrderProIdArray) {
                if (pro_id == "") {
                    pro_id += ss;
                } else {
                    pro_id += "," + ss;
                }
            }

            String pro_quantity = "";
            for (String ss : OrderProQtyArray) {
                if (pro_quantity == "") {
                    pro_quantity += ss;
                } else {
                    pro_quantity += "," + ss;
                }
            }
            String shipping_method = ShippingPrice;
            String payment_method = PaymentMethod;
            String order_size = "";
            for (String ss : OrderProSizeArray) {
                if (order_size == "") {
                    order_size += ss;
                } else {
                    order_size += "," + ss;
                }
            }

            String order_price = "";
            for (String ss : OrderProPriceArray) {
                if (order_price == "") {
                    order_price += ss;
                } else {
                    order_price += "," + ss;
                }
            }
            String order_total = TotalCartPrice;
            String cod_charge = String.valueOf(change);

            Log.d("order_pr", "" + pro_id + "===" + pro_quantity + "===" + order_size + "===" + order_price);

            String coupon_discount = discount_rate;
            String total = String.valueOf(amount);

            dialog.show();
            InsertOrder(customer_id, user_email, pro_id, pro_quantity, shipping_method, payment_method, order_size, order_price, order_total, coupon_code, coupon_discount, cod_charge, total);
        }
    }

    private void InsertOrder(String customer_id, String user_email, String pro_id, String pro_quantity, String shipping_method, String payment_method, String order_size, String order_price, String order_total, String coupon_code, String coupon_discount, String cod_charge, String total) {
        Call<Message> InsertOrderCall = productDataService.getInsertOrderData(customer_id,user_email,pro_id,pro_quantity,shipping_method,payment_method,order_size,order_price,order_total,coupon_code,coupon_discount,cod_charge,total);
        InsertOrderCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (dialog.isShowing()) {
                    dialog.hide();
                }
                for (int i = 0;i<OrderProIdArray.size();i++)
                {
                    String pro_idd = OrderProIdArray.get(i);
                    Call<Message> DeleteCartCall = productDataService.getDeleteCartData(pro_idd,user_id);
                    DeleteCartCall.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            String status = response.body().getStatus();
                            String message = response.body().getMessage();
                            if (status.equals("1"))
                            {
                                Log.d("message","Item Delete"+message);
                            }
                            else
                            {
                                Log.d("message",""+message);
                            }
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(OrderActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                String message = response.body().getMessage();
                Log.d("message",""+message);
                Intent i = new Intent(OrderActivity.this, ConformOrderActivity.class);
                startActivity(i);

                /*i.putExtra("item","mulItem");
                i.putExtra("customer_id",customer_id);
                i.putExtra("ordersta","Your Order has been Placed Sucessfully");
                i.putExtra("OrderProIdArray",OrderProIdArray);*/
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                if (dialog.isShowing()) {
                    dialog.hide();
                }
                Toast.makeText(OrderActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }
}
