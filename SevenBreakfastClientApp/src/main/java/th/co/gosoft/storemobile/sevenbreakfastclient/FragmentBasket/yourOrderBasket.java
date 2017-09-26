package th.co.gosoft.storemobile.sevenbreakfastclient.FragmentBasket;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import th.co.gosoft.android.framework.lib.utils.AFLogUtils;
import th.co.gosoft.android.framework.lib.zxing.BarcodeFormat;
import th.co.gosoft.android.framework.lib.zxing.WriterException;
import th.co.gosoft.android.framework.lib.zxing.common.BitMatrix;
import th.co.gosoft.android.framework.lib.zxing.qrcode.QRCodeWriter;
import th.co.gosoft.storemobile.sevenbreakfastclient.Connect_webservice;
import th.co.gosoft.storemobile.sevenbreakfastclient.MainActivity;
import th.co.gosoft.storemobile.sevenbreakfastclient.R;
import th.co.gosoft.storemobile.sevenbreakfastclient.data.bean.CustomerBasket;
import th.co.gosoft.storemobile.sevenbreakfastclient.model.ModelData;

/**
 * A simple {@link Fragment} subclass.
 */
public class yourOrderBasket extends Fragment {
    List<ModelData> DataRow = new ArrayList<>();
    public yourOrderBasket() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.done,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toolbar toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Youe Order");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        final CustomerBasket resOrder = (CustomerBasket) msg.obj;
                        if (resOrder != null) {
                            AFLogUtils.i("Return Code Basket : " + resOrder.getReturnCode() + ", Count : " );
                            if (resOrder.getBasketLists() != null && resOrder.getBasketLists().size() > 0) {
                                ModelData name;
                                for (CustomerBasket.BasketListModel item : resOrder.getBasketLists()) {
                                    AFLogUtils.i("Title : " + item.getTitle() + ", Body : " + item.getTitle() + ", Icon88 : " + item.getValue());
                                    name = new ModelData();
                                    name.setProductId(item.getProductId());
                                    DataRow.add(name);
                                }
                            }
                        }
                        break;
                }
            }
        };
        new Connect_webservice().Basket(handler);

        for (int i=0;i<DataRow.size();i++){
            new Connect_webservice().EditBasket(handler,DataRow.get(i).getProductId(),0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_button:
                Toast.makeText(getContext(),"Success",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_your_order_basket, container, false);
        TextView order = (TextView)view.findViewById(R.id.order_no);
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView store = (TextView)view.findViewById(R.id.Store);
        ImageView image = (ImageView)view.findViewById(R.id.qrcode);
        ReviewOrderBasket res = new ReviewOrderBasket();
        order.setText(res.receipt);
        store.setText(res.getStore());
        generateQRcode(res.receipt,image);
        return view;
    }

    private void generateQRcode(String text,ImageView image){
        int width = 400;
        int height = 400;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE,width,height);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            System.out.println(text.toString());
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bitmap.setPixel(i, j, bitMatrix.get(i, j) ? Color.BLACK: Color.WHITE);
                }
            }
            image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
    }


