package com.iterationtechnology.jalarambook.network;

import com.iterationtechnology.jalarambook.model.AuthorList;
import com.iterationtechnology.jalarambook.model.BestSellingList;
import com.iterationtechnology.jalarambook.model.CartList;
import com.iterationtechnology.jalarambook.model.CartTotal;
import com.iterationtechnology.jalarambook.model.CategoryList;
import com.iterationtechnology.jalarambook.model.CouponMessage;
import com.iterationtechnology.jalarambook.model.Customer;
import com.iterationtechnology.jalarambook.model.Login;
import com.iterationtechnology.jalarambook.model.Message;
import com.iterationtechnology.jalarambook.model.OrderList;
import com.iterationtechnology.jalarambook.model.ProductImgList;
import com.iterationtechnology.jalarambook.model.ProductList;
import com.iterationtechnology.jalarambook.model.ProductSizeList;
import com.iterationtechnology.jalarambook.model.SendOtp;
import com.iterationtechnology.jalarambook.model.SliderList;
import com.iterationtechnology.jalarambook.model.WishlistList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetProductDataService {

    @GET("json_android/slider.php")
    Call<SliderList> getSliderData();

    @GET("json_android/category.php")
    Call<CategoryList> getCategoryData();

    @GET("json_android/author.php")
    Call<AuthorList> getAuthorData();

    @GET("json_android/bestselling.php")
    Call<BestSellingList> getBestSellingData();

    @FormUrlEncoded
    @POST("json_android/product_img_view.php")
    Call<ProductImgList> getProductImgListData(@Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/product_size.php")
    Call<ProductSizeList> getProductSizeListData(@Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/insertcustomers.php")
    Call<Message> getCustomerListData(@Field("firstname") String firstname,
                                      @Field("lastname") String lastname,
                                      @Field("email") String email,
                                      @Field("contact") String contact,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("json_android/one_pro_wishlist.php")
    Call<Message> getOneProductWishlistListData(@Field("customer_id") String customer_id,
                                                @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/deletewishlist.php")
    Call<Message> getDeleteWishlistData(@Field("customer_id") String customer_id,
                                        @Field("pro_id") String pro_id);

    @FormUrlEncoded
    @POST("json_android/insertwishlist.php")
    Call<Message> getInsertWishlistData(@Field("customer_id") String customer_id,
                                        @Field("pro_id") String pro_id,
                                        @Field("wishlist_size_name") String wishlist_size_name);

    @FormUrlEncoded
    @POST("json_android/insertcart.php")
    Call<Message> getInsertCartData(@Field("customer_id") String customer_id,
                                    @Field("pro_id") String pro_id,
                                    @Field("pro_quantity") String pro_quantity,
                                    @Field("pro_price") String pro_price,
                                    @Field("cart_size_name") String cart_size_name);

    @FormUrlEncoded
    @POST("json_android/insert_recent_view_prop.php")
    Call<Message> getInsertRecentViewPropData(@Field("recent_pro_id") String recent_pro_id,
                                              @Field("ip_add") String ip_add);

    @FormUrlEncoded
    @POST("json_android/recentview.php")
    Call<ProductList> getRecentViewListData(@Field("ip_add") String ip_add);

    @FormUrlEncoded
    @POST("json_android/login.php")
    Call<Login> getLoginData(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("json_android/customer.php")
    Call<Customer> getCustomerData(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("json_android/editcustomer.php")
    Call<Message> getEditCustomerData(@Field("customer_id") String customer_id,
                                      @Field("firstname") String firstname,
                                      @Field("lastname") String lastname,
                                      @Field("email") String email,
                                      @Field("contact") String contact,
                                      @Field("address") String address,
                                      @Field("city") String city,
                                      @Field("state") String state,
                                      @Field("country") String country,
                                      @Field("zipcode") String zipcode);

    @FormUrlEncoded
    @POST("json_android/cart.php")
    Call<CartList> getCartData(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("json_android/updatecart.php")
    Call<Message> getUpdateCartData(@Field("pro_id") String pro_id,
                                    @Field("customer_id") String customer_id,
                                    @Field("cart_pro_quantity") String cart_pro_quantity);

    @FormUrlEncoded
    @POST("json_android/cart_total.php")
    Call<CartTotal> getCartTotalData(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("json_android/deletecart.php")
    Call<Message> getDeleteCartData(@Field("pro_id") String pro_id,
                                    @Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("json_android/insertshipping.php")
    Call<Message> getInsertShippingData(@Field("c_id_shipping") String c_id_shipping,
                                        @Field("shipping_address") String shipping_address);

    @FormUrlEncoded
    @POST("json_android/insertorder.php")
    Call<Message> getInsertOrderData(@Field("customer_id") String customer_id,
                                     @Field("user_email") String user_email,
                                     @Field("pro_id") String pro_id,
                                     @Field("pro_quantity") String pro_quantity,
                                     @Field("shipping_method") String shipping_method,
                                     @Field("payment_method") String payment_method,
                                     @Field("order_size") String order_size,
                                     @Field("order_price") String order_price,
                                     @Field("order_total") String order_total,
                                     @Field("coupon_code") String coupon_code,
                                     @Field("coupon_discount") String coupon_discount,
                                     @Field("cod_charge") String cod_charge,
                                     @Field("total") String total);

    @FormUrlEncoded
    @POST("json_android/deleteorder.php")
    Call<Message> getDeleteOrderData(@Field("invoice_no") String invoice_no);

    @FormUrlEncoded
    @POST("json_android/wishlist.php")
    Call<WishlistList> getWishlistData(@Field("customer_id") String customer_id);

    @FormUrlEncoded
    @POST("json_android/orderlist.php")
    Call<OrderList> getOrderListData(@Field("customer_id") String customer_id);

    /*@FormUrlEncoded
    @POST("json_android/product.php")
    Call<ProductList> getProductListData(@Field("cate_id") String cate_id,
                                         @Field("author_id") String author_id,
                                         @Field("min_price") String min_price,
                                         @Field("max_price") String max_price);*/

    @FormUrlEncoded
    @POST("json_android/searchproduct.php")
    Call<ProductList> getProductListData(@Field("pro_name") String pro_name,
                                         @Field("cate_id") String cate_id,
                                         @Field("author_id") String author_id,
                                         @Field("min_price") String min_price,
                                         @Field("max_price") String max_price);

    @FormUrlEncoded
    @POST("json_android/similarproduct.php")
    Call<ProductList> getSimilarProductListData(@Field("cate_id") String cate_id,
                                                @Field("pro_id") String author_id);

    @FormUrlEncoded
    @POST("json_android/changepassword.php")
    Call<Message> getChangePasswordData(@Field("user_id") String user_id,
                                        @Field("email") String email,
                                        @Field("contact") String contact,
                                        @Field("old_password") String old_password,
                                        @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("json_android/sendotp.php")
    Call<SendOtp> getSendOtpData(@Field("email") String email);

    @FormUrlEncoded
    @POST("json_android/resetpassword.php")
    Call<Message> getResetPasswordData(@Field("email") String email,
                                       @Field("password") String password);

    @FormUrlEncoded
    @POST("json_android/sendmobileotp.php")
    Call<SendOtp> getMobileSendOtpData(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("json_android/insertrating.php")
    Call<Message> getInsertRatingData(@Field("rating_customer_id") String rating_customer_id,
                                      @Field("rating_pro_id") String rating_pro_id,
                                      @Field("rating") String rating,
                                      @Field("review") String review);

    @FormUrlEncoded
    @POST("json_android/pincodecheck.php")
    Call<Message> getPincodeCheckData(@Field("pincode_no") String pincode_no);

    @FormUrlEncoded
    @POST("json_android/couponcode.php")
    Call<CouponMessage> getCouponcodeCheckData(@Field("promo_code") String promo_code);

}
