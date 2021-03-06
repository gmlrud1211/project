package com.example.db_14.travelplanner.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.db_14.travelplanner.R;

/**
 * Created by db-14 on 2017. 6. 16..
 */

public class CustomDialog extends Dialog {

    Button btn_1;
    Button btn_2;
    TextView txt_dialog_title;
    String title;

    View.OnClickListener btn1listener;
    View.OnClickListener btn2listener;
    public CustomDialog(Context context, View.OnClickListener btn1listener, View.OnClickListener btn2listener, String title) {
        super(context);
        this.btn1listener = btn1listener;
        this.btn2listener = btn2listener;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        txt_dialog_title = (TextView)findViewById(R.id.txt_dialog_title);
        btn_1 = (Button)findViewById(R.id.btn_viewinfo);
        btn_2 = (Button)findViewById(R.id.btn_remove);
        btn_1.setOnClickListener(btn1listener);
        btn_2.setOnClickListener(btn2listener);
        txt_dialog_title.setText(title);

        if(title.equals("여행지 정보"))
        {
            btn_1.setText("상세 정보");
            btn_2.setText("일정 삭제");
        }
        else if(title.equals("최단 경로 탐색"))
        {
            btn_1.setText("자동차");
            btn_2.setText("대중교통");
        }
    }

}
