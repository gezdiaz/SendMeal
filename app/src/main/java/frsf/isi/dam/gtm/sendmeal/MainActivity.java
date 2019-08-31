package frsf.isi.dam.gtm.sendmeal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    Button registerBtn;
    EditText nameEdit, passwordEdit, repeatPasswordEdit, emailEdit, cardNumberEdit, ccvEdit, dateEdit, cbuAliasEdit, cbuNumberEdit;
    TextView showAmountView;
    RadioGroup accountRadioGroup;
    SeekBar initialCreditSlider;
    ToggleButton sendNotfBtn;
    Switch isSellerSw;
    CheckBox acceptTermsCheck;
    LinearLayout layoutAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = findViewById(R.id.registerBtn);
        nameEdit = findViewById(R.id.nameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        repeatPasswordEdit = findViewById(R.id.repeatPasswordEdit);
        emailEdit = findViewById(R.id.emailEdit);
        cardNumberEdit = findViewById(R.id.cardNumberEdit);
        ccvEdit = findViewById(R.id.ccvEdit);
        dateEdit = findViewById(R.id.dateEdit);
        showAmountView = findViewById(R.id.showAmount);
        accountRadioGroup = findViewById(R.id.accountRadioGroup);
        initialCreditSlider = findViewById(R.id.initialCreditSlider);
        sendNotfBtn = findViewById(R.id.sendNotifBtn);
        isSellerSw = findViewById(R.id.isSellerSw);
        acceptTermsCheck = findViewById(R.id.acceptTermsCheck);
        cbuAliasEdit = findViewById(R.id.CBUAliasEdit);
        cbuNumberEdit = findViewById(R.id.CBUNumberEdit);
        layoutAccount = findViewById(R.id.layoutAccount);
        Boolean validations[] = new Boolean[9];


        nameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(nameEdit.getText().toString().isEmpty()){
                        nameEdit.setError(getString(R.string.errorEmptyField));
                        //no válido
                    }else{
                        //Válido
                    }
                }
            }
        });

        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(passwordEdit.getText().toString().isEmpty()){
                        passwordEdit.setError(getString(R.string.errorEmptyPassword));
                    }
                }
            }
        });
        repeatPasswordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(!repeatPasswordEdit.getText().toString().equals(passwordEdit.getText().toString())){
                        repeatPasswordEdit.setError(getString(R.string.errorRepeatPassword));
                    }
                }
            }
        });
        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(emailEdit.getText().toString().isEmpty()){
                        emailEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        cardNumberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(cardNumberEdit.getText().toString().isEmpty()){
                        cardNumberEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        ccvEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(ccvEdit.getText().toString().isEmpty()){
                        ccvEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        dateEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(dateEdit.getText().toString().isEmpty()){
                        dateEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });

        initialCreditSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                showAmountView.setText("" + (100+progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(emailEdit.getText().toString().isEmpty()){
                        emailEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        isSellerSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChk) {
                if(isChk){
                    layoutAccount.setVisibility(View.VISIBLE);
                }else{
                    layoutAccount.setVisibility(View.GONE);
                }
            }
        });
        cbuAliasEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(cbuAliasEdit.getText().toString().isEmpty()){
                        cbuAliasEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        cbuNumberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(!hasFocus){
                    if(cbuNumberEdit.getText().toString().isEmpty()){
                        cbuNumberEdit.setError(getString(R.string.errorEmptyField));
                    }
                }
            }
        });
        acceptTermsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChk) {
                if(isChk){
                    registerBtn.setEnabled(true);
                }else{
                    registerBtn.setEnabled(false);
                }
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}
