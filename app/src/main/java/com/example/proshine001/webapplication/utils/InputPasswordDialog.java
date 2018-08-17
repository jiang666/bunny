package com.example.proshine001.webapplication.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.proshine001.webapplication.R;

/**
 * 从bunnytouch 退出或切换时弹出的dialog
 */
public class InputPasswordDialog extends AlertDialog implements View.OnClickListener {
    private String title;
    private String textPrompt;
    private EditText etInputDialogMessage;
    private TextView tvTextprompt;
    public  OnInputDialogListener onInputDialogListener ;
    private Context context;
    private Button btCanncel;
    private Button btConfirm;

    public InputPasswordDialog(Context context, String title, String textPrompt, OnInputDialogListener onInputDialogListener) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_input_password, null);
        this.setView(view);
        etInputDialogMessage = (EditText) view.findViewById(R.id.et_password);
        tvTextprompt = (TextView) view.findViewById(R.id.tv_textprompt);
        btCanncel = (Button) view.findViewById(R.id.bt_cancel);
        btConfirm = (Button) view.findViewById(R.id.bt_confirm);
        btCanncel.setOnClickListener(this);
        btConfirm.setOnClickListener(this);
        this.title = title;
        this.textPrompt = textPrompt;
        this.onInputDialogListener = onInputDialogListener;
    }

    public void showDialog(){

        this.setTitle(title);
        tvTextprompt.setText(textPrompt);
        this.setIcon(R.drawable.ic_launcher);

        this.show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_cancel:
                onInputDialogListener.onCancel();
                this.dismiss();
                break;
            case R.id.bt_confirm:
                String password = etInputDialogMessage.getText().toString().trim();
                onInputDialogListener.onConfirm(password);
                this.dismiss();
                break;
        }
    }


    public interface OnInputDialogListener{
        void onCancel();
        void onConfirm(String text);

    }
}
